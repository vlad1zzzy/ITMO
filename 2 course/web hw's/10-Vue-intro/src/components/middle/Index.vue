<template>
  <div>
    <Post v-for="post in viewPosts" :post="post" :comments="postComments(post.id)" :isPage="false" :key="post.id"/>
  </div>
</template>

<script>
import Post from "@/components/middle/Post/Post";

export default {
  name: "Index",
  props: ["posts", "comments"],
  components: {Post},
  methods: {
    postComments: function (id) {
      return Object.values(this.comments).filter(comment => comment.postId === id)
    }
  },
  computed: {
    viewPosts: function () {
      return Object.values(this.posts).sort((a, b) => b.id - a.id);
    },
  }
}
</script>

<style scoped>

</style>