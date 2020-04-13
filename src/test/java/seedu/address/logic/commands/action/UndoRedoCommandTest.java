package seedu.address.logic.commands.action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DAY_AND_TIME_MONDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DAY_AND_TIME_SUNDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_CS1101S;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LESSON_TYPE_SEC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LESSON_TYPE_TUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS1101S;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_GEQ1000;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OFFICE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_ID_FIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_HOME;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalModManager.getTypicalModManager;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.facilitator.FacilAddCommand;
import seedu.address.logic.commands.facilitator.FacilDeleteCommand;
import seedu.address.logic.commands.facilitator.FacilEditCommand;
import seedu.address.logic.commands.lesson.LessonAddCommand;
import seedu.address.logic.commands.lesson.LessonDeleteCommand;
import seedu.address.logic.commands.lesson.LessonEditCommand;
import seedu.address.logic.commands.module.ModuleAddCommand;
import seedu.address.logic.commands.module.ModuleDeleteCommand;
import seedu.address.logic.commands.module.ModuleEditCommand;
import seedu.address.logic.commands.task.TaskAddCommand;
import seedu.address.logic.commands.task.TaskDeleteCommand;
import seedu.address.logic.commands.task.TaskEditCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.facilitator.Facilitator;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.task.Task;
import seedu.address.model.task.util.TaskDateTime;
import seedu.address.model.util.Description;
import seedu.address.testutil.EditFacilitatorDescriptorBuilder;
import seedu.address.testutil.EditLessonDescriptorBuilder;
import seedu.address.testutil.EditModuleDescriptorBuilder;
import seedu.address.testutil.EditTaskDescriptorBuilder;
import seedu.address.testutil.FacilitatorBuilder;
import seedu.address.testutil.LessonBuilder;
import seedu.address.testutil.ModuleBuilder;
import seedu.address.testutil.TaskBuilder;


public class UndoRedoCommandTest {
    @Test
    public void module_action_test() throws CommandException {
        Model baseModel = new ModelManager(getTypicalModManager(), new UserPrefs());
        Model resultingModel = new ModelManager(getTypicalModManager(), new UserPrefs());
        Model testModel = new ModelManager(getTypicalModManager(), new UserPrefs());

        ModuleAddCommand modAdd = new ModuleAddCommand(
                new Module(new ModuleCode("CS5223"), new Description("Multi-core Architecture")));
        modAdd.execute(testModel);
        modAdd.execute(resultingModel);

        Module editedModule = new ModuleBuilder().withModuleCode(VALID_MODULE_CODE_CS1101S)
                .withDescription(VALID_DESCRIPTION_CS1101S).build();
        ModuleEditCommand.EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder(editedModule).build();
        ModuleEditCommand modEdit = new ModuleEditCommand(
                new ModuleCode(VALID_MODULE_CODE_CS2103T), descriptor);
        modEdit.execute(testModel);
        modEdit.execute(resultingModel);

        ModuleDeleteCommand modDelete = new ModuleDeleteCommand(new ModuleCode(VALID_MODULE_CODE_CS2101));
        modDelete.execute(testModel);
        modDelete.execute(resultingModel);

        new UndoCommand().execute(testModel);
        new UndoCommand().execute(testModel);

        new RedoCommand().execute(testModel);
        new UndoCommand().execute(testModel);

        new UndoCommand().execute(testModel);

        new RedoCommand().execute(testModel);
        new RedoCommand().execute(testModel);
        new UndoCommand().execute(testModel);
        new UndoCommand().execute(testModel);

        assertEquals(baseModel.getModManager(), testModel.getModManager());

        new RedoCommand().execute(testModel);
        new UndoCommand().execute(testModel);
        new RedoCommand().execute(testModel);
        new RedoCommand().execute(testModel);
        new RedoCommand().execute(testModel);

        assertEquals(resultingModel.getModManager(), testModel.getModManager());
    }

