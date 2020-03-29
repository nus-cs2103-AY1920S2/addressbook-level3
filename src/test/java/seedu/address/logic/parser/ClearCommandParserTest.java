package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_FORCE_CLEAR;
import static seedu.address.logic.parser.CliSyntax.FLAG_ORDER_BOOK;
import static seedu.address.logic.parser.CliSyntax.FLAG_RETURN_BOOK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.parser.exceptions.ParseException;

class ClearCommandParserTest {

    private ClearCommandParser parser = new ClearCommandParser();

    @Test
    public void parse_invalidFlag_throwsParseException() {

        assertParseFailure(parser, "-h", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ClearCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_withoutFlag_returnNull() {
        try {
            ClearCommand actualCommand = parser.parse("");
            assertNull(actualCommand.getFlags());
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_onlyForceClearFlag_returnCorrectCommand() {
        HashSet<String> flags = new HashSet<>();
        flags.add(FLAG_FORCE_CLEAR.toString());
        try {
            ClearCommand actualCommand = parser.parse(FLAG_FORCE_CLEAR.toString());
            ClearCommand expectedCommand = new ClearCommand(flags);

            assertEquals(actualCommand, expectedCommand);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_onlyReturnFlag_returnCorrectCommand() {
        HashSet<String> flags = new HashSet<>();
        flags.add(FLAG_RETURN_BOOK.toString());
        try {
            ClearCommand actualCommand = parser.parse(FLAG_RETURN_BOOK.toString());
            ClearCommand expectedCommand = new ClearCommand(flags);

            assertEquals(actualCommand, expectedCommand);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_onlyOrderFlag_returnCorrectCommand() {
        HashSet<String> flags = new HashSet<>();
        flags.add(FLAG_ORDER_BOOK.toString());
        try {
            ClearCommand actualCommand = parser.parse(FLAG_ORDER_BOOK.toString());
            ClearCommand expectedCommand = new ClearCommand(flags);

            assertEquals(actualCommand, expectedCommand);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_forceClearAndOrderFlags_returnCorrectCommand() {
        HashSet<String> flags = new HashSet<>();
        flags.add(FLAG_FORCE_CLEAR.toString());
        flags.add(FLAG_ORDER_BOOK.toString());
        try {
            ClearCommand actualCommand = parser.parse(FLAG_FORCE_CLEAR + " " + FLAG_ORDER_BOOK);
            ClearCommand expectedCommand = new ClearCommand(flags);

            assertEquals(actualCommand, expectedCommand);

            actualCommand = parser.parse(FLAG_ORDER_BOOK + " " + FLAG_FORCE_CLEAR);
            assertEquals(actualCommand, expectedCommand);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_forceClearAndReturnFlags_returnCorrectCommand() {
        HashSet<String> flags = new HashSet<>();
        flags.add(FLAG_FORCE_CLEAR.toString());
        flags.add(FLAG_RETURN_BOOK.toString());
        try {
            ClearCommand actualCommand = parser.parse(FLAG_FORCE_CLEAR + " " + FLAG_RETURN_BOOK);
            ClearCommand expectedCommand = new ClearCommand(flags);

            assertEquals(actualCommand, expectedCommand);

            actualCommand = parser.parse(FLAG_RETURN_BOOK + " " + FLAG_FORCE_CLEAR);
            assertEquals(actualCommand, expectedCommand);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_allThreeFlags_returnErrorMessage() {
        String input = FLAG_FORCE_CLEAR + " " + FLAG_RETURN_BOOK + " " + FLAG_ORDER_BOOK;
        assertParseFailure(parser, input, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ClearCommand.MESSAGE_USAGE));

        input = FLAG_RETURN_BOOK + " " + FLAG_FORCE_CLEAR + " " + FLAG_ORDER_BOOK;
        assertParseFailure(parser, input, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ClearCommand.MESSAGE_USAGE));

        input = FLAG_ORDER_BOOK + " " + FLAG_FORCE_CLEAR + " " + FLAG_RETURN_BOOK;
        assertParseFailure(parser, input, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ClearCommand.MESSAGE_USAGE));

        input = FLAG_ORDER_BOOK + " " + FLAG_RETURN_BOOK + " " + FLAG_FORCE_CLEAR;
        assertParseFailure(parser, input, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ClearCommand.MESSAGE_USAGE));

        input = FLAG_RETURN_BOOK + " " + FLAG_ORDER_BOOK + " " + FLAG_FORCE_CLEAR;
        assertParseFailure(parser, input, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ClearCommand.MESSAGE_USAGE));

        input = FLAG_FORCE_CLEAR + " " + FLAG_ORDER_BOOK + " " + FLAG_RETURN_BOOK;
        assertParseFailure(parser, input, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ClearCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidFormat_returnErrorMessage() {
        // Return flag cannot be with order flag
        String input = FLAG_ORDER_BOOK + " " + FLAG_RETURN_BOOK;
        assertParseFailure(parser, input, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ClearCommand.MESSAGE_USAGE));

        input = FLAG_RETURN_BOOK + " " + FLAG_ORDER_BOOK;
        assertParseFailure(parser, input, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ClearCommand.MESSAGE_USAGE));

        // Without space in between flags
        input = FLAG_RETURN_BOOK.toString() + FLAG_FORCE_CLEAR.toString();
        assertParseFailure(parser, input, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ClearCommand.MESSAGE_USAGE));

        //Duplicate flags
        input = FLAG_ORDER_BOOK + " " + FLAG_ORDER_BOOK;
        assertParseFailure(parser, input, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ClearCommand.MESSAGE_USAGE));
    }
}
