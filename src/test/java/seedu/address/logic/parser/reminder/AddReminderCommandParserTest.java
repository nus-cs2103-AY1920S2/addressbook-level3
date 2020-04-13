package seedu.address.logic.parser.reminder;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIME_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_INPUT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_DATE;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_TIME;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.reminders.AddReminderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.reminders.AddReminderCommandParser;
import seedu.address.model.reminder.Description;
import seedu.address.model.reminder.Reminder;
import seedu.address.testutil.ReminderBuilder;

public class AddReminderCommandParserTest {
    private AddReminderCommandParser parser = new AddReminderCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        Reminder expectedReminder = new ReminderBuilder().build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_DESCRIPTION_INPUT + VALID_DATE_INPUT
                + VALID_TIME_INPUT, new AddReminderCommand(expectedReminder));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminderCommand.MESSAGE_USAGE);

        // missing description prefix
        assertParseFailure(parser, VALID_DESCRIPTION1 + VALID_DATE_INPUT + VALID_TIME_INPUT, expectedMessage);

        // missing date prefix
        assertParseFailure(parser, VALID_DESCRIPTION_INPUT + VALID_DATE1 + VALID_TIME_INPUT, expectedMessage);

        // missing time prefix
        assertParseFailure(parser, VALID_DESCRIPTION_INPUT + VALID_DATE_INPUT + VALID_TIME1, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_DESCRIPTION1 + VALID_DATE1 + VALID_TIME1, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid description
        assertParseFailure(parser, INVALID_DESCRIPTION_INPUT + VALID_DATE_INPUT
                + VALID_TIME_INPUT, Description.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, VALID_DESCRIPTION_INPUT + INVALID_DATE_INPUT
                + VALID_TIME_INPUT, MESSAGE_INVALID_DATE);

        // invalid time
        assertParseFailure(parser, VALID_DESCRIPTION_INPUT + VALID_DATE_INPUT
                + INVALID_TIME_INPUT, MESSAGE_INVALID_TIME);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_DESCRIPTION_INPUT + INVALID_DATE_INPUT
                + VALID_TIME_INPUT, Description.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + VALID_DESCRIPTION_INPUT
                        + VALID_DATE_INPUT + VALID_TIME_INPUT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminderCommand.MESSAGE_USAGE));
    }
}
