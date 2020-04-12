package seedu.eylah.expensesplitter.logic.parser;

import static seedu.eylah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eylah.expensesplitter.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.eylah.expensesplitter.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.eylah.commons.core.index.Index;
import seedu.eylah.commons.logic.parser.exception.ParseException;
import seedu.eylah.expensesplitter.logic.commands.PaidCommand;

public class PaidCommandParserTest {

    private PaidCommandParser parser = new PaidCommandParser();

    @Test
    public void parse_validArgsFullAmount_returnsPaidCommand() throws ParseException {
        Index index = ParserUtil.parseIndex("1");
        assertParseSuccess(parser, "1" , new PaidCommand(index , "all"));
    }

    @Test
    public void parse_validArgsNotFullAmount_returnsPaidCommand() throws ParseException {
        Index index = ParserUtil.parseIndex("1");
        assertParseSuccess(parser, "1 12.50" , new PaidCommand(index , "12.50"));
    }


    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser , "a",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT , PaidCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_compulsoryMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, PaidCommand.MESSAGE_USAGE);

        assertParseFailure(parser, null + "1",
            expectedMessage);
    }


}
