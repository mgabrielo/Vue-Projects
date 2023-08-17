<template>
    <div class="form-wrap">
        <form class="register">
            <p class="login-register"> Already have an Account ?
                <router-link class="router-link" :to="{ name: 'Login' }">Login</router-link>
            </p>
            <h2 class="">Create your VueFireBlog Account</h2>
            <div class="inputs">
                <div class="input">
                    <input type="text" placeholder="First Name" v-model="firstName">
                    <User class="icon" />
                </div>
                <div class="input">
                    <input type="text" placeholder="Last Name" v-model="lastName">
                    <User class="icon" />
                </div>
                <div class="input">
                    <input type="text" placeholder="Username" v-model="username">
                    <User class="icon" />
                </div>
                <div class="input">
                    <input type="text" placeholder="Email" v-model="email">
                    <Envelope class="icon" />
                </div>
                <div class="input">
                    <input type="password" placeholder="Password" v-model="password">
                    <Lock class="icon" />
                </div>
                <div class="error" v-show="error">{{ errorMsg }}</div>
            </div>
            <button @click.prevent="register">Sign Up</button>
            <div class="angle"></div>
        </form>
        <div class="background"></div>
    </div>
</template>

<script>
import Envelope from "../assets/Icons/envelope-regular.svg"
import Lock from "../assets/Icons/lock-alt-solid.svg"
import User from "../assets/Icons/user-alt-light.svg"
import { createUserWithEmailAndPassword } from "firebase/auth";

import { doc, setDoc } from "firebase/firestore";
import { db, auth } from '../firebase/firebaseInit';

export default {
    name: "Register",
    components: {
        Envelope, Lock, User
    },
    data() {
        return {
            firstName: "",
            lastName: "",
            username: "",
            email: "",
            password: "",
            error: null,
            errorMsg: ""
        }
    },
    methods: {

        async register() {
            if (this.email !== "" && this.password !== "" && this.firstName !== "" && this.lastName !== "" && this.username !== "") {
                this.error = false;
                this.errorMsg = "";
                const firebaseAuth = await auth;
                const createUser = await createUserWithEmailAndPassword(firebaseAuth, this.email, this.password);
                const result = await createUser;
                const user_data = {
                    firstName: this.firstName,
                    lastName: this.lastName,
                    username: this.username,
                    email: this.email,
                }
                await setDoc(doc(db, "users", result.user.uid), user_data);
                this.$router.push({ name: 'Home' })
                return;
            }

            this.error = true;
            this.errorMsg = " Please Fill Out All the Fields"
            return;
        }
    }
}
</script>

<style lang="scss" scoped >
.register {
    h2 {
        max-width: 350px;

    }
}
</style>