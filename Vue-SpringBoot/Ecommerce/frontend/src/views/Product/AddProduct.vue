<template>
    <div class="container">
        <div class="row">
            <div class="col-12 text-center">
                <h4>Add New Products</h4>
            </div>
        </div>
        <div class="row">
            <div class="col-3"></div>
            <div class="col-6">
                <form>
                    <div class="form-group">
                        <label>Category</label>
                        <select class="form-control" v-model="categoryId" required>
                            <option v-for="category in categories" :key="category.id" :value="category.id">
                                {{ category.categoryName }}
                            </option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Name</label>
                        <input type="text" class="form-control" v-model="name" />
                    </div>
                    <div class="form-group">
                        <label>description</label>
                        <input type="text" class="form-control" v-model="description" />
                    </div>
                    <div class="form-group">
                        <label>Image Url</label>
                        <input type="text" class="form-control" v-model="imageURL" />
                    </div>
                    <div class="form-group">
                        <label>Price</label>
                        <input type="number" class="form-control" v-model="price" />
                    </div>
                    <button type="button" class="btn btn-primary" @click="addProduct">Add Product</button>
                </form>
            </div>
            <div class="col-3"></div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import Swal from "sweetalert2"
export default {
    name: "AddProduct",
    props: ["categories", "baseURL"],
    data() {
        return {
            id: null,
            categoryId: null,
            name: null,
            description: null,
            imageURL: null,
            price: null
        }
    },
    methods: {
        addProduct() {
            const product = {
                categoryId: this.categoryId,
                name: this.name,
                description: this.description,
                imageURL: this.imageURL,
                price: this.price
            }

            axios.post(`${this.baseURL}/api/v1/product/add`, product)
                .then((res) => {
                    this.$router.push({ name: 'ProductView' })
                    Swal.fire({
                        title: res.data?.message,
                        icon: 'success'
                    })
                })
        }
    }
}
</script>

<style lang="scss" scoped></style>