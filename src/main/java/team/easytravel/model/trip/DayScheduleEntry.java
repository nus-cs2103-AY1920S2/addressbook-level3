package team.easytravel.model.trip;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.Optional;

import team.easytravel.commons.core.time.DateTime;
import team.easytravel.model.listmanagers.activity.Activity;
import team.easytravel.model.listmanagers.transportbooking.TransportBooking;
import team.easytravel.model.trip.exception.IllegalOperationException;
import team.easytravel.model.util.attributes.Location;
import team.easytravel.model.util.attributes.Title;
import team.easytravel.model.util.uniquelist.UniqueListElement;

/**
 * Represents a DayScheduleEntry Object in the Trip
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class DayScheduleEntry implements UniqueListElement {

    public static final String MESSAGE_ERROR_ACTIVITY_SCHEDULING = "Activity to be scheduled does not contain a start"
            + " time.";
    private static final String MESSAGE_ERROR_GET_TRANSPORT_POINTER = "Cannot get transport booking pointer of a "
            + "non-transport type schedule entry.";
    private static final String MESSAGE_ERROR_GET_ACTIVITY_POINTER = "Cannot get activity pointer of a non-activity "
            + " type schedule entry.";

    /**
     * Represents the type of schedule entry.
     * ACTIVITY represents a activity and TRANSPORT represents a transport booking.
     */
    private enum Type {
        ACTIVITY("Activity"),
        TRANSPORT("Transport Booking");

        private final String string;

        Type(String string) {
            this.string = string;
        }

        @Override
        public String toString() {
            return string;
        }
    }

    private final Type type;
    private final Title title;
    private final DateTime startDateTime;
    private final DateTime endDateTime;
    private final long durationInHours;
    private final Location location;
    private final Optional<Activity> activityPointer;
    private final Optional<TransportBooking> transportBookingPointer;

    private DayScheduleEntry(
            Type type, Title title, DateTime startDateTime, DateTime endDateTime, long durationInHours,
            Location location, Optional<Activity> activityPointer, Optional<TransportBooking> transportBookingPointer) {
        this.type = type;
        this.title = title;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.durationInHours = durationInHours;
        this.location = location;
        this.activityPointer = activityPointer;
        this.transportBookingPointer = transportBookingPointer;
    }

    /**
     * Returns a DayScheduleEntry given an activity and a startTime.
     */
    public static DayScheduleEntry fromActivity(Activity activity) {
        requireNonNull(activity);
        DateTime startTime = activity.getScheduledDateTime().orElseThrow(() -> new IllegalOperationException(
                MESSAGE_ERROR_ACTIVITY_SCHEDULING));
        DateTime endTime = startTime.plusHours(activity.getDuration().value);
        return new DayScheduleEntry(
                Type.ACTIVITY, activity.getTitle(), startTime, endTime, activity.getDuration().value,
                activity.getLocation(), Optional.of(activity), Optional.empty());
    }

    /**
     * Returns a DayScheduleEntry given a transport booking.
     */
    public static DayScheduleEntry fromTransportBooking(TransportBooking transportBooking) {
        requireNonNull(transportBooking);
        DateTime startTime = transportBooking.getStartDateTime();
        DateTime endTime = transportBooking.getEndDateTime();
        long durationInHours = startTime.hoursUntilExclusive(endTime);
        return new DayScheduleEntry(Type.TRANSPORT, new Title(transportBooking.getMode().value), startTime, endTime,
                durationInHours, transportBooking.getStartLocation(), Optional.empty(), Optional.of(transportBooking));
    }

    public boolean isActivity() {
        return type.equals(Type.ACTIVITY);
    }

    public boolean isTransportBooking() {
        return type.equals(Type.TRANSPORT);
    }

    public String getType() {
        return type.toString();
    }

    public Title getTitle() {
        return title;
    }

    public DateTime getStartDateTime() {
        return startDateTime;
    }

    public DateTime getEndDateTime() {
        return endDateTime;
    }

    public long getDurationInHours() {
        return durationInHours;
    }

    public Location getLocation() {
        return location;
    }

    public Activity getActivityPointer() {
        if (!isActivity()) {
            throw new IllegalOperationException(MESSAGE_ERROR_GET_ACTIVITY_POINTER);
        }
        return activityPointer.orElseThrow(() -> new AssertionError(
                "Activity pointer in DayScheduleEntry will not be null"));
    }

    public TransportBooking getTransportBookingPointer() {
        if (!isTransportBooking()) {
            throw new IllegalOperationException(MESSAGE_ERROR_GET_TRANSPORT_POINTER);
        }
        return transportBookingPointer.orElseThrow(() -> new AssertionError(
                "Transport booking pointer in DayScheduleEntry will not be null"));
    }

    /**
     * Returns true if this schedule entry overlaps with the other entry.
     */
    public boolean isOverlapping(DayScheduleEntry other) {
        DateTime otherStartDateTime = other.getStartDateTime();
        DateTime otherEndDateTime = other.getEndDateTime();

        DateTime max = endDateTime;
        if (otherEndDateTime.compareTo(max) > 0) {
            max = otherEndDateTime;
        }

        DateTime min = startDateTime;
        if (otherStartDateTime.compareTo(min) < 0) {
            min = otherStartDateTime;
        }
        long thisRange = startDateTime.hoursUntilExclusive(endDateTime);
        long otherRange = otherStartDateTime.hoursUntilExclusive(otherEndDateTime);
        long allowedRange = min.hoursUntilExclusive(max);

        return allowedRange < thisRange + otherRange;
    }

    @Override
    public boolean isSame(UniqueListElement other) {
        return equals(other);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof DayScheduleEntry)) {
            return false;
        }

        DayScheduleEntry otherEntry = (DayScheduleEntry) o;
        return type.equals(otherEntry.type)
                && title.equals(otherEntry.title)
                && startDateTime.equals(otherEntry.startDateTime)
                && endDateTime.equals(otherEntry.endDateTime)
                && location.equals(otherEntry.location)
                && durationInHours == otherEntry.durationInHours
                && activityPointer.equals(otherEntry.activityPointer)
                && transportBookingPointer.equals(otherEntry.transportBookingPointer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, title, startDateTime, endDateTime, durationInHours, location, activityPointer,
                transportBookingPointer);
    }

    @Override
    public String toString() {
        return "DayScheduleEntry{"
                + "type=" + type
                + ", title=" + title
                + ", startDateTime=" + startDateTime
                + ", endDateTime=" + endDateTime
                + ", durationInHours=" + durationInHours
                + ", location=" + location
                + ", activityPointer=" + activityPointer
                + ", transportBookingPointer=" + transportBookingPointer
                + '}';
    }
}
