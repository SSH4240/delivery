<script>
    import RegisterAlert from "../../../components/RegisterAlert.svelte";
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
    import {URL} from "../../../env";
    import axios from "axios";
    import * as SockJs from "sockjs-client/dist/sockjs";
    import {Client} from "@stomp/stompjs";
    import {WebSocket} from "ws";
    import {browser} from "$app/environment";

    let registerStoreStatus = true;

    export let TOKEN;
    export let orderList = [];
    export let client;
    onMount(async () => {
        TOKEN = sessionStorage.getItem('accessToken');
        await axios.get(`${URL}/api/order/list`, {
            headers: {
                Authorization: `Bearer ${TOKEN}`
            }
        })
            .then(response => {
                // console.log(response.data);
                orderList = response.data;
            })
            .catch(error => console.log(error));


        for (const item of orderList) {
            const orderItem = item.orderItem;
            for (const tem of orderItem) {
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
            if (item.state === "취소") {
                item.buttonName = "";
            } else if (item.state === "주문") {
                item.buttonName = "접수 및 거절";
            } else if (item.state === "배달") {
                item.buttonName = "배달완료"
            } else if (item.state === "완료") {
                item.buttonName = "";
            }
        }
        orderList = orderList;
        console.log(orderList);


        TOKEN = sessionStorage.getItem('accessToken');
        client = new Client({
            brokerURL: "",
            debug: function (str) {
                console.log(str);
            },
            connectHeaders: {
                'Authorization': "Bearer " + `${TOKEN}`
            },
            reconnectDelay: 5000,
            heartbeatIncoming: 4000,
            heartbeatOutgoing: 4000
        });

        if (typeof WebSocket !== "function") {
            client.webSocketFactory = function () {
                // @ts-ignore
                return new SockJs(`${URL}/webSocket`);
            };
        }


        client.onConnect = function (frame) {
            console.log('소켓 연결 성공', frame);
            client.subscribe("/topic/orders", async (data) => {

                TOKEN = sessionStorage.getItem('accessToken');
                await axios.get(`${URL}/api/order/list`, {
                    headers: {
                        Authorization: `Bearer ${TOKEN}`
                    }
                })
                    .then(response => {
                        // console.log(response.data);
                        orderList = response.data;
                    })
                    .catch(error => console.log(error));


                for (const item of orderList) {
                    const orderItem = item.orderItem;
                    for (const tem of orderItem) {
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
                    if (item.state === "취소") {
                        item.buttonName = "";
                    } else if (item.state === "주문") {
                        item.buttonName = "접수 및 거절";
                    } else if (item.state === "배달") {
                        item.buttonName = "배달완료"
                    } else if (item.state === "완료") {
                        item.buttonName = "";
                    }
                }
                orderList = orderList;
            });
        };

        client.onStompError = function (frame) {
            console.log("Broker reported error: " + frame.headers["message"]);
            console.log("Additional details: " + frame.body);
        };
        client.activate();
    });

    const changeState = (i) => {
        if (orderList[i].state === "취소") {
            console.log(orderList[i]);
        } else if (orderList[i].state === "주문") {
            let sign = prompt("접수하시겠습니까 거절하시겠습니까");
            if (sign === "접수") {
                const payload = {
                    orderId: orderList[i].orderId,
                };
                client.publish({destination: "/order/accept", body: JSON.stringify(payload)});
                if (browser) {
                    alert("주문 접수 성공");
                    window.location.href = "/admin/accept"
                }
            } else if (sign === "거절") {
                const payload = {
                    orderId: orderList[i].orderId,
                };
                client.publish({destination: "/order/deny", body: JSON.stringify(payload)});
                if (browser) {
                    alert("주문 거절");
                    window.location.href = "/admin/accept";
                }
            }

        } else if (orderList[i].state === "배달") {
            const payload = {
                orderId: orderList[i].orderId,
            };
            client.publish({destination: "/order/complete", body: JSON.stringify(payload)});
            if (browser) {
                alert("배달 완료");
                window.location.href = "/admin/accept";
            }
        }
    }

</script>

{#if registerStoreStatus}
    <div class="w-[80%] mx-auto">
        <h1 class="text-5xl mb-10 text-center">Accept Order</h1>

        <Table hoverable={true} class="w-[60%] mx-auto">
            <TableHead>
                <TableHeadCell>Order Number</TableHeadCell>
                <TableHeadCell>Order User</TableHeadCell>
                <TableHeadCell>Order Menu</TableHeadCell>
                <TableHeadCell>Total Price</TableHeadCell>
                <TableHeadCell>Status</TableHeadCell>
                <TableHeadCell></TableHeadCell>
            </TableHead>
            <TableBody class="divide-y">
                {#each orderList as order, i}
                    <TableBodyRow>
                        <TableBodyCell>{order.orderId}</TableBodyCell>
                        <TableBodyCell>{order.userId}</TableBodyCell>
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
                            <Button color="dark" on:click={() => changeState(i)}>{order.buttonName}</Button>
                        </TableBodyCell>
                    </TableBodyRow>
                {/each}
            </TableBody>
        </Table>
    </div>
{:else }
    <RegisterAlert/>
{/if}
