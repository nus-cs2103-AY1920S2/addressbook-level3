package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAssignment.getTypicalAssignmentAddressBook;
import static seedu.address.testutil.TypicalCourse.getTypicalCourseAddressBook;
import static seedu.address.testutil.TypicalFinance.getTypicalFinanceAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalProgress.getTypicalProgressAddressBook;
import static seedu.address.testutil.TypicalStudent.getTypicalStudentAddressBook;
import static seedu.address.testutil.TypicalTeacher.getTypicalTeacherAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.commandClear.ClearAssignmentCommand;
import seedu.address.logic.commands.commandClear.ClearCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.modelAssignment.AssignmentAddressBook;

public class ClearAssignmentABCommandTest {

    @Test
    public void execute_emptyAssignmentAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearAssignmentCommand(), model, ClearAssignmentCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAssignmentAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalTeacherAddressBook(), getTypicalStudentAddressBook(),
            getTypicalFinanceAddressBook(), getTypicalCourseAddressBook(),
                getTypicalAssignmentAddressBook(), getTypicalProgressAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalTeacherAddressBook(), getTypicalStudentAddressBook(),
            getTypicalFinanceAddressBook(), getTypicalCourseAddressBook(),
                new AssignmentAddressBook(), getTypicalProgressAddressBook(), new UserPrefs());

        assertCommandSuccess(new ClearAssignmentCommand(), model, ClearAssignmentCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
