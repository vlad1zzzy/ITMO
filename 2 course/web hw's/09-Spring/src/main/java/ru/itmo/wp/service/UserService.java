package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.PostForm;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.repository.RoleRepository;
import ru.itmo.wp.repository.TagsRepository;
import ru.itmo.wp.repository.UserRepository;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;

    /** @noinspection FieldCanBeLocal, unused */
    private final RoleRepository roleRepository;
    private final TagsRepository tagsRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, TagsRepository tagsRepository) {
        this.userRepository = userRepository;

        this.roleRepository = roleRepository;
        this.tagsRepository = tagsRepository;
        for (Role.Name name : Role.Name.values()) {
            if (roleRepository.countByName(name) == 0) {
                roleRepository.save(new Role(name));
            }
        }
    }

    public User register(UserCredentials userCredentials) {
        User user = new User();
        user.setLogin(userCredentials.getLogin());
        userRepository.save(user);
        userRepository.updatePasswordSha(user.getId(), userCredentials.getLogin(), userCredentials.getPassword());
        return user;
    }

    public boolean isLoginVacant(String login) {
        return userRepository.countByLogin(login) == 0;
    }

    public User findByLoginAndPassword(String login, String password) {
        return login == null || password == null ? null : userRepository.findByLoginAndPassword(login, password);
    }

    public User findById(Long id) {
        return id == null ? null : userRepository.findById(id).orElse(null);
    }

    public List<User> findAll() {
        return userRepository.findAllByOrderByIdDesc();
    }

    public void writePost(User user, PostForm postForm) {
        Post post = new Post();
        post.setTitle(postForm.getTitle());
        post.setText(postForm.getText());
        Set<Tag> tags = new HashSet<>();

        String tagsNames = postForm.getTags();
        for (String tagName : tagsNames.trim().split("\\s+")) {
            if (tagName.isEmpty()) {
                break;
            }
            Tag tag = new Tag();
            tag.setName(tagName);
            Tag foundTag = tagsRepository.findByName(tag.getName());
            if (foundTag == null) {
                tagsRepository.save(tag);
            } else {
                tag.setId(foundTag.getId());
            }
            tags.add(tag);
        }
        post.setTags(tags);
        user.addPost(post);
        userRepository.save(user);
    }
}
