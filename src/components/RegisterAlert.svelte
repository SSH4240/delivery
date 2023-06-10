<script>
    import {browser} from "$app/environment";

    const moveTo = (url) => {
        if(browser){
            window.location.href = url;
        }
    }
</script>
<div class="flex flex-col items-center justify-center">
    <div class="bg-gray-800 shadow rounded lg:w-1/3  md:w-1/2 w-full p-10 mt-16">
        <p tabindex="0" class="focus:outline-none text-2xl font-extrabold leading-6 text-white">You have not registered a store.</p>
        <div class="mt-8">
            <button on:click={() => {moveTo("/admin/registerStore")}} class="focus:ring-2 focus:ring-offset-2 focus:ring-indigo-700 text-sm font-semibold leading-none text-white focus:outline-none bg-indigo-700 border rounded hover:bg-indigo-600 py-4 w-full">
                Go to register
            </button>
        </div>
    </div>
</div>
