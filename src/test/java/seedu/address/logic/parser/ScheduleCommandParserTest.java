package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DESC_INVALID_NUM_DAYS;
import static seedu.address.logic.commands.CommandTestUtil.DESC_VALID_NUM_DAYS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NUM_DAYS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ScheduleCommand;

public class ScheduleCommandParserTest {
    private ScheduleCommandParser parser = new ScheduleCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, DESC_VALID_NUM_DAYS, new ScheduleCommand(Integer.valueOf(VALID_NUM_DAYS)));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE);

        // missing num_days prefix
        assertParseFailure(parser, VALID_NUM_DAYS, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE);

        // invalid num_days
        assertParseFailure(parser, DESC_INVALID_NUM_DAYS, expectedMessage);
    }
}
