package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.security.Guest;
import ru.itmo.wp.service.CommentService;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class PostPage extends Page {
    private final PostService postService;
    private final CommentService commentService;

    public PostPage(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @Guest
    @GetMapping("/post/{id}")
    public String postGet(Model model, HttpSession httpSession, @PathVariable String id) {
        try {
            long postId = Long.parseLong(id);
            Post post = postService.find(postId);
            if (post == null) {
                putMessage(httpSession, "No such post!");
                return "NoSuchPostPage";
            }
            model.addAttribute("post", post);
            model.addAttribute("comment", new Comment());
            model.addAttribute("comments", commentService.findAllByPostId(postId));
        } catch (NumberFormatException e) {
            putMessage(httpSession, "Invalid post id!");
            return "redirect:/";
        }
        return "PostPage";
    }

    @PostMapping("/post/{id}")
    public String comment(@PathVariable long id,
                          @Valid @ModelAttribute("comment") Comment comment,
                          BindingResult bindingResult,
                          HttpSession httpSession
                          ) {
        if (bindingResult.hasErrors()) {
            putMessage(httpSession, "You can not publish this comment!");
            return "redirect:/post/" + id;
        }

        commentService.publish(getUser(httpSession), postService.find(id), comment);
        putMessage(httpSession, "Your comment was posted!");

        return "redirect:/post/" + id;
    }
}
