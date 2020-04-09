package seedu.address.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.day.Day;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.restaurant.Restaurant;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Assignment> PREDICATE_SHOW_ALL_ASSIGNMENTS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Event> PREDICATE_SHOW_ALL_EVENTS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Restaurant> PREDICATE_SHOW_ALL_RESTAURANTS = unused -> true;

    //=================  User Prefs ==========================================================

    /**
     * Clears all data
     */
    void clear();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    //=================  Address Book ==========================================================

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    ObservableList<Assignment> getAssignmentList();

    ObservableList<Event> getEventsList();

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered person list for the result panel*/
    ObservableList<Person> getFilteredPersonListResult();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate} in the result panel.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonListResult(Predicate<Person> predicate);

    /**
     * Replaces restaurant book data with the data in {@code restaurantBook}.
     */
    void setRestaurantBook(ReadOnlyRestaurantBook restaurantBook);

    /** Returns the RestaurantBook */
    ReadOnlyRestaurantBook getRestaurantBook();

    /**
     * Returns true if a restaurant with the same identity as {@code restaurant} exists in the restaurant book.
     */
    boolean hasRestaurant(Restaurant restaurant);

    /**
     * Deletes the given restaurant.
     * The restaurant must exist in the restaurant book.
     */
    void deleteRestaurant(Restaurant target);

    /**
     * Adds the given restaurant.
     * {@code restaurant} must not already exist in the restaurant book.
     */
    void addRestaurant(Restaurant restaurant);

    /**
     * Replaces the given restaurant {@code target} with {@code editedRestaurant}.
     * {@code target} must exist in the restaurant book.
     * The restaurant identity of {@code editedRestaurant} must not be the same as another existing restaurant in the
     * restaurant book.
     */
    void setRestaurant(Restaurant target, Restaurant editedRestaurant);

    /** Returns an unmodifiable view of the filtered restaurant list */
    ObservableList<Restaurant> getFilteredRestaurantList();

    /**
     * Updates the filter of the filtered restaurant list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredRestaurantList(Predicate<Restaurant> predicate);

    //=================  Schoolwork Tracker ==========================================================

    /**
     * Replaces assignmentSchedule data with the data in {@code assignmentSchedule}.
     */
    void setAssignmentSchedule(ReadOnlyAssignmentSchedule assignmentSchedule);

    /** Returns the AssignmentSchedule */
    ReadOnlyAssignmentSchedule getAssignmentSchedule();

    /**
     * Adds the given assignment.
     * {@code Assignment} must not already exist in the scheduler.
     */
    void addAssignment(Assignment toAdd);

    /**
     * Returns true if an assignment with the same title and deadline as {@code assignment} exists in the scheduler.
     */
    boolean hasAssignment(Assignment toAdd);

    /**
     * Sorts the scheduler by the filter.
     */
    void sortAssignment(Comparator<Assignment> comparator);

    /**
     * Sets an assignment
     * @param assignmentToUpdate assignment to be replaced
     * @param updatedAssignment assignment to replace the replaced
     */
    void setAssignment(Assignment assignmentToUpdate, Assignment updatedAssignment);

    /**
     * Deletes the given assignment.
     * Assignment must exist in the Schoolwork Tracker.
     */
    void deleteAssignment(Assignment assignmentToDelete);

    //=================  Filtered Assignment List Accessors ==================================================

    /** Returns an unmodifiable view of the filtered assignment list */
    ObservableList<Assignment> getFilteredAssignmentList();

    /**
     * Updates the filter of the filtered assignment list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredAssignmentList(Predicate<Assignment> predicate);

    //=================  Event Book ==========================================================================

    /**
     * Replaces EventSchedule data with the data in {@code eventSchedule}.
     */
    void setEventSchedule(ReadOnlyEventSchedule eventSchedule);

    /** Returns the Event Schedule */
    ReadOnlyEventSchedule getEventSchedule();

    /**
     * Adds the given Event.
     * {@code Event} must not already exist in the event schedule.
     */
    void addEvent(Event toAdd);

    /** Deletes the given event; event must exist in the Event Schedule. */
    void deleteEvent(Event eventToDelete);

    /**
     * Returns true if an Event with the same title and date as {@code Event} exists in the event schedule
     */
    boolean hasEvent(Event toAdd);

    /**
     * Returns true if there is an Assignment due on the same date as the event which we want to add.
     */
    boolean hasAssignmentDueOnSameDate(Event toAdd);

    /**
     * Sets an event
     * @param eventToUpdate event to be replaced
     * @param updatedEvent event to replace the replaced event
     */
    void setEvent(Event eventToUpdate, Event updatedEvent);

    /**
     * Returns true if there is a clashing event (same timing and same date).
     */
    boolean hasClashingEvent(Event event);

    /** Sorts the event schedule by the filter */
    void sortEvent(Comparator<Event> comparator);

    //=================  Filtered Event List Accessors =======================================================

    /** Returns an unmodifiable view of the filtered event list */
    ObservableList<Event> getFilteredEventList();

    /**
     * Updates the filter of the filtered event list to filter by the given {@code Predicate}
     * @throws NullPointerException if {@code predicate} is null
     */
    void updateFilteredEventList(Predicate<Event> predicate);

    //=========== Bday List Accessors ====================================================================

    /**
     * Returns an unmodifiable view of the list of contacts with birthdays in the next 5 days.
     */
    ObservableList<Person> getBdayListResult();

    //=========== ScheduleList Visual Accessors =======================================================================

    /**
     * Creates the schedule for the upcoming numDays (today inclusive).
     */
    void createSchedule(int numDays);

    /**
     * Sets a day in the scheduleList.
     */
    void setDay(int index, Day toSet);

    /**
     * Returns an unmodifiable view of the user's upcoming schedule for the next n days (today inclusive).
     */
    ObservableList<Day> getSchedule();

    //=========== Undo and Redo =======================================================================

    /**
     *
     * @return the number of states currently saved for undo
     */
    int undoStackSize();

    /**
     *
     * @return the number of states currently saved for redo
     */
    int redoStackSize();

    /**
     * Undo the previously entered command
     */
    String undo();

    /**
     * Redo any command that was undone
     */
    String redo();
}
