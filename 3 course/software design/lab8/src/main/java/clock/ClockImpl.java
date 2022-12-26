package clock;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public class ClockImpl extends Clock {
    private Instant now;

    public ClockImpl() {
        setNow(Instant.now());
    }

    @Override
    public ZoneId getZone() {
        return ZoneId.systemDefault();
    }

    @Override
    public java.time.Clock withZone(final ZoneId zone) {
        return null;
    }

    @Override
    public Instant instant() {
        return now;
    }

    public void setNow(final Instant now) {
        this.now = now;
    }
}
