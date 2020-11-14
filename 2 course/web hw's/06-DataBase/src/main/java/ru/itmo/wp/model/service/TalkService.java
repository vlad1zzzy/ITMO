package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.TalkRepository;
import ru.itmo.wp.model.repository.impl.TalkRepositoryImpl;

/**
 * @noinspection UnstableApiUsage
 */
public class TalkService {
    private final TalkRepository talkRepository = new TalkRepositoryImpl();

    public void validateTalk(Talk talk) throws ValidationException {
        if (Strings.isNullOrEmpty(talk.getText())) {
            throw new ValidationException("Text is required");
        }
        if (talk.getText().length() > 255) {
            throw new ValidationException("Message too long");
        }
    }

    public void validateAndSave(Talk talk) throws ValidationException {
        validateTalk(talk);
        talkRepository.save(talk);
    }

}
