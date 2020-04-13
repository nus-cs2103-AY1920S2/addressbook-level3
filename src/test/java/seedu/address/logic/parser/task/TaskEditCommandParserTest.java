package seedu.address.logic.parser.task;

import static seedu.address.commons.core.Messages.MESSAGE_AT_WITHOUT_ON_ERROR;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_TOO_MANY_ARGUMENTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ON;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.task.TaskEditCommand;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.task.Task;
import seedu.address.model.task.util.TaskDateTime;
import seedu.address.model.task.util.TaskNumManager;
import seedu.address.testutil.EditTaskDescriptorBuilder;
import seedu.address.testutil.TaskBuilder;


public class TaskEditCommandParserTest {
    private TaskEditCommandParser parser = new TaskEditCommandParser();
    private String validPreamble = "cs2101 737 ";
    private String validDate = " 18/03/2022 ";
    private String validTime = " 23:23 ";
    private String invalidPreAmbleNumOnly = "888 ";
    private String invalidPreAmbleCodeOnly = "cs2101 ";
    private String emptyPreAmble = "";
    private String invalidDate = " 1/1/1911 ";
    private String invalidTime = " 1:15 ";

    @Test
    public void parse_editAllSuccess() throws Exception {
        Task editedTask = new TaskBuilder().withDescription("Megaman")
                .withTaskDateTime(new TaskDateTime("18/01/2021", "23:50")).build();
        TaskEditCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(editedTask).build();

        TaskEditCommand command = new TaskEditCommand(
                editedTask.getModuleCode(), editedTask.getTaskNum(), descriptor);

        String userInput = editedTask.getModuleCode().toString() + " " + editedTask.getTaskNum()
                + " /desc Megaman /on 18/01/2021 /at 23:50";

        assertParseSuccess(parser, userInput, command);
    }

    @Test
    public void parse_editPartialSuccess() throws Exception {
        Task editedTask = new TaskBuilder().withDescription("Megaman").withTaskDateTime(null).build();

        TaskEditCommand.EditTaskDescriptor descriptor1 = new EditTaskDescriptorBuilder(editedTask).build();
        TaskEditCommand command = new TaskEditCommand(
                editedTask.getModuleCode(), editedTask.getTaskNum(), descriptor1);

        String userInput1 = editedTask.getModuleCode().toString() + " " + editedTask.getTaskNum()
                + " /desc Megaman";

        assertParseSuccess(parser, userInput1, command);

        Task editedTask1 = new TaskBuilder()
                .withTaskDateTime(new TaskDateTime("22/02/2022")).build();

        TaskEditCommand.EditTaskDescriptor descriptor2 = new EditTaskDescriptorBuilder(editedTask1).build();
        descriptor2.setDescription(null);
        TaskEditCommand command1 = new TaskEditCommand(
                editedTask1.getModuleCode(), editedTask1.getTaskNum(), descriptor2);

        String userInput2 = editedTask1.getModuleCode().toString() + " " + editedTask1.getTaskNum()
                + " /on 22/02/2022";

        assertParseSuccess(parser, userInput2, command1);
    }

    @Test
    public void invalidDateTimeFailure() {
        String expectedMsg = TaskDateTime.MESSAGE_CONSTRAINTS;
        assertParseFailure(parser, validPreamble + PREFIX_ON + invalidDate, expectedMsg);
        assertParseFailure(parser,
                validPreamble + PREFIX_ON + validDate + PREFIX_AT + invalidTime, expectedMsg);
    }

    @Test
    public void emptyPreambleFailure() {
        assertParseFailure(parser, emptyPreAmble,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskEditCommand.MESSAGE_USAGE));
    }

    @Test
    public void wrongPreambleFailure() {
        assertParseFailure(parser, invalidPreAmbleCodeOnly + PREFIX_DESCRIPTION + " meh",
                TaskNumManager.MESSAGE_USAGE_CONSTRAINTS);

        assertParseFailure(parser, invalidPreAmbleNumOnly + PREFIX_DESCRIPTION + " meh",
                ModuleCode.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void atNonHasNoFailsFailure() {
        assertParseFailure(parser, validPreamble + PREFIX_ON + " non " + PREFIX_AT + validTime,
                TaskEditCommand.MESSAGE_NON_HAS_NO_TAILS);

        assertParseFailure(parser, validPreamble + PREFIX_AT + validTime,
                MESSAGE_AT_WITHOUT_ON_ERROR);
    }

    @Test
    public void tooManyArgsFailure() {
        assertParseFailure(parser,
                validPreamble + PREFIX_DESCRIPTION + " meh meh " + PREFIX_AT + validTime
                        + PREFIX_DESCRIPTION + " moah",
                String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_DESCRIPTION));

        assertParseFailure(parser,
                validPreamble + PREFIX_AT + validTime
                        + PREFIX_AT + " moah",
                String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_AT));

        assertParseFailure(parser,
                validPreamble + PREFIX_ON + " meh meh " + PREFIX_ON + validTime,
                String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_ON));
    }
}
