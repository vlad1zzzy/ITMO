<template>
<ul>
  <li>{{comment.text}}</li>
</ul>
</template>

<script>
export default {
  name: "Comment",
  props: ["comment"],
}
</script>

<style scoped>

</style>