package seedu.address.logic.parser.task;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_ARGUMENTS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_DAY_OUT_OF_BOUNDS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_DAY_STRING;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_MONTH_OUT_OF_BOUNDS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_MONTH_STRING;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_YEAR_STRING;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TASK_CMD_DAY_26;
import static seedu.address.logic.commands.CommandTestUtil.TASK_CMD_MONTH_03;
import static seedu.address.logic.commands.CommandTestUtil.TASK_CMD_YEAR_2020;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DAY_26;
import static seedu.address.logic.commands.CommandTestUtil.TASK_MONTH_03;
import static seedu.address.logic.commands.CommandTestUtil.TASK_YEAR_2020;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.task.TaskSearchCommandParser.DATE_STRING;
import static seedu.address.logic.parser.task.TaskSearchCommandParser.MONTH_STRING;
import static seedu.address.logic.parser.task.TaskSearchCommandParser.YEAR_STRING;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.task.TaskSearchCommand;
import seedu.address.model.task.TaskSearchPredicate;

public class TaskSearchCommandParserTest {
    private static String wrongSyntaxMessage =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskSearchCommand.MESSAGE_USAGE);
    private static String invalidParametersMessage =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    TaskSearchCommand.MESSAGE_INVALID_DAY_MONTH_YEAR);
    private static String outOfBoundsMessage =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    TaskSearchCommand.MESSAGE_OUT_OF_BOUNDS_VALUES);

    private TaskSearchCommandParser parser = new TaskSearchCommandParser();

    @Test
    public void parse_withPreamble_success() {
        // arbitrary preamble with parameters supplied
        assertParseSuccess(parser, PREAMBLE_NON_EMPTY + TASK_CMD_DAY_26 + TASK_CMD_YEAR_2020,
                new TaskSearchCommand(new TaskSearchPredicate(new HashMap<String, Integer>() {
                    {
                        put(DATE_STRING, Integer.parseInt(TASK_DAY_26));
                        put(YEAR_STRING, Integer.parseInt(TASK_YEAR_2020));
                    }
                })));
        // the rest of the methods below will not test the presence of Preamble
        // since we already tested it here.
    }

    @Test
    public void parse_allFieldsMissing_failure() {
        // no preamble, empty HashMap (no day/month/year supplied)
        assertParseFailure(parser, EMPTY_ARGUMENTS, wrongSyntaxMessage);
    }

    @Test
    public void parse_someFieldsPresentAndHaveValidValues_success() {
        // apply heuristic 'each valid input at Least Once in a Positive Test Case'

        // one and only valid field, year only
        HashMap<String, Integer> year = new HashMap<String, Integer>() {
            {
                put(YEAR_STRING, Integer.parseInt(TASK_YEAR_2020));
            }
        };

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TASK_CMD_YEAR_2020,
                new TaskSearchCommand(new TaskSearchPredicate(year)));

        assertParseSuccess(parser, TASK_CMD_YEAR_2020,
                new TaskSearchCommand(new TaskSearchPredicate(year)));

        // two and only two valid fields, day and month
        HashMap<String, Integer> dayMonth = new HashMap<String, Integer>() {
            {
                put(DATE_STRING, Integer.parseInt(TASK_DAY_26));
                put(MONTH_STRING, Integer.parseInt(TASK_MONTH_03));
            }
        };

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TASK_CMD_DAY_26 + TASK_CMD_MONTH_03,
                new TaskSearchCommand(new TaskSearchPredicate(dayMonth)));

        assertParseSuccess(parser, TASK_CMD_DAY_26 + TASK_CMD_MONTH_03,
                new TaskSearchCommand(new TaskSearchPredicate(dayMonth)));
    }

    @Test
    public void parse_allFieldsPresentAndHaveValidValues_success() {
        HashMap<String, Integer> fullHashMap = new HashMap<String, Integer>() {
            {
                put(DATE_STRING, Integer.parseInt(TASK_DAY_26));
                put(MONTH_STRING, Integer.parseInt(TASK_MONTH_03));
                put(YEAR_STRING, Integer.parseInt(TASK_YEAR_2020));
            }
        };

        // no preamble, full HashMap (all of day/month/year supplied)
        assertParseSuccess(parser, TASK_CMD_DAY_26 + TASK_CMD_MONTH_03 + TASK_CMD_YEAR_2020,
                new TaskSearchCommand(new TaskSearchPredicate(fullHashMap)));
    }

    @Test
    public void parse_fieldsArePresentButValuesOutOfBound_failure() {
        assertParseFailure(parser, INVALID_TASK_DAY_OUT_OF_BOUNDS + INVALID_TASK_MONTH_OUT_OF_BOUNDS,
                outOfBoundsMessage);
    }

    @Test
    public void parse_fieldsArePresentButAtLeastOneFieldHaveStringInput_failure() {
        // apply heuristic ‘no more than one invalid input in a test case’

        // invalid field is day
        assertParseFailure(parser, INVALID_TASK_DAY_STRING + TASK_CMD_MONTH_03 + TASK_CMD_YEAR_2020,
                invalidParametersMessage);

        // invalid field is month
        assertParseFailure(parser, TASK_CMD_DAY_26 + INVALID_TASK_MONTH_STRING + TASK_CMD_YEAR_2020,
                invalidParametersMessage);

        // invalid field is year
        assertParseFailure(parser, TASK_CMD_DAY_26 + TASK_CMD_MONTH_03 + INVALID_TASK_YEAR_STRING,
                invalidParametersMessage);
    }
}
