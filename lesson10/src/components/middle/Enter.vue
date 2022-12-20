<template>
  <div class="enter form-box">
    <div class="header">Enter</div>
    <div class="body">
      <form method="post" action="" @submit.prevent="onEnter">
        <div class="field">
          <div class="name">
            <label for="login">Login</label>
          </div>
          <div class="value">
            <input id="login" name="login" v-model="login"/>
          </div>
        </div>
        <div class="field">
          <div class="name">
            <label for="password">Password</label>
          </div>
          <div class="value">
            <input id="password" name="password" type="password" v-model="password"/>
          </div>
        </div>
        <div class="error"> {{ error }}</div>
        <div class="button-field">
          <input type="submit" value="Enter">
        </div>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Enter",
  data: function () {
    return {
      login: "",
      password: "",
      error: ""
    }
  },
  methods: {
    onEnter: function () {
      this.$root.$emit("onEnter", this.login, this.password);
    }
  },
  beforeCreate() {
    this.$root.$on("onEnterValidationError", error => this.error = error);
  }
}
</script>

<style scoped>

</style>