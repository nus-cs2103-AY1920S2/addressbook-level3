package team.easytravel.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.easytravel.testutil.Assert.assertThrows;

import static team.easytravel.testutil.TypicalAccommodation.ACCOMMODATION_BOOKING_BACKPACKER;
import static team.easytravel.testutil.TypicalAccommodation.ACCOMMODATION_BOOKING_HOSTEL;
import static team.easytravel.testutil.TypicalAccommodation.ACCOMMODATION_BOOKING_HOTEL;

import static team.easytravel.testutil.TypicalFixedExpense.FIXED_EXPENSE_HOTELS;
import static team.easytravel.testutil.TypicalFixedExpense.FIXED_EXPENSE_PLANE;
import static team.easytravel.testutil.TypicalFixedExpense.FIXED_EXPENSE_WIFI;
import static team.easytravel.testutil.TypicalPackingListItem.PACKING_LIST_JEANS;
import static team.easytravel.testutil.TypicalPackingListItem.PACKING_LIST_SHIRT;
import static team.easytravel.testutil.TypicalTransportBooking.TRANSPORT_BOOKING_BUS;
import static team.easytravel.testutil.TypicalTransportBooking.TRANSPORT_BOOKING_PLANE;
import static team.easytravel.testutil.TypicalTransportBooking.TRANSPORT_BOOKING_TRAIN;
import static team.easytravel.testutil.activity.TypicalActivity.ACTIVITY_DISNEYLAND;
import static team.easytravel.testutil.activity.TypicalActivity.ACTIVITY_PEAK;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

import team.easytravel.commons.core.GuiSettings;
import team.easytravel.model.listmanagers.AccommodationBookingManager;
import team.easytravel.model.listmanagers.ActivityManager;
import team.easytravel.model.listmanagers.FixedExpenseManager;
import team.easytravel.model.listmanagers.PackingListManager;
import team.easytravel.model.listmanagers.TransportBookingManager;
import team.easytravel.model.listmanagers.UserPrefs;
import team.easytravel.model.listmanagers.accommodationbooking.AccommodationBooking;
import team.easytravel.model.listmanagers.activity.Activity;
import team.easytravel.model.listmanagers.fixedexpense.FixedExpense;
import team.easytravel.model.listmanagers.packinglistitem.PackingListItem;
import team.easytravel.model.listmanagers.transportbooking.TransportBooking;
import team.easytravel.model.trip.TripManager;
import team.easytravel.model.util.uniquelist.exceptions.ElementNotFoundException;


