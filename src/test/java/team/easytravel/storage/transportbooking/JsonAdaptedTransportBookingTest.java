package team.easytravel.storage.transportbooking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static team.easytravel.testutil.Assert.assertThrows;
import static team.easytravel.testutil.TypicalTransportBooking.TRANSPORT_BOOKING_BUS;

import org.junit.jupiter.api.Test;

import team.easytravel.commons.core.time.DateTime;
import team.easytravel.commons.exceptions.IllegalValueException;
import team.easytravel.model.listmanagers.transportbooking.Mode;
import team.easytravel.model.util.attributes.Location;

class JsonAdaptedTransportBookingTest {

    public static final String INVALID_MODE = "T!T!@#E";
    public static final String INVALID_START_LOCATION = "S!NG@P@RE";
    public static final String INVALID_END_LOCATION = "M)&SC&W!";
    public static final String INVALID_START_DATE_TIME = "#21qwe-03-21321";
    public static final String INVALID_END_DATE_TIME = "21qwe-03-21321";

    public static final String VALID_MODE = TRANSPORT_BOOKING_BUS.getMode().value;
    public static final String VALID_START_LOCATION = TRANSPORT_BOOKING_BUS.getStartLocation().value;
    public static final String VALID_END_LOCATION = TRANSPORT_BOOKING_BUS.getEndLocation().value;
    public static final String VALID_START_DATE_TIME = TRANSPORT_BOOKING_BUS.getStartDateTime().toString();
    public static final String VALID_END_DATE_TIME = TRANSPORT_BOOKING_BUS.getEndDateTime().toString();


    @Test
    public void toModelType_validTransportBookingDetails_returnsActivity() throws Exception {
        JsonAdaptedTransportBooking transportBooking = new JsonAdaptedTransportBooking(TRANSPORT_BOOKING_BUS);
        assertEquals(TRANSPORT_BOOKING_BUS, transportBooking.toModelType());
    }

    @Test
    public void toModelType_invalidMode_throwsIllegalValueException() {
        JsonAdaptedTransportBooking transportBooking =
                new JsonAdaptedTransportBooking(INVALID_MODE, VALID_START_LOCATION, VALID_END_LOCATION,
                        VALID_START_DATE_TIME, VALID_END_DATE_TIME);
        String exceptedMessage = Mode.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, exceptedMessage, transportBooking::toModelType);
    }


    @Test
    public void toModelType_invalidStartLocation_throwsIllegalValueException() {
        JsonAdaptedTransportBooking transportBooking =
                new JsonAdaptedTransportBooking(VALID_MODE, INVALID_START_LOCATION, VALID_END_LOCATION,
                        VALID_START_DATE_TIME, VALID_END_DATE_TIME);
        String expectedMessage = Location.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, transportBooking::toModelType);
    }


    @Test
    public void toModelType_invalidEndLocation_throwsIllegalValueException() {
        JsonAdaptedTransportBooking transportBooking =
                new JsonAdaptedTransportBooking(VALID_MODE, VALID_START_LOCATION, INVALID_END_LOCATION,
                        VALID_START_DATE_TIME, VALID_END_DATE_TIME);
        String expectedMessage = Location.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, transportBooking::toModelType);
    }


    @Test
    public void toModelType_invalidStartDateTime_throwsIllegalValueException() {
        JsonAdaptedTransportBooking transportBooking =
                new JsonAdaptedTransportBooking(VALID_MODE, VALID_START_LOCATION, VALID_END_LOCATION,
                        INVALID_START_DATE_TIME, VALID_END_DATE_TIME);
        String expectedMessage = DateTime.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, transportBooking::toModelType);
    }

    @Test
    public void toModelType_invalidEndDateTime_throwsIllegalValueException() {
        JsonAdaptedTransportBooking transportBooking =
                new JsonAdaptedTransportBooking(VALID_MODE, VALID_START_LOCATION, VALID_END_LOCATION,
                        VALID_START_DATE_TIME, INVALID_END_DATE_TIME);
        String expectedMessage = DateTime.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, transportBooking::toModelType);
    }
}
