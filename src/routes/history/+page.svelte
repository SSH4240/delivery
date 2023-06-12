<script>
    import {
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
    })

    client.onConnect = function (frame) {
        console.log('소켓 연결 성공', frame);
        client.subscribe("/topic/orders", (data) => {
            const msg = JSON.parse(data.body);
            console.log(msg);
        });
    };


</script>

<div class="w-[80%] mx-auto">
    <h1 class="text-5xl mb-10 text-center">Order History</h1>

    <Table hoverable={true} class="w-[60%] mx-auto">
        <TableHead>
            <TableHeadCell>Order Number</TableHeadCell>
            <TableHeadCell>Product Names</TableHeadCell>
            <TableHeadCell>Total Price</TableHeadCell>
            <TableHeadCell>Status</TableHeadCell>
        </TableHead>
        <TableBody class="divide-y">
            <TableBodyRow>
                <TableBodyCell>딥치즈싸이버거</TableBodyCell>
                <TableBodyCell>
                    <p>딥치즈</p>
                    <p>딥치즈</p>
                </TableBodyCell>
                <TableBodyCell>5000원</TableBodyCell>
                <TableBodyCell>
                    진행중
                </TableBodyCell>
            </TableBodyRow>
        </TableBody>
    </Table>
</div>