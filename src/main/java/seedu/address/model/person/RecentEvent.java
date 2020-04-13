package seedu.address.model.person;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class is used to encompass a recent event,
 * consisting of an ID, place, activity and duration for display.
 */
public class RecentEvent {

    private final StringProperty eventId;
    private final StringProperty place;
    private final StringProperty activity;
    private final StringProperty time;

    public RecentEvent(String eventId, String place, String activity, String time) {
        this.eventId = new SimpleStringProperty(eventId);
        this.place = new SimpleStringProperty(place);
        this.activity = new SimpleStringProperty(activity);
        this.time = new SimpleStringProperty(time);
    }

    public String getEventId() {
        return eventId.get();
    }

    public String getPlace() {
        return place.get();
    }

    public String getActivity() {
        return activity.get();
    }

    public String getTime() {
        return time.get();
    }

    public StringProperty placeProperty() {
        return place;
    }

    public StringProperty activityProperty() {
        return activity;
    }

    public StringProperty timeProperty() {
        return time;
    }

    public StringProperty eventIdProperty() {
        return eventId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RecentEvent)) {
            return false;
        }
        return ((RecentEvent) o).getPlace().equals(this.getPlace())
                && ((RecentEvent) o).getActivity().equals(this.getActivity())
                && ((RecentEvent) o).getTime().equals(this.getTime())
                && ((RecentEvent) o).getEventId().equals(this.getEventId());
    }
}
