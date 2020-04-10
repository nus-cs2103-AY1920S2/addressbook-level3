package team.easytravel.model.trip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.easytravel.testutil.Assert.assertThrows;
import static team.easytravel.testutil.trip.TypicalTrip.TRIP_CHEESE;
import static team.easytravel.testutil.trip.TypicalTrip.TRIP_GRAD;
import static team.easytravel.testutil.trip.TypicalTrip.getTypicalTripManager;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import team.easytravel.model.trip.exception.IllegalOperationException;


class TripManagerTest {

    private final TripManager tripManager = new TripManager();

    @Test
    public void constructor() {
        tripManager.setTrip(TRIP_GRAD);
        assertEquals(Collections.EMPTY_LIST, tripManager.getDayScheduleEntryList(0));
    }

    @Test
    public void resetDataNullThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> tripManager.resetData(null));
    }

    @Test
    public void resetDataWithValidReadOnlyTripManagerReplacesData() {
        TripManager newData = getTypicalTripManager();
        tripManager.resetData(newData);
        assertEquals(newData.toString(), tripManager.toString());
        assertEquals(newData.hashCode(), tripManager.hashCode());
    }


    @Test
    public void hasTripNotInTripManagerReturnsFalse() {
        assertFalse(tripManager.hasTrip());
    }

    @Test
    public void hasTripManagerInTripManagerReturnsTrue() {
        tripManager.setTrip(TRIP_CHEESE);
        assertTrue(tripManager.hasTrip());
    }

    @Test
    public void getTripModifyListThrowsUnsupportedOperationException() {
        assertThrows(IllegalOperationException.class, () -> tripManager
                .getDayScheduleEntryLists().remove(0));
    }

}
