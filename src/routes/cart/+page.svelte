<script>
    import {
        Button,
        Input,
        Table,
        TableBody,
        TableBodyCell,
        TableBodyRow,
        TableHead,
        TableHeadCell
    } from 'flowbite-svelte';
    import {preferences} from '../../store'
    import {client} from "../SockJs";
    import {browser} from "$app/environment";
    import axios from "axios";
    import {URL} from "../../env";
    import {onMount} from "svelte";
    // socket
    // startSocket();

    console.log($preferences);

    let subtotal = 0;
    const removeCartItem = (id) => {
        $preferences = $preferences.filter((item) => item.id != id);
        preferences.set($preferences);

        subtotal = 0;
        for (const item of $preferences) {
            if (item.discountPrice) {
                subtotal += item.discountPrice;
            }
        }
    }

    $: subtotal;

    for (const item of $preferences) {
        if (item.discountPrice) {
            subtotal += item.discountPrice;
        }
    }

    export let userId;
    onMount(() => {
        const TOKEN = sessionStorage.getItem('accessToken');
        // user 정보 GET
        axios.get(`${URL}/api/info/id`,
            {
                headers: {
                    Authorization: `Bearer ${TOKEN}`
                }
            }
        ).then(response => {
            // user = response.data.body.user;
            console.log(response.data);
            userId = response.data;
        })
    })

    onMount(() => {
        const TOKEN = sessionStorage.getItem('accessToken');
        axios.get(`${URL}/api/menu/statistic`,
            {
                headers: {
                    Authorization: `Bearer ${TOKEN}`
                }
            }
        ).then(response => {
            // user = response.data.body.user;
            console.log(response.data);
            userId = response.data;
        })
    })
    // 주문하기
    const createOrder = () => {
        let orderItemList = [];
        let totalPrice = 0;

        for (const item of $preferences) {
            if (item.id) {
                // 햄버거
                if (parseInt(item.justQuty) > 0) {
                    orderItemList = [...orderItemList, {
                        storeId: 1,
                        menuId: item.menuId,
                        quantity: parseInt(item.justQuty)
                    }];
                }
                // 감튀
                if (parseInt(item.cokeQuty) > 0) {
                    orderItemList = [...orderItemList, {storeId: 1, menuId: 1, quantity: parseInt(item.cokeQuty)}];
                }
                // 콜라
                if (parseInt(item.friesQuty) > 0) {
                    orderItemList = [...orderItemList, {storeId: 1, menuId: 2, quantity: parseInt(item.friesQuty)}];
                }
                totalPrice += item.discountPrice
            }
        }

        console.log(orderItemList);
        console.log(totalPrice);
        const payload = {
            userId: userId,
            orderItem: orderItemList,
            totalPrice: totalPrice,
            storeId: 1,
            currentHour: new Date().getHours(),
        };
        client.publish({destination: "/order/create", body: JSON.stringify(payload)});
        if (totalPrice < 6000) {
            alert("주문 총액이 6000원 미만일 경우 주문이 불가능 합니다.");
        } else if (browser) {
            alert("주문 생성 성공");
            preferences.set([{}]);
            window.location.href = "/history";
        }
    };
</script>

<div class="w-[80%] mx-auto">
    <h1 class="text-5xl mb-10 text-center">Your Cart</h1>

    <Table hoverable={true} class="w-[60%] mx-auto">
        <TableHead>
            <TableHeadCell>Product name</TableHeadCell>
            <TableHeadCell>Just Quantity</TableHeadCell>
            <TableHeadCell>Coke Quantity</TableHeadCell>
            <TableHeadCell>French Fries Quantity</TableHeadCell>
            <TableHeadCell>Price</TableHeadCell>
            <TableHeadCell>Discount Price</TableHeadCell>
            <TableHeadCell>Remove</TableHeadCell>
        </TableHead>
        <TableBody class="divide-y">
            {#each $preferences as item}
                {#if item.name}
                    <TableBodyRow>
                        <TableBodyCell>{item.name}</TableBodyCell>
                        <TableBodyCell class="w-[20%]">
                            <Input type="number" size="sm" readonly bind:value={item.justQuty}/>
                        </TableBodyCell>
                        <TableBodyCell class="w-[20%]">
                            <Input type="number" size="sm" readonly bind:value={item.cokeQuty}/>
                        </TableBodyCell>
                        <TableBodyCell class="w-[20%]">
                            <Input type="number" size="sm" readonly bind:value={item.friesQuty}/>
                        </TableBodyCell>
                        <TableBodyCell>{item.price}</TableBodyCell>
                        <TableBodyCell>{item.discountPrice}</TableBodyCell>
                        <TableBodyCell>
                            <button type="button" on:click={() => removeCartItem(item.id)}
                                    class="bg-white rounded-md p-2 inline-flex items-center justify-center text-gray-400 hover:text-gray-500 hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-inset focus:ring-indigo-500">
                                <span class="sr-only">Close menu</span>
                                <svg class="h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                                     stroke="currentColor" aria-hidden="true">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                          d="M6 18L18 6M6 6l12 12"/>
                                </svg>
                            </button>
                        </TableBodyCell>
                    </TableBodyRow>
                {/if}
            {/each}
        </TableBody>
    </Table>
    <div class="w-[60%] text-right mx-auto mb-[100px]">
        SUBTOTAL : {subtotal}원
    </div>

    <div class="w-[70%] mx-auto">
        <Button color="dark" class="w-full mb-3" on:click={createOrder}>
            <span class="mr-3">Start Your Order</span>
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                 stroke="currentColor" class="w-6 h-6">
                <path stroke-linecap="round" stroke-linejoin="round" d="M13.5 4.5L21 12m0 0l-7.5 7.5M21 12H3"/>
            </svg>
        </Button>
        <Button color="dark" class="w-full mb-3" href="/">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                 stroke="currentColor" class="w-6 h-6">
                <path stroke-linecap="round" stroke-linejoin="round" d="M9 15L3 9m0 0l6-6M3 9h12a6 6 0 010 12h-3"/>
            </svg>
            <span class="ml-3">Back To All Products</span>
        </Button>
    </div>

</div>