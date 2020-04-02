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
import seedu.address.model.modelTeacher.Teacher;
import seedu.address.model.modelTeacher.TeacherIDContainsKeywordsPredicate;
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

  private final ID id;
  private final String type;

  private StudentIDContainsKeywordsPredicate studentIDContainsKeywordsPredicate = null;
  private TeacherIDContainsKeywordsPredicate teacherIDContainsKeywordsPredicate = null;
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
    //String[] keywords;
    //new TeacherIDContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    switch (type) {
      case "STUDENT":
        Student selectedStudent = model.getStudent(id);
        List<String> coursesIDStudent = selectedStudent.getAssignedCoursesIDString();
        model.updateExtraFilteredCourseList(new CourseIDContainsKeywordsPredicate(coursesIDStudent));
        return new CommandResult(
            String.format(Messages.MESSAGE_COURSES_SELECTED_OVERVIEW,
                model.getFilteredCourseList().size()));

      case "TEACHER":
        Teacher selectedTeacher = model.getTeacher(id);
        List<String> coursesIDTeacher = selectedTeacher.getAssignedCoursesIDString();
        model.updateExtraFilteredCourseList(new CourseIDContainsKeywordsPredicate(coursesIDTeacher));
        return new CommandResult(
            String.format(Messages.MESSAGE_COURSES_SELECTED_OVERVIEW,
                model.getFilteredCourseList().size()));

      case "FINANCE":
        throw new CommandException(MESSAGE_INVALID);

      case "COURSE":
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
        && teacherIDContainsKeywordsPredicate.equals(((SelectCommand) other).teacherIDContainsKeywordsPredicate)
        && financeIDContainsKeywordsPredicate.equals(((SelectCommand) other).financeIDContainsKeywordsPredicate)
        && courseIDContainsKeywordsPredicate.equals(((SelectCommand) other).courseIDContainsKeywordsPredicate)
        && assignmentIDContainsKeywordsPredicate.equals(((SelectCommand) other).assignmentIDContainsKeywordsPredicate)); // state check
  }
}
