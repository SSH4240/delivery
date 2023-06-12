<script>
    import {Button, Gallery, Label, Input, Select} from "flowbite-svelte";
    import {preferences} from '../../../store'
    import {onMount} from "svelte";
    import axios from "axios";
    import {URL} from "../../../env";

    /** @type {import('./$types').PageData} */
    export let data;

    export let TOKEN;
    export let menu = {};
    onMount(() => {
        TOKEN = sessionStorage.getItem('accessToken');
        axios.get(`${URL}/api/menu/detail`,
            {
                headers: {
                    Authorization: `Bearer ${TOKEN}`
                },
                params: {
                    menuId: parseInt(data.menuId)
                },
            }
        ).then(response => {
            console.log(response.data);
            menu = response.data;
        })
    })

    const image1 = {alt: '딥치즈버거', src: '/menu/딥치즈버거.png'}

    let selected = '';
    let options = [
        {value: "combo", name: "Combo"},
        {value: "just", name: "Just"},
    ]
    let burger;
    let coke;
    let fries;

    $: {
        if (selected === 'combo') {
            burger = 1;
            coke = 1;
            fries = 1;
        } else if (selected === 'just') {
            burger = 1;
            coke = 0;
            fries = 0;
        }
    }

    $: {
        if (burger <= 0) {
            burger = 0;
            coke = 0;
            fries = 0;
        } else if (burger >= 1 && selected === 'combo') {
            burger = burger;
            coke = burger;
            fries = burger;
        }
    }

    const onChangeBurger = () => {
        if (selected == '') {
            alert('please choose option');
            burger = undefined;
        }
    }


    const addToCart = () => {
        // $preferences // read value with automatic subscription
        let price = burger * menu.price;
        price += coke * 1000;
        price += fries * 500;

        let discountPrice;
        if(menu.discountPolicy === 'default'){
            discountPrice = price;
        }else if(menu.discountPolicy === 'quantity'){
            if(price >= 10000){
                discountPrice = price - 1000;
            }else{
                discountPrice = price;
            }
        }else if(menu.discountPolicy === 'percentage'){
            const cal = price * 0.1;
            if(price >= 15000){
                discountPrice = price - cal;
            }else{
                discountPrice = price;
            }
        }else if(menu.discountPolicy === 'earlybird'){
            const date = new Date();
            if(date.getHours() > 11){
                discountPrice = price;
            }else{
                discountPrice = price - 1000;
            }
        }

        const items = [...$preferences,
            {
                id: Math.random(),
                name: menu.name,
                menuId: menu.id,
                justQuty: burger,
                cokeQuty: coke,
                friesQuty: fries,
                price: price,
                discountPrice: discountPrice,
            }
        ];
        preferences.set(items);
    };
    $: console.log($preferences);
</script>

<div class="flex justify-center space-x-10 w-[80%] mx-auto">
    <Gallery class="gap-4 shadow-lg rounded w-[60%] justify-center">
        <img src={image1.src} alt={image1.alt} class="h-auto max-w-full rounded-lg"/>
    </Gallery>
    <div class="w-[30%]">
        <Button color="dark" class="w-full mb-3">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                 stroke="currentColor" class="w-6 h-6">
                <path stroke-linecap="round" stroke-linejoin="round" d="M9 15L3 9m0 0l6-6M3 9h12a6 6 0 010 12h-3"/>
            </svg>
            <span class="ml-3">Back To All Products</span>
        </Button>
        <h1 class="text-3xl mb-3">{menu.name}</h1>
        <p class="mb-3">{menu.description}</p>
        <p class="mb-3">{menu.price}원</p>
        <div class="mb-6">
            <div class="flex mb-2 items-center space-x-2">
                <Label class='block w-full w-[30%]'>Quty.
                    <Input type="number" bind:value={burger} on:change={onChangeBurger}/>
                </Label>
                <Label class="w-full">Select an option
                    <Select class="" items={options} bind:value={selected}/>
                </Label>
            </div>
            <div class="flex items-center space-x-4">
                <Label class='block w-full'>Coke Quty.
                    <Input type="number" readonly bind:value={coke}/>
                </Label>
                <Label class='block w-full'>French Fries Quty.
                    <Input type="number" readonly bind:value={fries}/>
                </Label>
            </div>
        </div>
        <Button color="dark" class="w-full" on:click={addToCart} href="/cart">
            <span class="mr-3">Add to Cart</span>
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                 stroke="currentColor" class="w-6 h-6">
                <path stroke-linecap="round" stroke-linejoin="round"
                      d="M2.25 3h1.386c.51 0 .955.343 1.087.835l.383 1.437M7.5 14.25a3 3 0 00-3 3h15.75m-12.75-3h11.218c1.121-2.3 2.1-4.684 2.924-7.138a60.114 60.114 0 00-16.536-1.84M7.5 14.25L5.106 5.272M6 20.25a.75.75 0 11-1.5 0 .75.75 0 011.5 0zm12.75 0a.75.75 0 11-1.5 0 .75.75 0 011.5 0z"/>
            </svg>
        </Button>
    </div>
</div>