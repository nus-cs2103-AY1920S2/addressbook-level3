package nasa.logic.commands.addcommands;

import static nasa.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS1231;
import static nasa.logic.commands.CommandTestUtil.VALID_MODULE_NAME_CS1231;
import static nasa.logic.commands.CommandTestUtil.assertCommandSuccess;
import static nasa.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nasa.logic.commands.exceptions.CommandException;
import nasa.model.HistoryBook;
import nasa.model.Model;
import nasa.model.ModelManager;
import nasa.model.NasaBook;
import nasa.model.UserPrefs;
import nasa.model.activity.Lesson;
import nasa.model.module.Module;
import nasa.model.module.ModuleCode;
import nasa.model.module.ModuleName;
import nasa.testutil.LessonBuilder;

public class AddLessonCommandTest {

    private Model model;
    private Module module;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new NasaBook(), new HistoryBook<>(), new UserPrefs());
        module = new Module(new ModuleCode(VALID_MODULE_CODE_CS1231), new ModuleName(VALID_MODULE_NAME_CS1231));
        model.addModule(module);
    }

    @Test
    public void execute_newLesson_success() {
        Lesson lesson = new LessonBuilder().build();

        Model expectedModel = new ModelManager(new NasaBook(), new HistoryBook<>(), new UserPrefs());
        expectedModel.addModule(new ModuleCode(VALID_MODULE_CODE_CS1231), new ModuleName(VALID_MODULE_NAME_CS1231));
        expectedModel.addActivity(module, lesson);

        AddLessonCommand command = new AddLessonCommand(lesson, new ModuleCode(VALID_MODULE_CODE_CS1231));
        assertCommandSuccess(command, model, String.format(AddDeadlineCommand.MESSAGE_SUCCESS, lesson), expectedModel);
    }

    @Test
    public void execute_duplicateLesson_failure() {
        Lesson lesson = new LessonBuilder().build();

        Model expectedModel = new ModelManager(model.getNasaBook(), model.getHistoryBook(), model.getUserPrefs());
        AddLessonCommand command = new AddLessonCommand(lesson, new ModuleCode(VALID_MODULE_CODE_CS1231));

        expectedModel.addActivity(module, lesson);
        assertThrows(CommandException.class, AddDeadlineCommand.MESSAGE_DUPLICATED_ACTIVITY, ()
            -> command.execute(expectedModel));
    }

    @Test
    public void constructor_nullDeadline_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddLessonCommand(null,
                new ModuleCode(VALID_MODULE_CODE_CS1231)));
    }

    @Test
    public void constructor_nullModuleCode_throwsNullPointerException() {
        Lesson lesson = new LessonBuilder().build();
        assertThrows(NullPointerException.class, () -> new AddLessonCommand(lesson, null));
    }
}
