package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Supplier;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandAdd.AddCourseCommand;
import seedu.address.logic.commands.commandAdd.AddFinanceCommand;
import seedu.address.logic.commands.commandAdd.AddStudentCommand;
import seedu.address.logic.commands.commandAdd.AddTeacherCommand;
import seedu.address.logic.commands.commandAdd.AddAssignmentCommand;
import seedu.address.logic.commands.commandAssign.AssignCommandBase;
import seedu.address.logic.commands.commandAssign.AssignStudentToCourseCommand;
import seedu.address.logic.commands.commandAssign.AssignTeacherToCourseCommand;
import seedu.address.logic.commands.commandClear.*;
import seedu.address.logic.commands.commandDelete.*;
import seedu.address.logic.commands.commandEdit.EditCourseCommand;
import seedu.address.logic.commands.commandEdit.EditFinanceCommand;
import seedu.address.logic.commands.commandEdit.EditStudentCommand;
import seedu.address.logic.commands.commandEdit.EditTeacherCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.modelAssignment.Assignment;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelFinance.Finance;
import seedu.address.model.modelGeneric.ReadOnlyAddressBookGeneric;
import seedu.address.model.modelStaff.Staff;
import seedu.address.model.modelStudent.Student;
import seedu.address.model.person.Person;
import seedu.address.storage.Storage;
import seedu.address.ui.SummaryPanel;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {

  public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
  private final Logger logger = LogsCenter.getLogger(LogicManager.class);

  private final Model model;
  private final Storage storage;
  private final AddressBookParser addressBookParser;
  private final UndoRedoStack undoRedoStack;
  private SummaryPanel summaryPanel;

  public LogicManager(Model model, Storage storage) {
    this.model = model;
    this.storage = storage;
    this.undoRedoStack = new UndoRedoStack();
    addressBookParser = new AddressBookParser();
  }

  @Override
  public CommandResult execute(String commandText) throws CommandException, ParseException {
    logger.info("----------------[USER COMMAND][" + commandText + "]");

    CommandResult commandResult;
    Command command = addressBookParser.parseCommand(commandText);
    command.setData(undoRedoStack);
    commandResult = command.execute(model);
    this.undoRedoStack.push(command);

    // Updates summary panel
    summaryPanel.updateTotalStudents(getFilteredStudentList().size());
    summaryPanel.updateTotalTeachers(getFilteredStaffList().size());
    summaryPanel.updateTotalCourses(getFilteredCourseList().size());
    summaryPanel.updateTotalFinances(getFilteredFinanceList().size());
    summaryPanel.updateTotalAssignments(getFilteredAssignmentList().size());

    return commandResult;
  }

  public void setSummaryPanel(SummaryPanel summaryPanel){
    this.summaryPanel = summaryPanel;
  }
  ///
  @Override
  public ReadOnlyAddressBook getAddressBook() {
    return model.getAddressBook();
  }

  @Override
  public ObservableList<Person> getFilteredPersonList() {
    return model.getFilteredPersonList();
  }

  @Override
  public Path getAddressBookFilePath() {
    return model.getAddressBookFilePath();
  }

  ///
  @Override
  public ReadOnlyAddressBookGeneric<Staff> getStaffAddressBook() {
    return model.getStaffAddressBook();
  }

  @Override
  public ObservableList<Staff> getFilteredStaffList() {
    return model.getFilteredStaffList();
  }

  @Override
  public Path getStaffAddressBookFilePath() {
    return model.getStaffAddressBookFilePath();
  }

  ///
  @Override
  public ReadOnlyAddressBookGeneric<Student> getStudentAddressBook() {
    return model.getStudentAddressBook();
  }

  @Override
  public ObservableList<Student> getFilteredStudentList() {
    return model.getFilteredStudentList();
  }

  @Override
  public Path getStudentAddressBookFilePath() {
    return model.getStudentAddressBookFilePath();
  }


  ///
  @Override
  public ReadOnlyAddressBookGeneric<Course> getCourseAddressBook() {
    return model.getCourseAddressBook();
  }

  @Override
  public ObservableList<Course> getFilteredCourseList() {
    return model.getFilteredCourseList();
  }

  @Override
  public Path getCourseAddressBookFilePath() {
    return model.getCourseAddressBookFilePath();
  }

  ///
  @Override
  public ReadOnlyAddressBookGeneric<Finance> getFinanceAddressBook() {
    return model.getFinanceAddressBook();
  }

  @Override
  public ObservableList<Finance> getFilteredFinanceList() {
    return model.getFilteredFinanceList();
  }

  @Override
  public Path getFinanceAddressBookFilePath() {
    return model.getFinanceAddressBookFilePath();
  }

  @Override
  public ReadOnlyAddressBookGeneric<Assignment> getAssignmentAddressBook() {
    return model.getAssignmentAddressBook();
  }

  @Override
  public ObservableList<Assignment> getFilteredAssignmentList() {
    return model.getFilteredAssignmentList();
  }

  @Override
  public Path getAssignmentAddressBookFilePath() {
    return model.getAssignmentAddressBookFilePath();
  }


  @Override
  public GuiSettings getGuiSettings() {
    return model.getGuiSettings();
  }

  @Override
  public void setGuiSettings(GuiSettings guiSettings) {
    model.setGuiSettings(guiSettings);
  }
}
