package team.easytravel.model.listmanagers.accommodationbooking;

import java.util.Objects;

import team.easytravel.commons.util.AppUtil;
import team.easytravel.commons.util.CollectionUtil;
import team.easytravel.model.util.attributes.Location;
import team.easytravel.model.util.uniquelist.UniqueListElement;

/**
 * Represents an AccommodationBooking in the AccommodationBookingManager.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class AccommodationBooking implements UniqueListElement {

    public static final String MESSAGE_DAY_CONSTRAINTS = "Start day must come before end day.";

    // Identity
    private final AccommodationName accommodationName;
    private final Day startDay;
    private final Day endDay;
    private final Location location;

    // Data
    private final Remark remark;

    /**
     * Every field must be present and not null.
     */
    public AccommodationBooking(AccommodationName accommodationName, Location location, Day startDay, Day endDay,
                                Remark remark) {
        CollectionUtil.requireAllNonNull(accommodationName, location, startDay, endDay, remark);
        AppUtil.checkArgument(isDayValid(startDay, endDay), MESSAGE_DAY_CONSTRAINTS);
        this.accommodationName = accommodationName;
        this.location = location;
        this.startDay = startDay;
        this.endDay = endDay;
        this.remark = remark;
    }

    public AccommodationName getAccommodationName() {
        return accommodationName;
    }

    public Location getLocation() {
        return location;
    }

    public Day getStartDay() {
        return startDay;
    }

    public Day getEndDay() {
        return endDay;
    }

    public Remark getRemark() {
        return remark;
    }

    // TODO: May want to check if day is within the trip's day here

    /**
     * Returns true if the specified start day is before the specified end day.
     */
    public boolean isDayValid(Day startDay, Day endDay) {
        return endDay.value - startDay.value >= 0;
    }

    /**
     * Returns true if an accommodation booking overlaps with another booking.
     */
    public boolean isOverlapping (AccommodationBooking other) {
        int otherStartDay = other.getStartDay().value;
        int otherEndDay = other.getEndDay().value;
        int thisStartDay = this.startDay.value;
        int thisEndDay = this.endDay.value;

        int max = Math.max(otherEndDay, thisEndDay);
        int min = Math.min(otherStartDay, thisStartDay);

        return (max - min) < ((thisEndDay - thisStartDay) + (otherEndDay - otherStartDay));
    }

    /**
     * Returns true if both accommodation bookings has the same identity fields.
     * This defines a weaker notion of equality between two accommodation bookings.
     */
    @Override
    public boolean isSame(UniqueListElement other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AccommodationBooking)) {
            return false;
        }

        AccommodationBooking otherAccommodationBooking = (AccommodationBooking) other;
        return accommodationName.equals(otherAccommodationBooking.accommodationName)
                && location.equals(otherAccommodationBooking.location)
                && startDay.equals(otherAccommodationBooking.startDay)
                && endDay.equals(otherAccommodationBooking.endDay);
    }

    /**
     * Returns true if both accommodation bookings have the same identity and data fields.
     * This defines a stronger notion of equality between two items.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AccommodationBooking)) {
            return false;
        }

        AccommodationBooking otherAccommodationBooking = (AccommodationBooking) other;
        return accommodationName.equals(otherAccommodationBooking.accommodationName)
                && location.equals(otherAccommodationBooking.location)
                && startDay.equals(otherAccommodationBooking.startDay)
                && endDay.equals(otherAccommodationBooking.endDay)
                && remark.equals(otherAccommodationBooking.remark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accommodationName, location, startDay, endDay, remark);
    }

    @Override
    public String toString() {
        return "Accommodation Booking - Name: " + accommodationName
                + " Location: " + location
                + " Start Day: " + startDay
                + " End Day: " + endDay
                + " Remark: " + remark;
    }
}
