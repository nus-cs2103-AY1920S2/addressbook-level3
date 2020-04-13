package seedu.address.logic.commands.commandAdd;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSEID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FINANCETYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEACHERID;

import seedu.address.commons.util.Constants;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandDelete.DeleteFinanceCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.manager.EdgeManager;
import seedu.address.model.Model;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelFinance.Finance;
import seedu.address.model.modelObjectTags.Amount;
import seedu.address.model.modelObjectTags.FinanceType;
import seedu.address.model.modelObjectTags.ID;
import seedu.address.model.modelObjectTags.Name;
import seedu.address.model.modelStaff.Staff;
import seedu.address.model.modelStudent.Student;

/**
 * Adds a finance to the address book.
 */
public class AddFinanceCommand extends AddCommand {
  //If finance type is misc, then Name, Date, Amount must be provided
  //Else if finance type is CourseStudent, then Date, CourseID, StudentID must be provided
  //Else if finance type is CourseStaff, then Date, CourseID, StaffID must be provided

  public static final String COMMAND_WORD = "add-finance";

  public static final String MESSAGE_FINANCETYPE = "Finance type ft/ was missing.\n";
  public static final String MESSAGE_DATE = "Date " + PREFIX_DATE + " was missing.\n";
  public static final String MESSAGE_NAME = "Name " + PREFIX_NAME + " was missing.\n";
  public static final String MESSAGE_AMOUNT = "Amount " + PREFIX_AMOUNT + " was missing.\n";
  public static final String MESSAGE_COURSEID = "CourseID " + PREFIX_COURSEID + " was missing.\n";
  public static final String MESSAGE_STUDENTID = "StudentID " + PREFIX_STUDENTID + " was missing.\n";
  public static final String MESSAGE_TEACHERID = "StaffID " + PREFIX_TEACHERID + " was missing.\n";

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
      + PREFIX_COURSEID + "2 "
      + PREFIX_STUDENTID + "22 "
      + PREFIX_TAG + "Late "
      + "\nType 3: A staff is paid for teaching a course(Specify ft/ as ct "
      + "Parameters: "
      + PREFIX_FINANCETYPE + "FINANCETYPE "
      + PREFIX_DATE + "DATE "
      + PREFIX_COURSEID + "COURSEID "
      + PREFIX_TEACHERID + "TEACHERID "
      + "[" + PREFIX_TAG + "TAG]...\n"
      + "Example: " + COMMAND_WORD + " "
      + PREFIX_FINANCETYPE + "ct "
      + PREFIX_DATE + "2020-12-09 "
      + PREFIX_COURSEID + "3 "
      + PREFIX_TEACHERID + "31 "
      + PREFIX_TAG + "Early ";


  public static final String MESSAGE_SUCCESS = "New finance added: %1$s";
  public static final String MESSAGE_DUPLICATE_FINANCE = "This finance already exists in the address book";
  public static final String MESSAGE_NOTFOUND_COURSE_ID = "Cannot find such course that with this ID";
  public static final String MESSAGE_NOTFOUND_STUDENT_ID = "Cannot find such student that with this ID";
  public static final String MESSAGE_NOTFOUND_TEACHER_ID = "Cannot find such staff that with this ID";
  public static final String MESSAGE_NOTFOUND_COURSE_STUDENT = "The specified course does not contain a student with that ID";
  public static final String MESSAGE_NOTFOUND_COURSE_TEACHER = "The specified course does not contain a staff with that ID";

  private Finance toAdd;

  private Integer index;

  /**
   * Creates an AddCommand to add the specified {@code Finance}
   */
  public AddFinanceCommand(Finance finance) {
    requireNonNull(finance);
    toAdd = finance;
  }

  public AddFinanceCommand(Finance finance, Integer index) {
    requireAllNonNull(finance, index);
    this.toAdd = finance;
    this.index = index;
  }

  protected void generateOppositeCommand() {
    oppositeCommand = new DeleteFinanceCommand(this.toAdd);
  }

  @Override
  protected void preprocessUndoableCommand(Model model) throws CommandException {
    requireNonNull(model);

    FinanceType financeType = toAdd.getFinanceType();
    if (financeType.toString().equals("cs")) {
      ID courseID = toAdd.getCourseID();
      ID studentID = toAdd.getStudentID();

      if (!model.has(courseID, Constants.ENTITY_TYPE.COURSE)) {
        throw new CommandException(MESSAGE_NOTFOUND_COURSE_ID);
      }
      Course course = (Course) model.get(courseID, Constants.ENTITY_TYPE.COURSE);
      if (!model.has(studentID, Constants.ENTITY_TYPE.STUDENT)) {
        throw new CommandException(MESSAGE_NOTFOUND_STUDENT_ID);
      }
      Student student = (Student) model.get(studentID, Constants.ENTITY_TYPE.STUDENT);
      if (!course.getAssignedStudentsID().contains(studentID)) {
        throw new CommandException(MESSAGE_NOTFOUND_COURSE_STUDENT);
      }

      Name name = new Name(String.format("Student %s %s has paid for Course %s %s",
          student.getName().toString(), studentID.toString(),
          course.getName().toString(), courseID.toString()));
      Amount amount = course.getAmount();

      try {
        toAdd = new Finance(name, toAdd.getFinanceType(), toAdd.getDate(), amount,
            toAdd.getCourseID(), toAdd.getStudentID(), toAdd.getStaffID(), toAdd.getTags());
      } catch (ParseException e) {
        e.printStackTrace();
      }

    } else if (financeType.toString().equals("ct")) {
      ID courseID = toAdd.getCourseID();
      ID staffID = toAdd.getStaffID();

      if (!model.has(courseID, Constants.ENTITY_TYPE.COURSE)) {
        throw new CommandException(MESSAGE_NOTFOUND_COURSE_ID);
      }
      Course course = (Course) model.get(courseID, Constants.ENTITY_TYPE.COURSE);
      if (!model.has(staffID, Constants.ENTITY_TYPE.STAFF)) {
        throw new CommandException(MESSAGE_NOTFOUND_TEACHER_ID);
      }
      Staff teacher = (Staff) model.get(staffID, Constants.ENTITY_TYPE.STAFF);
      if (!course.getAssignedStaffID().equals(staffID)) {
        throw new CommandException(MESSAGE_NOTFOUND_COURSE_TEACHER);
      }
      String courseName = course.getName().toString();
      Amount amount = new Amount("-" + teacher.getSalary().toString());
      String staffName = teacher.getName().toString();

      Name name = new Name(String.format("Paid Staff %s %s for teaching Course %s %s", staffName, staffID.toString()
          , courseName, courseID.toString()));

      try {
        this.toAdd = new Finance(name, toAdd.getFinanceType(), toAdd.getDate(), amount,
            toAdd.getCourseID(), toAdd.getStudentID(), toAdd.getStaffID(), toAdd.getTags());
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }

    if (model.has(toAdd)) {
      throw new CommandException(MESSAGE_DUPLICATE_FINANCE);
    }
  }

  @Override
  public CommandResult executeUndoableCommand(Model model) throws CommandException {
    requireNonNull(model);

    if (index == null) {
      model.add(toAdd);
    } else {
      model.addAtIndex(toAdd, index);
    }
    EdgeManager.revokeEdgesFromDeleteEvent(toAdd);
    return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
  }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
        || (other instanceof AddFinanceCommand // instanceof handles nulls
        && toAdd.equals(((AddFinanceCommand) other).toAdd));
  }
}
