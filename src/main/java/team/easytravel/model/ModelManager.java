package team.easytravel.model;

import static java.util.Objects.requireNonNull;
import static team.easytravel.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import team.easytravel.commons.core.GuiSettings;
import team.easytravel.commons.core.LogsCenter;
import team.easytravel.commons.util.CollectionUtil;
import team.easytravel.model.listmanagers.AccommodationBookingManager;
import team.easytravel.model.listmanagers.ActivityManager;
import team.easytravel.model.listmanagers.FixedExpenseManager;
import team.easytravel.model.listmanagers.PackingListManager;
import team.easytravel.model.listmanagers.ReadOnlyAccommodationBookingManager;
import team.easytravel.model.listmanagers.ReadOnlyActivityManager;
import team.easytravel.model.listmanagers.ReadOnlyFixedExpenseManager;
import team.easytravel.model.listmanagers.ReadOnlyPackingListManager;
import team.easytravel.model.listmanagers.ReadOnlyTransportBookingManager;
import team.easytravel.model.listmanagers.ReadOnlyUserPrefs;
import team.easytravel.model.listmanagers.TransportBookingManager;
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
import team.easytravel.model.trip.exception.IllegalOperationException;
import team.easytravel.model.util.attributes.Title;

/**
 * Represents the in-memory model of the EasyTravel data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TransportBookingManager transportBookingManager;
    private final FixedExpenseManager fixedExpenseManager;
    private final PackingListManager packingListManager;
    private final ActivityManager activityManager;
    private final AccommodationBookingManager accommodationBookingManager;
    private final UserPrefs userPrefs;
    private final TripManager tripManager;

    private final FilteredList<TransportBooking> filteredTransportBookingList;
    private final FilteredList<FixedExpense> filteredFixedExpenseList;
    private final FilteredList<PackingListItem> filteredPackingList;
    private final FilteredList<Activity> filteredActivityList;
    private final FilteredList<AccommodationBooking> filteredAccommodationBookingList;
    private final List<ObservableList<DayScheduleEntry>> filteredScheduleEntryLists;

    /**
     * Initializes a ModelManager with the given managers and userPrefs.
     */
    public ModelManager(ReadOnlyTransportBookingManager transportBookingManager,
                        ReadOnlyFixedExpenseManager fixedExpenseManager, ReadOnlyPackingListManager packingListManager,
                        ReadOnlyActivityManager activityManager,
                        ReadOnlyAccommodationBookingManager accommodationBookingManager,
                        TripManager tripManager, ReadOnlyUserPrefs userPrefs) {
        super();
        CollectionUtil.requireAllNonNull(transportBookingManager, fixedExpenseManager,
                packingListManager, activityManager, accommodationBookingManager, tripManager, userPrefs);

        logger.fine("Initializing with Easy Travel: " + tripManager + " and user prefs " + userPrefs);

        this.tripManager = new TripManager(tripManager);
        this.transportBookingManager = new TransportBookingManager(transportBookingManager);
        this.fixedExpenseManager = new FixedExpenseManager(fixedExpenseManager);
        this.packingListManager = new PackingListManager(packingListManager);
        this.activityManager = new ActivityManager(activityManager);
        this.accommodationBookingManager = new AccommodationBookingManager(accommodationBookingManager);
        this.userPrefs = new UserPrefs(userPrefs);

        if (tripManager.hasTrip()) {
            this.transportBookingManager.removeInvalidTransportBookings(this.tripManager.getTripStartDate(),
                    this.tripManager.getTripEndDate());
            this.activityManager.clearInvalidScheduleTime(this.tripManager.getTripStartDate());
            this.tripManager.scheduleAll(this.activityManager.getActivityList(),
                    this.transportBookingManager.getTransportBookings());
            filteredScheduleEntryLists = new ArrayList<>(this.tripManager.getDayScheduleEntryLists());
        } else {
            resetAllListManagers();
            filteredScheduleEntryLists = new ArrayList<>();
        }

        filteredTransportBookingList = new FilteredList<>(this.transportBookingManager.getTransportBookings());
        filteredFixedExpenseList = new FilteredList<>(this.fixedExpenseManager.getFixedExpenseList());
        filteredPackingList = new FilteredList<>(this.packingListManager.getUniquePackingList());
        filteredActivityList = new FilteredList<>(this.activityManager.getActivityList());
        filteredAccommodationBookingList = new FilteredList<>(this.accommodationBookingManager
                .getAccommodationBookingList());
    }

    // ========== UserPrefs ==========

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(UserPrefs userPrefs) {
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    // ========== TripManager ==========

    @Override
    public TripManager getTripManager() {
        return tripManager;
    }

    /**
     * Returns the status of the trip.
     */
    @Override
    public List<String> getStatus() {
        List<String> list = new ArrayList<>();
        if (!hasTrip()) {
            throw new IllegalOperationException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }
        String scheduleStatus = "Schedule Status:\n" + tripManager.getScheduleStatus() + "\n";
        String packingListManagerStatus = "Packing List Status:\n" + packingListManager.getStatus() + "\n";
        String fixedExpenseManagerStatus = "Expense Status:\n" + fixedExpenseManager.getStatus(getBudget()) + "\n";
        String accomodationManagerStatus = "Accommodation Status: \n"
                + accommodationBookingManager.getStatus(tripManager.getDuration()) + "\n";

        list.add(scheduleStatus);
        list.add(packingListManagerStatus);
        list.add(fixedExpenseManagerStatus);
        list.add(accomodationManagerStatus);

        return list;

    }

    @Override
    public boolean hasTrip() {
        return tripManager.hasTrip();
    }

    @Override
    public void setTrip(Trip trip) {
        if (hasTrip()) {
            throw new IllegalOperationException(TripManager.MESSAGE_ERROR_SET_TRIP);
        }
        tripManager.setTrip(trip);
        tripManager.scheduleAll(getFilteredActivityList(), getFilteredTransportBookingList());
        filteredScheduleEntryLists.addAll(tripManager.getDayScheduleEntryLists());
    }

    @Override
    public void deleteTrip() {
        if (!hasTrip()) {
            throw new IllegalOperationException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }
        resetAllListManagers();
        filteredScheduleEntryLists.clear();
    }

    @Override
    public int getBudget() {
        if (!hasTrip()) {
            throw new IllegalOperationException("Cannot get budget before setting a trip");
        }
        return tripManager.getTripBudget().value;
    }

    @Override
    public void setBudget(Budget editedBudget) {
        if (!hasTrip()) {
            throw new IllegalOperationException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }
        tripManager.setBudget(editedBudget);
    }

    @Override
    public void setTitle(Title editedTitle) {
        if (!hasTrip()) {
            throw new IllegalOperationException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }
        tripManager.setTripTitle(editedTitle);
    }

    @Override
    public void scheduleActivity(Activity toSchedule) {
        if (!hasTrip()) {
            throw new IllegalOperationException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }
        tripManager.scheduleActivity(toSchedule);
    }

    @Override
    public double getExchangeRate() {
        if (!hasTrip()) {
            throw new IllegalOperationException("Cannot get exchange rate before setting a trip");
        }
        return tripManager.getExchangeRate().value;
    }

    @Override
    public void unscheduleActivity(DayScheduleEntry toDelete) {
        if (!hasTrip()) {
            throw new IllegalOperationException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }
        tripManager.unscheduleActivity(toDelete);
    }

    @Override
    public void scheduleTransport(TransportBooking toSchedule) {
        if (!hasTrip()) {
            throw new IllegalOperationException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }
        tripManager.scheduleTransportBooking(toSchedule);
    }

    @Override
    public void unscheduleTransport(DayScheduleEntry toDelete) {
        if (!hasTrip()) {
            throw new IllegalOperationException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }
        tripManager.unscheduleTransportBooking(toDelete);
    }

    @Override
    public int getTripNumDays() {
        if (!hasTrip()) {
            throw new IllegalOperationException("Cannot get number of days before setting a trip");
        }
        return tripManager.getTripNumDays();
    }

    @Override
    public ObservableList<DayScheduleEntry> getDayScheduleEntryList(int dayIndex) {
        if (!hasTrip()) {
            throw new IllegalOperationException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }
        return filteredScheduleEntryLists.get(dayIndex);
    }

    @Override
    public List<ObservableList<DayScheduleEntry>> getScheduleList() {
        if (!hasTrip()) {
            throw new IllegalOperationException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }

        return filteredScheduleEntryLists;
    }

    // ========== TransportBookingManager ==========

    @Override
    public ReadOnlyTransportBookingManager getTransportBookingManager() {
        return transportBookingManager;
    }

    @Override
    public void setTransportBookingManager(ReadOnlyTransportBookingManager transportBookingManager) {
        this.transportBookingManager.resetData(transportBookingManager);
    }

    @Override
    public boolean hasTransportBooking(TransportBooking target) {
        requireNonNull(target);
        return transportBookingManager.hasTransportBooking(target);
    }

    @Override
    public void deleteTransportBooking(TransportBooking toDelete) {
        requireNonNull(toDelete);
        transportBookingManager.removeTransportBooking(toDelete);
    }

    @Override
    public void addTransportBooking(TransportBooking toAdd) {
        requireNonNull(toAdd);
        transportBookingManager.addTransportBooking(toAdd);
        updateFilteredTransportBookingList(PREDICATE_SHOW_ALL_TRANSPORT_BOOKINGS);
    }

    @Override
    public void setTransportBooking(TransportBooking target, TransportBooking edited) {
        CollectionUtil.requireAllNonNull(target, edited);
        transportBookingManager.setTransportBooking(target, edited);
    }

    @Override
    public ObservableList<TransportBooking> getFilteredTransportBookingList() {
        return filteredTransportBookingList;
    }

    @Override
    public void updateFilteredTransportBookingList(Predicate<TransportBooking> predicate) {
        requireNonNull(predicate);
        filteredTransportBookingList.setPredicate(predicate);
    }

    @Override
    public void sortTransportList(Comparator<TransportBooking> cmp) {
        requireNonNull(cmp);
        transportBookingManager.sortTransportList(cmp);
    }

    // ========== FixedExpenseManager ==========

    @Override
    public ReadOnlyFixedExpenseManager getFixedExpenseManager() {
        return fixedExpenseManager;
    }

    public void setFixedExpenseManager(ReadOnlyFixedExpenseManager fixedExpenseManager) {
        requireNonNull(fixedExpenseManager);
        this.fixedExpenseManager.resetData(fixedExpenseManager);
    }

    @Override
    public boolean hasFixedExpense(FixedExpense target) {
        requireNonNull(target);
        return fixedExpenseManager.hasFixedExpense(target);
    }

    @Override
    public void deleteFixedExpense(FixedExpense toDelete) {
        requireNonNull(toDelete);
        fixedExpenseManager.removeFixedExpense(toDelete);
    }

    @Override
    public void addFixedExpense(FixedExpense toAdd) {
        requireNonNull(toAdd);
        fixedExpenseManager.addFixedExpense(toAdd);
        updateFilteredFixedExpenseList(PREDICATE_SHOW_ALL_FIXED_EXPENSES);
    }

    @Override
    public void setFixedExpense(FixedExpense target, FixedExpense edited) {
        CollectionUtil.requireAllNonNull(target, edited);
        fixedExpenseManager.setFixedExpense(target, edited);
    }

    @Override
    public void sortFixedExpenseList(Comparator<FixedExpense> cmp) {
        requireNonNull(cmp);
        fixedExpenseManager.sortFixedExpenseList(cmp);
    }

    @Override
    public double getTotalExpense() {
        return fixedExpenseManager.getTotalExpense();
    }

    @Override
    public ObservableList<FixedExpense> getFilteredFixedExpenseList() {
        return filteredFixedExpenseList;
    }

    @Override
    public void updateFilteredFixedExpenseList(Predicate<FixedExpense> predicate) {
        requireNonNull(predicate);
        filteredFixedExpenseList.setPredicate(predicate);
    }

    // ========== PackingListManager ==========

    @Override
    public ReadOnlyPackingListManager getPackingListManager() {
        return packingListManager;
    }

    @Override
    public void setPackingListManager(ReadOnlyPackingListManager packingListManager) {
        requireAllNonNull(packingListManager);
        this.packingListManager.resetData(packingListManager);
    }

    @Override
    public boolean hasPackingListItem(PackingListItem target) {
        requireNonNull(target);
        return packingListManager.hasPackingListItem(target);
    }

    @Override
    public void deletePackingListItem(PackingListItem toDelete) {
        requireNonNull(toDelete);
        packingListManager.removePackingListItem(toDelete);
    }

    @Override
    public void addPackingListItem(PackingListItem toAdd) {
        requireNonNull(toAdd);
        packingListManager.addPackingListItem(toAdd);
        updateFilteredPackingList(PREDICATE_SHOW_ALL_PACKING_LIST_ITEMS);
    }

    @Override
    public void setPackingListItem(PackingListItem target, PackingListItem edited) {
        CollectionUtil.requireAllNonNull(target, edited);
        packingListManager.setPackingListItem(target, edited);
    }

    @Override
    public ObservableList<PackingListItem> getFilteredPackingList() {
        return filteredPackingList;
    }

    @Override
    public void sortPackingList(Comparator<PackingListItem> cmp) {
        requireNonNull(cmp);
        packingListManager.sortPackingList(cmp);
    }

    @Override
    public void updateFilteredPackingList(Predicate<PackingListItem> predicate) {
        requireNonNull(predicate);
        filteredPackingList.setPredicate(predicate);
    }

    // ========== ActivityManager ==========

    @Override
    public ReadOnlyActivityManager getActivityManager() {
        return activityManager;
    }

    public void setActivityManager(ReadOnlyActivityManager activityManager) {
        requireNonNull(activityManager);
        this.activityManager.resetData(activityManager);
    }

    @Override
    public boolean hasActivity(Activity target) {
        requireNonNull(target);
        return activityManager.hasActivity(target);
    }

    @Override
    public void deleteActivity(Activity toDelete) {
        requireNonNull(toDelete);
        activityManager.removeActivity(toDelete);

    }

    @Override
    public void addActivity(Activity toAdd) {
        requireNonNull(toAdd);
        activityManager.addActivity(toAdd);
        updateFilteredActivityList(PREDICATE_SHOW_ALL_ACTIVITIES);
    }

    @Override
    public void setActivity(Activity target, Activity edited) {
        CollectionUtil.requireAllNonNull(target, edited);
        activityManager.setActivity(target, edited);
    }

    @Override
    public ObservableList<Activity> getFilteredActivityList() {
        return filteredActivityList;
    }

    @Override
    public void updateFilteredActivityList(Predicate<Activity> predicate) {
        requireNonNull(predicate);
        filteredActivityList.setPredicate(predicate);
    }

    @Override
    public void sortActivityList(Comparator<Activity> cmp) {
        requireNonNull(cmp);
        activityManager.sortActivityList(cmp);
    }

    // ========== AccommodationBookingManager ==========

    @Override
    public ReadOnlyAccommodationBookingManager getAccommodationBookingManager() {
        return accommodationBookingManager;
    }

    @Override
    public void setAccommodationBookingManager(ReadOnlyAccommodationBookingManager accommodationBookingManager) {
        requireNonNull(accommodationBookingManager);
        this.accommodationBookingManager.resetData(accommodationBookingManager);
    }

    @Override
    public boolean hasAccommodationBooking(AccommodationBooking target) {
        requireNonNull(target);
        return accommodationBookingManager.hasAccommodationBooking(target);
    }

    @Override
    public void deleteAccommodationBooking(AccommodationBooking toDelete) {
        requireNonNull(toDelete);
        accommodationBookingManager.removeAccommodationBooking(toDelete);
    }

    @Override
    public void addAccommodationBooking(AccommodationBooking toAdd) {
        requireNonNull(toAdd);
        accommodationBookingManager.addAccommodationBooking(toAdd);
        updateFilteredAccommodationBookingList(PREDICATE_SHOW_ALL_ACCOMMODATION_BOOKINGS);
    }

    @Override
    public void setAccommodationBooking(AccommodationBooking target, AccommodationBooking edited) {
        CollectionUtil.requireAllNonNull(target, edited);
        accommodationBookingManager.setAccommodationBooking(target, edited);
    }

    @Override
    public ObservableList<AccommodationBooking> getFilteredAccommodationBookingList() {
        return filteredAccommodationBookingList;
    }

    @Override
    public void updateFilteredAccommodationBookingList(Predicate<AccommodationBooking> predicate) {
        accommodationBookingManager.sortAccommodationBookings();
        requireNonNull(predicate);
        filteredAccommodationBookingList.setPredicate(predicate);
    }

    @Override
    public boolean isOverlappingWithOthers(AccommodationBooking toCheck) {
        return getFilteredAccommodationBookingList().stream().anyMatch(x -> x.isOverlapping(toCheck));
    }

    @Override
    public void sortAccommodationList(Comparator<AccommodationBooking> cmp) {
        requireNonNull(cmp);
        accommodationBookingManager.sortAccommodationBookings(cmp);
    }

    // ========== Util ==========

    @Override
    public void resetAllListManagers() {
        tripManager.resetData(new TripManager());
        this.fixedExpenseManager.resetData(new FixedExpenseManager());
        this.packingListManager.resetData(new PackingListManager());
        this.activityManager.resetData(new ActivityManager());
        this.accommodationBookingManager.resetData(new AccommodationBookingManager());
        this.transportBookingManager.resetData(new TransportBookingManager());
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return userPrefs.equals(other.userPrefs)
                && transportBookingManager.equals(other.transportBookingManager)
                && filteredTransportBookingList.equals(other.filteredTransportBookingList)
                && fixedExpenseManager.equals(other.fixedExpenseManager)
                && filteredFixedExpenseList.equals(other.filteredFixedExpenseList)
                && packingListManager.equals(other.packingListManager)
                && filteredPackingList.equals(other.filteredPackingList)
                && activityManager.equals(other.activityManager)
                && filteredActivityList.equals(other.filteredActivityList)
                && accommodationBookingManager.equals(other.accommodationBookingManager)
                && filteredAccommodationBookingList.equals(other.filteredAccommodationBookingList)
                && tripManager.equals(other.tripManager)
                && filteredScheduleEntryLists.equals(other.filteredScheduleEntryLists);
    }
}
