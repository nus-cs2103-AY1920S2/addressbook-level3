package seedu.address.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.person.Person;
import seedu.address.model.restaurant.Restaurant;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Restaurant> PREDICATE_SHOW_ALL_RESTAURANTS = unused -> true;

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

    ArrayList<Assignment> getAssignmentList();

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

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

    /**
     * Replaces scheduler data with the data in {@code scheduler}.
     */
    void setScheduler(ReadOnlyScheduler scheduler);

    /** Returns the Scheduler */
    ReadOnlyScheduler getScheduler();

    /**
     * Adds the given assignment.
     * {@code assigment} must not already exist in the scheduler.
     */
    void addAssignment(Assignment toAdd);

    /**
     * Returns true if an assignment with the same title and deadline as {@code assignment} exists in the scheduler.
     */
    boolean hasAssignment(Assignment toAdd);

    void setAssignment(Assignment assignmentToUpdate, Assignment updatedAssignment);
}
