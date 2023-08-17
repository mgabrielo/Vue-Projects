<template>
    <div class="reset-password">
        <Modal v-if="modalActive" v-on:close-modal="closeModal" :modalMessage="modalMessage" />
        <Loading v-if="loading" />
        <div class="form-wrap">
            <form class="reset">
                <p class="login-register">
                    Back To
                    <router-link class="router-link" :to="{ name: 'Login' }">Login</router-link>
                </p>
                <h2 class="">Reset Password</h2>
                <p>Forgot Your Password? Enter Your Email to Reset It </p>
                <div class="inputs">
                    <div class="input">
                        <input type="text" placeholder="Email" v-model="email">
                        <Envelope class="icon" />
                    </div>
                </div>
                <button @click.prevent="resetPassword">Reset</button>
                <div class="angle"></div>
            </form>
        </div>
        <div class="background"></div>
    </div>
</template>

<script>
import Loading from "@/components/Loading.vue";
import { auth } from '../firebase/firebaseInit'
import { sendPasswordResetEmail } from "firebase/auth";
import Envelope from "../assets/Icons/envelope-regular.svg"
import Modal from "@/components/Modal.vue";
export default {
    name: 'ForgotPassword',
    components: {
        Envelope, Modal,
        Loading
    },
    data() {
        return {
            email: null,
            modalActive: null,
            modalMessage: "",
            loading: null
        }
    },
    methods: {
        resetPassword() {
            this.loading = true;
            sendPasswordResetEmail(auth, this.email).then(() => {
                this.modalMessage = "If your account exist you will recieve reset email"
                this.loading = false;
                this.modalActive = true;
            }).catch((err) => {
                this.modalMessage = err.message;
                this.loading = false;
                this.modalActive = true
            })
        }
        ,
        closeModal() {
            this.modalActive = !this.modalActive;
            this.email = "";
        }
    }
}
</script>

<style lang="scss" scoped>
.reset-password {
    position: relative;

    .form-wrap {
        .reset {
            h2 {
                margin-bottom: 8px;
            }

            p {
                text-align: center;
                margin-bottom: 32px;
            }
        }
    }
}
</style>