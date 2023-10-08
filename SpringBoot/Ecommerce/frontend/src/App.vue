<template>
  <NavBar />
  <nav>
    <router-link to="/">Home</router-link> |
    <router-link to="/about">About</router-link>
  </nav>
  <router-view :baseURL="baseURL" :categories="categories" :products="products">

  </router-view>
</template>
<script>
import NavBar from "./components/NavBar.vue";
import axios from 'axios';
export default {
  components: { NavBar },
  data() {
    return {
      baseURL: "http://localhost:8080",
      products: [],
      categories: []
    }
  },
  methods: {
    async fetchData() {

      await axios.get(this.baseURL + "/api/v1/category/list")
        .then(res => {
          this.categories = res.data
        }).catch((err) => console.log('err', err));

      await axios.get(this.baseURL + "/api/v1/product/")
        .then(res => {
          this.products = res.data
        }).catch((err) => console.log('err', err));
    }
  },
  mounted() {
    this.fetchData();
  }
};
</script>
<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
