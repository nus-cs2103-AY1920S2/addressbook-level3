package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.day.Day;
import seedu.address.model.event.Event;
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
    private final EventSchedule eventSchedule;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Person> filteredPersonsResult;
    private final FilteredList<Restaurant> filteredRestaurants;
    private final FilteredList<Assignment> filteredAssignments;
    private final FilteredList<Event> filteredEvents;
    private final FilteredList<Person> bdayList;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyRestaurantBook restaurantBook,
                        ReadOnlyScheduler scheduler, ReadOnlyEventSchedule eventSchedule, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, scheduler, eventSchedule, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonsList());
        filteredPersonsResult = new FilteredList<>(this.addressBook.getPersonsList());
        this.restaurantBook = new RestaurantBook(restaurantBook);
        this.scheduler = new Scheduler(scheduler);
        this.eventSchedule = new EventSchedule(eventSchedule);
        filteredRestaurants = new FilteredList<>(this.restaurantBook.getRestaurantsList());
        filteredAssignments = new FilteredList<>(this.scheduler.getAssignmentsList());
        filteredEvents = new FilteredList<>(this.eventSchedule.getEventsList());
        bdayList = new FilteredList<>(this.addressBook.getBdayList());
    }

    public ModelManager() {
        this(new AddressBook(), new RestaurantBook(), new Scheduler(), new EventSchedule(), new UserPrefs());
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
        updateFilteredAssignmentList(PREDICATE_SHOW_ALL_ASSIGNMENTS);
    }

    @Override
    public boolean hasAssignment(Assignment assignment) {
        requireNonNull(assignment);
        return scheduler.hasAssignment(assignment);
    }

    @Override
    public void sortAssignment(Comparator<Assignment> comparator) {
        scheduler.sortAssignment(comparator);
        updateFilteredAssignmentList(PREDICATE_SHOW_ALL_ASSIGNMENTS);
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
    public ObservableList<Assignment> getAssignmentList() {
        return filteredAssignments;
    }

    //=========== Event Schedule ================================================================================

    @Override
    public void setEventSchedule(ReadOnlyEventSchedule eventSchedule) {
        this.eventSchedule.resetData(eventSchedule);
    }

    @Override
    public void addEvent(Event event) {
        eventSchedule.addEvent(event);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
    }

    @Override
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return eventSchedule.hasEvent(event);
    }

    @Override
    public void sortEvent(Comparator<Event> comparator) {
        eventSchedule.sortEvent(comparator);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
    }

    @Override
    public ReadOnlyEventSchedule getEventSchedule() {
        return eventSchedule;
    }

    @Override
    public ObservableList<Event> getEventsList() {
        return filteredEvents;
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

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook} for the result panel
     */
    @Override
    public ObservableList<Person> getFilteredPersonListResult() {
        return filteredPersonsResult;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateFilteredPersonListResult(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersonsResult.setPredicate(predicate);
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
                && scheduler.equals(other.scheduler)
                && eventSchedule.equals(other.eventSchedule)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons)
                && filteredPersonsResult.equals(other.filteredPersonsResult)
                && filteredAssignments.equals(other.filteredAssignments)
                && filteredEvents.equals(other.filteredEvents)
                && filteredRestaurants.equals(other.filteredRestaurants);
    }

    //=========== Filtered Assignment List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Assignment> getFilteredAssignmentList() {
        return filteredAssignments;
    }

    @Override
    public void updateFilteredAssignmentList(Predicate<Assignment> predicate) {
        requireNonNull(predicate);
        filteredAssignments.setPredicate(predicate);
    }

    //=========== Filtered Event List Accessors ==================================================================

    /**
     * Returns an unmodifiable view of the list of {@code Events} backed by the internal list of
     * {@code versionedEventSchedule}
     */
    @Override
    public ObservableList<Event> getFilteredEventList() {
        return filteredEvents;
    }

    @Override
    public void updateFilteredEventList(Predicate<Event> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
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

    //=========== Filtered Bday List Accessors ====================================================================
    @Override
    public ObservableList<Person> getBdayListResult() {
        return this.addressBook.getBdayList();
    }

    //=========== Schedule Visual Accessor ========================================================================
    @Override
    public ObservableList<Day> getScheduleVisualResult() {
        return this.scheduler.getScheduleVisual();
    }

    //=========== Schedule Visual Accessor ========================================================================
    @Override
    public void undo() {

    }
}
