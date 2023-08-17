<template>
    <div class="create-post">
        <BlogCoverPreview v-show="this.$store.state.blogPhotoPreview" />
        <Loading v-show="loading" />
        <div class="container">
            <div :class="{ invisible: !error }" class="err-message">
                <p><span>Error : </span>{{ errorMsg }}</p>
            </div>
            <div class="blog-info">
                <input type="text" placeholder="Enter Blog Title" v-model="blogTitle">
                <div class="upload-file">
                    <label for="blog-photo">Upload Cover Photo</label>
                    <input type="file" ref="blogPhoto" id="blog-photo" @change="fileChange" accept=".png, .jpg .jpeg" />
                    <button @click="openPreview" :class="{ 'button-inactive': !this.$store.state.blogPhotoFileURL }"
                        class="preview">Preview
                        Photo</button>
                    <span>File Chosen: {{ this.$store.state.blogPhotoName }}</span>
                </div>
            </div>
            <div class="editor">
                <vue-editor :editorOptions="editorSettings" v-model="blogHTML" @image-added="imageHandler"
                    useCustomImageHandler />
            </div>
            <div class="blog-actions">
                <button @click="uploadBlog">Publish Blog</button>
                <router-link class="router-button" :to="{ name: 'BlogPreview' }">Post Preview</router-link>
            </div>
        </div>
    </div>
</template>

<script>
import { db, auth, storage } from '../firebase/firebaseInit';
import { ref, getDownloadURL, uploadBytesResumable } from "firebase/storage";
import { doc, getDoc, setDoc, addDoc, collection, updateDoc } from "firebase/firestore";
import { VueEditor, Quill } from 'vue2-editor'
import ImageResize from "quill-image-resize-vue";
import Loading from "../components/Loading.vue";
import { ImageDrop } from 'quill-image-drop-module';
import BlogCoverPreview from '@/components/BlogCoverPreview.vue';
window.Quill = Quill
Quill.register("modules/imageDrop", ImageDrop);
Quill.register("modules/imageResize", ImageResize);
export default {
    name: 'CreatePost',
    data() {
        return {
            file: null,
            error: null,
            errorMsg: null,
            loading: null,
            editorSettings: {
                modules: {
                    imageDrop: true,
                    imageResize: {},
                }
            }
        };
    },
    computed: {
        profileId() {
            return this.$store.state.profileId;
        },
        blogCoverPhotoName() {
            return this.$store.state.blogPhotoName;
        },
        blogTitle: {
            get() {
                return this.$store.state.blogTitle;
            },
            set(payload) {
                this.$store.commit("updateBlogTitle", payload);
            }
        },
        blogHTML: {
            get() {
                return this.$store.state.blogHTML;
            },
            set(payload) {
                this.$store.commit("newBlogPost", payload);
            }
        }
    },
    methods: {
        fileChange() {
            this.file = this.$refs.blogPhoto.files[0];
            const fileName = this.file.name;
            this.$store.commit("fileNameChange", fileName);
            this.$store.commit("createFileURL", URL.createObjectURL(this.file));
        },
        openPreview() {
            this.$store.commit("openPhotoPreview")
        },
        imageHandler(file, Editor, cursorLocation, resetUploader) {
            const storageRef = ref(storage, `documents/blogPostPhotos/${file.name}`);
            const uploadTask = uploadBytesResumable(storageRef, file);
            uploadTask.on("state_changed",
                (snapshot) => {
                    const progress =
                        Math.round((snapshot.bytesTransferred / snapshot.totalBytes) * 100);

                },
                (error) => {
                    console.log(error)
                }, async () => {
                    await getDownloadURL(uploadTask.snapshot.ref).then((downloadURL) => {
                        Editor.insertEmbed(cursorLocation, "image", downloadURL)
                        resetUploader()
                    });
                }
            );
        },
        uploadBlog() {
            if (this.blogTitle.length !== 0 && this.blogHTML.length !== 0) {
                if (this.file) {
                    this.loading = true
                    const storageRef = ref(storage, `documents/BlogCoverPhotos/${this.$store.state.blogPhotoName}`);
                    const uploadTask = uploadBytesResumable(storageRef, this.file);
                    uploadTask.on("state_changed",
                        (snapshot) => {
                            const progress = Math.round((snapshot.bytesTransferred / snapshot.totalBytes) * 100);

                        },
                        (error) => {
                            this.loading = false
                            console.log(error)
                        }, async () => {
                            await getDownloadURL(uploadTask.snapshot.ref).then((downloadURL) => {
                                const timeStamp = Date.now();
                                const dbRef = collection(db, "blogPosts")
                                const user_data = {

                                    blogHTML: this.blogHTML,
                                    blogCoverPhoto: downloadURL,
                                    blogCoverPhotoName: this.blogCoverPhotoName,
                                    blogTitle: this.blogTitle,
                                    profileId: this.profileId,
                                    date: timeStamp
                                }


                                addDoc(dbRef, user_data).then(async (docRef) => {
                                    const user_data_id = { blogID: docRef.id, }
                                    const dbR = doc(db, "blogPosts", docRef.id)
                                    await updateDoc(dbR, user_data_id)
                                        .then(async () => {
                                            await this.$store.dispatch("getPost")
                                            await this.$router.push({ name: 'Blogs' })

                                        })

                                })
                            });
                            this.$store.dispatch("getPost")
                        }
                    );
                    return;
                }
                this.error = true;
                this.errorMsg = "Please Add Blog Cover Photo"
                setTimeout(() => {
                    this.error = false;
                }, 5000);
            }
            this.error = true;
            this.errorMsg = "Please Fill in All Fields"
            setTimeout(() => {
                this.error = false;
            }, 5000);
        }
    },
    components: { BlogCoverPreview, Loading }
}
</script>

