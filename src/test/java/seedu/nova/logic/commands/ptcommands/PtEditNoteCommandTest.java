package seedu.nova.logic.commands.ptcommands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.nova.logic.commands.CommandTestUtil.assertCommandFailure;
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

public class PtEditNoteCommandTest {

    private final Nova nova = new Nova();
    private final ProgressTracker pt = getTypicalProgressTracker();
    private final VersionedAddressBook ab = new VersionedAddressBook(getTypicalAddressBook());

    @Test
    public void constructor_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PtEditNoteCommand(1, 1, null,
                "new note"));
    }

    @Test
    public void constructor_nullNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PtEditNoteCommand(1, 1, "ip",
                null));
    }

    @Test
    public void execute_invalidWeekIndex_throwsCommandException() {
        nova.setProgressTrackerNova(pt);
        nova.setAddressBookNova(ab);
        Model model = new ModelManager(nova, new UserPrefs());

        PtEditNoteCommand ptEditNoteCommand1 = new PtEditNoteCommand(3, 1, "ip", "new note");

        assertCommandFailure(ptEditNoteCommand1, model, PtEditNoteCommand.MESSAGE_FAILURE);

        PtEditNoteCommand ptEditNoteCommand2 = new PtEditNoteCommand(14, 1, "ip", "new note");

        assertCommandFailure(ptEditNoteCommand2, model, PtEditNoteCommand.MESSAGE_NOWEEK);
    }

    @Test
    public void execute_invalidTaskIndex_throwsCommandException() {
        nova.setProgressTrackerNova(pt);
        nova.setAddressBookNova(ab);
        Model model = new ModelManager(nova, new UserPrefs());

        PtEditNoteCommand ptEditNoteCommand = new PtEditNoteCommand(1, 2, "ip", "new note");

        assertCommandFailure(ptEditNoteCommand, model, PtEditNoteCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        Project ipProject = new Ip();
        Project tpProject = new Tp();
        PtTask ipProjectTask = new PtTaskBuilder().withProject(ipProject).build();
        PtTask tpProjectTask = new PtTaskBuilder().withProject(tpProject).build();

        PtEditNoteCommand editNoteIpProjectTaskCommand = new PtEditNoteCommand(1, 1,
                ipProjectTask.getProjectName(), "new note");
        PtEditNoteCommand editNoteTpProjectTaskCommand = new PtEditNoteCommand(1, 1,
                tpProjectTask.getProjectName(), "new note");

        // same object -> returns true
        assertTrue(editNoteIpProjectTaskCommand.equals(editNoteIpProjectTaskCommand));

        // same values -> returns true
        PtEditNoteCommand editNoteIpProjectTaskCommandCopy = new PtEditNoteCommand(1, 1,
                ipProjectTask.getProjectName(), "new note");
        assertTrue(editNoteIpProjectTaskCommand.equals(editNoteIpProjectTaskCommandCopy));

        //different types -> returns false
        assertFalse(editNoteIpProjectTaskCommand.equals(1));

        // null -> returns false
        assertFalse(editNoteIpProjectTaskCommand.equals(null));

        // different person -> returns false
        assertFalse(editNoteIpProjectTaskCommand.equals(editNoteTpProjectTaskCommand));
    }
}
