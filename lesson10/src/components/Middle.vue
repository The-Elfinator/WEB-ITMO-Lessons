<template>
  <div class="middle">
    <Sidebar :posts="posts"/>
    <main>
      <Index v-if="page === 'Index'"/>
      <Enter v-if="page === 'Enter'"/>
      <WritePost v-if="page === 'WritePost'"/>
    </main>
  </div>
</template>

<script>
import Sidebar from "@/components/sidebar/Sidebar.vue";
import Index from "@/components/middle/Index.vue";
import Enter from "@/components/middle/Enter.vue";
import WritePost from "@/components/middle/WritePost.vue";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Middle",
  props: ["posts"],
  data: function () {
    return {
      page: "Index"
    }
  },
  components: {WritePost, Enter, Index, Sidebar},
  beforeCreate() {
    this.$root.$on("onChangePage", (page) => {
      this.page = page;
    });
  }
}
</script>

<style scoped>

</style>