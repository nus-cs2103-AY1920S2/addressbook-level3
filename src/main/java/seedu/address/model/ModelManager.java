package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.person.Person;
import seedu.address.model.restaurant.Restaurant;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final RestaurantBook restaurantBook;
    private final Scheduler scheduler;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Restaurant> filteredRestaurants;
    private final ArrayList<Assignment> assignments;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyRestaurantBook restaurantBook,
                        ReadOnlyScheduler scheduler, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, scheduler, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.restaurantBook = new RestaurantBook(restaurantBook);
        this.scheduler = new Scheduler(scheduler);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonsList());
        filteredRestaurants = new FilteredList<>(this.restaurantBook.getRestaurantsList());
        assignments = this.scheduler.getAssignmentsList();
    }

    public ModelManager() {
        this(new AddressBook(), new RestaurantBook(), new Scheduler(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

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
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    //========== Schoolwork Tracker ==========================================================================
    @Override
    public void setScheduler(ReadOnlyScheduler scheduler) {
        this.scheduler.resetData(scheduler);
    }

    @Override
    public void addAssignment(Assignment assignment) {
        scheduler.addAssignment(assignment);
    }

    @Override
    public boolean hasAssignment(Assignment assignment) {
        requireNonNull(assignment);
        return scheduler.hasAssignment(assignment);
    }

    @Override
    public ReadOnlyScheduler getScheduler() {
        return scheduler;
    }

    @Override
    public void setAssignment(Assignment target, Assignment markedAssignment) {
        requireAllNonNull(target, markedAssignment);

        scheduler.setAssignment(target, markedAssignment);
    }

    @Override
    public ArrayList<Assignment> getAssignmentList() {
        return assignments;
    }

    //=========== RestaurantBook ================================================================================

    @Override
    public void setRestaurantBook(ReadOnlyRestaurantBook restaurantBookBook) {
        this.restaurantBook.resetData(restaurantBook);
    }

    @Override
    public ReadOnlyRestaurantBook getRestaurantBook() {
        return restaurantBook;
    }

    @Override
    public boolean hasRestaurant(Restaurant person) {
        requireNonNull(person);
        return restaurantBook.hasRestaurant(person);
    }

    @Override
    public void deleteRestaurant(Restaurant target) {
        restaurantBook.removeRestaurant(target);
    }

    @Override
    public void addRestaurant(Restaurant person) {
        restaurantBook.addRestaurant(person);
        updateFilteredRestaurantList(PREDICATE_SHOW_ALL_RESTAURANTS);
    }

    @Override
    public void setRestaurant(Restaurant target, Restaurant editedRestaurant) {
        requireAllNonNull(target, editedRestaurant);

        restaurantBook.setRestaurant(target, editedRestaurant);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
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
        return addressBook.equals(other.addressBook)
                && restaurantBook.equals(other.restaurantBook)
                // && scheduler.equals(other.scheduler)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons);
    }

    //=========== Filtered Restaurant List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Restaurant} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Restaurant> getFilteredRestaurantList() {
        return filteredRestaurants;
    }

    @Override
    public void updateFilteredRestaurantList(Predicate<Restaurant> predicate) {
        requireNonNull(predicate);
        filteredRestaurants.setPredicate(predicate);
    }
}