<style lang="scss">
.create-post {
    position: relative;
    height: 100%;

    button {
        margin-top: 0;

    }

    .router-button {
        text-decoration: none;
        color: #fff;

    }

    label,
    button,
    .router-button {
        transition: 500ms ease-in-out all;
        align-self: center;
        font-size: 14px;
        cursor: pointer;
        padding: 12px 24px;
        background-color: #303030;
        color: #fff;
        border-radius: 20px;
        text-transform: uppercase;

        //   &:focus {
        //     outline: none;
        //   }

        &:hover {
            background-color: rgba($color: #303030, $alpha: 0.7);
        }
    }

    .container {
        position: relative;
        height: 100%;
        padding: 10px 25px 60px;
    }

    .invisible {
        opacity: 0 !important;
    }

    .err-message {
        width: 100%;
        padding: 12px;
        border-radius: 8px;
        color: #fff;
        margin-bottom: 10px;
        background-color: #303030;
        opacity: 1;
        transition: .5s ease all;

        p {
            font-size: 14px;

        }

        span {
            font-weight: 600;
        }
    }

    .blog-info {
        display: flex;
        margin-bottom: 32px;

        input:nth-child(1) {
            min-width: 300px;
        }

        input {
            transition: .5 ease-in-out all;
            border: none;
            padding: 10px 4px;
            border-bottom: 1px solid #303030;

            &:focus {
                outline: none;
                box-shadow: 0 1px 0 0 #303030;
            }
        }

        .upload-file {
            flex: 1;
            margin-left: 16px;
            position: relative;
            display: flex;

            input {
                display: none;
            }

            .preview {
                margin-left: 16px;
                text-transform: initial;
            }

            span {
                font-size: 12px;
                margin-left: 16px;
                align-self: center;
            }
        }
    }

    .editor {
        height: 60vh;
        display: flex;
        flex-direction: column;

        .quillWrapper {
            position: relative;
            display: flex;
            height: 100%;
            flex-direction: column;
        }

        .ql-container {
            display: flex;
            flex-direction: column;
            height: 100%;
            overflow: scroll;
        }

        .ql-editor {
            padding: 20px 16px 30px;
        }
    }

    .blog-actions {
        margin-top: 32px;

        button {
            margin-right: 16px;

        }
    }
}
</style>