package seedu.address.logic.commands.commandAdd;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSEID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FINANCETYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEACHERID;

import java.util.Set;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelFinance.Finance;
import seedu.address.model.modelStudent.Student;
import seedu.address.model.modelTeacher.Teacher;
import seedu.address.model.person.Amount;
import seedu.address.model.person.FinanceType;
import seedu.address.model.person.ID;
import seedu.address.model.person.Name;

/**
 * Adds a finance to the address book.
 */
public class AddFinanceCommand extends AddCommand {
  //If finance type is misc, then Name, Date, Amount must be provided
  //Else if finance type is CourseStudent, then Date, CourseID, StudentID must be provided
  //Else if finance type is CourseTeacher, then Date, CourseID, TeacherID must be provided

  public static final String COMMAND_WORD = "add-finance";

  public static final String MESSAGE_FINANCETYPE = "Finance type ft/ was missing.\n";
  public static final String MESSAGE_DATE = "Date " + PREFIX_DATE + " was missing.\n";
  public static final String MESSAGE_NAME = "Name " + PREFIX_NAME + " was missing.\n";
  public static final String MESSAGE_AMOUNT = "Amount " + PREFIX_AMOUNT + " was missing.\n";
  public static final String MESSAGE_COURSEID = "CourseID " + PREFIX_COURSEID + " was missing.\n";
  public static final String MESSAGE_STUDENTID = "StudentID " + PREFIX_STUDENTID + " was missing.\n";
  public static final String MESSAGE_TEACHERID = "TeacherID " + PREFIX_TEACHERID + " was missing.\n";

  public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a finance to the address book. "
      + "\nType 1: Adding miscellaneous transactions(Specify ft/ as m "
      + "Parameters: "
      + PREFIX_FINANCETYPE + "FINANCETYPE "
      + PREFIX_DATE + "DATE "
      + PREFIX_NAME + "NAME "
      + PREFIX_AMOUNT + "AMOUNT "
      + "[" + PREFIX_TAG + "TAG]...\n"
      + "Example: " + COMMAND_WORD + " "
      + PREFIX_FINANCETYPE + "m "
      + PREFIX_DATE + "2020-12-09 "
      + PREFIX_NAME + "Paid NTU "
      + PREFIX_AMOUNT + "1200 "
      + PREFIX_TAG + "Partnership "
      + PREFIX_TAG + "Monthly "
      + "\nType 2: A student paying for a course(Specify ft/ as cs "
      + "Parameters: "
      + PREFIX_FINANCETYPE + "FINANCETYPE "
      + PREFIX_DATE + "DATE "
      + PREFIX_COURSEID + "COURSEID "
      + PREFIX_STUDENTID + "STUDENTID "
      + "[" + PREFIX_TAG + "TAG]...\n"
      + "Example: " + COMMAND_WORD + " "
      + PREFIX_FINANCETYPE + "cs "
      + PREFIX_DATE + "2020-12-09 "
      + PREFIX_COURSEID + "829 "
      + PREFIX_STUDENTID + "33 "
      + PREFIX_TAG + "Late "
      + "\nType 3: A teacher is paid for teaching a course(Specify ft/ as ct "
      + "Parameters: "
      + PREFIX_FINANCETYPE + "FINANCETYPE "
      + PREFIX_DATE + "DATE "
      + PREFIX_COURSEID + "COURSEID "
      + PREFIX_TEACHERID + "TEACHERID "
      + "[" + PREFIX_TAG + "TAG]...\n"
      + "Example: " + COMMAND_WORD + " "
      + PREFIX_FINANCETYPE + "ct "
      + PREFIX_DATE + "2020-12-09 "
      + PREFIX_COURSEID + "829 "
      + PREFIX_TEACHERID + "21 "
      + PREFIX_TAG + "Early ";


  public static final String MESSAGE_SUCCESS = "New finance added: %1$s";
  public static final String MESSAGE_DUPLICATE_FINANCE = "This finance already exists in the address book";
  public static final String MESSAGE_INVALID_COURSE_ID = "There is no such course that with ID";
  public static final String MESSAGE_INVALID_STUDENT_ID = "There is no such student that with ID";
  public static final String MESSAGE_INVALID_TEACHER_ID = "There is no such teacher that with ID";
  public static final String MESSAGE_INVALID_COURSESTUDENT = "The specified course does not contain a student with that ID";
  public static final String MESSAGE_INVALID_COURSETEACHER = "The specified course does not contain a teacher with that ID";

