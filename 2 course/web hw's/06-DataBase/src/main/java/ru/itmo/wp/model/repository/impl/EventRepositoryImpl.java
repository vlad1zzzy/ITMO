package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.repository.EventRepository;
import ru.itmo.wp.model.repository.BaseRepository;

import java.sql.*;

public class EventRepositoryImpl extends BaseRepository<Event> implements EventRepository {

    public EventRepositoryImpl() {
        super(Event.class.getSimpleName());
    }

    protected Event toThing(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        Event event = new Event();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    event.setId(resultSet.getLong(i));
                    break;
                case "userId":
                    event.setUserId(resultSet.getLong(i));
                    break;
                case "creationTime":
                    event.setCreationTime(resultSet.getTimestamp(i));
                    break;
                case "type":
                    event.setType(Event.EventType.valueOf(resultSet.getString(i)));
                    break;
                default:
                    // No operations.
            }
        }
        return event;
    }


    @Override
    public void save(Event event) {
        saveThing(
                event,
                "INSERT INTO `Event` (`userId`, `type`, `creationTime`) VALUES (?, ?, NOW())",
                new Object[]{event.getUserId(), event.getType().toString()}
        );
    }
}
