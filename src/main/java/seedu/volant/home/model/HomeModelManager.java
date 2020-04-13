package seedu.volant.home.model;

import static java.util.Objects.requireNonNull;
import static seedu.volant.commons.logic.Page.HOME;
import static seedu.volant.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.volant.commons.core.GuiSettings;
import seedu.volant.commons.logic.Page;
import seedu.volant.commons.model.Model;
import seedu.volant.commons.model.ReadOnlyUserPrefs;
import seedu.volant.commons.model.UserPrefs;
import seedu.volant.home.model.trip.Trip;

/**
 * Represents the in-memory model of the trip list data on the HOME page.
 */
public class HomeModelManager implements Model {

    /** {@code Predicate} that always evaluate to true */
    private final Predicate<Trip> predicateShowAllTrips = unused -> true;
    private final TripList tripList;
    private final UserPrefs userPrefs;
    private final FilteredList<Trip> filteredTrips;
    private final Page page = HOME;

    /**
     * Initializes a HomeModelManager with the given tripList and userPrefs.
     */
    public HomeModelManager(ReadOnlyTripList tripList, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(tripList, userPrefs);

        LOGGER.fine("Initializing with trip list: " + tripList + " and user prefs " + userPrefs);

        this.tripList = new TripList(tripList);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredTrips = new FilteredList<>(this.tripList.getTripList());
    }

    public HomeModelManager() {
        this(new TripList(), new UserPrefs());
    }

    //=========== TripList ================================================================================

    public void setTripList(ReadOnlyTripList tripList) {
        this.tripList.resetData(tripList);
    }

    public TripList getTripList() {
        return tripList;
    }

    /**
     * Returns true if trip is within the trip list within model.
     */
    public boolean hasTrip(Trip trip) {
        requireNonNull(trip);
        return tripList.hasTrip(trip);
    }

    /**
     * Returns true if the date range of trip to be added clashes with existing trips within model.
     */
    public boolean hasClash(Trip trip) {
        requireNonNull(trip);
        return tripList.hasClash(trip);
    }

    /**
     * Removes specified target {@code Trip} from trip list within model.
     */
    public void deleteTrip(Trip target) {
        tripList.removeTrip(target);
        updateFilteredTripList(predicateShowAllTrips);
    }

    /**
     * Adds trip to trip list within model.
     */
    public void addTrip(Trip trip) {
        tripList.addTrip(trip);
        updateFilteredTripList(predicateShowAllTrips);
    }

    /**
     * Replaces target trip with editedTrip in the trip list within model.
     */
    public void setTrip(Trip target, Trip editedTrip) {
        requireAllNonNull(target, editedTrip);
        tripList.setTrip(target, editedTrip);
    }

    public Predicate<Trip> getPredicateShowAllTrips() {
        return predicateShowAllTrips;
    }

    //=========== Filtered Trip List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Trip} backed by the internal list of
     * {@code versionedAddressBook}
     */

    public ObservableList<Trip> getFilteredTripList() {
        return filteredTrips;
    }

    /**
     * Updates the filtered trip list according to the given predicate.
     */
    public void updateFilteredTripList(Predicate<Trip> predicate) {
        requireNonNull(predicate);
        filteredTrips.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof HomeModelManager)) {
            return false;
        }

        // state check
        HomeModelManager other = (HomeModelManager) obj;
        return tripList.equals(other.tripList)
                && userPrefs.equals(other.userPrefs)
                && filteredTrips.equals(other.filteredTrips);
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
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

    @Override
    public Path getVolantFilePath() {
        return userPrefs.getVolantFilePath();
    }

    @Override
    public void setVolantFilePath(Path volantFilePath) {
        requireNonNull(volantFilePath);
        userPrefs.setVolantFilePath(volantFilePath);
    }

}
