//package seedu.address.logic.parser;
//
//import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//
//import static seedu.address.logic.commands.CommandTestUtil.DAY_DESC_MON;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_DAY_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_ENDTIME_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_STARTTIME_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_END;
//import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_START;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_DAY_MON;
//
//import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
//import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
//import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CLIENT;
//
//import java.util.ArrayList;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.address.commons.core.index.Index;
//import seedu.address.logic.commands.FindCommand;
//import seedu.address.logic.commands.ScheduleCommand;
//import seedu.address.model.schedule.Day;
//import seedu.address.model.schedule.EndTime;
//import seedu.address.model.schedule.Schedule;
//import seedu.address.model.schedule.StartTime;
//
//public class ScheduleCommandParserTest {
//
//    private static final String MESSAGE_INVALID_FORMAT =
//            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE);
//
//    private ScheduleCommandParser parser = new ScheduleCommandParser();
//
//    @Test
//    public void parse_emptyArg_throwsParseException() {
//        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
//    }
//
//    @Test
//    public void parse_missingParts_failure() {
//        // no index specified
//        assertParseFailure(parser, VALID_DAY_MON, MESSAGE_INVALID_FORMAT);
//
//        // no index and no field specified
//        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
//
//    }
//
//    @Test
//    public void parse_allFieldsSpecified_success() {
//        Index targetIndex = INDEX_SECOND_CLIENT;
//        String userInput = targetIndex.getOneBased() + DAY_DESC_MON + TIME_DESC_START + TIME_DESC_END;
//
//        ArrayList<Schedule> scheduleArrayList = new ArrayList<Schedule>();
//        scheduleArrayList.add(Schedule.stringToSchedule("day/mon st/1200 et/1400"));
//        ScheduleCommand expectedCommand = new ScheduleCommand(targetIndex, scheduleArrayList);
//        assertParseSuccess(parser, userInput, expectedCommand);
//    }
//
//    @Test
//    public void parse_missingOptionalFields_success() {
//        Index targetIndex = INDEX_SECOND_CLIENT;
//        String userInput = "" + targetIndex.getOneBased();
//
//        ArrayList<Schedule> scheduleArrayList = new ArrayList<Schedule>();
//        ScheduleCommand expectedCommand = new ScheduleCommand(targetIndex, scheduleArrayList);
//        assertParseSuccess(parser, userInput, expectedCommand);
//    }
//
//    @Test
//    public void parse_invalidValue_failure() {
//        assertParseFailure(parser, "1" + INVALID_DAY_DESC, Day.MESSAGE_CONSTRAINTS); // invalid day
//        assertParseFailure(parser, "1" + INVALID_STARTTIME_DESC, StartTime.MESSAGE_CONSTRAINTS); // invalid start time
//        assertParseFailure(parser, "1" + INVALID_ENDTIME_DESC, EndTime.MESSAGE_CONSTRAINTS); // invalid end time
//    }
//}
