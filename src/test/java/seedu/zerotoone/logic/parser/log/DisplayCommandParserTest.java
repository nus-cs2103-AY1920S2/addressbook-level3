package seedu.zerotoone.logic.parser.log;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.zerotoone.commons.core.Messages;
import seedu.zerotoone.logic.commands.Command;
import seedu.zerotoone.logic.commands.log.DisplayCommand;
import seedu.zerotoone.logic.parser.exceptions.ParseException;

class DisplayCommandParserTest {
    private DisplayCommandParser parser = new DisplayCommandParser();

    @Test
    public void parse_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    public void parse_invalidArguments_throwsParseException() {
        Exception exceptionThrown;
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
            DisplayCommand.MESSAGE_USAGE);

        // Missing Prefix
        exceptionThrown = assertThrows(ParseException.class, () ->
            parser.parse("this"));
        assertEquals(expectedMessage, exceptionThrown.getMessage());

        // Invalid Prefix
        exceptionThrown = assertThrows(ParseException.class, () ->
            parser.parse(" f/something"));
        assertEquals(expectedMessage, exceptionThrown.getMessage());

        // Non Empty Preamble
        exceptionThrown = assertThrows(ParseException.class, () ->
            parser.parse("preamble e/search"));
        assertEquals(expectedMessage, exceptionThrown.getMessage());

        // Invalid Start Date
        exceptionThrown = assertThrows(ParseException.class, () ->
            parser.parse("st/invalid"));
        assertEquals(expectedMessage, exceptionThrown.getMessage());

        // Invalid End Date
        exceptionThrown = assertThrows(ParseException.class, () ->
            parser.parse("et/invalid"));
        assertEquals(expectedMessage, exceptionThrown.getMessage());
    }

    @Test
    public void parse_validArguments_returnsFindCommand() throws ParseException {
        LocalDateTime now = LocalDateTime.now();
        Command exceptedCommand = new DisplayCommand(
            Optional.of(now),
            Optional.of(now));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String dateFormatted = now.format(formatter);

        assertEquals(exceptedCommand, parser.parse(
            String.format(" st/%s et/%s", dateFormatted, dateFormatted)));
    }

}
