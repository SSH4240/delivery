<script>
    import {onMount} from "svelte";
    import {page} from "$app/stores";

    onMount(() => {
        const token = $page.url.searchParams.get('token');
        const role = $page.url.searchParams.get('role');
        console.log(token);
        console.log('role', role);
        sessionStorage.setItem('accessToken', token);

        if(role === 'CUSTOMER'){
            window.location.href = '/'
        }else if(role === 'MANAGER'){
            window.location.href = '/admin'
        }
    })
</script>