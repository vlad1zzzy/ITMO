package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.repository.CommentRepository;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void publish(User user, Post post, Comment comment) {
        comment.setUser(user);
        comment.setPost(post);
        commentRepository.save(comment);
    }

    public List<Comment> findAllByPostId(long id) {
        return commentRepository.findAllByPostIdOrderByCreationTimeDesc(id);
    }
}
