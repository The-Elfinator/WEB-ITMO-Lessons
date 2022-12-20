<template>
  <aside>
    <SidebarPost v-for="post in viewPosts" :post="post" :key="post.id"/>
  </aside>
</template>

<script>
import SidebarPost from "@/components/sidebar/SidebarPost.vue";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Sidebar",
  props: ["posts"],
  computed: {
    viewPosts: function() {
      return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
    }
  },
  components: {SidebarPost}
}
</script>

<style scoped>

</style>