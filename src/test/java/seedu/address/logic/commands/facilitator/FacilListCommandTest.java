package seedu.address.logic.commands.facilitator;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFacilitatorAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalModManager.getTypicalModManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandType;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code FacilListCommand}.
 */
public class FacilListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalModManager(), new UserPrefs());
        expectedModel = new ModelManager(model.getModManager(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new FacilListCommand(), model, FacilListCommand.MESSAGE_SUCCESS, CommandType.FACILITATOR,
                expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showFacilitatorAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new FacilListCommand(), model, FacilListCommand.MESSAGE_SUCCESS, CommandType.FACILITATOR,
                expectedModel);
    }
}
