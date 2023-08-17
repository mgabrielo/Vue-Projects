<template>
  <div class="home">
    <BlogPost v-if="!user" :post="welcomeScreen" />
    <BlogPost :post="post" v-for="(post, index) in blogPostFeed" :key="index" />
    <div class="blog-card-wrap">
      <div class="container">
        <h3>View More Recent Blogs</h3>
        <div class="blog-cards">
          <BlogCard :post="post" v-for="(post, index) in  blogPostCards" :key="index" />
        </div>
      </div>
    </div>
    <div class="updates" v-if="!user">
      <div class="container">
        <h2>Never Miss a Post. Register For a Free-Account Today</h2>
        <router-link class="router-button" to="#">Register For VueFireBlog
          <Arrow class="arrow arrow-light" />
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
import BlogPost from "../components/BlogPost.vue"
import BlogCard from "../components/BlogCard.vue"
import Arrow from "../assets/Icons/arrow-right-light.svg"
export default {
  name: "Home",
  components: {
    Arrow, BlogPost, BlogCard
  },
  data() {
    return {
      welcomeScreen: {
        title: "Welcome !!!",
        blogPost: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum",
        welcomeScreen: true,
        photo: "coding",
      },


    }
  },
  computed: {
    blogPostFeed() {
      return this.$store.getters.blogPostsFeed
    },
    blogPostCards() {
      return this.$store.getters.blogPostCards
    },
    user() {
      return this.$store.state.user
    }
  }
};
</script>

<style lang="scss" scoped>
.blog-card-wrap {
  h3 {
    font-weight: 300;
    font-size: 20px;
    margin-bottom: 32px;
  }
}

.updates {
  .container {
    padding: 100px 25px;
    display: flex;
    flex-direction: column;
    align-items: center;

    @media (min-width: 800px) {
      padding: 125px 25px;
      flex-direction: row;
    }

    .router-button {
      display: flex;
      font-size: 14px;
      text-decoration: none;

      @media (min-width: 700px) {
        margin-left: auto;
      }
    }

    h2 {
      font-weight: 300;
      font-size: 32px;
      max-width: 425px;
      width: 100%;
      text-align: center;
      text-transform: uppercase;

      @media (min-width: 800) {
        text-align: initial;
        font-size: 40px;
      }
    }
  }
}
</style>
