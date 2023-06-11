package com.spring.delivery.service;

import com.spring.delivery.domain.Address;
import com.spring.delivery.domain.Store;
import com.spring.delivery.repository.StoreRepository;
import com.spring.delivery.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    public void createStore(Long userId){
        Store store = new Store();
        store.setName("맘터 1호점");
        store.setAddress(new Address("구미시", "대학로", "111"));
        store.setPhoneNum("12345678");
        store.setOpenTime(9);
        store.setClosedTime(18);
        store.setUser(userRepository.findById(userId).get());
        storeRepository.save(store);
    }
}
