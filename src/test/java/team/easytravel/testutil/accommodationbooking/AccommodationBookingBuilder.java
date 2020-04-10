package team.easytravel.testutil.accommodationbooking;

import team.easytravel.model.listmanagers.accommodationbooking.AccommodationBooking;
import team.easytravel.model.listmanagers.accommodationbooking.AccommodationName;
import team.easytravel.model.listmanagers.accommodationbooking.Day;
import team.easytravel.model.listmanagers.accommodationbooking.Remark;
import team.easytravel.model.util.attributes.Location;

/**
 * A utility class to help with building Accommodation Booking objects.
 */
public class AccommodationBookingBuilder {

    public static final String DEFAULT_ACCOMMODATION_NAME = "Ritz Carlton";
    public static final String DEFAULT_LOCATION = "KL";
    public static final Integer DEFAULT_START_DAY = 1;
    public static final Integer DEFAULT_END_DAY = 4;
    public static final String DEFAULT_REMARK = "Check-in at 2pm.";

    private AccommodationName accommodationName;
    private Location location;
    private Day startDay;
    private Day endDay;
    private Remark remark;

    public AccommodationBookingBuilder() {
        accommodationName = new AccommodationName(DEFAULT_ACCOMMODATION_NAME);
        location = new Location(DEFAULT_LOCATION);
        startDay = new Day(DEFAULT_START_DAY);
        endDay = new Day(DEFAULT_END_DAY);
        remark = new Remark(DEFAULT_REMARK);
    }

    /**
     * Initializes the AccommodationBookingBuilder with the data of {@code accommodationBooking}.
     */
    public AccommodationBookingBuilder(AccommodationBooking accommodationBooking) {
        accommodationName = accommodationBooking.getAccommodationName();
        location = accommodationBooking.getLocation();
        startDay = accommodationBooking.getStartDay();
        endDay = accommodationBooking.getEndDay();
        remark = accommodationBooking.getRemark();
    }

    /**
     * Sets the {@code accommodationName} of the {@code AccommodationBooking} that we are building.
     */
    public AccommodationBookingBuilder withAccommodationName(String accommodationName) {
        this.accommodationName = new AccommodationName(accommodationName);
        return this;
    }

    /**
     * Sets the {@code location} of the {@code AccommodationBooking} that we are building.
     */
    public AccommodationBookingBuilder withLocation(String location) {
        this.location = new Location(location);
        return this;
    }

    /**
     * Sets the {@code startDay} of the {@code AccommodationBooking} that we are building.
     */
    public AccommodationBookingBuilder withStartDay(Integer startDay) {
        this.startDay = new Day(startDay);
        return this;
    }

    /**
     * Sets the {@code endDay} of the {@code AccommodationBooking} that we are building.
     */
    public AccommodationBookingBuilder withEndDay(Integer endDay) {
        this.endDay = new Day(endDay);
        return this;
    }

    /**
     * Sets the {@code remark} of the {@code AccommodationBooking} that we are building.
     */
    public AccommodationBookingBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    public AccommodationBooking build() {
        return new AccommodationBooking(accommodationName, location, startDay, endDay, remark);
    }

}
