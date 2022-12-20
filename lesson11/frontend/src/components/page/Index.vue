<template>
    <div>
        Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aspernatur autem culpa debitis dicta error facere harum libero magni, minus officiis quibusdam ratione rem voluptate. Atque consequuntur culpa deleniti dolorum eaque eius facilis fugit harum ipsum iste labore magnam, magni, modi natus neque nisi nostrum nulla obcaecati officia quae quidem repellat vitae voluptatibus. Aspernatur aut blanditiis eos facilis fugiat, fugit illum libero quia sequi sunt. Ab, accusamus accusantium aspernatur consequatur delectus distinctio doloremque earum eos ex exercitationem fugit hic illum impedit inventore ipsum iste magnam maiores minima non nulla odit officiis omnis perspiciatis quas quidem quo repellendus sapiente sit tempora tempore.
    </div>
<!--    <div>-->
<!--        <article v-for="post in sortedPost" v-bind:key="post.id">-->
<!--            <Post :post="post" :postCommentsCount="getPostCommentsCount(post)"/>-->
<!--        </article>-->
<!--    </div>-->
</template>

<script>
// import Post from "@/components/page/Post.vue";

export default {
    name: "Index",
    // components: {Post},
    props: ["posts", "commentsCounts"],
    // computed: {
    //     sortedPost: function () {
    //         return Object.values(this.posts).sort((a, b) => b.id - a.id)
    //     }
    // },
    // methods: {
    //     getPostCommentsCount: function (post) {
    //         return this.commentsCounts[post.id];
    //     }
    // }
}
</script>

<style scoped>

</style>
