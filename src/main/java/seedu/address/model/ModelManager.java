package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.modelTeacher.ReadOnlyTeacherAddressBook;
import seedu.address.model.modelTeacher.TeacherAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.Teacher;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final TeacherAddressBook teacherAddressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Teacher> filteredTeachers;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyTeacherAddressBook teacherAddressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, teacherAddressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + "Initializing with  teacher address book: " + teacherAddressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.teacherAddressBook = new TeacherAddressBook(teacherAddressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredTeachers = new FilteredList<>(this.teacherAddressBook.getTeacherList());
    }

    public ModelManager() {
        this(new AddressBook(), new TeacherAddressBook(), new UserPrefs());
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

    @Override
    public Path getTeacherAddressBookFilePath() {
        return userPrefs.getTeacherAddressBookFilePath();
    }

    @Override
    public void setTeacherAddressBookFilePath(Path teacherAddressBookFilePath) {
        requireNonNull(teacherAddressBookFilePath);
        userPrefs.setTeacherAddressBookFilePath(teacherAddressBookFilePath);
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

    @Override
    public void setTeacherAddressBook(ReadOnlyTeacherAddressBook teacherAddressBook) {
        this.teacherAddressBook.resetData(teacherAddressBook);
    }

    @Override
    public ReadOnlyTeacherAddressBook getTeacherAddressBook() {
        return teacherAddressBook;
    }

    @Override
    public boolean hasTeacher(Teacher teacher) {
        requireNonNull(teacher);
        return teacherAddressBook.hasTeachers(teacher);
    }

    @Override
    public void deleteTeacher(Teacher target) {
        teacherAddressBook.removeTeacher(target);
    }

    @Override
    public void addTeacher(Teacher teacher) {
        teacherAddressBook.addTeacher(teacher);
        updateFilteredTeacherList(PREDICATE_SHOW_ALL_TEACHERS);
    }

    @Override
    public void setTeacher(Teacher target, Teacher editedTeacher) {
        requireAllNonNull(target, editedTeacher);

        teacherAddressBook.setTeacher(target, editedTeacher);
    }

    //=========== Filtered List Accessors =============================================================

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

    /**
     * Returns an unmodifiable view of the list of {@code Teacher} backed by the internal list of
     * {@code versionedTeacherAddressBook}
     */
    @Override
    public ObservableList<Teacher> getFilteredTeacherList() {
        return filteredTeachers;
    }

    @Override
    public void updateFilteredTeacherList(Predicate<Teacher> predicate) {
        requireNonNull(predicate);
        filteredTeachers.setPredicate(predicate);
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
        return teacherAddressBook.equals(other.teacherAddressBook)
            && userPrefs.equals(other.userPrefs)
            && filteredTeachers.equals(other.filteredTeachers);
    }

}
