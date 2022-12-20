<template>
  <div class="write-post form">
    <div class="header">Write Post</div>
    <div class="body">
      <form method="post" action="" @submit.prevent="writePost()">
        <div class="field">
          <div class="name">
            <label for="title">Title</label>
          </div>
          <div class="value">
            <input id="title" name="title" v-model="title"/>
          </div>
        </div>
        <div class="field">
          <div class="name">
            <label for="text">Text</label>
          </div>
          <div class="value">
            <textarea id="text" name="text" v-model="text"></textarea>
          </div>
        </div>
        <div class="error">{{ error }}</div>
        <div class="button-field">
          <input type="submit" value="Publish Post">
        </div>
      </form>
    </div>
  </div>
</template>

<script>

export default {
  name: "WritePost",
  methods: {
    writePost: function() {
      this.$root.$emit("onWritePost", this.title, this.text);
    }
  },
  data: function () {
    return {
      title: "",
      text: "",
      error: ""
    }
  },
  beforeCreate() {
    this.$root.$on("onWritePostValidationError", (error) => {
      this.error = error;
    });
  }
}
</script>

<style scoped>

</style>