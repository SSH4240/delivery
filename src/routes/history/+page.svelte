<script>
    import {
        Table,
        TableBody,
        TableBodyCell,
        TableBodyRow,
        TableHead,
        TableHeadCell
    } from 'flowbite-svelte';
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