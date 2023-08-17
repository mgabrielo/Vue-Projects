<template>
    <div class="modal" ref="modal">
        <div class="modal-content">
            <CloseIcon @click="closePreview" class="icon" />
            <img :src="this.blogCoverPhoto" alt="" />
        </div>
    </div>
</template>

<script>
import CloseIcon from '../assets/Icons/times-circle-light.svg'
export default {

    components: {
        CloseIcon
    },
    methods: {
        closePreview() {
            this.$store.commit("openPhotoPreview");
        }
    },
    computed: {
        blogCoverPhoto() {
            return this.$store.state.blogPhotoFileURL
        }
    }
}

</script>

<style lang="scss" scoped>
.modal {
    top: 0;
    background-color: rgba(0, 0, 0, 0.5);
    z-index: 99;
    position: fixed;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
    width: 100%;
    box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);


    .modal-content {
        display: flex;
        justify-content: center;
        background-color: #fff;
        position: relative;
        height: 400px;
        width: 600px;
        padding: 50px;
        border-radius: 12px;
        box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);

        .icon {
            width: 24px;
            height: auto;
            cursor: pointer;
            font-size: 24px;
            position: absolute;
            top: 15px;
            right: 15px;
            color: #303030;
        }

        img {
            margin-top: 15px;
            display: block;
            width: auto;
            height: 100%;
            box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
        }
    }
}
</style>