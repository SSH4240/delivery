<script>
    import RegisterAlert from "../../../components/RegisterAlert.svelte";
    import {
        Button,
        Select,
        Table,
        TableBody,
        TableBodyCell,
        TableBodyRow,
        TableHead,
        TableHeadCell
    } from "flowbite-svelte";

    let selected = "none"
    let countries = [
        {value: "none", name: "적용하지않음"},
        {value: "us", name: "정량"},
        {value: "ca", name: "정률"},
        {value: "fr", name: "조조"},
    ]

    let registerStoreStatus = true;
</script>

{#if registerStoreStatus}
    <div class="w-[80%] mx-auto">
        <h1 class="text-5xl mb-10 text-center">Discount Settings</h1>

        <Table hoverable={true} class="w-[60%] mx-auto mb-10">
            <TableHead>
                <TableHeadCell>Menu Id</TableHeadCell>
                <TableHeadCell>Menu Name</TableHeadCell>
                <TableHeadCell>Price</TableHeadCell>
                <TableHeadCell></TableHeadCell>
            </TableHead>
            <TableBody class="divide-y">
                <TableBodyRow>
                    <TableBodyCell>1</TableBodyCell>
                    <TableBodyCell>딥치즈버거</TableBodyCell>
                    <TableBodyCell>5000원</TableBodyCell>
                    <TableBodyCell>
                        <Select class="mt-2" items={countries} bind:value={selected}/>
                    </TableBodyCell>
                </TableBodyRow>
            </TableBody>
        </Table>

        <div class="w-[60%] mx-auto">
            <Button color="dark" class="w-full mb-3">
                <span class="mr-3">Update Discount Policy</span>
            </Button>
        </div>
    </div>
{:else }
    <RegisterAlert/>
{/if}