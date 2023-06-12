<script>
    import {
        Button,
        Table,
        TableBody,
        TableBodyCell,
        TableBodyRow,
        TableHead,
        TableHeadCell
    } from 'flowbite-svelte';
    import {onMount} from "svelte";
    import {client} from "../SockJs";
    import axios from "axios";
    import {URL} from "../../env";
    import {browser} from "$app/environment";

    export let TOKEN;
    export let orderList = [];
    onMount(async () => {
        TOKEN = sessionStorage.getItem('accessToken');
        let userId;
        // 내아이디
        await axios.get(`${URL}/api/info/id`,
            {
                headers: {
                    Authorization: `Bearer ${TOKEN}`
                }
            }
        ).then(response => {
            // user = response.data.body.user;
            console.log(response.data);
            userId = response.data;
        });
        // 주문리스트요청
        await axios.get(`${URL}/api/customer/order/list`, {
            params: {
                userId: userId
            },
            headers: {
                Authorization: `Bearer ${TOKEN}`
            }
        })
            .then(response => {
                if (response.status === 200) {
                    orderList = response.data;
                    console.log(orderList);
                }
            })
            .catch(error => console.log(error));

        for (const item of orderList) {
            const orderItem = item.orderItem;
            for (const tem of orderItem) {
                console.log(TOKEN);
                await axios.get(`${URL}/api/menu/detail`, {
                    params: {
                        menuId: tem.menuId
                    },
                    headers: {
                        Authorization: `Bearer ${TOKEN}`
                    }
                }).then(response => {
                    tem.name = response.data.name;
                });
            }
            item.orderItem = orderItem;
        }
        orderList = orderList;
    })

    client.onConnect = function (frame) {
        console.log('소켓 연결 성공', frame);
        client.subscribe("/topic/orders", async (data) => {
            const TOKEN = sessionStorage.getItem('accessToken');
            let user;
            // user 정보 GET
            await axios.get(`${URL}/api/info`,
                {
                    headers: {
                        Authorization: `Bearer ${TOKEN}`
                    }
                }
            ).then(response => {
                // console.log(response.data.body);
                user = response.data.body.user;
            })
            const msg = JSON.parse(data.body);

            if(msg.userEmail === user.email){
                alert(msg.message)

                let userId;
                // 내아이디
                await axios.get(`${URL}/api/info/id`,
                    {
                        headers: {
                            Authorization: `Bearer ${TOKEN}`
                        }
                    }
                ).then(response => {
                    // user = response.data.body.user;
                    console.log(response.data);
                    userId = response.data;
                });
                // 다시 요청
                await axios.get(`${URL}/api/customer/order/list`, {
                    params: {
                        userId: userId
                    },
                    headers: {
                        Authorization: `Bearer ${TOKEN}`
                    }
                })
                    .then(response => {
                        if (response.status === 200) {
                            orderList = response.data;
                            console.log(orderList);
                        }
                    })
                    .catch(error => console.log(error));

                for (const item of orderList) {
                    const orderItem = item.orderItem;
                    for (const tem of orderItem) {
                        console.log(TOKEN);
                        await axios.get(`${URL}/api/menu/detail`, {
                            params: {
                                menuId: tem.menuId
                            },
                            headers: {
                                Authorization: `Bearer ${TOKEN}`
                            }
                        }).then(response => {
                            tem.name = response.data.name;
                        });
                    }
                    item.orderItem = orderItem;
                }
                orderList = orderList;
            }
            console.log(msg);
        });
    };


    const cancelOrder = (orderId) => {
        const payload = {
            orderId : orderId
        }
        client.publish({destination: "/order/cancel", body: JSON.stringify(payload)});
        // if (browser) {
        //     alert("주문 취소 성공");
        //     window.location.href = "/";
        // }
    }

</script>

<div class="w-[80%] mx-auto">
    <h1 class="text-5xl mb-10 text-center">Order History</h1>

    <Table hoverable={true} class="w-[60%] mx-auto">
        <TableHead>
            <TableHeadCell>Order Number</TableHeadCell>
            <TableHeadCell>Product Names</TableHeadCell>
            <TableHeadCell>Total Price</TableHeadCell>
            <TableHeadCell>Status</TableHeadCell>
            <TableHeadCell></TableHeadCell>
        </TableHead>
        {#each orderList as order}
            <TableBody class="divide-y">
                <TableBodyRow>
                    <TableBodyCell>{order.orderId}</TableBodyCell>
                    <TableBodyCell>
                        {#each order.orderItem as item}
                            <p>{item.name} X {item.quantity}</p>
                        {/each}
                    </TableBodyCell>
                    <TableBodyCell>{order.totalPrice}원</TableBodyCell>
                    <TableBodyCell>
                        {order.state}
                    </TableBodyCell>
                    <TableBodyCell>
                        <Button color="dark" on:click={() => cancelOrder(order.orderId)}>주문 취소</Button>
                    </TableBodyCell>
                </TableBodyRow>
            </TableBody>
        {/each}
    </Table>
</div>