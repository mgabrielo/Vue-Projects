

// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAuth } from 'firebase/auth';
import { getFirestore } from 'firebase/firestore';
import { getStorage } from 'firebase/storage';
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
const firebaseConfig = {
    apiKey: "AIzaSyB00AjN4eWvFgWSGY_oFOVLBGmbkRLcmrI",
    authDomain: "vuefireblog-e9110.firebaseapp.com",
    projectId: "vuefireblog-e9110",
    storageBucket: "vuefireblog-e9110.appspot.com",
    messagingSenderId: "741144287290",
    appId: "1:741144287290:web:194b53ca88eb3cccde7d03"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
export const auth = getAuth(app);
export const db = getFirestore(app);
export const storage = getStorage(app);



