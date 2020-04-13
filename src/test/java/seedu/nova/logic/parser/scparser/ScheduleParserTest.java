package seedu.nova.logic.parser.scparser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.nova.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.nova.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.nova.logic.commands.sccommands.ScViewCommand;
import seedu.nova.logic.commands.sccommands.ScViewDayCommand;
import seedu.nova.logic.commands.sccommands.ScViewWeekCommand;
import seedu.nova.logic.parser.exceptions.ParseException;

class ScheduleParserTest {

    private final ScheduleParser parser = new ScheduleParser();
    private final String testDate = " t\\2020-01-01";
    private final String testWeek = " week i\\5";

    @Test
    void parseCommand_viewDay() throws Exception {

        ScViewDayCommand command = (ScViewDayCommand) parser.parseCommand(ScViewCommand.COMMAND_WORD, testDate);
        assertEquals(new ScViewDayCommand(LocalDate.of(2020, 1, 1)), command);
    }

    @Test
    void parseCommand_viewWeek() throws Exception {

        ScViewWeekCommand command = (ScViewWeekCommand) parser.parseCommand(ScViewWeekCommand.COMMAND_WORD, testWeek);
        assertEquals(new ScViewWeekCommand(5), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("", ""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand", ""));
    }

}
