package com.spring.delivery.service;

import com.spring.delivery.domain.*;
import com.spring.delivery.dto.OrderDTO;
import com.spring.delivery.exception.MinimumOrderAmountNotMetException;
import com.spring.delivery.exception.OrderCancellationNotAllowedException;
import com.spring.delivery.exception.OrderedWithNoMainMenuException;
import com.spring.delivery.exception.StoreClosedException;
import com.spring.delivery.repository.*;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @PostConstruct
    public void init() {
        executorService.scheduleAtFixedRate(this::checkUnacceptedOrders, 1, 1, TimeUnit.MINUTES);
    }

    private void checkUnacceptedOrders(){
        LocalDateTime currentTime = LocalDateTime.now();
        List<Order> orders = new ArrayList<>();
        if (orderRepository.findAllByStatus(OrderStatus.ORDER).isPresent())
            orders = orderRepository.findAllByStatus(OrderStatus.ORDER).get();

        for (Order order : orders){
            if (order.getOrderTime().plusMinutes(1).isBefore(currentTime))
                cancel(order.getId());
        }
    }

    public void create(OrderDTO orderDTO){
        if (orderDTO.getTotalPrice() < 6000)
            throw new MinimumOrderAmountNotMetException("최소 주문 금액 6000원을 넘어야 주문이 가능합니다.");

        Store store = storeRepository.findById(orderDTO.getStoreId()).get();
        int storeOpen = 1;  //store.getRunTime();
        int storeClosed = 10;   //  추후 store runtime 저장방식 정해지면 수정 필요
        if (LocalDateTime.now().getHour() < storeOpen || LocalDateTime.now().getHour() > storeClosed)
            throw new StoreClosedException("가게 운영 시간이 아닙니다.");

        boolean haveMainMenu = false;
        for (Long menuId : orderDTO.getMenus()){
            if (menuRepository.findById(menuId).get().getMenuType().equals(MenuType.MAIN))
                haveMainMenu = true;
        }
        if (!haveMainMenu)
            throw new OrderedWithNoMainMenuException("사이드 메뉴 만으로는 주문이 불가능합니다.");


        Order order = new Order();

        order.setUser(userRepository.findById(orderDTO.getUserId()).get());
        order.setStatus(OrderStatus.ORDER);
        order.setOrderTime(LocalDateTime.now());

        for (Long menuId : orderDTO.getMenus()){
            OrderItem item = new OrderItem();

            item.setMenu(menuRepository.findById(menuId).get());
            order.addOrderItem(item);
        }

        orderRepository.save(order);
    }

    public void cancel(Long orderId){
        Order order = orderRepository.findById(orderId).get();
        if (order.getStatus().equals(OrderStatus.DELIVERY))
            throw new OrderCancellationNotAllowedException("배달중인 주문은 취소가 불가능합니다.");
        else if (order.getStatus().equals(OrderStatus.COMPLETED))
            throw new OrderCancellationNotAllowedException("이미 배달이 완료된 주문은 취소가 불가능합니다.");
        else if (order.getStatus().equals(OrderStatus.CANCELLED))
            throw new OrderCancellationNotAllowedException("이미 취소된 주문은 취소가 불가능합니다.");

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    public List<Order> findAllOrdersByUserId(Long userId){
        if (orderRepository.findAllByUserId(userId).isPresent())
            return orderRepository.findAllByUserId(userId).get();
        return null;
    }
    public List<Order> findAllOrders(){
//        if (orderRepository.findAllByStatus(OrderStatus.ORDER).isPresent())
//            return orderRepository.findAllByStatus(OrderStatus.ORDER).get();
        return orderRepository.findAll();
    }

    public void acceptOrder(Long orderId){
        Order order = orderRepository.findById(orderId).get();
        if (!order.getStatus().equals(OrderStatus.ORDER))
            throw new RuntimeException("\'주문\'상태의 주문만 수락할 수 있습니다.");
        order.setStatus(OrderStatus.DELIVERY);
        orderRepository.save(order);
    }

    public void setOrderDelivered(Long orderId){
        Order order = orderRepository.findById(orderId).get();
        order.setStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);
    }
}
