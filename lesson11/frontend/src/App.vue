<template>
    <div id="app">
        <Header :user="user"/>
        <Middle :posts="posts"/>
        <Footer/>
    </div>
</template>

<script>
import Header from "./components/Header";
import Middle from "./components/Middle";
import Footer from "./components/Footer";
import axios from "axios";

export default {
    name: 'App',

    components: {
        Footer,
        Middle,
        Header
    },

    data: function () {
        return {
            posts: [],
            user: null
        }
    },
    beforeCreate() {

        this.$root.$on("onJwt", (jwt, indexRedirection) => {
            localStorage.setItem("jwt", jwt);
            axios.get("/api/1/users/jwt", {
                params: {
                    jwt: jwt
                }
            }).then(response => {
                this.user = response.data;
                if (indexRedirection) {
                    this.$root.$emit("onChangePage", "Index");
                }
            })
        })

        this.$root.$on("onEnter", (login, password) => {
            if (login === "") {
                this.$root.$emit("onEnterValidationError", "Login is required");
                return;
            }
            if (password === "") {
                this.$root.$emit("onEnterValidationError", "Password is required");
                return;
            }

            axios.post("/api/1/jwts", {login, password}).then(response => {
                    this.$root.$emit("onJwt", response.data, true);
            }).catch(error => {
                this.$root.$emit("onEnterValidationError", error.response.data);
            });
        });

        this.$root.$on("onLogout", () => {
            this.user = null;
            localStorage.removeItem("jwt");
        });

    },

    beforeMount() {
        const jwt = localStorage.getItem("jwt");
        if (jwt && !this.user) {
            this.$root.$emit("onJwt", jwt, false);
        }
        axios.get("/api/1/posts").then(
            response => this.posts = response.data
        );
    }
}
</script>

<style>
</style>
