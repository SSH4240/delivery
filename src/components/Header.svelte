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
        <!--        로그인 O-->
        <div class="">
            <Button id="avatar-menu" color="dark" pill>MENU</Button>
            <NavHamburger on:click={toggle} class1="w-full md:flex md:w-auto md:order-1"/>
        </div>

        <!--        로그인 X-->
        <div class="hidden">
            <Button color="dark" href="/login">Log in</Button>
            <Button color="dark" href="/signup">Sign up</Button>
        </div>
    </div>
    <Dropdown placement="bottom" triggeredBy="#avatar-menu">
        <DropdownHeader>
            <span class="block text-sm"> Name </span>
            <span class="block truncate text-sm font-medium"> Email </span>
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