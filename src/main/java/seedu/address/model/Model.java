package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelAssignment.Assignment;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelFinance.Finance;
import seedu.address.model.modelGeneric.ModelObject;
import seedu.address.model.modelGeneric.ReadOnlyAddressBookGeneric;
import seedu.address.model.modelStaff.Staff;
import seedu.address.model.modelStudent.Student;
import seedu.address.model.modelProgress.Progress;
import seedu.address.model.person.ID;
import seedu.address.model.person.Person;
import seedu.address.ui.MainWindow;

/**
 * The API of the Model component.
 */
public interface Model {

  /**
   * {@code Predicate} that always evaluate to true
   */
  Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
  Predicate<Staff> PREDICATE_SHOW_ALL_STAFFS = unused -> true;
  Predicate<Student> PREDICATE_SHOW_ALL_STUDENTS = unused -> true;
  Predicate<Finance> PREDICATE_SHOW_ALL_FINANCES = unused -> true;
  Predicate<Course> PREDICATE_SHOW_ALL_COURSES = unused -> true;
  Predicate<Assignment> PREDICATE_SHOW_ALL_ASSIGNMENTS = unused -> true;
  Predicate<Progress> PREDICATE_SHOW_ALL_PROGRESSES = unused -> true;


  Predicate<Person> PREDICATE_HIDE_ALL_PERSONS = unused -> false;
    Predicate<Staff> PREDICATE_HIDE_ALL_STAFFS = unused -> false;
    Predicate<Student> PREDICATE_HIDE_ALL_STUDENTS = unused -> false;
    Predicate<Finance> PREDICATE_HIDE_ALL_FINANCES = unused -> false;
    Predicate<Course> PREDICATE_HIDE_ALL_COURSES = unused -> false;
    Predicate<Assignment> PREDICATE_HIDE_ALL_ASSIGNMENTS = unused -> false;
    Predicate<Progress> PREDICATE_HIDE_ALL_PROGRESSES = unused -> false;


  String COURSE_ENTITY_NAME = "course";

    public MainWindow getMainWindow();

    public void setMainWindow(MainWindow mainWindow);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

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

    boolean has(ModelObject obj) throws CommandException;

    void delete(ModelObject obj) throws CommandException;

    void add(ModelObject obj) throws CommandException;

    void addAtIndex(ModelObject obj, Integer index) throws CommandException;

    void set(ModelObject target, ModelObject editedTarget) throws CommandException;

    boolean hasStudent(ID studentID);

    Student getStudent(ID studentID);

    boolean hasCourse(ID courseID);

    Course getCourse(ID courseID);

    boolean hasAssignment(ID assignmentID);

    Assignment getAssignment(ID assignmentID);

    boolean hasStaff(ID staffID);

    Staff getStaff(ID staffID);

    boolean hasProgress(ID assignmentID, ID studentID) throws CommandException;

    Progress getProgress(ID assignmentID, ID studentID) throws CommandException;

  /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    ///

    /**
     * Returns the user prefs' address book file path.
     */
    Path getStaffAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setStaffAddressBookFilePath(Path staffAddressBookFilePath);

    /**
     * Returns the staffAddressBook
     */
    ReadOnlyAddressBookGeneric<Staff> getStaffAddressBook();

    /**
     * Replaces staff address book data with the data in {@code teacerAddressBook}.
     */
    void setStaffAddressBook(ReadOnlyAddressBookGeneric<Staff> staffAddressBook);

    /**
     * Returns an unmodifiable view of the filtered staff list
     */
    ObservableList<Staff> getFilteredStaffList();


    /**
     * Updates the filter of the filtered staff list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredStaffList(Predicate<Staff> predicate);

    /**
     * Updates the filter of the filtered staff list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateObservedDataFilteredStaffList(Predicate<Staff> predicate);

    /**
     * Updates the filter of the filtered staff list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateExtraFilteredStaffList(Predicate<Staff> predicate);

    /**
     * Updates the filter of the filtered staff list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateObservedExtraFilteredStaffList(Predicate<Staff> predicate);

    ///

    /**
     * Returns the user prefs' address book file path.
     */
    Path getStudentAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setStudentAddressBookFilePath(Path studentAddressBookFilePath);

    /**
     * Returns the studentAddressBook
     */
    ReadOnlyAddressBookGeneric<Student> getStudentAddressBook();

    /**
     * Replaces student address book data with the data in {@code staffAddressBook}.
     */
    void setStudentAddressBook(ReadOnlyAddressBookGeneric<Student> studentAddressBook);

    /**
     * Returns an unmodifiable view of the filtered student list
     */
    ObservableList<Student> getFilteredStudentList();

    /**
     * Updates the filter of the filtered student list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredStudentList(Predicate<Student> predicate);

    /**
     * Updates the filter of the filtered student list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateObservedDataFilteredStudentList(Predicate<Student> predicate);

    /**
     * Updates the filter of the filtered student list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateExtraFilteredStudentList(Predicate<Student> predicate);

    /**
     * Updates the filter of the filtered student list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateObservedExtraFilteredStudentList(Predicate<Student> predicate);

    ///

    /**
     * Returns the user prefs' address book file path.
     */
    Path getCourseAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setCourseAddressBookFilePath(Path courseAddressBookFilePath);

