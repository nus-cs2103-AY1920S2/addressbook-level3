package seedu.address.logic.commands.taskcommand.deletecommand;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalNusModules.getTypicalModuleBook;
import static seedu.address.testutil.TypicalTasks.DEADLINE_A_DATE_A;
import static seedu.address.testutil.TypicalTasks.DEADLINE_B_DATE_A;
import static seedu.address.testutil.TypicalTasks.DEADLINE_C_DATE_B;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.calender.Deadline;
import seedu.address.model.calender.Task;

class DeleteDeadlineCommandTest {


    private static final String DATE_A = "20-04-2020";
    private static final String MISC_CATEGORY = "MISC";

    private Model model = new ModelManager(new AddressBook(), new UserPrefs(),
            getTypicalModuleBook(), FXCollections.observableList(new ArrayList<Task>()));


    @Test
    public void execute_deleteTask_successful() throws Exception {
        DeleteDeadlineCommand deleteDeadlineCommand = new DeleteDeadlineCommand(new Deadline(1, "delete"));

        model.addDeadlineTask(DEADLINE_A_DATE_A);

        Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs(),
                getTypicalModuleBook(), model.getDeadlineTaskList());
        try {
            expectedModel.addDeadlineTask(DEADLINE_A_DATE_A);
            assertCommandSuccess(deleteDeadlineCommand, model, expectedModel);
        } catch (UnsupportedOperationException ex) {
            System.out.println(ex);
        }
    }

    @Test
    public void execute_argumentWithInvalidIndex_showsFailCommandResult() {
        DeleteDeadlineCommand deleteDeadlineCommand = new DeleteDeadlineCommand(new Deadline(10000, "delete"));
        String expectedMessage = DeleteDeadlineCommand.MESSAGE_FAIL;

        Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs(),
                getTypicalModuleBook(), model.getDeadlineTaskList());
        assertCommandSuccess(deleteDeadlineCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        final DeleteDeadlineCommand standardCommand = new DeleteDeadlineCommand(DEADLINE_A_DATE_A);

        // same values -> returns true
        DeleteDeadlineCommand commandWithSameValues = new DeleteDeadlineCommand(
                new Deadline("Test A with Date A", DATE_A, MISC_CATEGORY, "add"));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different Date -> returns false
        assertFalse(standardCommand.equals(new DeleteDeadlineCommand(DEADLINE_C_DATE_B)));

        // different different description -> returns false
        assertFalse(standardCommand.equals(new DeleteDeadlineCommand(DEADLINE_B_DATE_A)));
    }
}
