package nasa.logic.commands;

import static nasa.logic.commands.CommandTestUtil.assertCommandFailure;
import static nasa.logic.commands.CommandTestUtil.assertCommandSuccess;
import static nasa.testutil.TypicalModules.GEH1001;
import static nasa.testutil.TypicalModules.getTypicalNasaBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nasa.model.Model;
import nasa.model.ModelManager;
import nasa.model.UserPrefs;
import nasa.model.module.Module;
import nasa.testutil.ModuleBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalNasaBook(), new UserPrefs());
    }

    @Test
    public void execute_newModule_success() {
        Module validModule = GEH1001;

        Model expectedModel = new ModelManager(model.getNasaBook(), new UserPrefs());
        expectedModel.addModule(validModule);

        assertCommandSuccess(new AddModuleCommand(validModule), model,
                String.format(AddModuleCommand.MESSAGE_SUCCESS, validModule), expectedModel);
    }

    @Test
    public void execute_duplicateModule_throwsCommandException() {
        Module moduleInList = model.getNasaBook().getModuleList().get(0);
        assertCommandFailure(new AddModuleCommand(moduleInList), model, AddModuleCommand.MESSAGE_DUPLICATED_MODULE);
    }

}
