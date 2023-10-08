<template>
    <div class="container">
        <div class="row">
            <div class="col-12 text-center">
                <h4>Products</h4>
                <router-link :to="{ name: 'AddProduct' }" style="float: right;">
                    <button class="btn">Add Product</button>
                </router-link>
            </div>
        </div>
        <div class="row">
            <!-- display all products -->
            <div class="col-md-4 col-xl-4 col-12 pt-3 d-flex" v-for="product of products" :key="product?.id">
                <ProductBox :product="product" />
            </div>
        </div>
    </div>
</template>

<script>
import ProductBox from '@/components/ProductBox.vue';

export default {
    name: "ProductView",
    props: ["products"],
    components: {
        ProductBox
    }
}
</script>

<style lang="scss" scoped></style>