//@@author yapdianhao
package seedu.jelphabot.logic.parser;

import static seedu.jelphabot.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jelphabot.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.jelphabot.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.jelphabot.testutil.TypicalIndexes.INDEX_FIRST_TASK;

import org.junit.jupiter.api.Test;

import seedu.jelphabot.logic.commands.DeleteReminderCommand;

public class DeleteReminderCommandParserTest {

    private DeleteReminderCommandParser parser = new DeleteReminderCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(
            parser, "  ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteReminderCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnDeleteReminderCommand() {
        assertParseSuccess(parser, "1", new DeleteReminderCommand(INDEX_FIRST_TASK));
        assertParseSuccess(parser, "   1", new DeleteReminderCommand(INDEX_FIRST_TASK));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteReminderCommand.MESSAGE_USAGE));
    }
}
