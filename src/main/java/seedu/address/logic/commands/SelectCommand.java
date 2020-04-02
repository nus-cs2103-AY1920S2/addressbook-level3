package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.modelAssignment.AssignmentIDContainsKeywordsPredicate;
import seedu.address.model.modelCourse.CourseIDContainsKeywordsPredicate;
import seedu.address.model.modelFinance.FinanceIDContainsKeywordsPredicate;
import seedu.address.model.modelStudent.StudentIDContainsKeywordsPredicate;
import seedu.address.model.modelTeacher.TeacherIDContainsKeywordsPredicate;

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

  private StudentIDContainsKeywordsPredicate studentIDContainsKeywordsPredicate = null;
  private TeacherIDContainsKeywordsPredicate teacherIDContainsKeywordsPredicate = null;
  private FinanceIDContainsKeywordsPredicate financeIDContainsKeywordsPredicate = null;
  private CourseIDContainsKeywordsPredicate courseIDContainsKeywordsPredicate = null;
  private AssignmentIDContainsKeywordsPredicate assignmentIDContainsKeywordsPredicate = null;

  public SelectCommand(StudentIDContainsKeywordsPredicate studentIDContainsKeywordsPredicate) {
    this.studentIDContainsKeywordsPredicate = studentIDContainsKeywordsPredicate;
  }

  public SelectCommand(TeacherIDContainsKeywordsPredicate teacherIDContainsKeywordsPredicate) {
    this.teacherIDContainsKeywordsPredicate = teacherIDContainsKeywordsPredicate;
  }

  public SelectCommand(FinanceIDContainsKeywordsPredicate financeIDContainsKeywordsPredicate) {
    this.financeIDContainsKeywordsPredicate = financeIDContainsKeywordsPredicate;
  }

  public SelectCommand(CourseIDContainsKeywordsPredicate courseIDContainsKeywordsPredicateredicate) {
    this.courseIDContainsKeywordsPredicate = courseIDContainsKeywordsPredicate;
  }

  public SelectCommand(AssignmentIDContainsKeywordsPredicate assignmentIDContainsKeywordsPredicate) {
    this.assignmentIDContainsKeywordsPredicate = assignmentIDContainsKeywordsPredicate;
  }

  @Override
  public CommandResult execute(Model model) throws CommandException {
    requireNonNull(model);
    if (studentIDContainsKeywordsPredicate != null){
      model.updateExtraFilteredStudentList(studentIDContainsKeywordsPredicate);
      return new CommandResult(
          String.format(Messages.MESSAGE_STUDENTS_SELECTED_OVERVIEW,
              model.getFilteredStudentList().size()));

    } else if (teacherIDContainsKeywordsPredicate != null){
      model.updateExtraFilteredTeacherList(teacherIDContainsKeywordsPredicate);
      return new CommandResult(
          String.format(Messages.MESSAGE_TEACHERS_SELECTED_OVERVIEW,
              model.getFilteredTeacherList().size()));

    } else if (financeIDContainsKeywordsPredicate != null){
      model.updateExtraFilteredFinanceList(financeIDContainsKeywordsPredicate);
      return new CommandResult(
          String.format(Messages.MESSAGE_FINANCES_SELECTED_OVERVIEW,
              model.getFilteredFinanceList().size()));

    } else if (courseIDContainsKeywordsPredicate != null){
      model.updateExtraFilteredCourseList(courseIDContainsKeywordsPredicate);
      return new CommandResult(
          String.format(Messages.MESSAGE_COURSES_SELECTED_OVERVIEW,
              model.getFilteredCourseList().size()));

    } else if (assignmentIDContainsKeywordsPredicate != null){
      model.updateExtraFilteredAssignmentList(assignmentIDContainsKeywordsPredicate);
      return new CommandResult(
          String.format(Messages.MESSAGE_ASSIGNMENT_SELECTED_OVERVIEW,
              model.getFilteredAssignmentList().size()));

    } else {
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
