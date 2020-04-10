package team.easytravel.storage.accommodationbooking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static team.easytravel.testutil.Assert.assertThrows;
import static team.easytravel.testutil.TypicalAccommodation.ACCOMMODATION_BOOKING_BACKPACKER;

import org.junit.jupiter.api.Test;

import team.easytravel.commons.exceptions.IllegalValueException;
import team.easytravel.model.listmanagers.accommodationbooking.AccommodationBooking;
import team.easytravel.model.listmanagers.accommodationbooking.AccommodationName;
import team.easytravel.model.listmanagers.accommodationbooking.Day;
import team.easytravel.model.listmanagers.accommodationbooking.Remark;
import team.easytravel.model.util.attributes.Location;


/**
 * Jackson-friendly version of {@link AccommodationBooking}.
 */
class JsonAdaptedAccommodationBookingTest {

    public static final String INVALID_NAME = "HO#T@EL";
    public static final String INVALID_LOCATION = "M^SC^W";
    public static final Integer INVALID_STARTDAY = -1;
    public static final Integer INVALID_ENDDAY = -100;
    public static final String INVALID_REMARK = "DSOFNWEINREIOWNFDSNFJWENKJRNDSKJFNKJDNSFKJNSDKJFNSKJN"
            + "FJKSNFKJNSKJDFNKJSDNFJK"
            + "NDJFNDSJFNDJSKNFKJDSNFKJSDNKJFNSDKJFNJWENIUNDSKFNOEWINROISDNFKMSLKFMOINFKDSNSLKFDSNLKF";

    public static final String VALID_NAME = ACCOMMODATION_BOOKING_BACKPACKER.getAccommodationName().value;
    public static final String VALID_LOCATION = ACCOMMODATION_BOOKING_BACKPACKER.getLocation().value;
    public static final Integer VALID_STARTDAY = ACCOMMODATION_BOOKING_BACKPACKER.getStartDay().value;
    public static final Integer VALID_ENDDAY = ACCOMMODATION_BOOKING_BACKPACKER.getEndDay().value;
    public static final String VALID_REMARK = ACCOMMODATION_BOOKING_BACKPACKER.getRemark().value;

    @Test
    public void toModelType_validAccommodationDetails_returnsAccommodation() throws Exception {
        JsonAdaptedAccommodationBooking accommodationBooking = new JsonAdaptedAccommodationBooking(
                ACCOMMODATION_BOOKING_BACKPACKER);
        assertEquals(ACCOMMODATION_BOOKING_BACKPACKER, accommodationBooking.toModelType());
    }

    @Test
    public void toModelType_invalidAccommodationName_throwsIllegalValueException() {
        JsonAdaptedAccommodationBooking accommodationBooking =
                new JsonAdaptedAccommodationBooking(INVALID_NAME, VALID_LOCATION, VALID_STARTDAY, VALID_ENDDAY,
                        VALID_REMARK);
        String expectedMessage = AccommodationName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, accommodationBooking::toModelType);
    }

    @Test
    public void toModelType_invalidLocation_throwsIllegalValueException() {
        JsonAdaptedAccommodationBooking accommodationBooking =
                new JsonAdaptedAccommodationBooking(VALID_NAME, INVALID_LOCATION, VALID_STARTDAY,
                        VALID_ENDDAY, VALID_REMARK);
        String expectedMessage = Location.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, accommodationBooking::toModelType);
    }

    @Test
    public void toModelType_invalidStartDay_throwsIllegalValueException() {
        JsonAdaptedAccommodationBooking accommodationBooking =
                new JsonAdaptedAccommodationBooking(VALID_NAME, VALID_LOCATION, INVALID_STARTDAY, VALID_ENDDAY,
                        VALID_REMARK);
        String expectedMessage = Day.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, accommodationBooking::toModelType);
    }

    @Test
    public void toModelType_invalidEndDay_throwsIllegalValueException() {
        JsonAdaptedAccommodationBooking accommodationBooking =
                new JsonAdaptedAccommodationBooking(VALID_NAME, VALID_LOCATION, VALID_STARTDAY, INVALID_ENDDAY,
                        VALID_REMARK);
        String expectedMessage = Day.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, accommodationBooking::toModelType);
    }

    @Test
    public void toModelType_invalidRemark_throwsIllegalValueException() {
        JsonAdaptedAccommodationBooking accommodationBooking =
                new JsonAdaptedAccommodationBooking(VALID_NAME, VALID_LOCATION, VALID_STARTDAY, VALID_ENDDAY,
                        INVALID_REMARK);
        String expectedMessage = Remark.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, accommodationBooking::toModelType);
    }
}
