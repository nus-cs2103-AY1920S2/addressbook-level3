package seedu.address.logic.commands.facilitator;

import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalModManager.getTypicalModManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandType;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.facilitator.Facilitator;
import seedu.address.testutil.FacilitatorBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code FacilAddCommand}.
 */
public class FacilAddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalModManager(), new UserPrefs());
    }

    @Test
    public void execute_newFacilitator_success() {
        Facilitator validFacilitator = new FacilitatorBuilder().withName(VALID_NAME_AMY).build();

        Model expectedModel = new ModelManager(model.getModManager(), new UserPrefs());
        expectedModel.addFacilitator(validFacilitator);

        assertCommandSuccess(new FacilAddCommand(validFacilitator), model,
                String.format(FacilAddCommand.MESSAGE_SUCCESS, validFacilitator),
                CommandType.FACILITATOR, expectedModel);
    }

    @Test
    public void execute_duplicateFacilitator_throwsCommandException() {
        Facilitator facilitatorInList = model.getModManager().getFacilitatorList().get(0);
        assertCommandFailure(new FacilAddCommand(facilitatorInList), model,
                FacilAddCommand.MESSAGE_DUPLICATE_FACILITATOR);
    }

}
