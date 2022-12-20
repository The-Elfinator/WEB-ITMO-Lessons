<template>
    <div class="middle">
        <Sidebar :posts="viewPosts"/>
        <main>
            <Index v-if="page === 'Index'" :posts="posts"/>
            <Enter v-if="page === 'Enter'"/>
            <Register v-if="page === 'Register'"/>
            <WritePost v-if="page === 'WritePost'"/>
            <EditPost v-if="page === 'EditPost'"/>
        </main>
    </div>
</template>

<script>
import Sidebar from "./sidebar/Sidebar";
import Index from "./page/Index";
import Enter from "./page/Enter";
import WritePost from "./page/WritePost";
import EditPost from "./page/EditPost";
import Register from "./page/Register";

export default {
    name: "Middle",
    data: function () {
        return {
            page: "Index"
        }
    },
    components: {
        WritePost,
        Enter,
        Index,
        Sidebar,
        EditPost,
        Register
    },
    props: ["posts"],
    computed: {
        viewPosts: function () {
            return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
        }
    },
    beforeCreate() {
        this.$root.$on("onChangePage", (page) => this.page = page)
    }
}
</script>

<style scoped>

</style>
