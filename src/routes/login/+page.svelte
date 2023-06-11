<script>
    import {browser} from "$app/environment";

    const URL = "http://localhost:8080/oauth2/authorization";
    const redirectURL = "http://localhost:5173/oauth/redirect";

    const moveTo = (where) => {
        if(browser){
            window.location.href = `${URL}/${where}?redirect_uri=${redirectURL}`;
        }
    }
</script>

<div class="flex flex-col items-center justify-center">
    <div class="bg-gray-800 shadow rounded lg:w-1/3  md:w-1/2 w-full p-10 mt-16">
        <p tabindex="0" class="focus:outline-none text-2xl font-extrabold leading-6 text-white">Login to your
            account</p>
        <p tabindex="0" class="focus:outline-none text-sm mt-4 font-medium leading-none text-white">Dont have
            account?
            <a href="/signup"
               class="hover:text-gray-500 focus:text-gray-500 focus:outline-none focus:underline hover:underline text-sm font-medium leading-none text-white cursor-pointer">
                Sign up here
            </a>
        </p>
        <button aria-label="Continue with google" on:click={() => moveTo('google')}
                class="bg-white focus:outline-none focus:ring-2 focus:ring-offset-1 focus:ring-gray-700 py-3.5 px-4 border rounded-lg border-gray-700 flex items-center w-full mt-10">
            <img src="https://tuk-cdn.s3.amazonaws.com/can-uploader/sign_in-svg2.svg" alt="google">
            <p class="text-base font-medium ml-4 text-gray-700">Continue with Google</p>
        </button>
        <button aria-label="Continue with github" on:click={() => moveTo('github')}
                class="bg-white focus:outline-none  focus:ring-2 focus:ring-offset-1 focus:ring-gray-700 py-3.5 px-4 border rounded-lg border-gray-700 flex items-center w-full mt-4">
            <img src="https://tuk-cdn.s3.amazonaws.com/can-uploader/sign_in-svg3.svg" alt="github">
            <p class="text-base font-medium ml-4 text-gray-700">Continue with Github</p>
        </button>

        <div class="mt-8">
            <button class="focus:ring-2 focus:ring-offset-2 focus:ring-indigo-700 text-sm font-semibold leading-none text-white focus:outline-none bg-indigo-700 border rounded hover:bg-indigo-600 py-4 w-full">
                Create my account
            </button>
        </div>
    </div>
</div>
