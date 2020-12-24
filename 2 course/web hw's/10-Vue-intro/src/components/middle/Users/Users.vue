<template>
  <div>
    <div class="caption">Users</div>
    <table>
      <thead>
      <tr class="first-line">
        <th>Id</th>
        <th>Login</th>
        <th>Name</th>
        <th>Status</th>
      </tr>
      </thead>
      <tbody>
      <UserInTable v-for="user in users" :user="user" :key="user.id"/>
      </tbody>
    </table>
  </div>
</template>

<script>
import UserInTable from "@/components/middle/Users/UserInTable";

export default {
  name: "Users",
  components: {UserInTable},
  props: ["users"],
}
</script>

<style>
table {
  width: 100%;
  text-align: center;
  border-collapse: collapse;
}

.caption {
  background-color: var(--datatable-color);
  font-size: 0.9rem;
  border-top-left-radius: 5px;
  border-top-right-radius: 5px;
  padding: 0.25rem 0.5rem;
}

th {
  border: 1px solid var(--datatable-color);
  padding: 0.5em;
  font-size: 0.8rem;
  font-weight: inherit;
}

.first-line{
  font-weight: bold;
}

tbody tr:nth-child(2n-1) {
  background-color: var(--datatable-dark-cell-color);
}
</style>