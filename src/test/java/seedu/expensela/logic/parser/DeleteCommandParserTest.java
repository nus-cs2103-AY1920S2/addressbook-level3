package seedu.expensela.logic.parser;

import static seedu.expensela.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.expensela.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.expensela.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.expensela.testutil.TypicalIndexes.INDEX_EIGHTH_TRANSACTION;
import static seedu.expensela.testutil.TypicalIndexes.INDEX_ELEVENTH_TRANSACTION;
import static seedu.expensela.testutil.TypicalIndexes.INDEX_FIFTH_TRANSACTION;
import static seedu.expensela.testutil.TypicalIndexes.INDEX_FIRST_TRANSACTION;
import static seedu.expensela.testutil.TypicalIndexes.INDEX_FOURTH_TRANSACTION;
import static seedu.expensela.testutil.TypicalIndexes.INDEX_INFINITE_TRANSACTION;
import static seedu.expensela.testutil.TypicalIndexes.INDEX_NINTH_TRANSACTION;
import static seedu.expensela.testutil.TypicalIndexes.INDEX_SECOND_TRANSACTION;
import static seedu.expensela.testutil.TypicalIndexes.INDEX_SEVENTH_TRANSACTION;
import static seedu.expensela.testutil.TypicalIndexes.INDEX_SIXTH_TRANSACTION;
import static seedu.expensela.testutil.TypicalIndexes.INDEX_TENTH_TRANSACTION;
import static seedu.expensela.testutil.TypicalIndexes.INDEX_THIRD_TRANSACTION;
import static seedu.expensela.testutil.TypicalIndexes.INDEX_TWELFTH_TRANSACTION;

import org.junit.jupiter.api.Test;

import seedu.expensela.logic.commands.DeleteCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteCommand(INDEX_FIRST_TRANSACTION));
        assertParseSuccess(parser, "2", new DeleteCommand(INDEX_SECOND_TRANSACTION));
        assertParseSuccess(parser, "3", new DeleteCommand(INDEX_THIRD_TRANSACTION));
        assertParseSuccess(parser, "4", new DeleteCommand(INDEX_FOURTH_TRANSACTION));
        assertParseSuccess(parser, "5", new DeleteCommand(INDEX_FIFTH_TRANSACTION));
        assertParseSuccess(parser, "6", new DeleteCommand(INDEX_SIXTH_TRANSACTION));
        assertParseSuccess(parser, "7", new DeleteCommand(INDEX_SEVENTH_TRANSACTION));
        assertParseSuccess(parser, "8", new DeleteCommand(INDEX_EIGHTH_TRANSACTION));
        assertParseSuccess(parser, "9", new DeleteCommand(INDEX_NINTH_TRANSACTION));
        assertParseSuccess(parser, "10", new DeleteCommand(INDEX_TENTH_TRANSACTION));
        assertParseSuccess(parser, "11", new DeleteCommand(INDEX_ELEVENTH_TRANSACTION));
        assertParseSuccess(parser, "12", new DeleteCommand(INDEX_TWELFTH_TRANSACTION));
        assertParseSuccess(parser, "999999", new DeleteCommand(INDEX_INFINITE_TRANSACTION));

    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "(", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, ")", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "*", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "&", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "^", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "%", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "$", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "#", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "@", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "!", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "`", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "~", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, ".", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, ",", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "?", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, ":", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "{", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "}", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));

    }
}