  private Finance toAdd;

  /**
   * Creates an AddCommand to add the specified {@code Finance}
   */
  public AddFinanceCommand(Finance finance) {
    requireNonNull(finance);
    toAdd = finance;
  }

  protected void generateOppositeCommand() {

  }

  @Override
  public CommandResult executeUndoableCommand(Model model) throws CommandException {
    requireNonNull(model);

    FinanceType financeType = toAdd.getFinanceType();
    if (financeType.toString().equals("cs")) {
      ID courseid = toAdd.getCourseID();
      ID studentid = toAdd.getStudentID();
      Amount amount = new Amount("999");
      String courseName = "";
      String studentName = "";
      Set<ID> assignedStudentsID = null;
      boolean foundCourse = false;
      boolean foundStudent = false;
      boolean foundCourseStudent = false;

      for (Course course : model.getFilteredCourseList()){
        if (course.getId().toString().equals(courseid.toString())) {
          courseName = course.getName().toString();
          amount = course.getAmount();
          assignedStudentsID = course.getAssignedStudentsID();
          foundCourse = true;
          break;
        }
      }

      for (Student student : model.getFilteredStudentList()){
        if (student.getID().toString().equals(studentid.toString())) {
          studentName = student.getName().toString();
          foundStudent = true;
          break;
        }
      }

      if (!foundCourse) {
        throw new CommandException(MESSAGE_INVALID_COURSE_ID);
      } else if (!foundStudent) {
        throw new CommandException(MESSAGE_INVALID_STUDENT_ID);
      }

      for (ID assignedStudentID : assignedStudentsID) {
        if (assignedStudentID.equals(studentid)) {
          foundCourseStudent = true;
          break;
        }
      }

      if (!foundCourseStudent) {
        throw new CommandException(MESSAGE_INVALID_COURSESTUDENT);
      }

      Name name = new Name(String.format("Student %s %s has paid for Course %s %s", studentName, studentid.toString()
      , courseName, courseid.toString()));

      toAdd = new Finance(name, toAdd.getFinanceType(), toAdd.getDate(), amount,
          toAdd.getCourseID(), toAdd.getStudentID(), toAdd.getTeacherID(), toAdd.getTags());
    } else if (financeType.toString().equals("ct")) {
      ID courseid = toAdd.getCourseID();
      ID teacherid = toAdd.getTeacherID();
      Amount amount = new Amount("999");
      String courseName = "";
      String teacherName = "";
      String assignedTeacher = "";
      boolean foundCourse = false;
      boolean foundTeacher = false;

      for (Course course : model.getFilteredCourseList()){
        if (course.getId().toString().equals(courseid.toString())) {
          courseName = course.getName().toString();
          amount = new Amount("-" + course.getAmount().toString());
          assignedTeacher = course.getAssignedTeacherID().toString();
          foundCourse = true;
          break;
        }
      }

      for (Teacher teacher : model.getFilteredTeacherList()){
        if (teacher.getID().toString().equals(teacherid.toString())) {
          teacherName = teacher.getName().toString();
          foundTeacher = true;
          break;
        }
      }

      if (!foundCourse) {
        throw new CommandException(MESSAGE_INVALID_COURSE_ID);
      } else if (!foundTeacher) {
        throw new CommandException(MESSAGE_INVALID_TEACHER_ID);
      }

      if (!assignedTeacher.equals(teacherid.toString())) {
        throw new CommandException(MESSAGE_INVALID_COURSETEACHER);
      }

      Name name = new Name(String.format("Teacher %s %s has been paid for teaching Course %s %s", teacherName, teacherid.toString()
          , courseName, courseid.toString()));

      toAdd = new Finance(name, toAdd.getFinanceType(), toAdd.getDate(), amount,
          toAdd.getCourseID(), toAdd.getStudentID(), toAdd.getTeacherID(), toAdd.getTags());
    }

    if (model.hasFinance(toAdd)) {
      throw new CommandException(MESSAGE_DUPLICATE_FINANCE);
    }

    model.addFinance(toAdd);
    return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
  }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
        || (other instanceof AddFinanceCommand // instanceof handles nulls
        && toAdd.equals(((AddFinanceCommand) other).toAdd));
  }
}
