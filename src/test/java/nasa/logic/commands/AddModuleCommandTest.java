package nasa.logic.commands;

import static nasa.logic.commands.CommandTestUtil.assertCommandSuccess;
import static nasa.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nasa.logic.commands.exceptions.CommandException;
import nasa.model.Model;
import nasa.model.ModelManager;
import nasa.model.NasaBook;
import nasa.model.UserPrefs;

import nasa.model.module.Module;
import nasa.model.module.ModuleCode;
import nasa.model.module.ModuleName;

// Integrated test TODO: changed name to AddModuleCommandTest
public class AddModuleCommandTest {

    private static final String MODULE_CODE = "CS2103";
    private static final String MODULE_NAME = "Software Engineering";

    private Model model;

    @BeforeEach
    public void setModel() {
        model = new ModelManager(new NasaBook(), new UserPrefs());
    }

    @Test
    public void execute_newModule_success() throws Exception {
        Module module = new Module(new ModuleCode(MODULE_CODE), new ModuleName(MODULE_NAME));

        Model expectedModel = new ModelManager(model.getNasaBook(), model.getUserPrefs());
        expectedModel.addModule(module);

        AddModuleCommand command = new AddModuleCommand(module);
        assertCommandSuccess(command, model, String.format(AddModuleCommand.MESSAGE_SUCCESS, module), expectedModel);
    }

    @Test
    public void execute_duplicateModule_fail() throws Exception {
        Module module = new Module(new ModuleCode(MODULE_CODE), new ModuleName(MODULE_NAME));
        AddModuleCommand command = new AddModuleCommand(module);
        command.execute(model); //add one time
        // cannot add the same module again
        assertThrows(CommandException.class, () -> command.execute(model));
    }
}
