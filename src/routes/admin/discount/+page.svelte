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
    import {onMount} from "svelte";
    import axios from "axios";
    import {URL} from "../../../env";
    import {browser} from "$app/environment";

    // let selected = "none"
    let countries = [
        {value: "default", name: "적용하지않음"},
        {value: "quantity", name: "정량"},
        {value: "percentage", name: "정률"},
        {value: "earlybird", name: "조조"},
    ]

    let registerStoreStatus = true;

    export let menus = [];
    export let TOKEN;
    onMount(() => {
        TOKEN = sessionStorage.getItem('accessToken');

        axios.get(`${URL}/api/menu/list`, {
            headers: {
                Authorization: `Bearer ${TOKEN}`
            }
        }).then(response => {
            menus = response.data;
            console.log(menus)
            menus = menus.filter(item => item.menuType != "side");
        })
    })

    const updateDiscountPolicy = (item) => {
        console.log(TOKEN);
        axios.post(`${URL}/api/menu/discount/apply`,
            {
                name: item.name,
                discountPolicy: item.discountPolicy,
            },
            {
                headers: {
                    Authorization: `Bearer ${TOKEN}`
                }
            }
        ).then(response => {
            alert('update success');
            if(browser){
                window.location.href = '/admin/discount';
            }
        })
    }
    $: menus = menus;
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
                {#each menus as item}
                    <TableBodyRow>
                        <TableBodyCell>{item.id}</TableBodyCell>
                        <TableBodyCell>{item.name}</TableBodyCell>
                        <TableBodyCell>{item.price}원</TableBodyCell>
                        <TableBodyCell>
                            <Select class="mt-2" items={countries} bind:value={item.discountPolicy} on:change={() => updateDiscountPolicy(item)}/>
                        </TableBodyCell>
                    </TableBodyRow>
                {/each}
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