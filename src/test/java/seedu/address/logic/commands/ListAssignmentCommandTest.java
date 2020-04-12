package seedu.address.logic.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.commandList.ListAssignmentCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalAssignment.getTypicalAssignmentAddressBook;
import static seedu.address.testutil.TypicalCourse.getTypicalCourseAddressBook;
import static seedu.address.testutil.TypicalFinance.getTypicalFinanceAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalProgress.getTypicalProgressAddressBook;
import static seedu.address.testutil.TypicalStudent.getTypicalStudentAddressBook;
import static seedu.address.testutil.TypicalTeacher.getTypicalTeacherAddressBook;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListAssignmentCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalTeacherAddressBook(), getTypicalStudentAddressBook(),
                getTypicalFinanceAddressBook(), getTypicalCourseAddressBook(), getTypicalAssignmentAddressBook(), getTypicalProgressAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), model.getStaffAddressBook(),
                model.getStudentAddressBook(), model.getFinanceAddressBook(), model.getCourseAddressBook(),
                model.getAssignmentAddressBook(), model.getProgressAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListAssignmentCommand(), model, ListAssignmentCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListAssignmentCommand(), model, ListAssignmentCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
