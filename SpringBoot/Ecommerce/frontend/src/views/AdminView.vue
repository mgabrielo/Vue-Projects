<template>
    <router-link :to="{ name: 'CategoryView' }">
        <button class="btn btn-primary btn-lg">Admin Category</button>
    </router-link>
    <br />
    <router-link :to="{ name: 'ProductView' }">
        <button class="btn btn-primary btn-lg">Admin Products</button>
    </router-link>
</template>

<script>
export default {
    name: "AdminView"
}
</script>

<style scoped>
.btn {
    margin-top: 10px;
}
</style>