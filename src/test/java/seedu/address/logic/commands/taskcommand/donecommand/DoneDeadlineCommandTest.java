package seedu.address.logic.commands.taskcommand.donecommand;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalNusModules.getTypicalModuleBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTask;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.calender.Deadline;
import seedu.address.model.calender.Task;
import seedu.address.testutil.TypicalTasks;

class DoneDeadlineCommandTest {
    private static final Task validDeadline = TypicalTasks.DEADLINE_A_DATE_A;
    private static final Task anotherValidDeadline = TypicalTasks.DEADLINE_B_DATE_A;

    private Model model = new ModelManager(new AddressBook(), new UserPrefs(),
            getTypicalModuleBook(), FXCollections.observableList(getTypicalTask()));


    @Test
    public void execute_markTaskAsDone_successful() throws Exception {

        Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs(),
                getTypicalModuleBook(), model.getDeadlineTaskList());


        try {
            expectedModel.doneDeadlineTask(new Deadline(1, "done"));
        } catch (UnsupportedOperationException ex) {
            System.out.println(ex);
        }

        assertTrue(expectedModel.getDeadlineTaskList().get(0).getStatus());

    }

    @Test
    public void execute_argumentWithInvalidIndex_showsFailCommandResult() {
        DoneDeadlineCommand doneDeadlineCommand = new DoneDeadlineCommand(new Deadline(1000, "done"));
        String expectedMessage = DoneDeadlineCommand.MESSAGE_FAIL;

        Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs(),
               getTypicalModuleBook(), model.getDeadlineTaskList());

        assertCommandSuccess(doneDeadlineCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        final DoneDeadlineCommand standardCommand = new DoneDeadlineCommand(validDeadline);

        // same values -> returns true
        DoneDeadlineCommand commandWithSameValues = new DoneDeadlineCommand(validDeadline);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different Dates -> returns false
        assertFalse(standardCommand.equals(new DoneDeadlineCommand(anotherValidDeadline)));

    }
}
