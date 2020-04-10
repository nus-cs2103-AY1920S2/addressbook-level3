package team.easytravel.storage.accommodationbooking;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import team.easytravel.commons.exceptions.IllegalValueException;
import team.easytravel.model.listmanagers.accommodationbooking.AccommodationBooking;
import team.easytravel.model.listmanagers.accommodationbooking.AccommodationName;
import team.easytravel.model.listmanagers.accommodationbooking.Day;
import team.easytravel.model.listmanagers.accommodationbooking.Remark;
import team.easytravel.model.util.attributes.Location;

/**
 * Jackson-friendly version of {@link AccommodationBooking}.
 */
public class JsonAdaptedAccommodationBooking {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Accommodation Booking's %s field is missing!";

    private final String name;
    private final String location;
    private final Integer startDay;
    private final Integer endDay;
    private final String remark;

    /**
     * Constructs a {@code JsonAdaptedAccommodationBooking} with the given details.
     */
    @JsonCreator
    public JsonAdaptedAccommodationBooking(
            @JsonProperty("name") String name, @JsonProperty("location") String location,
            @JsonProperty("startDay") Integer startDay, @JsonProperty("endDay") Integer endDay,
            @JsonProperty("remark") String remark) {
        this.name = name;
        this.location = location;
        this.startDay = startDay;
        this.endDay = endDay;
        this.remark = remark;
    }

    /**
     * Converts a given {@code AccommodationBooking} into this class for Jackson use.
     */
    public JsonAdaptedAccommodationBooking(AccommodationBooking source) {
        name = source.getAccommodationName().value;
        location = source.getLocation().value;
        startDay = source.getStartDay().value;
        endDay = source.getEndDay().value;
        remark = source.getRemark().value;
    }

    /**
     * Converts this Jackson-friendly adapted accommodation booking object into the
     * model's {@code AccommodationBooking} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted accommodation booking.
     */
    public AccommodationBooking toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    AccommodationName.class.getSimpleName()));
        }
        if (!AccommodationName.isValidName(name)) {
            throw new IllegalValueException(AccommodationName.MESSAGE_CONSTRAINTS);
        }
        final AccommodationName modelAccommodationName = new AccommodationName(name);

        if (location == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Location.class.getSimpleName()));
        }
        if (!Location.isValidLocation(location)) {
            throw new IllegalValueException(Location.MESSAGE_CONSTRAINTS);
        }
        final Location modelLocation = new Location(location);

        if (startDay == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Day.class.getSimpleName()));
        }
        if (!Day.isValidDay(startDay)) {
            throw new IllegalValueException(Day.MESSAGE_CONSTRAINTS);
        }
        final Day modelStartDay = new Day(startDay);

        if (endDay == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Day.class.getSimpleName()));
        }
        if (!Day.isValidDay(endDay)) {
            throw new IllegalValueException(Day.MESSAGE_CONSTRAINTS);
        }
        final Day modelEndDay = new Day(endDay);

        if (remark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName()));
        }
        if (!Remark.isValidRemark(remark)) {
            throw new IllegalValueException(Remark.MESSAGE_CONSTRAINTS);
        }
        final Remark modelRemark = new Remark(remark);

        return new AccommodationBooking(modelAccommodationName, modelLocation, modelStartDay, modelEndDay, modelRemark);
    }

}