public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager(new TransportBookingManager(),
            new FixedExpenseManager(), new PackingListManager(), new ActivityManager(),
            new AccommodationBookingManager(), new TripManager(), new UserPrefs());

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new TransportBookingManager(), new TransportBookingManager(modelManager
                .getTransportBookingManager()));
        assertEquals(new FixedExpenseManager(), new FixedExpenseManager(modelManager.getFixedExpenseManager()));
        assertEquals(new PackingListManager(), new PackingListManager(modelManager.getPackingListManager()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAccommodationBookingManager(null));
        assertThrows(NullPointerException.class, () -> modelManager.setTransportBookingManager(null));
        assertThrows(NullPointerException.class, () -> modelManager.setFixedExpenseManager(null));
        assertThrows(NullPointerException.class, () -> modelManager.setPackingListManager(null));
        assertThrows(NullPointerException.class, () -> modelManager.setTrip(null));

    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    // ========== TransportBookingManager ==========
    @Test
    public void hasTransportBookingModelManager() {
        assertThrows(NullPointerException.class, () -> modelManager.hasTransportBooking(null));
        TransportBooking newTransport = TRANSPORT_BOOKING_BUS;
        assertFalse(modelManager.hasTransportBooking(newTransport));
    }

    @Test
    public void deleteTransportBookingModelManager() {
        assertThrows(NullPointerException.class, () -> modelManager.deleteTransportBooking(null));
        TransportBooking newTransport = TRANSPORT_BOOKING_BUS;
        assertThrows(ElementNotFoundException.class, () -> modelManager.deleteTransportBooking(newTransport));
        modelManager.addTransportBooking(newTransport);
        modelManager.deleteTransportBooking(newTransport);
        assertFalse(modelManager.getFilteredTransportBookingList().contains(newTransport));
        assertFalse(modelManager.getFilteredTransportBookingList()
                .contains(TRANSPORT_BOOKING_PLANE));
    }

    @Test
    public void addTransportBookingModelManager() {
        assertThrows(NullPointerException.class, () -> modelManager.addTransportBooking(null));
        TransportBooking newTransport = TRANSPORT_BOOKING_TRAIN;
        modelManager.addTransportBooking(newTransport);
        assertTrue(modelManager.getFilteredTransportBookingList().contains(newTransport));
        assertFalse(modelManager.getFilteredTransportBookingList().contains(TRANSPORT_BOOKING_BUS));
    }

    @Test
    public void setTransportBookingModelManager() {
        assertThrows(NullPointerException.class, () -> modelManager.setTransportBooking(null, null));
        TransportBooking newTransportTrain = TRANSPORT_BOOKING_TRAIN;
        TransportBooking newTransportPlane = TRANSPORT_BOOKING_PLANE;
        assertThrows(ElementNotFoundException.class, () -> modelManager
                .setTransportBooking(newTransportTrain,
                        newTransportPlane)); //Event where tries to set non-existent booking.

        modelManager.addTransportBooking(newTransportPlane);
        modelManager.setTransportBooking(newTransportPlane, newTransportTrain);
        assertTrue(modelManager.getFilteredTransportBookingList().contains(newTransportTrain));
        assertFalse(modelManager.getFilteredTransportBookingList().contains(newTransportPlane));
    }

    @Test
    public void updateFilteredTransportBookingListModelManager() {
        assertThrows(NullPointerException.class, () -> modelManager.updateFilteredTransportBookingList(null));
    }

    @Test
    public void sortTransportListModelManager() {
        assertThrows(NullPointerException.class, () -> modelManager.sortTransportList(null));
        modelManager.addTransportBooking(TRANSPORT_BOOKING_TRAIN);
        modelManager.addTransportBooking(TRANSPORT_BOOKING_BUS);
        modelManager.sortTransportList(Comparator.comparing(x -> x.getMode().value));

        ModelManager sortedModelManager = new ModelManager(new TransportBookingManager(),
                new FixedExpenseManager(), new PackingListManager(), new ActivityManager(),
                new AccommodationBookingManager(), new TripManager(), new UserPrefs());

        sortedModelManager.addTransportBooking(TRANSPORT_BOOKING_BUS);
        sortedModelManager.addTransportBooking(TRANSPORT_BOOKING_TRAIN);

        assertEquals(modelManager.getFilteredTransportBookingList().get(0), sortedModelManager
                .getFilteredTransportBookingList().get(0)); //Allow users to check for list

    }

    // ========== FixedExpenseManager ==========
    @Test
    public void hasFixedExpenseModelManager() {
        assertThrows(NullPointerException.class, () -> modelManager.hasFixedExpense(null));
        FixedExpense newFixedExpense = FIXED_EXPENSE_HOTELS;
        assertFalse(modelManager.hasFixedExpense(newFixedExpense));
    }

    @Test
    public void deleteFixedExpenseModelManager() {
        assertThrows(NullPointerException.class, () -> modelManager.deleteFixedExpense(null));
        FixedExpense newFixedExpense = FIXED_EXPENSE_HOTELS;
        assertThrows(ElementNotFoundException.class, () -> modelManager.deleteFixedExpense(newFixedExpense));
        modelManager.addFixedExpense(newFixedExpense);
        modelManager.deleteFixedExpense(newFixedExpense);
        assertFalse(modelManager.getFilteredFixedExpenseList().contains(newFixedExpense));
        assertFalse(modelManager.getFilteredFixedExpenseList()
                .contains(FIXED_EXPENSE_HOTELS));
    }

    @Test
    public void addFixedExpenseModelManager() {
        assertThrows(NullPointerException.class, () -> modelManager.addFixedExpense(null));
        FixedExpense newFixedExpense = FIXED_EXPENSE_HOTELS;
        modelManager.addFixedExpense(newFixedExpense);
        assertTrue(modelManager.getFilteredFixedExpenseList().contains(newFixedExpense));
        assertFalse(modelManager.getFilteredFixedExpenseList().contains(FIXED_EXPENSE_PLANE));
    }

    @Test
    public void setFixedExpenseModelManager() {
        assertThrows(NullPointerException.class, () -> modelManager.setFixedExpense(null, null));
        FixedExpense newFixedExpenseHotels = FIXED_EXPENSE_HOTELS;
        FixedExpense newFixedExpenseWifi = FIXED_EXPENSE_WIFI;
        assertThrows(ElementNotFoundException.class, () -> modelManager
                .setFixedExpense(newFixedExpenseHotels,
                        newFixedExpenseWifi)); //Event where tries to set non-existent fixed expense.

        modelManager.addFixedExpense(newFixedExpenseHotels);
        modelManager.setFixedExpense(newFixedExpenseHotels, newFixedExpenseWifi);
        assertTrue(modelManager.getFilteredFixedExpenseList().contains(newFixedExpenseWifi));
        assertFalse(modelManager.getFilteredFixedExpenseList().contains(newFixedExpenseHotels));
    }

    @Test
    public void updateFilteredFixedExpenseListModelManager() {
        assertThrows(NullPointerException.class, () -> modelManager.updateFilteredFixedExpenseList(null));
    }

    @Test
    public void sortFixedExpenseModelManager() {
        assertThrows(NullPointerException.class, () -> modelManager.sortFixedExpenseList(null));
        modelManager.addFixedExpense(FIXED_EXPENSE_PLANE);
        modelManager.addFixedExpense(FIXED_EXPENSE_HOTELS);
        modelManager.sortFixedExpenseList(Comparator.comparing(x -> x.getAmount().value));

        ModelManager sortedModelManager = new ModelManager(new TransportBookingManager(),
                new FixedExpenseManager(), new PackingListManager(), new ActivityManager(),
                new AccommodationBookingManager(), new TripManager(), new UserPrefs());

        sortedModelManager.addFixedExpense(FIXED_EXPENSE_HOTELS);
        sortedModelManager.addFixedExpense(FIXED_EXPENSE_PLANE);

        assertEquals(modelManager.getFilteredFixedExpenseList().get(0), sortedModelManager
                .getFilteredFixedExpenseList().get(0));

    }


    // ========== PackingListManager ==========
    @Test
    public void hasPackingListItemModelManager() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPackingListItem(null));
        PackingListItem newPackingListItem = PACKING_LIST_JEANS;
        assertFalse(modelManager.hasPackingListItem(newPackingListItem));
    }

    @Test
    public void deletePackingListItemModelManager() {
        assertThrows(NullPointerException.class, () -> modelManager.deletePackingListItem(null));
        PackingListItem newPackingListItem = PACKING_LIST_JEANS;
        assertThrows(ElementNotFoundException.class, () -> modelManager.deletePackingListItem(newPackingListItem));
        modelManager.addPackingListItem(newPackingListItem);
        modelManager.deletePackingListItem(newPackingListItem);
        assertFalse(modelManager.getFilteredPackingList().contains(newPackingListItem));
        assertFalse(modelManager.getFilteredPackingList()
                .contains(PACKING_LIST_SHIRT));
    }

    @Test
    public void addPackingListItemModelManager() {
        assertThrows(NullPointerException.class, () -> modelManager.addPackingListItem(null));
        PackingListItem newPackingList = PACKING_LIST_JEANS;
        modelManager.addPackingListItem(newPackingList);
        assertTrue(modelManager.getFilteredPackingList().contains(newPackingList));
        assertFalse(modelManager.getFilteredPackingList().contains(PACKING_LIST_SHIRT));
    }

    @Test
    public void setPackingListItemModelManager() {
        assertThrows(NullPointerException.class, () -> modelManager.setPackingListItem(null, null));
        PackingListItem newPackingListItemShirt = PACKING_LIST_SHIRT;
        PackingListItem newPackingListItemJeans = PACKING_LIST_JEANS;
        assertThrows(ElementNotFoundException.class, () -> modelManager
                .setPackingListItem(newPackingListItemJeans,
                        newPackingListItemShirt)); //Event where tries to set non-existent packing list.

        modelManager.addPackingListItem(newPackingListItemShirt);
        modelManager.setPackingListItem(newPackingListItemShirt, newPackingListItemJeans);
        assertTrue(modelManager.getFilteredPackingList().contains(newPackingListItemJeans));
        assertFalse(modelManager.getFilteredPackingList().contains(newPackingListItemShirt));
    }

    @Test
    public void updateFilteredPackingListModelManager() {
        assertThrows(NullPointerException.class, () -> modelManager.updateFilteredPackingList(null));
    }

    @Test
    public void sortPackingListItemModelManager() {
        assertThrows(NullPointerException.class, () -> modelManager.sortPackingList(null));
        modelManager.addPackingListItem(PACKING_LIST_SHIRT);
        modelManager.addPackingListItem(PACKING_LIST_JEANS);
        modelManager.sortPackingList(Comparator.comparing(x -> x.getItemName().value));

        ModelManager sortedModelManager = new ModelManager(new TransportBookingManager(),
                new FixedExpenseManager(), new PackingListManager(), new ActivityManager(),
                new AccommodationBookingManager(), new TripManager(), new UserPrefs());

        sortedModelManager.addPackingListItem(PACKING_LIST_JEANS);
        sortedModelManager.addPackingListItem(PACKING_LIST_SHIRT);

        assertEquals(modelManager.getFilteredPackingList().get(0), sortedModelManager
                .getFilteredPackingList().get(0));
    }

    // ========== ActivityManager ==========

    @Test
    public void hasActivityManagerModelManager() {
        assertThrows(NullPointerException.class, () -> modelManager.hasActivity(null));
        Activity newActivity = ACTIVITY_DISNEYLAND;
        assertFalse(modelManager.hasActivity(newActivity));
    }

    @Test
    public void deleteActivityManagerModelManager() {
        assertThrows(NullPointerException.class, () -> modelManager.deleteActivity(null));
        Activity newActivity = ACTIVITY_DISNEYLAND;
        assertThrows(ElementNotFoundException.class, () -> modelManager.deleteActivity(newActivity));
        modelManager.addActivity(newActivity);
        modelManager.deleteActivity(newActivity);
        assertFalse(modelManager.getFilteredActivityList().contains(newActivity));
        assertFalse(modelManager.getFilteredActivityList()
                .contains(newActivity));
    }

    @Test
    public void addActivityManagerModelManager() {
        assertThrows(NullPointerException.class, () -> modelManager.addActivity(null));
        Activity newActivity = ACTIVITY_PEAK;
        modelManager.addActivity(newActivity);
        assertTrue(modelManager.getFilteredActivityList().contains(newActivity));
        assertFalse(modelManager.getFilteredActivityList().contains(ACTIVITY_DISNEYLAND));
    }

    @Test
    public void setActivityManagerModelManager() {
        assertThrows(NullPointerException.class, () -> modelManager.setActivity(null, null));
        Activity newActivityPeak = ACTIVITY_PEAK;
        Activity newActivityDisneyland = ACTIVITY_DISNEYLAND;
        assertThrows(ElementNotFoundException.class, () -> modelManager
                .setActivity(newActivityPeak,
                        newActivityDisneyland)); //Event where tries to set non-existent activity.

        modelManager.addActivity(newActivityPeak);
        modelManager.setActivity(newActivityPeak, newActivityDisneyland);
        assertTrue(modelManager.getFilteredActivityList().contains(newActivityDisneyland));
        assertFalse(modelManager.getFilteredActivityList().contains(newActivityPeak));
    }

    @Test
    public void updateFilteredActivityManagerModelManager() {
        assertThrows(NullPointerException.class, () -> modelManager.updateFilteredActivityList(null));
    }

    @Test
    public void sortActivityManagerModelManager() {
        assertThrows(NullPointerException.class, () -> modelManager.sortActivityList(null));
        modelManager.addActivity(ACTIVITY_PEAK);
        modelManager.addActivity(ACTIVITY_DISNEYLAND);
        modelManager.sortActivityList(Comparator.comparing(x -> x.getTitle().value));

        ModelManager sortedModelManager = new ModelManager(new TransportBookingManager(),
                new FixedExpenseManager(), new PackingListManager(), new ActivityManager(),
                new AccommodationBookingManager(), new TripManager(), new UserPrefs());

        sortedModelManager.addActivity(ACTIVITY_DISNEYLAND);
        sortedModelManager.addActivity(ACTIVITY_PEAK);

        assertEquals(modelManager.getFilteredActivityList().get(0), sortedModelManager
                .getFilteredActivityList().get(0));
    }

    // ========== AccommodationBookingManager ==========

    @Test
    public void hasAccommodationBookingModelManager() {
        assertThrows(NullPointerException.class, () -> modelManager.hasAccommodationBooking(null));
        AccommodationBooking newAccommodationBooking = ACCOMMODATION_BOOKING_BACKPACKER;
        assertFalse(modelManager.hasAccommodationBooking(newAccommodationBooking));
    }

    @Test
    public void deleteAccommodationBookingModelManager() {
        assertThrows(NullPointerException.class, () -> modelManager.deleteAccommodationBooking(null));
        AccommodationBooking newAccommodation = ACCOMMODATION_BOOKING_HOSTEL;
        assertThrows(ElementNotFoundException.class, () -> modelManager.deleteAccommodationBooking(newAccommodation));
        modelManager.addAccommodationBooking(newAccommodation);
        modelManager.deleteAccommodationBooking(newAccommodation);
        assertFalse(modelManager.getFilteredAccommodationBookingList().contains(newAccommodation));
        assertFalse(modelManager.getFilteredAccommodationBookingList()
                .contains(newAccommodation));
    }

    @Test
    public void addAccommodationBookingModelManager() {
        assertThrows(NullPointerException.class, () -> modelManager.addAccommodationBooking(null));
        AccommodationBooking newAccommodation = ACCOMMODATION_BOOKING_HOTEL;
        modelManager.addAccommodationBooking(newAccommodation);
        assertTrue(modelManager.getFilteredAccommodationBookingList().contains(newAccommodation));
        assertFalse(modelManager.getFilteredAccommodationBookingList().contains(ACCOMMODATION_BOOKING_BACKPACKER));
    }

    @Test
    public void setAccommodationBookingModelManager() {
        assertThrows(NullPointerException.class, () -> modelManager.setAccommodationBooking(null, null));
        AccommodationBooking newAccommodationHotel = ACCOMMODATION_BOOKING_HOTEL;
        AccommodationBooking newAccommodationBackpacker = ACCOMMODATION_BOOKING_BACKPACKER;
        assertThrows(ElementNotFoundException.class, () -> modelManager
                .setAccommodationBooking(newAccommodationHotel,
                        newAccommodationBackpacker)); //Event where tries to set non-existent Accommodation.

        modelManager.addAccommodationBooking(newAccommodationBackpacker);
        modelManager.setAccommodationBooking(newAccommodationBackpacker, newAccommodationHotel);
        assertTrue(modelManager.getFilteredAccommodationBookingList().contains(newAccommodationHotel));
        assertFalse(modelManager.getFilteredAccommodationBookingList().contains(newAccommodationBackpacker));
    }

    @Test
    public void updateFilteredAccommodationBookingModelManager() {
        assertThrows(NullPointerException.class, () -> modelManager.updateFilteredAccommodationBookingList(null));
    }

    @Test
    public void sortAccommodationBookingModelManager() {
        assertThrows(NullPointerException.class, () -> modelManager.sortAccommodationList(null));
        modelManager.addAccommodationBooking(ACCOMMODATION_BOOKING_HOTEL);
        modelManager.addAccommodationBooking(ACCOMMODATION_BOOKING_BACKPACKER);
        modelManager.sortActivityList(Comparator.comparing(x -> x.getTitle().value));

        ModelManager sortedModelManager = new ModelManager(new TransportBookingManager(),
                new FixedExpenseManager(), new PackingListManager(), new ActivityManager(),
                new AccommodationBookingManager(), new TripManager(), new UserPrefs());

        sortedModelManager.addAccommodationBooking(ACCOMMODATION_BOOKING_BACKPACKER);
        sortedModelManager.addAccommodationBooking(ACCOMMODATION_BOOKING_HOTEL);

        assertEquals(modelManager.getFilteredAccommodationBookingList().get(0), sortedModelManager
                .getFilteredAccommodationBookingList().get(0));
    }
}
