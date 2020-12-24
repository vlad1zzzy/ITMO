<template>
  <footer>
    <div class="counters">
      <div class="counter__users">Users: {{ countUsers }}</div>
      <div class="counter__posts">Posts: {{ countPosts }}</div>
    </div>
    <a href="/">Codeforces</a> &copy; 2020 by Mike Mirzayanov
  </footer>
</template>

<script>
export default {
  name: "Footer",
  props: ["countUsers", "countPosts"],
}
</script>

<style scoped>

</style>