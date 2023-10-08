<template>
    <div class="card">
        <div class="embed-responsive  embed-responsive-16y9">
            <img class="card-img-top" :src="product?.imageUrl" alt="Card image cap">
        </div>
        <div class="card-body">
            <h5 class="card-title">{{ product?.name }}</h5>
            <p class="card-text">{{ product?.description }} </p>
        </div>
    </div>
</template>

<script>
export default {
    name: "ProductBox",
    props: ["product"]
}
</script>

<style scoped>
.card-img-top {
    object-fit: cover;
}
</style>