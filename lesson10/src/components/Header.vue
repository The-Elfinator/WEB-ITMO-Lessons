<template>
  <header>
    <a href="/" @click.prevent="changePage('Index')"><img src="@/assets/img/logo.png" alt="Codeforces" title="Codeforces"/></a>
    <div class="languages">
      <a href="#"><img src="@/assets/img/gb.png" alt="In English" title="In English"/></a>
      <a href="#"><img src="@/assets/img/ru.png" alt="In Russian" title="In Russian"/></a>
    </div>
    <template v-if="userId">
      <div class="enter-or-register-box">
        {{ users[userId].name }}
        |
        <a href="#" @click.prevent="logout()">Logout</a>
      </div>
    </template>
    <template v-else>
      <div class="enter-or-register-box">
        <a href="#" @click.prevent="changePage('Enter')">Enter</a>
        |
        <a href="#">Register</a>
      </div>
    </template>
    <nav>
      <ul>
        <li><a href="#" @click.prevent="changePage('Index')">Home</a></li>
        <li><a href="#">Users</a></li>
        <li v-if="userId"><a href="#" @click.prevent="changePage('WritePost')">WritePost</a></li>
      </ul>
    </nav>
  </header>
</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Header",
  props: ["users", "userId"],
  methods: {
    changePage: function (page) {
      this.$root.$emit("onChangePage", page);
    },
    logout: function () {
      this.$root.$emit("onLogout");
    }
  }
}
</script>

<style scoped>

</style>