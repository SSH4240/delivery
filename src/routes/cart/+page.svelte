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

    console.log($preferences);
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
                        <TableBodyCell>5000원</TableBodyCell>
                        <TableBodyCell>
                            <button type="button"
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
        SUBTOTAL
    </div>

    <div class="w-[70%] mx-auto">
        <Button color="dark" class="w-full mb-3">
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