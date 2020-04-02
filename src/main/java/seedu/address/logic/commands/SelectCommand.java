package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.modelAssignment.AssignmentIDContainsKeywordsPredicate;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelCourse.CourseIDContainsKeywordsPredicate;
import seedu.address.model.modelFinance.FinanceIDContainsKeywordsPredicate;
import seedu.address.model.modelStudent.Student;
import seedu.address.model.modelStudent.StudentIDContainsKeywordsPredicate;
import seedu.address.model.modelStaff.Staff;
import seedu.address.model.modelStaff.StaffIDContainsKeywordsPredicate;
import seedu.address.model.person.ID;

/**
 * Finds and lists all courses in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SelectCommand extends Command {

  public static final String COMMAND_WORD = "select";

  public static final String MESSAGE_USAGE =
      COMMAND_WORD + ": Finds all courses whose names contain any of "
          + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
          + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
          + "Example: " + COMMAND_WORD + " alice bob charlie";
  public static final String MESSAGE_INVALID = "Invalid select arguments";
  public static final String MESSAGE_INVALID_STUDENTID = "There is no such student with that ID";
  public static final String MESSAGE_INVALID_STAFFID = "There is no such staff with that ID";
  public static final String MESSAGE_INVALID_COURSEID = "There is no such course with that ID";

  private final ID id;
  private final String type;

  private StudentIDContainsKeywordsPredicate studentIDContainsKeywordsPredicate = null;
  private StaffIDContainsKeywordsPredicate staffIDContainsKeywordsPredicate = null;
  private FinanceIDContainsKeywordsPredicate financeIDContainsKeywordsPredicate = null;
  private CourseIDContainsKeywordsPredicate courseIDContainsKeywordsPredicate = null;
  private AssignmentIDContainsKeywordsPredicate assignmentIDContainsKeywordsPredicate = null;

  public SelectCommand(String type, ID id) {
    this.type = type;
    this.id = id;
  }

  @Override
  public CommandResult execute(Model model) throws CommandException {
    requireNonNull(model);

    switch (type) {
      case "STUDENT":
        if (!model.hasStudent(id)){
          throw new CommandException(MESSAGE_INVALID_STUDENTID);
        }
        Student selectedStudent = model.getStudent(id);
        List<String> coursesIDStudent = selectedStudent.getAssignedCoursesIDString();
        model.updateExtraFilteredStudentCourseList(new CourseIDContainsKeywordsPredicate(coursesIDStudent));
        return new CommandResult(
            String.format(Messages.MESSAGE_COURSES_SELECTED_OVERVIEW,
                model.getFilteredCourseList().size()));

      case "STAFF":
        if (!model.hasStaff(id)){
          throw new CommandException(MESSAGE_INVALID_STAFFID);
        }
        Staff selectedStaff = model.getStaff(id);
        List<String> coursesIDStaff = selectedStaff.getAssignedCoursesIDString();
        model.updateExtraFilteredStaffCourseList(new CourseIDContainsKeywordsPredicate(coursesIDStaff));
        return new CommandResult(
            String.format(Messages.MESSAGE_COURSES_SELECTED_OVERVIEW,
                model.getFilteredCourseList().size()));

      case "FINANCE":
        throw new CommandException(MESSAGE_INVALID);

      case "COURSE":
        if (!model.hasCourse(id)){
          throw new CommandException(MESSAGE_INVALID_COURSEID);
        }
        Course selectedCourse = model.getCourse(id);
        List<String> studentsID = selectedCourse.getAssignedStudentsIDString();
        model.updateExtraFilteredStudentList(new StudentIDContainsKeywordsPredicate(studentsID));
        return new CommandResult(
            String.format(Messages.MESSAGE_STUDENTS_SELECTED_OVERVIEW,
                model.getFilteredStudentList().size()));

      case "ASSIGNMENT":
        throw new CommandException(MESSAGE_INVALID);

      default:
        throw new CommandException(MESSAGE_INVALID);
    }
  }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
        || (other instanceof SelectCommand // instanceof handles nulls
        && studentIDContainsKeywordsPredicate.equals(((SelectCommand) other).studentIDContainsKeywordsPredicate)
        && staffIDContainsKeywordsPredicate.equals(((SelectCommand) other).staffIDContainsKeywordsPredicate)
        && financeIDContainsKeywordsPredicate.equals(((SelectCommand) other).financeIDContainsKeywordsPredicate)
        && courseIDContainsKeywordsPredicate.equals(((SelectCommand) other).courseIDContainsKeywordsPredicate)
        && assignmentIDContainsKeywordsPredicate.equals(((SelectCommand) other).assignmentIDContainsKeywordsPredicate)); // state check
  }
}
