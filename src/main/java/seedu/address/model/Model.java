package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.event.consult.Consult;
import seedu.address.model.event.tutorial.Tutorial;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Consult> PREDICATE_SHOW_ALL_CONSULTS = unused -> true;
    Predicate<Tutorial> PREDICATE_SHOW_ALL_TUTORIALS = unused -> true;
    Predicate<Reminder> PREDICATE_SHOW_ALL_REMINDERS = unused -> true;

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

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    // Consult-level operations =======================================================================================

    /**
     * Returns true if a consult with the same identity as {@code consult} exists in TAble.
     */
    boolean hasConsult(Consult consult);

    /**
     * Adds the given consult.
     * {@code consult} must not already exist in TAble.
     */
    void addConsult(Consult consult);

    /**
     * Deletes the given consult.
     * The consult must exist in TAble.
     */
    void deleteConsult(Consult target);

    /**
     * Replaces the given consult {@code consultToEdit} with {@code editedConsult}.
     * {@code consultToEdit} must exist in TAble.
     * The person identity of {@code editedConsult} must not be the same as another existing consult in TAble.
     */
    void setConsult(Consult consultToEdit, Consult editedConsult);

    /**
     * Deletes all consults in the Consult TAble.
     */
    void clearConsults();

    /** Returns an unmodifiable view of the filtered consult list */
    ObservableList<Consult> getFilteredConsultList();

    /**
     * Updates the filter of the filtered consult list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredConsultList(Predicate<Consult> predicate);

    // Tutorial-level operations =====================================================================================

    /**
     * Returns true if a tutorial with the same identity as {@code tutorial} exists in TAble.
     */
    boolean hasTutorial(Tutorial tutorial);

    /**
     * Adds the given tutorial.
     * {@code tutorial} must not already exist in TAble.
     */
    void addTutorial(Tutorial tutorial);

    /**
     * Deletes the given tutorial.
     * The tutorial must exist in TAble.
     */
    void deleteTutorial(Tutorial target);

    /** Returns an unmodifiable view of the filtered tutorial list */
    ObservableList<Tutorial> getFilteredTutorialList();

    /**
     * Updates the filter of the filtered tutorial list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTutorialList(Predicate<Tutorial> predicate);

    // Reminder-level operations =====================================================================================

    /**
     * Returns true if a reminder with the same identity as {@code reminder} exists in TAble.
     */
    boolean hasReminder(Reminder reminder);

    /**
     * Adds the given reminder.
     * {@code reminder} must not already exist in TAble.
     */
    void addReminder(Reminder reminder);

    /**
     * Deletes the given reminder.
     * The reminder must exist in TAble.
     */
    void deleteReminder(Reminder target);

    /**
     * Replaces the given reminder {@code reminderToEdit} with {@code editedReminder}.
     * {@code reminderToEdit} must exist in TAble.
     * The identity of {@code editedReminder} must not be the same as another existing reminder in TAble.
     */
    void setReminder(Reminder reminderToEdit, Reminder editedReminder);

    /**
     * Marks the given reminder as done.
     * The reminder must exist in TAble.
     */
    Reminder doneReminder(Reminder target);

    /** Returns an unmodifiable view of the filtered reminder list */
    ObservableList<Reminder> getFilteredReminderList();

    /**
     * Updates the filter of the filtered reminder list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredReminderList(Predicate<Reminder> predicate);

}
