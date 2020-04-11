package seedu.zerotoone.logic.parser.log;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.zerotoone.commons.core.Messages;
import seedu.zerotoone.logic.commands.Command;
import seedu.zerotoone.logic.commands.log.FindCommand;
import seedu.zerotoone.logic.parser.exceptions.ParseException;
import seedu.zerotoone.model.workout.WorkoutName;

class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    public void parse_invalidArguments_throwsParseException() {
        Exception exceptionThrown;
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
            FindCommand.MESSAGE_USAGE);

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
        String workoutName = "My Workout";
        LocalDateTime now = LocalDateTime.now();
        Command exceptedCommand = new FindCommand(
            Optional.of(now),
            Optional.of(now),
            Optional.of(new WorkoutName(workoutName)));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String dateFormatted = now.format(formatter);

        assertEquals(exceptedCommand, parser.parse(
            String.format(" w/%s st/%s et/%s", workoutName, dateFormatted, dateFormatted)));

        // Has Spaces Inside
        assertEquals(exceptedCommand, parser.parse(
            String.format(" w/      %s         st/%s et/%s", workoutName, dateFormatted, dateFormatted)));
    }

}
