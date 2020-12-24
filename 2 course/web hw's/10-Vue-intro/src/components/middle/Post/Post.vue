<template>
  <div>
    <article>
      <div class="title"><a href="#" @click.prevent="onPostPage(post.id)"
                            style="text-decoration: none">{{post.title}}</a></div>
      <div class="information">By {{ post.userId }}</div>
      <div class="body">{{ post.text }}</div>
      <div class="footer">
        <div class="left">
          <img src="../../../assets/img/voteup.png" title="Vote Up" alt="Vote Up"/>
          <span class="positive-score">+173</span>
          <img src="../../../assets/img/votedown.png" title="Vote Down" alt="Vote Down"/>
        </div>
        <div class="right">
          <img src="../../../assets/img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
          Some time
          <img src="../../../assets/img/comments_16x16.png" title="Comments" alt="Comments"/>
          {{ Object.keys(comments).length }}
        </div>
      </div>
    </article>
    <div class="comments" v-if="isPage">
      <div class="title">Comments:</div>
      <ul v-if="comments.length">
          <Comment v-for="comment in comments" :comment="comment" :postId="post.id" :key="comment.id"/>
      </ul>
      <div v-else>You can be first!</div>
    </div>
  </div>
</template>

<script>
import Comment from "@/components/middle/Post/Comment";

export default {
  name: "Post",
  components: {Comment},
  props: ["post", "comments", "isPage"],
  methods: {
    onPostPage: function (postId) {
      this.$root.$emit("onPost", postId);
    }
  }
}
</script>

<style scoped>

article {
  margin-bottom: 2em;
}

.title {
  color: var(--caption-color);
  font-weight: bold;
  font-size: 1.25rem;
}

.information {
  margin-top: 0.25rem;
  font-size: 0.85rem;
  color: #888;
}

.body {
  margin: 0.3rem 0;
  border-left: 4px solid var(--border-color);
  padding-left: 0.75rem;
  font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
}

.body p:last-child {
  margin: 0;
}

.attachment li {
  list-style: none;
  padding: 0.25rem 20px;
  margin: 0;
  background: url("../../../assets/img/paperclip-16x16.png") 0 2px no-repeat;
  font-size: 0.75rem;
  color: #888;
}

.footer {
  border: 1px solid var(--border-color);
  border-radius: var(--border-radius);
  overflow: hidden;
  padding: 0.1rem;
  margin-top: 0.25rem;
}

.footer .left {
  float: left;
  padding-left: 0.5rem;
}

.footer .left img {
  position: relative;
  top: 5px;
}

.footer .right img {
  position: relative;
  margin-left: 0.5rem;
  top: 2px;
}

.footer .right {
  float: right;
  font-size: 0.85rem;
  line-height: 2rem;
  padding-right: 0.5rem;
}

.footer .positive-score {
  color: green;
  font-weight: bold;
  line-height: 1.75rem;
}

</style>