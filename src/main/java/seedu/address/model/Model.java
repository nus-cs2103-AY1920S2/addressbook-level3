package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.modelTeacher.ReadOnlyTeacherAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.Teacher;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Teacher> PREDICATE_SHOW_ALL_TEACHERS = unused -> true;

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




    /**
     * Returns the user prefs' address book file path.
     */
    Path getTeacherAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setTeacherAddressBookFilePath(Path teacherAddressBookFilePath);

    /**
     * Replaces teacher address book data with the data in {@code teacerAddressBook}.
     */
    void setTeacherAddressBook(ReadOnlyTeacherAddressBook teacherAddressBook);

    /** Returns the teacherAddressBook */
    ReadOnlyTeacherAddressBook getTeacherAddressBook();

    /**
     * Returns true if a teacher with the same identity as {@code teacher} exists in the address book.
     */
    boolean hasTeacher(Teacher teacher);

    /**
     * Deletes the given teacher.
     * The teacher must exist in the address book.
     */
    void deleteTeacher(Teacher target);

    /**
     * Adds the given teacher.
     * {@code teacher} must not already exist in the address book.
     */
    void addTeacher(Teacher teacher);

    /**
     * Replaces the given teacher {@code target} with {@code editedTeacher}.
     * {@code target} must exist in the address book.
     * The teacher identity of {@code editedTeacher} must not be the same as another existing teacher in the address book.
     */
    void setTeacher(Teacher target, Teacher editedTeacher);

    /** Returns an unmodifiable view of the filtered teacher list */
    ObservableList<Teacher> getFilteredTeacherList();

    /**
     * Updates the filter of the filtered teacher list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTeacherList(Predicate<Teacher> predicate);
}
