package seedu.address.logic;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.modelAssignment.Assignment;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelFinance.Finance;
import seedu.address.model.modelGeneric.ReadOnlyAddressBookGeneric;
import seedu.address.model.modelObjectTags.Person;
import seedu.address.model.modelStaff.Staff;
import seedu.address.model.modelStudent.Student;
import seedu.address.ui.MainWindow;
import seedu.address.ui.SummaryPanel;

import java.nio.file.Path;
import java.util.function.Predicate;

/**
 * API of the Logic component
 */
public interface Logic {

    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;
    ///

    public void setMainWindow(MainWindow mainWindow);

    /**
     * Sets the summary panel of logic
     *
     * @param summaryPanel the summary panel
     */
    public void setSummaryPanel(SummaryPanel summaryPanel);

    /**
     * Returns the AddressBook.
     *
     * @see seedu.address.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns an unmodifiable view of the filtered list of persons
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */

    ///

    /**
     * Returns the StaffAddressBook.
     *
     * @see seedu.address.model.Model#getStaffAddressBook()
     */
    ReadOnlyAddressBookGeneric<Staff> getStaffAddressBook();

    /**
     * Returns an unmodifiable view of the filtered list of staffs
     */
    ObservableList<Staff> getFilteredStaffList();

    /**
     * Returns the user prefs' staff address book file path.
     */
    Path getStaffAddressBookFilePath();

    ///

    /**
     * Returns the StudentAddressBook.
     *
     * @see seedu.address.model.Model#getStudentAddressBook()
     */
    ReadOnlyAddressBookGeneric<Student> getStudentAddressBook();

    /**
     * Returns an unmodifiable view of the filtered list of students
     */
    ObservableList<Student> getFilteredStudentList();

    /**
     * Returns the user prefs' student address book file path.
     */
    Path getStudentAddressBookFilePath();
    ///

    /**
     * Returns the FinanceAddressBook.
     *
     * @see seedu.address.model.Model#getFinanceAddressBook()
     */
    ReadOnlyAddressBookGeneric<Finance> getFinanceAddressBook();

    /**
     * Returns an unmodifiable view of the filtered list of finances
     */
    ObservableList<Finance> getFilteredFinanceList();

    /**
     * Returns the user prefs' staff address book file path.
     */
    Path getFinanceAddressBookFilePath();
    ///

    /**
     * Returns the CourseAddressBook.
     *
     * @see seedu.address.model.Model#getCourseAddressBook()
     */
    ReadOnlyAddressBookGeneric<Course> getCourseAddressBook();

    /**
     * Returns an unmodifiable view of the filtered list of courses
     */
    ObservableList<Course> getFilteredCourseList();

    /**
     * Returns the user prefs' course address book file path.
     */
    Path getCourseAddressBookFilePath();
    //TODO

    /**
     * Returns the AssignmentAddressBook.
     *
     * @see seedu.address.model.Model#getCourseAddressBook()
     */
    ReadOnlyAddressBookGeneric<Assignment> getAssignmentAddressBook();

    /**
     * Returns an unmodifiable view of the filtered list of courses
     */
    ObservableList<Assignment> getFilteredAssignmentList();

    /**
     * Returns the user prefs' course address book file path.
     */
    Path getAssignmentAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    // ========================= Getters for Details View ======================
    ObservableMap<String, Object> getFilteredStudentDetailsMap();

    ObservableMap<String, Object> getFilteredCourseDetailsMap();

    ObservableMap<String, Object> getFilteredStaffDetailsMap();

    ObservableMap<String, Object> getFilteredFinanceDetailsMap();

    ObservableMap<String, Object> getFilteredAssignmentDetailsMap();

    // ========================== Getters for Predicates =========================

    public Predicate<Student> getDataStudentPredicate();

    public Predicate<Staff> getDataStaffPredicate();

    public Predicate<Finance> getDataFinancePredicate();

    public Predicate<Course> getDataCoursePredicate();

    public Predicate<Assignment> getDataAssignmentPredicate();

    public Predicate<Student> getExtraStudentPredicate();

    public Predicate<Staff> getExtraStaffPredicate();

    public Predicate<Finance> getExtraFinancePredicate();

    public Predicate<Course> getExtraStudentCoursePredicate();

    public Predicate<Course> getExtraStaffCoursePredicate();

    public Predicate<Assignment> getExtraAssignmentPredicate();
}
