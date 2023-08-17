import Vue from 'vue'
import Vuex from 'vuex'
import { doc, getDocs, getDoc, updateDoc, collection, query, orderBy, deleteDoc } from "firebase/firestore";
import { db, auth } from '../firebase/firebaseInit';

Vue.use(Vuex)

export default new Vuex.Store({
  state: {

    blogPosts: [],
    postLoaded: null,
    blogHTML: "Write your blog title here...",
    blogTitle: "",
    blogPhotoName: "",
    blogPhotoFileURL: null,
    blogPhotoPreview: null,
    editPost: null,
    user: null,
    profileEmail: null,
    profileFirstName: null,
    profileLastName: null,
    profileUsername: null,
    profileId: null,
    profileInitials: null,
  },
  getters: {
    blogPostsFeed(state) {
      return state.blogPosts.slice(0, 2)
    },
    blogPostCards(state) {
      return state.blogPosts.slice(2, 6)
    }
  },
  mutations: {
    newBlogPost(state, payload) {
      state.blogHTML = payload
      // console.log(state.blogHTML)
    },
    openPhotoPreview(state) {
      state.blogPhotoPreview = !state.blogPhotoPreview
    },
    updateBlogTitle(state, payload) {
      state.blogTitle = payload
    },
    filterBlogPost(state, payload) {
      state.blogPosts = state.blogPosts.filter(post => post.blogID !== payload)
    },
    setBlogState(state, payload) {
      state.blogTitle = payload.blogTitle;
      state.blogHTML = payload.blogHTML;
      state.blogPhotoFileURL = payload.blogCoverPhoto;
      state.blogPhotoName = payload.blogCoverPhotoName;
    },
    fileNameChange(state, payload) {
      state.blogPhotoName = payload;
    },
    createFileURL(state, payload) {
      state.blogPhotoFileURL = payload
    },
    toggleEditPost(state, payload) {
      state.editPost = payload;
    },
    updateUser(state, payload) {
      state.user = payload
    },
    setProfileInfo(state, doc) {
      state.profileId = doc.id;
      // console.log(doc.data().email)
      state.profileEmail = doc.data().email;
      state.profileFirstName = doc.data().firstName
      state.profileLastName = doc.data().lastName
      state.profileUsername = doc.data().username
    },
    setProfileInitals(state) {
      state.profileInitials = state.profileFirstName.match(/(\b\S)?/g).join("").toUpperCase()
        + state.profileLastName.match(/(\b\S)?/g).join("").toUpperCase()
    },
    changeFirstName(state, payload) {
      state.profileFirstName = payload
    },
    changeLastName(state, payload) {
      state.profileLastName = payload
    },
    changeUsername(state, payload) {
      state.profileUsername = payload
    },
  },
  actions: {
    async getCurrentUser({ commit }) {
      const database = await getDoc(doc(db, "users", auth.currentUser.uid));
      const dbResult = await database;
      commit("setProfileInfo", dbResult);
      commit("setProfileInitals");
      // console.log(dbResult)
    },

    async updateUserSettings({ commit, state }) {
      const user_data = {
        firstName: this.state.profileFirstName,
        lastName: this.state.profileLastName,
        username: this.state.profileUsername,
      }
      await updateDoc(doc(db, "users", auth.currentUser.uid), user_data);
      commit("setProfileInitals");
    },

    async getPost({ state }) {
      const postCollection = collection(db, "blogPosts");
      const data = await getDocs(query(postCollection), orderBy('date', 'desc'));

      data.docs.map((doc) => {
        if (!state.blogPosts.some(post => post.blogID === doc.id)) {
          const data = {
            blogID: doc.data().blogID,
            blogHTML: doc.data().blogHTML,
            blogCoverPhoto: doc.data().blogCoverPhoto,
            blogTitle: doc.data().blogTitle,
            blogDate: doc.data().date,
            blogCoverPhotoName: doc.data().blogCoverPhotoName
          }
          state.blogPosts.push(data)
        }
      })
      state.postLoaded = true
      // console.log(state.blogPosts)
    },

    async deletePost({ commit }, payload) {
      const getPost = doc(db, "blogPosts", payload);
      await deleteDoc(getPost).then(() => {
        commit("filterBlogPost", payload)
      })
    },

    async updatePost({ commit, dispatch }, payload) {
      commit("filterBlogPost", payload)
      await dispatch("getPost")
    }

  },
  modules: {
  }
})
