<template>
    <div class="container">
        <div class="row">
            <div class="col-12 text-center">
                <h3 class="pt-2"> Our Category</h3>
                <router-link :to="{ name: 'AddCategory' }">
                    <button class="btn" style="float: right;">Add Category</button>
                </router-link>
            </div>
        </div>
        <div class="row">
            <div v-for=" category  of  categories " :key="category?.id" class="col-xl-4 col-md-4 d-flex pt-3">
                <CategoryBox :category="category" />
            </div>
        </div>
    </div>
</template>

<script>
import CategoryBox from '../../components/category/CategoryBox.vue'
export default {
    name: "CategoryView",
    props: ["categories"],
    data() {
        return {
            baseURL: "http://localhost:8080",
        };
    },
    components: {
        CategoryBox
    },
    methods: {

    },
    mounted() {

    }
}
</script>

<style lang="scss" scoped></style>