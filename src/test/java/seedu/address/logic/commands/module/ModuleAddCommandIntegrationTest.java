package seedu.address.logic.commands.module;

import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS1101S;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalModManager.getTypicalModManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandType;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.Module;
import seedu.address.testutil.ModuleBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code ModuleAddCommand}.
 */
public class ModuleAddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalModManager(), new UserPrefs());
    }

    @Test
    public void execute_newModule_success() {
        Module validModule = new ModuleBuilder().withModuleCode(VALID_MODULE_CODE_CS1101S).build();

        Model expectedModel = new ModelManager(model.getModManager(), new UserPrefs());
        expectedModel.addModule(validModule);

        assertCommandSuccess(new ModuleAddCommand(validModule), model,
                String.format(ModuleAddCommand.MESSAGE_SUCCESS, validModule),
                CommandType.MODULE, expectedModel);
    }

    @Test
    public void execute_duplicateModule_throwsCommandException() {
        Module moduleInList = model.getModManager().getModuleList().get(0);
        assertCommandFailure(new ModuleAddCommand(moduleInList), model,
                ModuleAddCommand.MESSAGE_DUPLICATE_MODULE);
    }

}
