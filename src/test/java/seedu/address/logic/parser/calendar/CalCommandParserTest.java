package seedu.address.logic.parser.calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_CALENDAR_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.calendar.CalFindCommand;
import seedu.address.logic.commands.calendar.CalViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.calendar.Calendar;

public class CalCommandParserTest {
    private CalCommandParser parser = new CalCommandParser();

    @Test
    public void parse_view() throws Exception {
        CalViewCommand thisCalViewCommand = (CalViewCommand) parser.parse(Command.COMMAND_WORD_VIEW + " "
            + PREFIX_WEEK + " " + "this");
        CalViewCommand nextCalViewCommand = (CalViewCommand) parser.parse(Command.COMMAND_WORD_VIEW + " "
                + PREFIX_WEEK + " " + "next");

        assertEquals(new CalViewCommand(Calendar.getNowCalendar()), thisCalViewCommand);
        assertEquals(new CalViewCommand(Calendar.getNextWeekCalendar()), nextCalViewCommand);
    }

    @Test
    public void parse_find() throws Exception {
        CalFindCommand calFindCommand = (CalFindCommand) parser.parse(Command.COMMAND_WORD_FIND + " empty");
        assertEquals(new CalFindCommand(), calFindCommand);
    }

    @Test
    public void parse_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_UNKNOWN_CALENDAR_COMMAND,
                HelpCommand.MESSAGE_USAGE), () -> parser.parse(""));
    }

    @Test
    public void parse_unknownCalendarCommand_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_UNKNOWN_CALENDAR_COMMAND,
                HelpCommand.MESSAGE_USAGE), () -> parser.parse("cal unknownCommand"));
    }
}
