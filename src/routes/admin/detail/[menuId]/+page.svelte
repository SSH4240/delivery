<script>
    import {Button, Gallery, Label, Input, Textarea, Fileupload} from "flowbite-svelte";
    import {onMount} from "svelte";
    import axios from "axios";
    import {URL} from "../../../../env";
    import {browser} from "$app/environment";

    const image1 = {alt: '딥치즈버거', src: '/menu/딥치즈버거.png'}

    let fileuploadprops = {
        id: 'user_avatar'
    }
    let textareaprops = {
        id: 'message',
        name: 'message',
        label: 'Your message',
        rows: 4,
        placeholder: 'Leave a comment...',
    };

    /** @type {import('./$types').PageData} */
    export let data;
    export let TOKEN;
    export let menu = {};
    export let originName;
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
            // console.log(response.data);
            menu = response.data;
            originName = menu.name;
        })
    })

    const updateMenu = () => {
        axios.post(`${URL}/api/menu/update`,
            {
                name: originName,
                updatedName: menu.name,
                price : parseInt(menu.price),
                description : menu.description,
            }, {
                headers: {
                    Authorization: `Bearer ${TOKEN}`
                }
            })
            .then(response => {
                alert("메뉴 업데이트 성공");
                if(browser){
                    window.location.href=`/admin/detail/${menu.id}`
                }
            })
            .catch(error => console.log(error));
    }
    // 동일한 메뉴가 있으면 예외를 발생 시킨다.
</script>

<div class="flex justify-center space-x-10 w-[80%] mx-auto">
    <Gallery class="gap-4 shadow-lg rounded w-[60%] justify-center items-center">
        <img src={image1.src} alt={image1.alt} class="h-auto max-w-full rounded-lg"/>
    </Gallery>
    <div class="w-[30%]">
        <div class='mb-6'>
            <Label>Menu Name</Label>
            <Input size="md" bind:value={menu.name}/>
        </div>
        <div class='mb-6'>
            <Label>Menu Price</Label>
            <Input size="md" bind:value={menu.price}/>
        </div>
        <div class="mb-6">
            <Label>Menu Description</Label>
            <Textarea {...textareaprops} bind:value={menu.description}/>
        </div>
        <div class="mb-6">
            <Label>Upload file</Label>
            <Fileupload {...fileuploadprops}/>
        </div>
        <Button color="dark" class="w-full" on:click={updateMenu}>
            <span class="mr-3">Update</span>
        </Button>
    </div>
</div>