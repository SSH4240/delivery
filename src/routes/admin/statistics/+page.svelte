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
    import {onMount} from "svelte";
    import axios from "axios";
    import {URL} from "../../../env";

    let registerStoreStatus = true;


    export let menu = [];
    onMount(async () => {
        const TOKEN = sessionStorage.getItem('accessToken');
        await axios.get(`${URL}/api/menu/statistic`,
            {
                headers: {
                    Authorization: `Bearer ${TOKEN}`
                }
            }
        ).then(response => {
            // console.log(response.data);
            menu = response.data;
        });
    })
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
                {#each menu as item}
                    <TableBodyRow>
                        <TableBodyCell>{item.menuId}</TableBodyCell>
                        <TableBodyCell>{item.menuName}</TableBodyCell>
                        <TableBodyCell>{item.menuPrice}원</TableBodyCell>
                        <TableBodyCell>{item.quantity}</TableBodyCell>
                    </TableBodyRow>
                {/each}
            </TableBody>
        </Table>
    </div>
{:else }
    <RegisterAlert/>
{/if}