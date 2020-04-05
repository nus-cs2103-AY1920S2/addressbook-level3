package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;

/**
 * Preferences and settings of scheduler
 */
public class EventSchedulePrefs {
    private EventScheduleView eventScheduleView;
    private LocalDateTime localDateTime;

    public EventSchedulePrefs(EventScheduleView eventScheduleView, LocalDateTime localDateTime) {
        requireNonNull(eventScheduleView);
        requireNonNull(localDateTime);
        this.eventScheduleView = eventScheduleView;
        this.localDateTime = localDateTime;
    }

    public EventScheduleView getEventScheduleView() {
        return eventScheduleView;
    }

    public void setEventScheduleView(EventScheduleView eventScheduleView) {
        this.eventScheduleView = eventScheduleView;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EventSchedulePrefs)) {
            return false;
        }

        EventSchedulePrefs otherPrefs = (EventSchedulePrefs) other;
        return otherPrefs.getLocalDateTime().equals(getLocalDateTime())
                && otherPrefs.getEventScheduleView().equals(getEventScheduleView());
    }

    @Override
    public String toString() {
        return eventScheduleView.name() + " " + localDateTime.toLocalDate().toString();
    }
}
