package seedu.nova.logic.commands.ptcommands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.nova.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.nova.testutil.Assert.assertThrows;
import static seedu.nova.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.nova.testutil.TypicalPtTasks.getTypicalProgressTracker;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.nova.logic.commands.CommandResult;
import seedu.nova.model.Model;
import seedu.nova.model.ModelManager;
import seedu.nova.model.Nova;
import seedu.nova.model.UserPrefs;
import seedu.nova.model.VersionedAddressBook;
import seedu.nova.model.progresstracker.Ip;
import seedu.nova.model.progresstracker.ProgressTracker;
import seedu.nova.model.progresstracker.Project;
import seedu.nova.model.progresstracker.PtNote;
import seedu.nova.model.progresstracker.PtTask;
import seedu.nova.model.progresstracker.Tp;
import seedu.nova.testutil.ModelStub;
import seedu.nova.testutil.PtTaskBuilder;

public class PtAddCommandTest {
    private final Nova nova = new Nova();
    private final ProgressTracker pt = getTypicalProgressTracker();
    private final VersionedAddressBook ab = new VersionedAddressBook(getTypicalAddressBook());

    @Test
    public void constructor_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PtAddCommand(1, null, "taskDesc"));
    }

    @Test
    public void constructor_nullTaskDesc_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PtAddCommand(1, "ip", null));
    }

    @Test
    public void execute_ptTaskAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPtTaskAdded modelStub = new ModelStubAcceptingPtTaskAdded();
        PtTask validPtTask = new PtTaskBuilder().withNote(new PtNote("")).build();

        CommandResult commandResult = new PtAddCommand(validPtTask.getPtWeek(),
                validPtTask.getProjectName(), validPtTask.getTaskDesc().toString()).execute(modelStub);

        assertEquals(String.format(PtAddCommand.MESSAGE_SUCCESS, validPtTask.getPtWeek(),
                validPtTask.getProjectName().toUpperCase()), commandResult.getFeedbackToUser());
        assertEquals(validPtTask, modelStub.ptTasksAdded.get(0));
    }

    @Test
    public void execute_invalidWeekIndex_throwsCommandException() {
        nova.setProgressTrackerNova(pt);
        nova.setAddressBookNova(ab);
        Model model = new ModelManager(nova, new UserPrefs());

        PtAddCommand ptAddCommand = new PtAddCommand(14, "ip", "taskdesc");

        assertCommandFailure(ptAddCommand, model, PtAddCommand.MESSAGE_NOWEEK);
    }

    @Test
    public void equals() {
        Project ipProject = new Ip();
        Project tpProject = new Tp();
        PtTask ipProjectTask = new PtTaskBuilder().withProject(ipProject).build();
        PtTask tpProjectTask = new PtTaskBuilder().withProject(tpProject).build();

        PtAddCommand addIpProjectTaskCommand = new PtAddCommand(1,
                ipProjectTask.getProjectName(), "taskDesc");
        PtAddCommand addTpProjectTaskCommand = new PtAddCommand(1,
                tpProjectTask.getProjectName(), "taskDesc");

        // same object -> returns true
        assertTrue(addIpProjectTaskCommand.equals(addIpProjectTaskCommand));

        // same values -> returns true
        PtAddCommand addIpProjectTaskCommandCopy = new PtAddCommand(1, "ip", "taskDesc");
        assertTrue(addIpProjectTaskCommand.equals(addIpProjectTaskCommandCopy));

        //different types -> returns false
        assertFalse(addIpProjectTaskCommand.equals(1));

        // null -> returns false
        assertFalse(addIpProjectTaskCommand.equals(null));

        // different person -> returns false
        assertFalse(addIpProjectTaskCommand.equals(addTpProjectTaskCommand));
    }

    /**
     * A Model stub that always accept the ptTask being added.
     */
    private static class ModelStubAcceptingPtTaskAdded extends ModelStub {
        final ArrayList<PtTask> ptTasksAdded = new ArrayList<>();

        @Override
        public void addPtTask(String projectName, int weekNum, PtTask task) {
            requireNonNull(projectName);
            requireNonNull(task);

            ptTasksAdded.add(task);
        }

        @Override
        public ProgressTracker getProgressTracker() {
            return new ProgressTracker();
        }

        @Override
        public Ip getProgressTrackerIp() {
            ProgressTracker pt = getProgressTracker();
            return pt.getIp();
        }

        @Override
        public Tp getProgressTrackerTp() {
            ProgressTracker pt = getProgressTracker();
            return pt.getTp();
        }
    }
}
