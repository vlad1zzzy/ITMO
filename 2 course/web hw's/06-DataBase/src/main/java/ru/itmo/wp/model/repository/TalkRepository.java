package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Talk;

import java.util.List;

public interface TalkRepository extends ThingRepository<Talk> {
    List<Talk> findAllById(long id);
}