    /**
     * Returns the courseAddressBook
     */
    ReadOnlyAddressBookGeneric<Course> getCourseAddressBook();

    /**
     * Replaces course address book data with the data in {@code teacerAddressBook}.
     */
    void setCourseAddressBook(ReadOnlyAddressBookGeneric<Course> courseAddressBook);

    /**
     * Returns an unmodifiable view of the filtered course list
     */
    ObservableList<Course> getFilteredCourseList();

    /**
     * Updates the filter of the filtered course list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredCourseList(Predicate<Course> predicate);

    /**
     * Updates the filter of the filtered course list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateObservedDataFilteredCourseList(Predicate<Course> predicate);

    /**
     * Updates the filter of the filtered course list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateExtraFilteredStudentCourseList(Predicate<Course> predicate);

  /**
   * Updates the filter of the filtered course list to filter by the given {@code predicate}.
   *
   * @throws NullPointerException if {@code predicate} is null.
   */
  void updateExtraFilteredStaffCourseList(Predicate<Course> predicate);

    /**
     * Updates the filter of the filtered course list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateObservedExtraFilteredCourseList(Predicate<Course> predicate);

    ///

    /**
     * Returns the user prefs' address book file path.
     */
    Path getFinanceAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setFinanceAddressBookFilePath(Path financeAddressBookFilePath);

    /**
     * Returns the financeAddressBook
     */
    ReadOnlyAddressBookGeneric<Finance> getFinanceAddressBook();

    /**
     * Replaces finance address book data with the data in {@code teacerAddressBook}.
     */
    void setFinanceAddressBook(ReadOnlyAddressBookGeneric<Finance> financeAddressBook);

    /**
     * Returns an unmodifiable view of the filtered finance list
     */
    ObservableList<Finance> getFilteredFinanceList();

    /**
     * Updates the filter of the filtered finance list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredFinanceList(Predicate<Finance> predicate);

    /**
     * Updates the filter of the filtered finance list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateObservedDataFilteredFinanceList(Predicate<Finance> predicate);

    /**
     * Updates the filter of the filtered finance list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateExtraFilteredFinanceList(Predicate<Finance> predicate);

    /**
     * Updates the filter of the filtered finance list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateObservedExtraFilteredFinanceList(Predicate<Finance> predicate);

    ///

    /**
     * Returns the user prefs' assignment address book file path.
     */
    Path getAssignmentAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAssignmentAddressBookFilePath(Path assignmentAddressBookFilePath);

    /**
     * Returns the assignmentAddressBook
     */
    ReadOnlyAddressBookGeneric<Assignment> getAssignmentAddressBook();

    /**
     * Replaces assignment address book data with the data in {@code teacerAddressBook}.
     */
    void setAssignmentAddressBook(ReadOnlyAddressBookGeneric<Assignment> assignmentAddressBook);

    /**
     * Returns an unmodifiable view of the filtered finance list
     */
    ObservableList<Assignment> getFilteredAssignmentList();

    /**
     * Updates the filter of the filtered assignment list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredAssignmentList(Predicate<Assignment> predicate);

    /**
     * Updates the filter of the filtered assignment list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateObservedDataFilteredAssignmentList(Predicate<Assignment> predicate);

    /**
     * Updates the filter of the filtered assignment list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateExtraFilteredAssignmentList(Predicate<Assignment> predicate);

    /**
     * Updates the filter of the filtered assignment list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateObservedExtraFilteredAssignmentList(Predicate<Assignment> predicate);

    // ====================================================================== //
    // There is no CRUD operations for Progress objects for now
    /**
     * Returns the user prefs' assignment address book file path.
     */
    Path getProgressAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setProgressAddressBookFilePath(Path progressAddressBookFilePath);

    /**
     * Returns the progressAddressBook
     */
    ReadOnlyAddressBookGeneric<Progress> getProgressAddressBook();

    /**
     * Replaces progress address book data with the data in {@code teacerAddressBook}.
     */
    void setProgressAddressBook(ReadOnlyAddressBookGeneric<Progress> progressAddressBook);

    /**
     * Returns an unmodifiable view of the filtered finance list
     */
    ObservableList<Progress> getFilteredProgressList();

    /**
     * Updates the filter of the filtered assignment list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredProgressList(Predicate<Progress> predicate);

    // ======================== FOR ASSIGN COMMANDS ============================== //

    void assignStudentToCourse(ID studentID, ID courseID) throws CommandException;

    void assignAssignmentToCourse(ID assignmentID, ID courseID) throws CommandException;

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

    void assignTeacherToCourse(ID teacherID, ID courseID) throws CommandException;

    // ======================== FOR UNASSIGN COMMANDS ============================== //

    void unassignAssignmentFromCourse(ID assignmentID, ID courseID) throws CommandException;

    void unassignStudentFromCourse(ID studentID, ID courseID) throws CommandException;

}
