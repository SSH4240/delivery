<script>
    import {
        Navbar,
        NavBrand,
        NavLi,
        NavUl,
        NavHamburger,
        Dropdown,
        DropdownItem,
        DropdownHeader,
        Button
    } from 'flowbite-svelte'
    import {page} from "$app/stores";
    import {browser} from "$app/environment";
    import {onMount} from "svelte";
    import axios from "axios";
    import {URL} from "../env";

    let toggle = false;
    let hidden = false;

    const path = $page.url.pathname;
    const pathArray = path.split('/');
    const pathName = pathArray[2];
    let register, accept, discount, statistics = false;

    if(pathName === 'accept') accept = true;
    else if(pathName === 'discount') discount = true;
    else if(pathName === 'statistics') statistics = true;
    else if(pathName === 'registerStore') register = false;
    else register = true;


    const moveTo = (url) => {
        if(browser){
            window.location.href = url;
        }
    }

    export let login;
    export let noLogin;
    export let user;
    onMount(() => {
        const TOKEN = sessionStorage.getItem('accessToken');
        // user 정보 GET
        axios.get(`${URL}/api/info`,
            {
                headers: {
                    Authorization: `Bearer ${TOKEN}`
                }
            }
        ).then(response => {
            // console.log(response.data.body);
            user = response.data.body.user;
        })

        if (TOKEN) {
            login = '';
            noLogin = 'hidden';
        } else {
            login = 'hidden';
            noLogin = '';
        }
    })
</script>

<div class="flex px-2 sm:px-4 py-2.5 w-full items-center justify-around">
    <Navbar {hidden} {toggle} navDivClass="mx-auto flex flex-wrap items-center space-x-2 p-0" navClass="px-0 sm:px-0 py-0 w-full">
        <button on:click={() => moveTo("/admin")}>
            <NavBrand >
                <img
                        src="/mom.jpeg"
                        class="mr-3 h-6 sm:h-9"
                        alt="Mom's Touch Logo"
                />
                <span  class="self-center whitespace-nowrap text-xl font-semibold dark:text-white">MOM'S TOUCH</span>
            </NavBrand>
        </button>

        <NavUl {hidden} ulClass="flex flex-col ml-3 md:flex-row md:space-x-8 md:mt-0 md:text-sm md:font-medium bg-white border-0">
            <NavLi on:click={() => {moveTo("/admin")}} active="{register}">Register Menu</NavLi>
            <NavLi on:click={() => {moveTo("/admin/accept")}} active="{accept}">Accept Order</NavLi>
            <NavLi on:click={() => {moveTo("/admin/discount")}} active="{discount}">Discount Settings</NavLi>
            <NavLi on:click={() => {moveTo("/admin/statistics")}} active="{statistics}">Statistics</NavLi>
        </NavUl>
    </Navbar>

    <div class="flex items-center md:order-2">
        <!--        로그인 O-->
        <div class="{login}">
            <Button id="avatar-menu" color="dark" pill>MENU</Button>
            <NavHamburger on:click={toggle} class1="w-full md:flex md:w-auto md:order-1"/>
        </div>

        <!--        로그인 X-->
        <div class="{noLogin}">
            <Button color="dark" href="/admin/login">Log in</Button>
            <Button color="dark" href="/admin/signup">Sign up</Button>
        </div>
    </div>

    <Dropdown placement="bottom" triggeredBy="#avatar-menu" class="z-10">
        <DropdownHeader>
            <span class="block text-sm"> {user.username}</span>
            <span class="block truncate text-sm font-medium"> {user.email}</span>
        </DropdownHeader>
        <DropdownItem>Sign out</DropdownItem>
    </Dropdown>
</div>
