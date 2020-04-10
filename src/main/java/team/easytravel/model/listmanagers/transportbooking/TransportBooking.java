package team.easytravel.model.listmanagers.transportbooking;

import java.util.Objects;

import team.easytravel.commons.core.time.DateTime;
import team.easytravel.commons.util.AppUtil;
import team.easytravel.commons.util.CollectionUtil;
import team.easytravel.model.util.attributes.Location;
import team.easytravel.model.util.uniquelist.UniqueListElement;

/**
 * Represents a TransportBooking in the TransportBookingManager.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class TransportBooking implements UniqueListElement {

    public static final String MESSAGE_LOCATION_CONSTRAINTS = "Start location cannot be equal to end location.";
    public static final String MESSAGE_TIME_CONSTRAINTS = "Start time must come before end time.";

    private final Mode mode;
    private final Location startLocation;
    private final Location endLocation;
    private final DateTime startDateTime;
    private final DateTime endDateTime;

    /**
     * Every field must be present and not null.
     */
    public TransportBooking(Mode mode, Location startLocation, Location endLocation, DateTime startDateTime,
                            DateTime endDateTime) {
        CollectionUtil.requireAllNonNull(mode, startLocation, endLocation, startDateTime, endDateTime);
        AppUtil.checkArgument(areLocationsValid(startLocation, endLocation), MESSAGE_LOCATION_CONSTRAINTS);
        AppUtil.checkArgument(isTimeValid(startDateTime, endDateTime), MESSAGE_TIME_CONSTRAINTS);
        this.mode = mode;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public Mode getMode() {
        return mode;
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public Location getEndLocation() {
        return endLocation;
    }

    public DateTime getStartDateTime() {
        return startDateTime;
    }

    public DateTime getEndDateTime() {
        return endDateTime;
    }

    /**
     * Returns true if the specified start and end location are different.
     */
    public boolean areLocationsValid(Location startLocation, Location endLocation) {
        return !startLocation.equals(endLocation);
    }

    /**
     * Returns true if the specified start time is before the specified end time..
     */
    public boolean isTimeValid(DateTime startDateTime, DateTime endDateTime) {
        return startDateTime.compareTo(endDateTime) < 0;
    }

    /**
     * Returns true if this transport booking is equal to {@code other} as per {@link #equals(Object)}.
     * This defines an equality between two transport bookings.
     */
    @Override
    public boolean isSame(UniqueListElement other) {
        return equals(other);
    }

    /**
     * Returns true if both transport bookings have the same data fields.
     * This defines an equality between two transport bookings.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TransportBooking)) {
            return false;
        }

        TransportBooking otherTransportBooking = (TransportBooking) other;
        return mode.equals(otherTransportBooking.mode)
                && startLocation.equals(otherTransportBooking.startLocation)
                && endLocation.equals(otherTransportBooking.endLocation)
                && startDateTime.equals(otherTransportBooking.startDateTime)
                && endDateTime.equals(otherTransportBooking.endDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mode, startLocation, endLocation, startDateTime, endDateTime);
    }

    @Override
    public String toString() {
        return "TransportBooking - Mode: " + mode
                + " Start Location: " + startLocation
                + " End Location: " + endLocation
                + " Start Date-Time: " + startDateTime
                + " End Date-Time: " + endDateTime;
    }
}
