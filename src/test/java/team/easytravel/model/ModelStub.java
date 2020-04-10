package team.easytravel.model;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import team.easytravel.commons.core.GuiSettings;
import team.easytravel.model.listmanagers.ReadOnlyAccommodationBookingManager;
import team.easytravel.model.listmanagers.ReadOnlyActivityManager;
import team.easytravel.model.listmanagers.ReadOnlyFixedExpenseManager;
import team.easytravel.model.listmanagers.ReadOnlyPackingListManager;
import team.easytravel.model.listmanagers.ReadOnlyTransportBookingManager;
import team.easytravel.model.listmanagers.ReadOnlyUserPrefs;
import team.easytravel.model.listmanagers.UserPrefs;
import team.easytravel.model.listmanagers.accommodationbooking.AccommodationBooking;
import team.easytravel.model.listmanagers.activity.Activity;
import team.easytravel.model.listmanagers.fixedexpense.FixedExpense;
import team.easytravel.model.listmanagers.packinglistitem.PackingListItem;
import team.easytravel.model.listmanagers.transportbooking.TransportBooking;
import team.easytravel.model.trip.Budget;
import team.easytravel.model.trip.DayScheduleEntry;
import team.easytravel.model.trip.Trip;
import team.easytravel.model.trip.TripManager;
import team.easytravel.model.util.attributes.Title;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setUserPrefs(UserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }
    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyTransportBookingManager getTransportBookingManager() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setTransportBookingManager(ReadOnlyTransportBookingManager transportBookingManager) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasTransportBooking(TransportBooking target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteTransportBooking(TransportBooking toDelete) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addTransportBooking(TransportBooking toAdd) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setTransportBooking(TransportBooking target, TransportBooking edited) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<TransportBooking> getFilteredTransportBookingList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredTransportBookingList(Predicate<TransportBooking> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortTransportList(Comparator<TransportBooking> cmp) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyFixedExpenseManager getFixedExpenseManager() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setFixedExpenseManager(ReadOnlyFixedExpenseManager fixedExpenseManager) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasFixedExpense(FixedExpense target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteFixedExpense(FixedExpense toDelete) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addFixedExpense(FixedExpense toAdd) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setFixedExpense(FixedExpense target, FixedExpense edited) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortFixedExpenseList(Comparator<FixedExpense> cmp) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<FixedExpense> getFilteredFixedExpenseList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredFixedExpenseList(Predicate<FixedExpense> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyPackingListManager getPackingListManager() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setPackingListManager(ReadOnlyPackingListManager packingListManager) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasPackingListItem(PackingListItem target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deletePackingListItem(PackingListItem toDelete) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addPackingListItem(PackingListItem toAdd) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setPackingListItem(PackingListItem target, PackingListItem edited) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<PackingListItem> getFilteredPackingList() {
        return null;
    }

    @Override
    public void updateFilteredPackingList(Predicate<PackingListItem> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortPackingList(Comparator<PackingListItem> cmp) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addActivity(Activity activity) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setActivityManager(ReadOnlyActivityManager newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyActivityManager getActivityManager() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasActivity(Activity activity) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteActivity(Activity target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setActivity(Activity target, Activity editActivity) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Activity> getFilteredActivityList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredActivityList(Predicate<Activity> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortActivityList(Comparator<Activity> cmp) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyAccommodationBookingManager getAccommodationBookingManager() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAccommodationBookingManager(ReadOnlyAccommodationBookingManager accommodationBookingManager) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasAccommodationBooking(AccommodationBooking target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteAccommodationBooking(AccommodationBooking toDelete) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addAccommodationBooking(AccommodationBooking toAdd) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAccommodationBooking(AccommodationBooking target, AccommodationBooking edited) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<AccommodationBooking> getFilteredAccommodationBookingList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredAccommodationBookingList(Predicate<AccommodationBooking> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean isOverlappingWithOthers(AccommodationBooking toCheck) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortAccommodationList(Comparator<AccommodationBooking> cmp) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public TripManager getTripManager() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasTrip() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setTrip(Trip edited) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteTrip() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void scheduleActivity(Activity toSchedule) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void unscheduleActivity(DayScheduleEntry toDelete) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void scheduleTransport(TransportBooking toSchedule) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void unscheduleTransport(DayScheduleEntry toDelete) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public int getTripNumDays() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public int getBudget() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public double getTotalExpense() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public double getExchangeRate() {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public void setTitle(Title title) {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public ObservableList<DayScheduleEntry> getDayScheduleEntryList(int dayIndex) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public List<ObservableList<DayScheduleEntry>> getScheduleList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void resetAllListManagers() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public List<String> getStatus() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setBudget(Budget editedBudget) {
        throw new AssertionError("This method should not be called.");
    }
}
