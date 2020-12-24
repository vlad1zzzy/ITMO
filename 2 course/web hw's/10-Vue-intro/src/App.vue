<template>
  <div id="app">
    <Header :userId="this.userId" :users="users"/>
    <Middle :posts="this.posts" :users="users" :comments="this.comments"/>
    <Footer :countUsers="countUsers" :countPosts="countPosts"/>
  </div>
</template>

<script>
import Header from "./components/Header";
import Middle from "./components/Middle";
import Footer from "./components/Footer";

export default {
  name: 'App',
  components: {
    Footer,
    Middle,
    Header
  },
  data: function () {
    return this.$root.$data;
  },
  methods: {
    sizeOf: function (obj) {
      return Object.keys(obj).length;
    }
  },
  computed: {
    countUsers: function () {
      return this.sizeOf(this.users);
    },
    countPosts: function () {
      return this.sizeOf(this.posts);
    }
  },
  beforeCreate() {
    this.$root.$on("onEnter", (login, password) => {
      if (password === "") {
        this.$root.$emit("onEnterValidationError", "Password is required");
        return;
      }

      const users = Object.values(this.users).filter(u => u.login === login);
      if (users.length === 0) {
        this.$root.$emit("onEnterValidationError", "No such user");
      } else {
        this.userId = users[0].id;
        this.$root.$emit("onChangePage", "Index");
      }
    });

    this.$root.$on("onLogout", () => this.userId = null);

    this.$root.$on("onRegister", (login, name, password) => {
      if (!login || !name || !password) {
        this.$root.$emit("OnRegisterValidationError", "All fields are required")
        return;
      }
      if (!/^[a-z]{3,16}$/.test(login)) {
        this.$root.$emit("OnRegisterValidationError", "Invalid Login")
        return;
      }
      if (Object.values(this.users).filter(user => user.login === login).length) {
        this.$root.$emit("OnRegisterValidationError", "Login is already in use")
        return;
      }
      if (name.length > 32) {
        this.$root.$emit("OnRegisterValidationError", "Invalid Name")
        return;
      }

      const id = Math.max(...Object.keys(this.posts)) + 1;
      this.$root.$set(this.users, id, {
        id, login, name, admin: false
      });
      this.$root.$emit("onChangePage", "Enter")
    })

    this.$root.$on("onWritePost", (title, text) => {
      if (this.userId) {
        if (!title || title.length < 5) {
          this.$root.$emit("onWritePostValidationError", "Title is too short");
        } else if (!text || text.length < 10) {
          this.$root.$emit("onWritePostValidationError", "Text is too short");
        } else {
          const id = Math.max(...Object.keys(this.posts)) + 1;
          this.$root.$set(this.posts, id, {
            id, title, text, userId: this.userId
          });
        }
      } else {
        this.$root.$emit("onWritePostValidationError", "No access");
      }
    });

    this.$root.$on("onEditPost", (id, text) => {
      if (this.userId) {
        if (!id) {
          this.$root.$emit("onEditPostValidationError", "ID is invalid");
        } else if (!text || text.length < 10) {
          this.$root.$emit("onEditPostValidationError", "Text is too short");
        } else {
          let posts = Object.values(this.posts).filter(p => p.id === parseInt(id));
          if (posts.length) {
            posts.forEach((item) => {
              item.text = text;
            });
          } else {
            this.$root.$emit("onEditPostValidationError", "No such post");
          }
        }
      } else {
        this.$root.$emit("onEditPostValidationError", "No access");
      }
    });

    this.$root.$on("getPostComments", (id) => {
      return Object.values(this.comments).filter(comment => comment.postId === id)})
  }
}
</script>

<style>
#app {

}
</style>
