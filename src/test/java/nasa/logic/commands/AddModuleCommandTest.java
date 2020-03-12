package NASA.logic.commands;

import NASA.model.module.Module;
import NASA.model.module.ModuleCode;
import NASA.model.module.ModuleName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

import NASA.model.ModelManagerNasa;
import NASA.model.UserPrefsNasa;
import NASA.model.ModelNasa;
import NASA.model.NasaBook;

public class AddModuleCommandTest {

    private ModelNasa model;

    @BeforeEach
    public void setModel() {
        model = new ModelManagerNasa(new NasaBook(), new UserPrefsNasa());
    }

    @Test
    public void execute_newModule_success() throws Exception {
        Module module = new Module(new ModuleCode("CS2100"), new ModuleName("Computer Organization"));
        ModelNasa expectedModel = new ModelManagerNasa(model.getNasaBook(), model.getUserPrefsNasa());
        expectedModel.addModule(module);
        AddModuleCommand command = new AddModuleCommand(module);
        command.execute(model);
        assertEquals(expectedModel, model);
    }
}
