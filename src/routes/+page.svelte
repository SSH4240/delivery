<script>
    import {Card, Button, Label, Select} from "flowbite-svelte";

    const menus = [
        {
            name: '딥치즈버거',
            price: 5000
        },
        {
            name: '불싸이버거',
            price: 5500
        },
        {
            name: '싸이플렉스버거',
            price: 7000
        },
        {
            name: '화이트갈릭싸이버거',
            price: 6000
        }
    ];

    let selected;
    let stores = [
        {value: "us", name: "United States"},
        {value: "ca", name: "Canada"},
        {value: "fr", name: "France"},
    ]


</script>

<div class="mx-auto w-[60%]">
    <h1 class="text-5xl text-center mb-10 bg-gray-800 text-white rounded p-3">Welcome To Mom's Touch!</h1>
</div>

<div class="flex justify-end mb-10">
    <div class="w-1/3">
        <Label>Select Store
            <Select class="mt-2" items={stores} bind:value={selected} placeholder="Choose Store..."/>
        </Label>
    </div>
</div>

<div class="flex space-x-5">
    {#each menus as item}
        <Card padding="none">
            <a href="/">
                <img class="p-8 rounded-t-lg" src="/menu/{item.name}.png" alt="product 1"/>
            </a>
            <div class="px-5 pb-5">
                <a href="/detail" class="mb-3 block">
                    <h5 class='text-center text-xl font-semibold tracking-tight text-gray-900 dark:text-white'>
                        {item.name}
                    </h5>
                </a>
                <div class="flex justify-between items-center">
                    <span class="text-3xl font-bold text-gray-900 dark:text-white">{item.price}원</span>
                    <Button href="/detail" color="dark">Buy Now</Button>
                </div>
            </div>
        </Card>
    {/each}
</div>
