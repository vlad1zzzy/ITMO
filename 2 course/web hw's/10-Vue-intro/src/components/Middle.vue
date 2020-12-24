<template>
  <div class="middle">
    <Sidebar :posts="viewPosts"/>
    <main>
      <Index v-if="page === 'Index'" :posts="posts" :comments="comments"/>
      <Enter v-if="page === 'Enter'"/>
      <Register v-if="page === 'Register'"/>
      <WritePost v-if="page === 'WritePost'"/>
      <EditPost v-if="page === 'EditPost'"/>
      <Users v-if="page === 'Users'" :users="users"/>
      <Post v-if="page === 'Post'" :post="post" :comments="this.postComments" :isPage="true"/>
    </main>
  </div>
</template>

<script>
import Sidebar from "@/components/sidebar/Sidebar";
import Index from "@/components/middle/Index";
import Register from "@/components/middle/Register";
import Enter from "@/components/middle/Enter";
import WritePost from "@/components/middle/WritePost";
import EditPost from "@/components/middle/EditPost";
import Users from "@/components/middle/Users/Users";
import Post from "@/components/middle/Post/Post";

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
    Register,
    Index,
    Sidebar,
    EditPost,
    Users,
    Post,
  },
  props: ["posts", "users", "comments"],
  computed: {
    viewPosts: function () {
      return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
    },
  }, beforeCreate() {
    this.$root.$on("onChangePage", (page) => this.page = page)
    this.$root.$on(
        "onPost", (postId) => {
          this.page = "Post"
          this.post = this.posts[postId]
          this.postComments = Object.values(this.comments).filter(comment => comment.postId === this.post.id)
        }
    )
  }
}
</script>

<style scoped>

</style>