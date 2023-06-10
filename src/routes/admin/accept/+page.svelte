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

    let registerStoreStatus = true;
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
                <TableBodyRow>
                    <TableBodyCell>1</TableBodyCell>
                    <TableBodyCell>testUser</TableBodyCell>
                    <TableBodyCell>
                        <p>딥치즈</p>
                        <p>딥치즈</p>
                    </TableBodyCell>
                    <TableBodyCell>5000원</TableBodyCell>
                    <TableBodyCell>
                        주문
                    </TableBodyCell>
                    <TableBodyCell>
                        <Button color="dark">주문 받기</Button>
                    </TableBodyCell>
                </TableBodyRow>
            </TableBody>
        </Table>
    </div>
{:else }
    <RegisterAlert/>
{/if}
