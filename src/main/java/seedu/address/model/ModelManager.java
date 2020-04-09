package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Stack;
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

    private final Stack<ModelState> undoStates;
    private final Stack<ModelState> redoStates;
    private ModelState currentModel;

    private AddressBook addressBook;
    private RestaurantBook restaurantBook;
    private Scheduler scheduler;
    private EventSchedule eventSchedule;
    private UserPrefs userPrefs;
    private FilteredList<Person> filteredPersons;
    private FilteredList<Person> filteredPersonsResult;
    private FilteredList<Restaurant> filteredRestaurants;
    private FilteredList<Assignment> filteredAssignments;
    private FilteredList<Event> filteredEvents;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyRestaurantBook restaurantBook,
                        ReadOnlyScheduler scheduler, ReadOnlyEventSchedule eventSchedule, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, scheduler, eventSchedule, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.currentModel = new ModelState(addressBook, restaurantBook, scheduler, eventSchedule, userPrefs);
        this.undoStates = new Stack<>();
        this.redoStates = new Stack<>();
        undoStates.push(currentModel);
        update();
    }

    public ModelManager() {
        this.currentModel = new ModelState();
        this.undoStates = new Stack<>();
        this.redoStates = new Stack<>();
        undoStates.push(currentModel);
        update();
    }

    @Override
    public void clear() {
        createNewState("BIRTHDAY");
        setAddressBook(new AddressBook());
        setRestaurantBook(new RestaurantBook());
        setScheduler(new Scheduler());
        setEventSchedule(new EventSchedule());
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
        createNewState("ADDRESS");
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        createNewState("ADDRESS");
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        createNewState("ADDRESS");
        addressBook.setPerson(target, editedPerson);
    }

    //========== Schoolwork Tracker ==========================================================================

    @Override
    public void setScheduler(ReadOnlyScheduler scheduler) {
        this.scheduler.resetData(scheduler);
    }

    @Override
    public void addAssignment(Assignment assignment) {
        createNewState("ASSIGNMENTS");
        scheduler.addAssignment(assignment);
        updateFilteredAssignmentList(PREDICATE_SHOW_ALL_ASSIGNMENTS);
    }

    @Override
    public void sortAssignment(Comparator<Assignment> comparator) {
        scheduler.sortAssignment(comparator);
        updateFilteredAssignmentList(PREDICATE_SHOW_ALL_ASSIGNMENTS);
    }

    @Override
    public void setAssignment(Assignment target, Assignment markedAssignment) {
        requireAllNonNull(target, markedAssignment);
        createNewState("ASSIGNMENTS");
        scheduler.setAssignment(target, markedAssignment);
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
    public ObservableList<Assignment> getAssignmentList() {
        return filteredAssignments;
    }

    @Override
    public void deleteAssignment(Assignment target) {
        scheduler.removeAssignment(target);
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
    public void setEvent(Event target, Event markedEvent) {
        requireAllNonNull(target, markedEvent);
        createNewState("EVENTS");
        eventSchedule.setEvent(target, markedEvent);
    }

    @Override
    public boolean hasAssignmentDueOnSameDate(Event toAdd) {
        LocalDate eventDate = toAdd.getEventDate().getDateTime().toLocalDate();
        ObservableList<Assignment> assignments = getAssignmentList();
        for (int i = 0; i < assignments.size(); i++) {
            LocalDate assignmentDate = assignments.get(i).getDeadline().getDateTime().toLocalDate();
            if (eventDate.isEqual(assignmentDate)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void deleteEvent(Event target) {
        eventSchedule.removeEvent(target);
    }

    @Override
    public void sortEvent(Comparator<Event> comparator) {
        eventSchedule.sortEvent(comparator);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
    }

    @Override
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return eventSchedule.hasEvent(event);
    }

    @Override
    public boolean hasClashingEvent(Event event) {
        requireNonNull(event);
        return eventSchedule.hasClashingEvent(event);
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
    public void setRestaurantBook(ReadOnlyRestaurantBook restaurantBook) {
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
        createNewState("RESTAURANTS");
        restaurantBook.removeRestaurant(target);
    }

    @Override
    public void addRestaurant(Restaurant restaurant) {
        createNewState("RESTAURANTS");
        restaurantBook.addRestaurant(restaurant);
        updateFilteredRestaurantList(PREDICATE_SHOW_ALL_RESTAURANTS);
    }

    @Override
    public void setRestaurant(Restaurant target, Restaurant editedRestaurant) {
        requireAllNonNull(target, editedRestaurant);
        createNewState("RESTAURANTS");
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
    public void calculateScheduleIntensity(int numDays) {
        this.scheduler.calculateScheduleIntensity(numDays);
    }

    @Override
    public ObservableList<Day> getScheduleVisualResult() {
        return this.scheduler.getScheduleVisual();
    }

    //=========== Undo and Redo ========================================================================

    /**
     * Duplicates current state, pops current state, pushes the duplicate, the push the current state into undostack
     * @param commandType the command type (which databased is changed) that led to this state
     */
    private void createNewState(String commandType) {
        ModelState state = ModelState.copy(currentModel);

        currentModel = undoStates.pop();
        currentModel.setCommandType(commandType);
        update();

        undoStates.push(state);
        undoStates.push(currentModel);

        while (!redoStates.isEmpty()) {
            redoStates.pop();
        }
    }

    /**
     * Make all attributes point to the current state ones
     */
    private void update() {
        this.addressBook = this.currentModel.getAddressBook();
        this.userPrefs = this.currentModel.getUserPrefs();
        filteredPersons = this.currentModel.getFilteredPersons();
        filteredPersonsResult = this.currentModel.getFilteredPersonsResult();
        this.restaurantBook = this.currentModel.getRestaurantBook();
        this.scheduler = this.currentModel.getScheduler();
        this.eventSchedule = this.currentModel.getEventSchedule();
        filteredRestaurants = this.currentModel.getFilteredRestaurants();
        filteredAssignments = this.currentModel.getFilteredAssignments();
        filteredEvents = this.currentModel.getFilteredEvents();
    }

    /**
     * Returns the size of the undo stack
     * @return size of undo stack
     */
    public int undoStackSize() {
        return undoStates.size();
    }

    /**
     * Returns size of redo stack
     * @return size of redo stack
     */
    public int redoStackSize() {
        return redoStates.size();
    }

    /**
     * Un-does the last operation that alters something, pops the top of the undo stack into the redo stack
     * and makes the resulting top of the new undo stack the current state
     * @return the command type that represents which database is changed
     */
    public String undo() {
        String commandType = undoStates.peek().getCommandType();
        ModelState popped = undoStates.pop();
        redoStates.push(popped);
        currentModel = undoStates.peek();
        update();

        return commandType;
    }

    /**
     * Re-does the last undone operation, pops the top of redo stack into the undo stack
     * and makes the resulting top of the new undo stack the current state
     * @return the command type that represents which database is changed
     */
    public String redo() {
        String commandType = redoStates.peek().getCommandType();
        ModelState popped = redoStates.pop();
        undoStates.push(popped);
        currentModel = undoStates.peek();
        update();

        return commandType;
    }

}
