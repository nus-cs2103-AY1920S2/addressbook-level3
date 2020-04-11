package seedu.eylah.expensesplitter.logic.parser;

import static seedu.eylah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eylah.expensesplitter.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.eylah.expensesplitter.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.eylah.commons.core.index.Index;
import seedu.eylah.commons.logic.parser.exception.ParseException;
import seedu.eylah.expensesplitter.logic.commands.DeleteItemCommand;

public class DeleteItemCommandParserTest {

    private DeleteItemCommandParser parser = new DeleteItemCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteItemCommand() throws ParseException {
        Index index = ParserUtil.parseIndex("1");
        assertParseSuccess(parser, "1", new DeleteItemCommand(index));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteItemCommand.MESSAGE_USAGE));
    }
}
