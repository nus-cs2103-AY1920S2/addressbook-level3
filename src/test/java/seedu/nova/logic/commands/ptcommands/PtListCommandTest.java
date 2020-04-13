package seedu.nova.logic.commands.ptcommands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.nova.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.nova.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.nova.testutil.Assert.assertThrows;
import static seedu.nova.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.nova.testutil.TypicalPtTasks.PTTASKIP1;
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

public class PtListCommandTest {

    private final Nova nova = new Nova();
    private final ProgressTracker pt = getTypicalProgressTracker();
    private final VersionedAddressBook ab = new VersionedAddressBook(getTypicalAddressBook());

    @Test
    public void constructor_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PtListCommand(1, null));
    }

    @Test
    public void execute_validProjectAndWeekIndex_success() {
        nova.setProgressTrackerNova(pt);
        nova.setAddressBookNova(ab);
        Model model = new ModelManager(nova, new UserPrefs());

        PtListCommand ptListCommand = new PtListCommand(1, "ip");

        String expectedList = "  " + 1 + ") " + PTTASKIP1.toString() + "\n"
                + "       Note: " + PTTASKIP1.getNote().toString() + "\n";
        String expectedMessage = String.format(PtListCommand.MESSAGE_SUCCESS, "IP", 1) + expectedList;

        ModelManager expectedModel = new ModelManager(model.getNova(), new UserPrefs());

        assertCommandSuccess(ptListCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidWeekIndex_throwsCommandException() {
        nova.setProgressTrackerNova(pt);
        nova.setAddressBookNova(ab);
        Model model = new ModelManager(nova, new UserPrefs());

        PtListCommand ptAddCommand = new PtListCommand(14, "ip");

        assertCommandFailure(ptAddCommand, model, PtListCommand.MESSAGE_NOWEEK);
    }

    @Test
    public void equals() {
        Project ipProject = new Ip();
        Project tpProject = new Tp();
        PtTask ipProjectTask = new PtTaskBuilder().withProject(ipProject).build();
        PtTask tpProjectTask = new PtTaskBuilder().withProject(tpProject).build();

        PtListCommand listIpProjectTaskCommand = new PtListCommand(1,
                ipProjectTask.getProjectName());
        PtListCommand listTpProjectTaskCommand = new PtListCommand(1,
                tpProjectTask.getProjectName());

        // same object -> returns true
        assertTrue(listIpProjectTaskCommand.equals(listIpProjectTaskCommand));

        // same values -> returns true
        PtListCommand listIpProjectTaskCommandCopy = new PtListCommand(1, "ip");
        assertTrue(listIpProjectTaskCommand.equals(listIpProjectTaskCommandCopy));

        //different types -> returns false
        assertFalse(listIpProjectTaskCommand.equals(1));

        // null -> returns false
        assertFalse(listIpProjectTaskCommand.equals(null));

        // different person -> returns false
        assertFalse(listIpProjectTaskCommand.equals(listTpProjectTaskCommand));
    }
}
