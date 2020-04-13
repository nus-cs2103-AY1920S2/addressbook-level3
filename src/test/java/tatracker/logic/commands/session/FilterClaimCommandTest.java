//@@author Chuayijing

package tatracker.logic.commands.session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_MODULE_CODE;
import static tatracker.logic.commands.CommandTestUtil.assertCommandFailure;
import static tatracker.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tatracker.logic.commands.session.FilterClaimCommand.MESSAGE_FILTERED_CLAIMS_SUCCESS;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tatracker.logic.commands.CommandResult.Action;
import tatracker.model.Model;
import tatracker.model.ModelManager;
import tatracker.model.UserPrefs;
import tatracker.model.util.SampleDataUtil;

public class FilterClaimCommandTest {

    private Model model = new ModelManager(SampleDataUtil.getSampleTaTracker(), new UserPrefs());
    private Model expectedModel = new ModelManager(SampleDataUtil.getSampleTaTracker(), new UserPrefs());

    @BeforeEach
    public void setup() {
        model.updateFilteredDoneSessionList(Model.PREDICATE_SHOW_ALL_SESSIONS, "");
        expectedModel.updateFilteredDoneSessionList(Model.PREDICATE_SHOW_ALL_SESSIONS, "");
    }

    @Test
    public void equals() {
        DoneSessionPredicate firstPredicate = new DoneSessionPredicate("CS3243");

        DoneSessionPredicate secondPredicate = new DoneSessionPredicate("CS2103T");

        FilterClaimCommand filterFirstCommand = new FilterClaimCommand(firstPredicate);
        FilterClaimCommand filterSecondCommand = new FilterClaimCommand(firstPredicate);

        // same object -> returns true
        assertEquals(filterFirstCommand, filterFirstCommand);

        // same values -> returns true
        FilterClaimCommand findFirstCommandCopy = new FilterClaimCommand(firstPredicate);
        assertEquals(filterFirstCommand, findFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, filterFirstCommand);

        // null -> returns false
        assertNotEquals(null, filterFirstCommand);
    }

    @Test
    public void execute_zeroKeywords_noSessionFound() {
        DoneSessionPredicate predicate = new DoneSessionPredicate("");
        FilterClaimCommand command = new FilterClaimCommand(predicate);
        expectedModel.updateFilteredDoneSessionList(predicate, "");
        assertCommandFailure(command, model, MESSAGE_INVALID_MODULE_CODE);
    }

    @Test
    public void execute_keyword_success() {
        String expectedMsg = String.format(MESSAGE_FILTERED_CLAIMS_SUCCESS, "CS3243");
        DoneSessionPredicate predicate = new DoneSessionPredicate("CS3243");
        FilterClaimCommand command = new FilterClaimCommand(predicate);
        expectedModel.setCurrClaimFilter("Module: " + "CS3243");
        expectedModel.updateFilteredDoneSessionList(predicate, "CS3243");
        assertCommandSuccess(command, model, expectedMsg, expectedModel, Action.FILTER_CLAIMS);
        assertEquals(expectedModel.getFilteredDoneSessionList(), model.getFilteredDoneSessionList());
    }
}
