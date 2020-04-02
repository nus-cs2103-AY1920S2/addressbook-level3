package seedu.address.logic;

import java.nio.file.Path;
import java.util.function.Predicate;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.modelAssignment.Assignment;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelFinance.Finance;
import seedu.address.model.modelGeneric.ReadOnlyAddressBookGeneric;
import seedu.address.model.modelStaff.Staff;
import seedu.address.model.modelStudent.Student;
import seedu.address.model.person.Person;
import seedu.address.ui.SummaryPanel;

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

  /**
   * Sets the summary panel of logic
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

  /**
   * Updates the filter of the filtered staff list to filter by the given {@code predicate}.
   *
   * @throws NullPointerException if {@code predicate} is null.
   */
  void updateObservedDataFilteredStaffList(Predicate<Staff> predicate);
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

  /**
   * Updates the filter of the filtered student list to filter by the given {@code predicate}.
   *
   * @throws NullPointerException if {@code predicate} is null.
   */
  void updateObservedDataFilteredStudentList(Predicate<Student> predicate);
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

  /**
   * Updates the filter of the filtered finance list to filter by the given {@code predicate}.
   *
   * @throws NullPointerException if {@code predicate} is null.
   */
  void updateObservedDataFilteredFinanceList(Predicate<Finance> predicate);
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

  /**
   * Updates the filter of the filtered course list to filter by the given {@code predicate}.
   *
   * @throws NullPointerException if {@code predicate} is null.
   */
  void updateObservedDataFilteredCourseList(Predicate<Course> predicate);
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
   * Updates the filter of the filtered assignment list to filter by the given {@code predicate}.
   *
   * @throws NullPointerException if {@code predicate} is null.
   */
  void updateObservedDataFilteredAssignmentList(Predicate<Assignment> predicate);

  /**
   * Returns the user prefs' GUI settings.
   */
  GuiSettings getGuiSettings();

  /**
   * Set the user prefs' GUI settings.
   */
  void setGuiSettings(GuiSettings guiSettings);

  // ========================== Getters for Predicates =========================

  public Predicate<Student> getDataStudentPredicate();

  public Predicate<Staff> getDataStaffPredicate();

  public Predicate<Finance> getDataFinancePredicate();

  public Predicate<Course> getDataCoursePredicate();

  public Predicate<Assignment> getDataAssignmentPredicate();

  public Predicate<Student> getExtraStudentPredicate();

  public Predicate<Staff> getExtraStaffPredicate();

  public Predicate<Finance> getExtraFinancePredicate();

  public Predicate<Course> getExtraCoursePredicate();

  public Predicate<Assignment> getExtraAssignmentPredicate();
}
