<template>
  <section>
    <div class="header">
      {{ post.title }}
    </div>
    <div class="body">
      {{ post.text }}
    </div>
    <div class="footer">
      <a href="#" @click.prevent="onPostPage(post.id)">View all</a>
    </div>
  </section>
</template>

<script>
export default {
  name: "SidebarPost",
  props: ["post"],
  methods: {
    onPostPage: function (postId) {
      console.log(postId)
      this.$root.$emit("onPost", postId);
    },
  }
}
</script>

<style scoped>
section {
  margin-bottom: 1rem;
}
</style>