package team.easytravel.storage.transportbooking;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import team.easytravel.commons.core.time.DateTime;
import team.easytravel.commons.exceptions.IllegalValueException;
import team.easytravel.model.listmanagers.transportbooking.Mode;
import team.easytravel.model.listmanagers.transportbooking.TransportBooking;
import team.easytravel.model.util.attributes.Location;

/**
 * Jackson-friendly version of {@link TransportBooking}.
 */
class JsonAdaptedTransportBooking {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "TransportBooking's %s field is missing!";

    private final String mode;
    private final String startLocation;
    private final String endLocation;
    private final String startDateTime;
    private final String endDateTime;

    /**
     * Constructs a {@code JsonAdaptedTransportBooking} with the given details.
     */
    @JsonCreator
    public JsonAdaptedTransportBooking(@JsonProperty("mode") String mode,
                                       @JsonProperty("startLocation") String startLocation,
                                       @JsonProperty("endLocation") String endLocation,
                                       @JsonProperty("startDateTime") String startDateTime,
                                       @JsonProperty("endDateTime") String endDateTime) {
        this.mode = mode;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    /**
     * Converts a given {@code TransportBooking} into this class for Jackson use.
     */
    public JsonAdaptedTransportBooking(TransportBooking source) {
        mode = source.getMode().value;
        startLocation = source.getStartLocation().value;
        endLocation = source.getEndLocation().value;
        startDateTime = source.getStartDateTime().getStorageFormat();
        endDateTime = source.getEndDateTime().getStorageFormat();
    }

    /**
     * Converts this Jackson-friendly adapted transport booking object into the model's {@code TransportBooking} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted transport booking.
     */
    public TransportBooking toModelType() throws IllegalValueException {
        if (mode == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Mode.class.getSimpleName()));
        }
        if (!Mode.isValidMode(mode)) {
            throw new IllegalValueException(Mode.MESSAGE_CONSTRAINTS);
        }
        final Mode modelMode = new Mode(mode);

        if (startLocation == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Location.class.getSimpleName()));
        }
        if (!Location.isValidLocation(startLocation)) {
            throw new IllegalValueException(Location.MESSAGE_CONSTRAINTS);
        }
        final Location modelStartLocation = new Location(startLocation);

        if (endLocation == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Location.class.getSimpleName()));
        }
        if (!Location.isValidLocation(endLocation)) {
            throw new IllegalValueException(Location.MESSAGE_CONSTRAINTS);
        }
        final Location modelEndLocation = new Location(endLocation);

        if (startDateTime == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, DateTime.class.getSimpleName()));
        }
        if (!DateTime.isValidDateTime(startDateTime)) {
            throw new IllegalValueException(DateTime.MESSAGE_CONSTRAINTS);
        }
        DateTime modelStartDateTime = DateTime.fromString(startDateTime);

        if (endDateTime == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, DateTime.class.getSimpleName()));
        }
        if (!DateTime.isValidDateTime(endDateTime)) {
            throw new IllegalValueException(DateTime.MESSAGE_CONSTRAINTS);
        }
        DateTime modelEndDateTime = DateTime.fromString(endDateTime);

        return new TransportBooking(
                modelMode, modelStartLocation, modelEndLocation, modelStartDateTime, modelEndDateTime);
    }

}
