package nasa.logic.commands.addcommands;

import static nasa.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS1231;
import static nasa.logic.commands.CommandTestUtil.VALID_MODULE_NAME_CS1231;
import static nasa.logic.commands.CommandTestUtil.assertCommandSuccess;
import static nasa.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nasa.model.HistoryBook;
import nasa.model.Model;
import nasa.model.ModelManager;
import nasa.model.NasaBook;
import nasa.model.UserPrefs;
import nasa.model.activity.Deadline;
import nasa.model.module.Module;
import nasa.model.module.ModuleCode;
import nasa.model.module.ModuleName;
import nasa.testutil.DeadlineBuilder;

public class AddDeadlineCommandTest {

    private Model model;
    private Module module;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new NasaBook(), new HistoryBook<>(), new HistoryBook<>(), new UserPrefs());
        module = new Module(new ModuleCode(VALID_MODULE_CODE_CS1231), new ModuleName(VALID_MODULE_NAME_CS1231));
        model.addModule(module);
    }

    @Test
    public void execute_newDeadline_success() {
        Deadline deadline = new DeadlineBuilder().build();

        Model expectedModel = new ModelManager(new NasaBook(), new HistoryBook<>(), new HistoryBook<>(),
                new UserPrefs());
        expectedModel.addModule(new Module(new ModuleCode(VALID_MODULE_CODE_CS1231),
                new ModuleName(VALID_MODULE_NAME_CS1231)));
        expectedModel.addDeadline(new ModuleCode(VALID_MODULE_CODE_CS1231), deadline);

        AddDeadlineCommand command = new AddDeadlineCommand(deadline, new ModuleCode(VALID_MODULE_CODE_CS1231));
        assertCommandSuccess(command, model,
            String.format(AddDeadlineCommand.MESSAGE_SUCCESS, deadline), expectedModel);
    }

    @Test
    public void constructor_nullDeadline_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            new AddDeadlineCommand(null, new ModuleCode(VALID_MODULE_CODE_CS1231)));
    }

    @Test
    public void constructor_nullModuleCode_throwsNullPointerException() {
        Deadline deadline = (new DeadlineBuilder().build());
        assertThrows(NullPointerException.class, () ->
            new AddDeadlineCommand(deadline, null));
    }
}
