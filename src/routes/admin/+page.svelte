<script>
    import RegisterAlert from "../../components/RegisterAlert.svelte";
    import {Drawer, Button, CloseButton, Label, Input, Textarea, Fileupload, Card} from 'flowbite-svelte'
    import {sineIn} from 'svelte/easing';
    import axios from "axios";
    import {URL} from "../../env";
    import {onMount} from "svelte";
    import {browser} from "$app/environment";

    let hidden4 = true;
    let transitionParams = {
        x: -320,
        duration: 200,
        easing: sineIn
    };
    let fileuploadprops = {
        id: 'user_avatar'
    }

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

    let registerStoreStatus = true;

    export let TOKEN;
    onMount(() => {
        TOKEN = sessionStorage.getItem('accessToken');

        axios.get(`${URL}/api/menu/list`, {
            headers: {
                Authorization: `Bearer ${TOKEN}`
            }
        }).then(response => {
            console.log(response.data);
        })
    })

    let name;
    let price;
    let description;
    const registerMenu = () => {
        axios.post(`${URL}/api/menu/create`,
            {
                name: name,
                menuType : "main",
                price : parseInt(price),
                description : description,
                imageName : '임시',
                storeId : 1
            },
            {
                headers: {
                    Authorization: `Bearer ${TOKEN}`
                }
            }).then(response => {
                alert('create');
                if(browser){
                    window.location.href="/admin";
                }
        })
    }
</script>


{#if registerStoreStatus}

    <div class="flex justify-end mb-10">
        <Button color="dark" on:click={() => (hidden4 = false)}>Register Menu</Button>
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
                        <Button href="/admin/detail" color="dark">View Detail</Button>
                    </div>
                </div>
            </Card>
        {/each}
    </div>
{:else }
    <RegisterAlert/>
{/if}


<!--Register Form-->
<Drawer transitionType="fly" {transitionParams} bind:hidden={hidden4} id='sidebar4'>
    <div class='flex items-center'>
        <h5 id="drawer-label"
            class="inline-flex items-center mb-6 text-base font-semibold text-gray-500 uppercase dark:text-gray-400">
            <svg class="w-5 h-5 mr-2" aria-hidden="true" fill="currentColor" viewBox="0 0 20 20"
                 xmlns="http://www.w3.org/2000/svg">
                <path fill-rule="evenodd"
                      d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2v-3a1 1 0 00-1-1H9z"
                      clip-rule="evenodd"></path>
            </svg>
            New Menu
        </h5>
        <CloseButton on:click={() => (hidden4 = true)} class='mb-4 dark:text-white'/>
    </div>
    <form action="#" class="mb-6">
        <div class="mb-6">
            <Label for='menuName' class='block mb-2'>Name</Label>
            <Input id='menuName' name='menuName' required placeholder="싸이버거" bind:value={name}/>
        </div>
        <div class="mb-6">
            <Label for='price' class='block mb-2'>Price</Label>
            <Input id='price' name='price' required placeholder="5000" bind:value={price}/>
        </div>
        <div class="mb-6">
            <Label for="description" class="mb-2">Description</Label>
            <Textarea id="message" placeholder="Write menu description..." rows="4" name="message"
                      bind:value={description}/>
        </div>
        <div class="mb-6">
            <Label class="pb-2">Upload file</Label>
            <Fileupload {...fileuploadprops}/>
        </div>

        <Button color="dark" type="submit" class="w-full" on:click={registerMenu}>
            Register
        </Button>
    </form>
</Drawer>
