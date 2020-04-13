//@@author yapdianhao
package seedu.jelphabot.logic.parser;

import static seedu.jelphabot.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jelphabot.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.jelphabot.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.jelphabot.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.jelphabot.testutil.TypicalReminders.ASSESSMENT_REMINDER;

import org.junit.jupiter.api.Test;

import seedu.jelphabot.logic.commands.ReminderCommand;

public class ReminderCommandParserTest {

    private ReminderCommandParser parser = new ReminderCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(
            parser, " ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArg_throwsParseException() {
        ReminderCommand reminderCommand = new ReminderCommand(INDEX_FIRST_TASK, ASSESSMENT_REMINDER);
        assertParseSuccess(parser, "1 days/1 hours/1", reminderCommand);
        assertParseSuccess(parser, "   1  days/ 1 \n hours/  1 \t", reminderCommand);
    }

}
