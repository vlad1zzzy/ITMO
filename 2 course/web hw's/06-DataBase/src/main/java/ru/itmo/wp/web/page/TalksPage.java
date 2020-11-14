package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.TalkRepository;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.TalkRepositoryImpl;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;
import ru.itmo.wp.model.service.TalkService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class TalksPage extends Page {
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final TalkRepository talkRepository = new TalkRepositoryImpl();
    private final TalkService talkService = new TalkService();

    void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
        User user = getUser(request);
        if (user == null) {
            request.getSession().setAttribute("message", "Talks page available only for authorized users");
            throw new RedirectException("/index");
        } else {
            view.put("users", userRepository.findAll());
            view.put("talks", talkRepository.findAllById(user.getId()));
        }
    }

    void send(HttpServletRequest request) throws ValidationException {
        User sourceUser = getUser(request);
        User targetUser = userRepository.findByLoginOrEmail(request.getParameter("addressee"), "login");

        Talk talk = new Talk();
        talk.setSourceUserId(sourceUser.getId());
        talk.setTargetUserId(targetUser.getId());
        talk.setText(request.getParameter("text"));

        talkService.validateAndSave(talk);

        throw new RedirectException("/talks");
    }
}
