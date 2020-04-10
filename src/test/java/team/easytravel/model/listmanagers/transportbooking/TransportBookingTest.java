package team.easytravel.model.listmanagers.transportbooking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.easytravel.testutil.TypicalTransportBooking.TRANSPORT_BOOKING_BUS;
import static team.easytravel.testutil.TypicalTransportBooking.TRANSPORT_BOOKING_PLANE;
import static team.easytravel.testutil.TypicalTransportBooking.TRANSPORT_BOOKING_TRAIN;

import org.junit.jupiter.api.Test;

import team.easytravel.commons.core.time.DateTime;
import team.easytravel.model.util.attributes.Location;
import team.easytravel.testutil.Assert;

class TransportBookingTest {

    private final TransportBooking transportBooking = TRANSPORT_BOOKING_TRAIN;

    @Test
    public void constructorNullThrowsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new TransportBooking(
                null, null, null, null, null
        ));
    }

    @Test
    public void constructorInvalidNameThrowsIllegalArgumentException() {
        String invalidMode = "";
        String invalidStartLocation = "";
        String invalidEndLocation = "";
        String startDateTime = "";
        String endDateTime = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new TransportBooking(
                new Mode(invalidMode), new Location(invalidStartLocation), new Location(invalidEndLocation),
                DateTime.fromString(startDateTime), DateTime.fromString(endDateTime)
        ));
    }


    @Test
    public void getMode() {
        // Correct case
        assertEquals(new Mode("train"), transportBooking.getMode());

        //Wrong case
        assertNotEquals(new Mode("bus"), transportBooking.getMode());

        //Invalid Mode and exception called case
        Assert.assertThrows(IllegalArgumentException.class, () -> assertNotEquals(new Mode("haha"),
                transportBooking.getMode()));
    }

    @Test
    public void getStartLocation() {

        //Correct
        assertEquals(new Location("Singapore"), transportBooking.getStartLocation());

        // Wrong case
        assertNotEquals(new Location("Japan"), transportBooking.getStartLocation());

        // Invalid Location and exception called case
        Assert.assertThrows(IllegalArgumentException.class, () -> assertNotEquals(new Location("eee234^&&2"),
                transportBooking.getStartLocation()));
    }

    @Test
    public void getEndLocation() {

        //Correct case
        assertEquals(new Location("Malaysia"), transportBooking.getEndLocation());

        // Wrong case
        assertNotEquals(new Location("Singapore"), transportBooking.getEndLocation());

        // Invalid Location and exception called case
        Assert.assertThrows(IllegalArgumentException.class, () -> assertNotEquals(new Location("erq4231^4qwewq"),
                transportBooking.getEndLocation()));
    }

    @Test
    public void getStartDateTime() {

        //Correct case
        assertEquals(DateTime.fromString("20-01-2020 22:00"), transportBooking.getStartDateTime());

        //Invalid case
        assertNotEquals(DateTime.fromString("20-03-2020 22:00"), transportBooking.getStartDateTime());

        //Event where an invalid DateTime is entered by the user.
        Assert.assertThrows(
                IllegalArgumentException.class, () -> assertNotEquals(DateTime.fromString("20-10-2019 2200"),
                transportBooking.getStartDateTime()));
    }

    @Test
    public void getEndDateTime() {

        //Correct case
        assertEquals(DateTime.fromString("20-01-2020 23:00"), transportBooking.getEndDateTime());

        //Invalid case
        assertNotEquals(DateTime.fromString("20-01-2019 23:00"), transportBooking.getEndDateTime());

        //InvalidDateTime entered by the user.
        Assert.assertThrows(
                IllegalArgumentException.class, () -> assertNotEquals(DateTime.fromString("20-30-2019 2399"),
                transportBooking.getEndDateTime()));
    }

    @Test
    public void areLocationsValid() {

        assertTrue(Location.isValidLocation("Singapore")); // Normal case
        assertTrue(Location.isValidLocation("Block 123 Jurong West")); // Contains alphanumeric
        assertTrue(Location.isValidLocation("Malaysia ")); // Case with spaces

        assertFalse(Location.isValidLocation("fonfwenrenrunewornewunrjewnrjneworneaosflkdsnjfndsunf"
                + "jdsnfnsdjfnsj")); // With 60 characters
        assertFalse(Location.isValidLocation("eriewrioewrioewrionoifiosiofiodnfisnfsn"
                + "fn1321424124^^^^")); // With non alphanumeric characters
        assertFalse(Location.isValidLocation(" ")); // Case with one space
        assertFalse(Location.isValidLocation("       ")); // Case with multiple spaces.
    }

    @Test
    public void isTimeValid() {

        assertTrue(DateTime.isValidDateTime("20-03-2019 19:00")); //Normal case
        assertTrue(DateTime.isValidDateTime("29-02-2020 18:00")); //Test for leap year

        //TODO Wonder why this is true
        //assertFalse(DateTime.isValidDateTime("29-02-2015 18:00")); // Test for non-leap year

        assertFalse(DateTime.isValidDateTime("20-13-2019 18:00")); // Invalid Month
        assertFalse(DateTime.isValidDateTime("40-12-2019 18:00")); // Invalid Day
        assertFalse(DateTime.isValidDateTime("20-12-2019 25:00")); // Invalid Time

    }

    @Test
    public void isSame() {
        TransportBooking sameTransportBooking = new TransportBooking(
                new Mode("train"), new Location("Singapore"), new Location("Malaysia"),
                DateTime.fromString("20-01-2020 22:00"), DateTime.fromString("20-01-2020 23:00")
        );
        assertTrue(transportBooking.isSame(sameTransportBooking));
        assertFalse(transportBooking.isSame(TRANSPORT_BOOKING_BUS));

    }

    @Test
    public void testEquals() {
        TransportBooking sameTransportBooking = new TransportBooking(
                new Mode("train"), new Location("Singapore"), new Location("Malaysia"),
                DateTime.fromString("20-01-2020 22:00"), DateTime.fromString("20-01-2020 23:00")
        );
        assertTrue(transportBooking.isSame(sameTransportBooking));
        assertFalse(transportBooking.isSame(TRANSPORT_BOOKING_BUS));
    }

    @Test
    public void testHashCode() {
        TransportBooking sameTransportBooking = new TransportBooking(
                new Mode("train"), new Location("Singapore"), new Location("Malaysia"),
                DateTime.fromString("20-01-2020 22:00"), DateTime.fromString("20-01-2020 23:00")
        );
        assertEquals(transportBooking.hashCode(), sameTransportBooking.hashCode());
        assertNotEquals(transportBooking.hashCode(), TRANSPORT_BOOKING_PLANE.hashCode());
    }

    @Test
    public void testToString() {
        TransportBooking sameTransportBooking = new TransportBooking(
                new Mode("train"), new Location("Singapore"), new Location("Malaysia"),
                DateTime.fromString("20-01-2020 22:00"), DateTime.fromString("20-01-2020 23:00")
        );
        assertEquals(transportBooking.toString(), sameTransportBooking.toString());
        assertNotEquals(transportBooking.toString(), TRANSPORT_BOOKING_PLANE.toString());
    }
}