    @Test
    public void lesson_action_test() throws CommandException {
        Model baseModel = new ModelManager(getTypicalModManager(), new UserPrefs());
        Model testModel = new ModelManager(getTypicalModManager(), new UserPrefs());

        LessonAddCommand lessonAdd = new LessonAddCommand(new LessonBuilder().withLessonType(VALID_LESSON_TYPE_SEC)
                .withDayAndTime(VALID_DAY_AND_TIME_MONDAY).build());
        lessonAdd.execute(testModel);

        Lesson editedLesson = new LessonBuilder().withModuleCode(VALID_MODULE_CODE_GEQ1000)
                .withLessonType(VALID_LESSON_TYPE_TUT)
                .withDayAndTime(VALID_DAY_AND_TIME_SUNDAY).withVenue(VALID_VENUE_HOME).build();
        LessonEditCommand.EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder(editedLesson).build();
        LessonEditCommand lessonEdit = new LessonEditCommand(testModel.getLessons().get(0).getModuleCode(),
                INDEX_FIRST, descriptor);
        lessonEdit.execute(testModel);

        Lesson lessonToDelete = testModel.getLessons().get(INDEX_FIRST.getZeroBased());
        LessonDeleteCommand lessonDelete = new LessonDeleteCommand(INDEX_FIRST, lessonToDelete.getModuleCode());
        lessonDelete.execute(testModel);

        new UndoCommand().execute(testModel);
        new UndoCommand().execute(testModel);

        new RedoCommand().execute(testModel);
        new UndoCommand().execute(testModel);

        new UndoCommand().execute(testModel);

        new RedoCommand().execute(testModel);
        new RedoCommand().execute(testModel);
        new UndoCommand().execute(testModel);
        new UndoCommand().execute(testModel);

        assertEquals(baseModel.getModManager(), testModel.getModManager());
    }

    @Test
    public void facil_action_test() throws CommandException {
        Model baseModel = new ModelManager(getTypicalModManager(), new UserPrefs());
        Model testModel = new ModelManager(getTypicalModManager(), new UserPrefs());

        Facilitator validFacilitator = new FacilitatorBuilder().withName("Yu Haifeng").withModuleCodes().build();
        FacilAddCommand facilAdd = new FacilAddCommand(validFacilitator);
        facilAdd.execute(testModel);

        Facilitator editedFacilitator = new FacilitatorBuilder().withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withOffice(VALID_OFFICE_BOB).build();
        FacilEditCommand.EditFacilitatorDescriptor descriptor =
                new EditFacilitatorDescriptorBuilder(editedFacilitator).build();
        FacilEditCommand facilEdit = new FacilEditCommand(INDEX_FIRST, descriptor);
        facilEdit.execute(testModel);

        FacilDeleteCommand facilDelete = new FacilDeleteCommand(INDEX_FIRST);
        facilDelete.execute(testModel);

        new UndoCommand().execute(testModel);
        new UndoCommand().execute(testModel);

        new RedoCommand().execute(testModel);
        new UndoCommand().execute(testModel);

        new UndoCommand().execute(testModel);

        new RedoCommand().execute(testModel);
        new RedoCommand().execute(testModel);
        new UndoCommand().execute(testModel);
        new UndoCommand().execute(testModel);

        assertEquals(baseModel.getModManager(), testModel.getModManager());
    }

    @Test
    public void task_action_test() throws CommandException {
        Model baseModel = new ModelManager(getTypicalModManager(), new UserPrefs());
        Model testModel = new ModelManager(getTypicalModManager(), new UserPrefs());

        TaskAddCommand taskAdd = new TaskAddCommand(
                Task.makeNonScheduledTask(
                        new Description("Zoom zoom vromm"), new ModuleCode(VALID_MODULE_CODE_CS2103T)));
        taskAdd.execute(testModel);

        Task editedTask = new TaskBuilder().withDescription("Megaman")
                .withTaskDateTime(new TaskDateTime("18/01/2021", "23:50")).build();
        TaskEditCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(editedTask).build();
        TaskEditCommand taskEdit = new TaskEditCommand(
                new ModuleCode(TaskBuilder.DEFAULT_MOD_CODE), TaskBuilder.DEFAULT_TASK_NUM, descriptor);
        taskEdit.execute(testModel);

        TaskDeleteCommand taskDelete = new TaskDeleteCommand(
                new ModuleCode(VALID_MODULE_CODE_CS2103T), Integer.parseInt(VALID_TASK_ID_FIRST));
        taskDelete.execute(testModel);

        new UndoCommand().execute(testModel);
        new UndoCommand().execute(testModel);

        new RedoCommand().execute(testModel);
        new UndoCommand().execute(testModel);

        new UndoCommand().execute(testModel);

        new RedoCommand().execute(testModel);
        new RedoCommand().execute(testModel);
        new UndoCommand().execute(testModel);
        new UndoCommand().execute(testModel);

        assertEquals(baseModel.getModManager(), testModel.getModManager());
    }
}
