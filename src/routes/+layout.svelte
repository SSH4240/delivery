<script>
    import '../app.postcss';
    import Header from "../components/Header.svelte";
    import AdminHeader from "../components/AdminHeader.svelte";
    import {page} from "$app/stores";

    const path = $page.url.pathname;
    const pathArray = path.split('/');
</script>


{#if pathArray[1] === 'admin'}
    <AdminHeader/>
    <main class="min-h-[600px] w-full mx-auto max-w-7xl py-6 sm:px-6 lg:px-8 max-h-full">
        <slot/>
    </main>
{:else}
    <Header/>
    <main class="min-h-[600px] w-full mx-auto max-w-7xl py-6 sm:px-6 lg:px-8 max-h-full">
        <slot/>
    </main>
{/if}