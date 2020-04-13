package seedu.address.logic.parser.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_TASK_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_ARGUMENTS;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_CODE_DESC_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.TASK_CMD_DAY_26;
import static seedu.address.logic.commands.CommandTestUtil.TASK_CMD_MONTH_03;
import static seedu.address.logic.commands.CommandTestUtil.TASK_CMD_YEAR_2020;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DAY_26;
import static seedu.address.logic.commands.CommandTestUtil.TASK_ID_DESC_VALID_ID;
import static seedu.address.logic.commands.CommandTestUtil.TASK_MONTH_03;
import static seedu.address.logic.commands.CommandTestUtil.TASK_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.TASK_YEAR_2020;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FIND_WORDS_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FIND_WORDS_HOMEWORK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FIND_WORDS_PROGRAMMING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_ID_FIRST;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.task.TaskFindCommand;
import seedu.address.logic.commands.task.TaskForOneModuleCommand;
import seedu.address.logic.commands.task.TaskListCommand;
import seedu.address.logic.commands.task.TaskListUnDoneCommand;
import seedu.address.logic.commands.task.TaskMarkAsDoneCommand;
import seedu.address.logic.commands.task.TaskSearchCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.TaskContainsInModulePredicate;
import seedu.address.model.task.TaskContainsKeywordsPredicate;
import seedu.address.model.task.TaskSearchPredicate;

public class TaskCommandParserTest {
    private TaskCommandParser parser = new TaskCommandParser();

    @Test
    public void parse_list() throws Exception {
        assertTrue(parser.parse(Command.COMMAND_WORD_LIST) instanceof TaskListCommand);
    }

    @Test
    public void parse_find() throws Exception {
        List<String> keywords = Arrays.asList(VALID_FIND_WORDS_PROGRAMMING,
                VALID_FIND_WORDS_ASSIGNMENT,
                VALID_FIND_WORDS_HOMEWORK);
        TaskFindCommand command = (TaskFindCommand) parser.parse(Command.COMMAND_WORD_FIND + " "
                + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new TaskFindCommand(new TaskContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parse_listTaskForMod() throws Exception {
        TaskForOneModuleCommand command = (TaskForOneModuleCommand) parser.parse(Command.COMMAND_WORD_MODULE
                + MODULE_CODE_DESC_CS2103T);
        assertEquals(new TaskForOneModuleCommand(
                new TaskContainsInModulePredicate(VALID_MODULE_CODE_CS2103T)), command);
    }

    @Test
    public void parse_mark_done() throws Exception {
        TaskMarkAsDoneCommand command = (TaskMarkAsDoneCommand) parser.parse(Command.COMMAND_WORD_DONE + " "
                + MODULE_CODE_DESC_CS2103T + TASK_ID_DESC_VALID_ID);
        assertEquals(new TaskMarkAsDoneCommand(VALID_MODULE_CODE_CS2103T, VALID_TASK_ID_FIRST), command);
    }

    @Test
    public void parse_seeUndoneTasks() throws Exception {
        assertTrue(parser.parse(Command.COMMAND_WORD_UNDONE) instanceof TaskListUnDoneCommand);
    }

    @Test
    public void parse_search() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        TaskSearchCommand command = (TaskSearchCommand) parser.parse(Command.COMMAND_WORD_SEARCH
                + TASK_CMD_DAY_26 + TASK_CMD_MONTH_03 + TASK_CMD_YEAR_2020);
        HashMap<String, Integer> filters = new HashMap<String, Integer>() {
            {
                put("date", Integer.parseInt(TASK_DAY_26));
                put("month", Integer.parseInt(TASK_MONTH_03));
                put("year", Integer.parseInt(TASK_YEAR_2020));
            }
        };
        assertEquals(new TaskSearchCommand(new TaskSearchPredicate(filters)), command);
    }

    @Test
    public void parse_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_UNKNOWN_TASK_COMMAND,
                HelpCommand.MESSAGE_USAGE), () -> parser.parse(EMPTY_ARGUMENTS));
    }

    @Test
    public void parse_unknownTaskCommand_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_UNKNOWN_TASK_COMMAND,
                HelpCommand.MESSAGE_USAGE), () -> parser.parse(TASK_UNKNOWN_COMMAND));
    }
}
