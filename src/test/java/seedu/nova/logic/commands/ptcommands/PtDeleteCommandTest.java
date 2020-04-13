package seedu.nova.logic.commands.ptcommands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.nova.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.nova.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.nova.testutil.Assert.assertThrows;
import static seedu.nova.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.nova.testutil.TypicalPtTasks.getTypicalProgressTracker;

import org.junit.jupiter.api.Test;

import seedu.nova.model.Model;
import seedu.nova.model.ModelManager;
import seedu.nova.model.Nova;
import seedu.nova.model.UserPrefs;
import seedu.nova.model.VersionedAddressBook;
import seedu.nova.model.progresstracker.Ip;
import seedu.nova.model.progresstracker.ProgressTracker;
import seedu.nova.model.progresstracker.Project;
import seedu.nova.model.progresstracker.PtTask;
import seedu.nova.model.progresstracker.Tp;
import seedu.nova.testutil.PtTaskBuilder;

public class PtDeleteCommandTest {

    private final Nova nova = new Nova();
    private final ProgressTracker pt = getTypicalProgressTracker();
    private final VersionedAddressBook ab = new VersionedAddressBook(getTypicalAddressBook());

    @Test
    public void constructor_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PtDeleteCommand(1, null, 1));
    }

    @Test
    public void execute_validTaskAndWeekIndex_success() {
        nova.setProgressTrackerNova(pt);
        nova.setAddressBookNova(ab);
        Model model = new ModelManager(nova, new UserPrefs());

        PtDeleteCommand ptDeleteCommand = new PtDeleteCommand(1, "ip", 1);

        String expectedMessage = String.format(
                PtDeleteCommand.MESSAGE_SUCCESS, 1, 1, "IP");

        ModelManager expectedModel = new ModelManager(model.getNova(), new UserPrefs());

        assertCommandSuccess(ptDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTaskIndex_throwsCommandException() {
        nova.setProgressTrackerNova(pt);
        nova.setAddressBookNova(ab);
        Model model = new ModelManager(nova, new UserPrefs());

        PtDeleteCommand ptDeleteCommand = new PtDeleteCommand(1, "ip", 3);

        assertCommandFailure(ptDeleteCommand, model, PtDeleteCommand.MESSAGE_FAILURE);
    }

    @Test
    public void execute_invalidWeekIndex_throwsCommandException() {
        nova.setProgressTrackerNova(pt);
        nova.setAddressBookNova(ab);
        Model model = new ModelManager(nova, new UserPrefs());

        PtDeleteCommand ptDeleteCommand = new PtDeleteCommand(3, "ip", 1);

        assertCommandFailure(ptDeleteCommand, model, PtDeleteCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        Project ipProject = new Ip();
        Project tpProject = new Tp();
        PtTask ipProjectTask = new PtTaskBuilder().withProject(ipProject).build();
        PtTask tpProjectTask = new PtTaskBuilder().withProject(tpProject).build();

        PtDeleteCommand deleteIpProjectTaskCommand = new PtDeleteCommand(1,
                ipProjectTask.getProjectName(), 1);
        PtDeleteCommand deleteTpProjectTaskCommand = new PtDeleteCommand(1,
                tpProjectTask.getProjectName(), 1);

        // same object -> returns true
        assertTrue(deleteIpProjectTaskCommand.equals(deleteIpProjectTaskCommand));

        // same values -> returns true
        PtDeleteCommand deleteIpProjectTaskCommandCopy = new PtDeleteCommand(1,
                ipProjectTask.getProjectName(), 1);
        assertTrue(deleteIpProjectTaskCommand.equals(deleteIpProjectTaskCommandCopy));

        //different types -> returns false
        assertFalse(deleteIpProjectTaskCommand.equals(1));

        // null -> returns false
        assertFalse(deleteIpProjectTaskCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteIpProjectTaskCommand.equals(deleteTpProjectTaskCommand));
    }
}
