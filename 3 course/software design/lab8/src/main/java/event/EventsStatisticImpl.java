package event;

import clock.ClockImpl;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EventsStatisticImpl implements EventsStatistic {
    private final ClockImpl clock;
    private final Map<String, List<Instant>> events = new HashMap<>();

    public EventsStatisticImpl() {
        this.clock = new ClockImpl();
    }

    public EventsStatisticImpl(final ClockImpl clock) {
        this.clock = clock;
    }

    @Override
    public void incEvent(String name) {
        var instants = events.getOrDefault(name, new ArrayList<>());
        instants.add(clock.instant());
        events.put(name, instants);
    }
    @Override
    public double getEventStatisticByName(String name) {
        var instants = events.get(name);

        return instants == null ? 0 : getRM(instants);
    }

    @Override
    public Map<String, Double> getAllEventStatistic() {
        return events
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> getRM(entry.getValue())));
    }

    @Override
    public void printStatistic() {
        getAllEventStatistic().forEach((name, value) -> System.out.println(name + ": " + value));
    }

    public double getRM(List<Instant> instants) {
        var hourTime = clock.instant().minus(Duration.ofHours(1));
        var hourStat = instants
                .stream()
                .filter(instant -> instant.isAfter(hourTime))
                .collect(Collectors.toList());
        return hourStat.size() / 60.0;
    }
}
