<template>
    <div class="card">
        <div class="embed-responsive  embed-responsive-16y9">
            <img class="card-img-top" :src="category?.imageUrl" alt="Card image cap">
        </div>
        <div class="card-body">
            <h5 class="card-title">{{ category?.categoryName }}</h5>
            <p class="card-text">{{ category?.description }}</p>
        </div>
    </div>
</template>

<script>

export default {
    name: "CategoryBox",
    props: ["category"],
}
</script>

<style scoped>
.card-img-top {
    object-fit: cover
}
</style>