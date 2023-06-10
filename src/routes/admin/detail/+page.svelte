<script>
    import {Button, Gallery, Label, Input, Textarea, Fileupload} from "flowbite-svelte";

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

    // 동일한 메뉴가 있으면 예외를 발생 시킨다.
</script>

<div class="flex justify-center space-x-10 w-[80%] mx-auto">
    <Gallery class="gap-4 shadow-lg rounded w-[60%] justify-center items-center">
        <img src={image1.src} alt={image1.alt} class="h-auto max-w-full rounded-lg"/>
    </Gallery>
    <div class="w-[30%]">
        <div class='mb-6'>
            <Label>Menu Name</Label>
            <Input size="md"/>
        </div>
        <div class='mb-6'>
            <Label>Menu Price</Label>
            <Input size="md"/>
        </div>
        <div class="mb-6">
            <Label>Menu Description</Label>
            <Textarea {...textareaprops}/>
        </div>
        <div class="mb-6">
            <Label>Upload file</Label>
            <Fileupload {...fileuploadprops}/>
        </div>
        <Button color="dark" class="w-full">
            <span class="mr-3">Update</span>
        </Button>
    </div>
</div>