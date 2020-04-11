package seedu.zerotoone.logic.parser.log;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.zerotoone.commons.core.Messages;
import seedu.zerotoone.commons.core.index.Index;
import seedu.zerotoone.logic.commands.AboutCommand;
import seedu.zerotoone.logic.commands.Command;
import seedu.zerotoone.logic.commands.log.DeleteCommand;
import seedu.zerotoone.logic.commands.log.FindCommand;
import seedu.zerotoone.logic.commands.log.ListCommand;
import seedu.zerotoone.logic.parser.exceptions.ParseException;
import seedu.zerotoone.model.workout.WorkoutName;

class LogCommandParserTest {
    private LogCommandParser parser = new LogCommandParser();

    @Test
    public void parse_invalidInput_throwsParseException() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AboutCommand.MESSAGE_USAGE);

        Exception exceptionThrown = assertThrows(ParseException.class, () -> parser.parse(""));
        assertEquals(expectedMessage, exceptionThrown.getMessage());
    }

    @Test
    public void parse_unknownCommand_throwsParseException() {
        String expectedMessage = Messages.MESSAGE_UNKNOWN_COMMAND;
        Exception exceptionThrown = assertThrows(ParseException.class, () -> parser.parse("unKnownComand"));
        assertEquals(expectedMessage, exceptionThrown.getMessage());
    }

    @Test
    public void parse_validListCommand_returnsListCommand() throws ParseException {
        Command expectedCommand = new ListCommand();

        String validInput = "list";
        Command actualCommand = parser.parse(validInput);
        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_validDeleteCommand_returnsDeleteCommand() throws ParseException {
        Command expectedCommand = new DeleteCommand(Index.fromOneBased(1));

        String validInput = "delete 1";
        Command actualCommand = parser.parse(validInput);
        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_validFindCommand_returnsFindCommand() throws ParseException {
        Command expectedCommand = new FindCommand(
            Optional.empty(),
            Optional.empty(),
            Optional.of(new WorkoutName("press")));

        String validInput = "find w/press";
        Command actualCommand = parser.parse(validInput);
        assertEquals(expectedCommand, actualCommand);
    }

}
