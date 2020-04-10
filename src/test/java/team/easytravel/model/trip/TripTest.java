package team.easytravel.model.trip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import team.easytravel.commons.core.time.Date;
import team.easytravel.model.util.attributes.Title;
import team.easytravel.testutil.Assert;

class TripTest {

    private final Trip trip = new Trip(new Title("Cheese Land"),
            Date.fromString("28-09-2020"), Date.fromString("05-10-2020"),
            new Budget(1000), new ExchangeRate(1.03));


    @Test
    public void constructorInvalidTitleThrowsIllegalArgumentException() {
        String invalidTitle = " ";

        Assert.assertThrows(IllegalArgumentException.class, () -> new Trip(new Title(invalidTitle),
                Date.fromString("28-09-2020"), Date.fromString("05-10-2020"),
                new Budget(1000), new ExchangeRate(1.03)));
    }

    @Test
    public void constructorInvalidStartThrowsIllegalArgumentException() {
        String invalidStart = " ";

        Assert.assertThrows(IllegalArgumentException.class, () -> new Trip(new Title("Cheese Land"),
                Date.fromString(invalidStart), Date.fromString("05-10-2020"),
                new Budget(1000), new ExchangeRate(1.03)));
    }

    @Test
    public void constructorInvalidEndThrowsIllegalArgumentException() {
        String invalidEnd = " ";

        Assert.assertThrows(IllegalArgumentException.class, () -> new Trip(new Title("Cheese Land"),
                Date.fromString("28-09-2020"), Date.fromString(invalidEnd),
                new Budget(1000), new ExchangeRate(1.03)));
    }

    @Test
    public void constructorInvalidBudgetThrowsIllegalArgumentException() {
        Integer invalidBudget = -222; //negative

        Assert.assertThrows(IllegalArgumentException.class, () -> new Trip(new Title("Cheese Land"),
                Date.fromString("28-09-2020"), Date.fromString("10-05-2020"),
                new Budget(invalidBudget), new ExchangeRate(1.03)));
    }
    @Test
    public void constructorInvalidExchangeRateThrowsIllegalArgumentException() {
        Double invalidExchangeRate = -0.3; //negative

        Assert.assertThrows(IllegalArgumentException.class, () -> new Trip(new Title("Cheese Land"),
                Date.fromString("28-09-2020"), Date.fromString("10-05-2020"),
                new Budget(1000), new ExchangeRate(invalidExchangeRate)));
    }


    @Test
    public void getTitle() {

        // Correct case
        Assertions.assertEquals(new Title("Cheese Land"), trip.getTitle());

        //Different case
        Assertions.assertNotEquals(new Title("Not Cheese Land"), trip.getTitle());

    }

    @Test
    public void getDate() {

        // Correct case
        assertEquals(Date.fromString("28-09-2020"),
                trip.getStartDate());

        //Different case
        assertNotEquals(Date.fromString("20-09-2020"),
                trip.getStartDate());

        // Correct case
        assertEquals(Date.fromString("05-10-2020"),
                trip.getEndDate());

        //Different case
        assertNotEquals(Date.fromString("20-09-2020"),
                trip.getEndDate());

    }

    @Test
    public void getBudget() {

        // Correct case
        assertEquals(new Budget(1000).toString(),
                trip.getBudget().toString());

        //Different case
        assertNotEquals(new Budget(2000).toString(),
                trip.getBudget().toString());

    }

    @Test
    public void getExchangeRate() {

        // Correct case
        assertEquals(new ExchangeRate(1.03).toString(),
                trip.getExchangeRate().toString());

        //Different case
        assertNotEquals(new ExchangeRate(1.3).toString(),
                trip.getExchangeRate().toString());

    }

    @Test
    public void isValidTrip() {
        String date1 = "28-09-2020";
        String date2 = "5-10-2020";

        assertTrue(Trip.isValidTrip(Date.fromString(date1), Date.fromString(date2))); //within bound
        assertFalse(Trip.isValidTrip(Date.fromString(date2), Date.fromString(date1))); //date2 is later

        String dateYear = "5-10-2023";
        assertFalse(Trip.isValidTrip(Date.fromString(date1), Date.fromString(dateYear))); //many years

        date2 = "27-10-2020";
        assertTrue(Trip.isValidTrip(Date.fromString(date1), Date.fromString(date2))); //exactly 30 days

        date2 = "28-10-2020";
        assertFalse(Trip.isValidTrip(Date.fromString(date1), Date.fromString(date2))); //exactly 31 days

        assertTrue(Trip.isValidTrip(Date.fromString(date1), Date.fromString(date1))); //one day
    }


    @Test
    public void testToString() {
        Trip editedTrip =
                new Trip(new Title("Cheese Land"),
                        Date.fromString("28-09-2020"), Date.fromString("05-10-2020"),
                        new Budget(1000), new ExchangeRate(1.03));

        assertEquals(trip.toString(), editedTrip.toString());

        editedTrip = new Trip(new Title("Not Cheese Land"),
                        Date.fromString("28-09-2020"), Date.fromString("05-10-2020"),
                        new Budget(1000), new ExchangeRate(1.03));

        assertNotEquals(editedTrip.toString(), trip.toString()); //different title

    }
}
