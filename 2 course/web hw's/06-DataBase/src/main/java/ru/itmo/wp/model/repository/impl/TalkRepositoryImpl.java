package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.repository.TalkRepository;
import ru.itmo.wp.model.repository.BaseRepository;

import java.sql.*;
import java.util.List;

public class TalkRepositoryImpl extends BaseRepository<Talk> implements TalkRepository {

    public TalkRepositoryImpl() {
        super(Talk.class.getSimpleName());
    }

    public List<Talk> findAllById(long id) {
        return findAllBy(
                "SELECT * FROM Talk WHERE sourceUserId=? or targetUserId=? ORDER BY id DESC",
                new Object[]{id, id}
        );
    }

    public Talk toThing(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        Talk talk = new Talk();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    talk.setId(resultSet.getLong(i));
                    break;
                case "sourceUserId":
                    talk.setSourceUserId(resultSet.getLong(i));
                    break;
                case "targetUserId":
                    talk.setTargetUserId(resultSet.getLong(i));
                    break;
                case "text":
                    talk.setText(resultSet.getString(i));
                    break;
                case "creationTime":
                    talk.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }
        return talk;
    }

    public void save(Talk talk) {
        saveThing(
                talk,
                "INSERT INTO `Talk` (`sourceUserId`,`targetUserId`, `text`, `creationTime`) VALUES (?, ?, ?, NOW())",
                new Object[]{talk.getSourceUserId(), talk.getTargetUserId(), talk.getText()}
        );
    }
}
