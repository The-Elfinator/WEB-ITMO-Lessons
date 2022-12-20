<template>
  <div id="app">
    <Header :users="users" :userId="userId" />
    <Middle :posts="posts"/>
    <Footer/>
  </div>
</template>

<script>
import Header from "@/components/Header.vue";
import Middle from "@/components/Middle.vue";
import Footer from "@/components/Footer.vue";

export default {
  name: 'App',
  data: function () {
    return this.$root.$data;
  },
  components: {Footer, Middle, Header},
  beforeCreate() {
    this.$root.$on("onEnter", (login, password) => {
      if (!login) {
        this.$root.$emit("onEnterValidationError", "Login is required");
        return;
      }
      if (!password) {
        this.$root.$emit("onEnterValidationError", "Password is required");
        return;
      }

      const users = Object.values(this.users).filter(u => u.login === login);
      if (users.length === 0) {
        this.$root.$emit("onEnterValidationError", "Login not found!");
      } else {
        this.userId = users[0].id;
        this.$root.$emit("onChangePage", "Index");
      }

    });

    this.$root.$on("onLogout", () => {
      this.userId = null;
      this.$root.$emit("onChangePage", "Index");
    });

    this.$root.$on("onWritePost", (title, text) => {
      if (!title) {
        this.$root.$emit("onWritePostValidationError", "Title is required");
        return;
      }
      if (!text) {
        this.$root.$emit("onWritePostValidationError", "Text is required");
        return;
      }
      if (!this.userId) {
        this.$root.$emit("onWritePostValidationError", "Enter into the website!");
        return;
      }

      const id = Math.max(...Object.keys(this.posts)) + 1;

      this.$set(this.posts, id, {
        id, title, text, userId: this.userId
      });
      this.$root.$emit("onChangePage", "Index");
    });
  }
}
</script>

<style scoped>

</style>
