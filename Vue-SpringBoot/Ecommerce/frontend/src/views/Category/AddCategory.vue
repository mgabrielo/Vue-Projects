<template>
  <div class="container">
    <div class="row">
      <div class="col-12 pt-2 text-center">
        <h3>Add Category</h3>
      </div>
    </div>
    <div class="pt-3 row">
      <div class="col-3"></div>
      <div class="col-6">
        <form>
          <div class="form-group">
            <label>Category Name</label>
            <input type="text" class="form-control" v-model="categoryName" />
          </div>
          <div class="form-group">
            <label>Category Description</label>
            <textarea rows="3" class="form-control" v-model="description" />
          </div>
          <div class="form-group">
            <label>Category Image</label>
            <input type="text" class="form-control" v-model="imageUrl" />
          </div>
          <button type="button" class="btn btn-primary" @click="addCategory">Submit</button>
        </form>
      </div>
      <div class="col-3"></div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Swal from "sweetalert2"
export default {
  name: 'AddCategory',
  data() {
    return {
      categoryName: '',
      description: '',
      imageUrl: ''
    }
  },
  methods: {
    addCategory() {
      // console.log({ 'Categoryname': this.categoryName, 'Description': this.description, 'imageUrl': this.imageUrl })
      const baseURL = "http://localhost:8080"
      const newCategory = {
        categoryName: this.categoryName,
        description: this.description,
        imageUrl: this.imageUrl
      }
      axios({
        method: "post",
        url: `${baseURL}/api/v1/category/create`,
        data: JSON.stringify(newCategory),
        headers: {
          "Content-Type": 'application/json'
        }
      }).then((response) => {
        console.log(response.data)
        Swal.fire({
          title: response.data?.message,
          icon: 'success'
        })
      }).catch((err) => {
        console.log(err)
      })
    }
  }
};
</script>

<style lang="scss" scoped></style>
