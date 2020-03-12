package nasa.logic.commands;

import nasa.model.module.Module;
import nasa.model.module.ModuleCode;
import nasa.model.module.ModuleName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

import nasa.model.ModelManager;
import nasa.model.UserPrefs;
import nasa.model.Model;
import nasa.model.NasaBook;

public class AddModuleCommandTest {

    private Model model;

    @BeforeEach
    public void setModel() {
        model = new ModelManager(new NasaBook(), new UserPrefs());
    }

    @Test
    public void execute_newModule_success() throws Exception {
        Module module = new Module(new ModuleCode("CS2100"), new ModuleName("Computer Organization"));
        Model expectedModel = new ModelManager(model.getNasaBook(), model.getUserPrefs());
        expectedModel.addModule(module);
        AddModuleCommand command = new AddModuleCommand(module);
        command.execute(model);
        assertEquals(expectedModel, model);
    }
}
