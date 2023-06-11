<script>
    import {
        Navbar,
        NavBrand,
        NavUl,
        NavHamburger,
        Dropdown,
        DropdownItem,
        DropdownHeader,
        DropdownDivider,
        Button
    } from 'flowbite-svelte'
    import {onMount} from "svelte";
    import axios from "axios";
    import {URL} from "../env";


    export let login;
    export let noLogin;
    export let user;
    onMount(() => {
        const TOKEN = sessionStorage.getItem('accessToken');
        // user 정보 GET
        axios.get(`${URL}/api/customer`,
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

<Navbar let:hidden let:toggle>
    <NavBrand href="/">
        <img
                src="/mom.jpeg"
                class="mr-3 h-6 sm:h-9"
                alt="Mom's Touch Logo"
        />
        <span class="self-center whitespace-nowrap text-xl font-semibold dark:text-white">MOM'S TOUCH</span>
    </NavBrand>
    <div class="flex items-center md:order-2">
        <div class="{login}">
            <!--        로그인 O-->
            <Button id="avatar-menu" color="dark" pill>MENU</Button>
            <NavHamburger on:click={toggle} class1="w-full md:flex md:w-auto md:order-1"/>
        </div>
        <div class="{noLogin}">
            <!--        로그인 X-->
            <Button color="dark" href="/login">Log in</Button>
            <Button color="dark" href="/signup">Sign up</Button>
        </div>
    </div>
    <Dropdown placement="bottom" triggeredBy="#avatar-menu" class="z-10">
        <DropdownHeader>
            <span class="block text-sm"> {user.username}</span>
            <span class="block truncate text-sm font-medium"> {user.email}</span>
        </DropdownHeader>
        <DropdownItem href="/cart">
            Cart
        </DropdownItem>
        <DropdownItem href="/history">
            Order History
        </DropdownItem>
        <DropdownDivider/>
        <DropdownItem>Sign out</DropdownItem>
    </Dropdown>
    <NavUl {hidden}>
        <!--        <NavLi href="/" active={true}>Main</NavLi>-->
    </NavUl>
</Navbar>