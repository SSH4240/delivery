<script>
    import RegisterAlert from "../../../components/RegisterAlert.svelte";
    import {
        Table,
        TableBody,
        TableBodyCell,
        TableBodyRow,
        TableHead,
        TableHeadCell
    } from "flowbite-svelte";

    let registerStoreStatus = true;
</script>

{#if registerStoreStatus}
    <div class="w-[80%] mx-auto">
        <h1 class="text-5xl mb-10 text-center">Statistics</h1>

        <Table hoverable={true} class="w-[60%] mx-auto mb-10">
            <TableHead>
                <TableHeadCell>Menu Id</TableHeadCell>
                <TableHeadCell>Menu Name</TableHeadCell>
                <TableHeadCell>Price</TableHeadCell>
                <TableHeadCell>Sales Quantity</TableHeadCell>
            </TableHead>
            <TableBody class="divide-y">
                <TableBodyRow>
                    <TableBodyCell>1</TableBodyCell>
                    <TableBodyCell>딥치즈버거</TableBodyCell>
                    <TableBodyCell>5000원</TableBodyCell>
                    <TableBodyCell>100개</TableBodyCell>
                </TableBodyRow>
            </TableBody>
        </Table>
    </div>
{:else }
    <RegisterAlert/>
{/if}