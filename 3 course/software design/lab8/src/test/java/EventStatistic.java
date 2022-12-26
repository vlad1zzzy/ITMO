import clock.ClockImpl;
import event.EventsStatistic;
import event.EventsStatisticImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventStatistic {
    private final double ONE_PER_SECOND = 1;
    private final double ONE_PER_MINUTE = ONE_PER_SECOND / 60;
    private ClockImpl clock;
    private EventsStatistic statistic;

    @BeforeEach
    void init() {
        clock = new ClockImpl();
        statistic = new EventsStatisticImpl(clock);
    }

    @Test
    void unknown() {
        assertEquals(0.0, statistic.getEventStatisticByName("qwerty"));
    }


    @Test
    void simple() {
        final String name = "simple";
        statistic.incEvent(name);
        assertEquals(ONE_PER_MINUTE, statistic.getEventStatisticByName(name));
    }

    @Test
    void multi() {
        final int times = 10;
        final String name = "multi";

        for (int i = 0; i < times; i++) {
            statistic.incEvent(name);
        }

        assertEquals(ONE_PER_MINUTE * times, statistic.getEventStatisticByName(name));
    }

    @Test
    void different() {
        final int times = 10;
        final String name = "multi";

        for (int i = 0; i < times; i++) {
            statistic.incEvent(name);
            statistic.incEvent(name + 1);
            statistic.incEvent(name + 2);
        }

        assertEquals(ONE_PER_MINUTE * times, statistic.getEventStatisticByName(name));
    }

    @Test
    void hour() {
        final String name = "hour";

        statistic.incEvent(name);

        clock.setNow(Instant.now().plus(Duration.ofHours(1)));

        assertEquals(0, statistic.getEventStatisticByName(name));
    }

    @Test
    void all() {
        final String x = "x";
        final String y = "y";
        final String z = "z";

        final int t1 = 8;
        final int t2 = 5;
        final int t3 = 3;

        Instant now = Instant.now();

        clock.setNow(now.minus(Duration.ofMinutes(60)));

        for (int i = 0; i < t1; i++) {
            statistic.incEvent(x);
            statistic.incEvent(y);
            statistic.incEvent(z);
        }

        clock.setNow(now.minus(Duration.ofMinutes(30)));

        for (int i = 0; i < t2; i++) {
            statistic.incEvent(y);
            statistic.incEvent(z);
        }

        clock.setNow(now);

        for (int i = 0; i < t3; i++) {
            statistic.incEvent(z);
        }

        assertEquals(new HashMap<>() {{
            put(x, 0 * ONE_PER_MINUTE);
            put(y, t2 * ONE_PER_MINUTE);
            put(z, (t2 + t3) * ONE_PER_MINUTE);
        }}, statistic.getAllEventStatistic());
    }
}
