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

public class PtDeleteNoteCommandTest {

    private final Nova nova = new Nova();
    private final ProgressTracker pt = getTypicalProgressTracker();
    private final VersionedAddressBook ab = new VersionedAddressBook(getTypicalAddressBook());

    @Test
    public void constructor_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PtDeleteNoteCommand(1, 1, null));
    }

    @Test
    public void execute_validProjectAndTaskAndWeekIndex_success() {
        nova.setProgressTrackerNova(pt);
        nova.setAddressBookNova(ab);
        Model model = new ModelManager(nova, new UserPrefs());

        PtDeleteNoteCommand ptDeleteNoteCommand = new PtDeleteNoteCommand(1, 1, "ip");

        String expectedMessage = String.format(
                PtDeleteNoteCommand.MESSAGE_SUCCESS, 1, 1, "IP");

        ModelManager expectedModel = new ModelManager(model.getNova(), new UserPrefs());

        assertCommandSuccess(ptDeleteNoteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTaskIndex_throwsCommandException() {
        nova.setProgressTrackerNova(pt);
        nova.setAddressBookNova(ab);
        Model model = new ModelManager(nova, new UserPrefs());

        PtDeleteNoteCommand ptDeleteNoteCommand = new PtDeleteNoteCommand(1, 3, "ip");

        assertCommandFailure(ptDeleteNoteCommand, model, PtDeleteNoteCommand.MESSAGE_FAILURE);
    }

    @Test
    public void execute_invalidWeekIndex_throwsCommandException() {
        nova.setProgressTrackerNova(pt);
        nova.setAddressBookNova(ab);
        Model model = new ModelManager(nova, new UserPrefs());

        PtDeleteNoteCommand ptDeleteNoteCommand1 = new PtDeleteNoteCommand(3, 1, "ip");

        assertCommandFailure(ptDeleteNoteCommand1, model, PtDeleteNoteCommand.MESSAGE_FAILURE);

        PtDeleteNoteCommand ptDeleteNoteCommand2 = new PtDeleteNoteCommand(14, 1, "ip");

        assertCommandFailure(ptDeleteNoteCommand2, model, PtDeleteNoteCommand.MESSAGE_NOWEEK);
    }

    @Test
    public void equals() {
        Project ipProject = new Ip();
        Project tpProject = new Tp();
        PtTask ipProjectTask = new PtTaskBuilder().withProject(ipProject).build();
        PtTask tpProjectTask = new PtTaskBuilder().withProject(tpProject).build();

        PtDeleteNoteCommand deleteNoteIpProjectTaskCommand = new PtDeleteNoteCommand(1, 1,
                ipProjectTask.getProjectName());
        PtDeleteNoteCommand deleteNoteTpProjectTaskCommand = new PtDeleteNoteCommand(1, 1,
                tpProjectTask.getProjectName());

        // same object -> returns true
        assertTrue(deleteNoteIpProjectTaskCommand.equals(deleteNoteIpProjectTaskCommand));

        // same values -> returns true
        PtDeleteNoteCommand deleteNoteIpProjectTaskCommandCopy = new PtDeleteNoteCommand(1, 1,
                ipProjectTask.getProjectName());
        assertTrue(deleteNoteIpProjectTaskCommand.equals(deleteNoteIpProjectTaskCommandCopy));

        //different types -> returns false
        assertFalse(deleteNoteIpProjectTaskCommand.equals(1));

        // null -> returns false
        assertFalse(deleteNoteIpProjectTaskCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteNoteIpProjectTaskCommand.equals(deleteNoteTpProjectTaskCommand));
    }
}
