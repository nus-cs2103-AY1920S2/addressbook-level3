package seedu.nova.logic.parser.scparser;

import static seedu.nova.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.nova.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.nova.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.nova.logic.commands.sccommands.ScViewCommand;
import seedu.nova.logic.commands.sccommands.ScViewDayCommand;
import seedu.nova.logic.commands.sccommands.ScViewWeekCommand;


class ScViewCommandParserTest {

    private ScViewCommandParser parser = new ScViewCommandParser();

    private String testDateArgument = " t\\2020-01-01";
    private String testWeekArgument = " week i\\5";
    private String testWeekArgumentNoWeekWord = testWeekArgument.substring(5);
    private String testDateBothArgument = " i\\5 t\\2020-01-01";
    private String testWeekBothArgument = " week t\\2020-01-01 i\\5";

    private LocalDate testDate = LocalDate.parse(testDateArgument.substring(3));
    private int testWeek = Integer.parseInt(testWeekArgument.substring(8));


    @Test
    void parse_viewDayWithDate() {
        assertParseSuccess(parser, testDateArgument, new ScViewDayCommand(testDate));
    }

    @Test
    void parse_viewDayNoDate() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScViewCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_viewDayBothArguments() {
        assertParseFailure(parser, testDateBothArgument, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ScViewCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_viewWeekIndexPresent() {
        assertParseSuccess(parser, testWeekArgument, new ScViewWeekCommand(testWeek));
    }

    @Test
    void parse_viewWeekNoIndex() {
        assertParseFailure(parser, "week ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ScViewWeekCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_viewWeekNoWeekWord() {
        assertParseFailure(parser, testWeekArgumentNoWeekWord, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ScViewCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_viewWeekBothArguments() {
        assertParseFailure(parser, testWeekBothArgument, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ScViewWeekCommand.MESSAGE_USAGE));
    }
}
