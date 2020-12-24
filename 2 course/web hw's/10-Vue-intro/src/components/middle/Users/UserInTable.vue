<template>
  <tr>
    <th>{{ user.id }}</th>
    <th>{{ user.login }}</th>
    <th>{{ user.name }}</th>
    <th>{{ user.admin ? "admin" : "user" }}</th>
  </tr>
</template>

<script>
export default {
  name: "UserInTable",
  props: ["user"]
}
</script>

<style scoped>

</style>