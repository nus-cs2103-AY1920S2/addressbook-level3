package seedu.address.logic.parser.reminder;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIME_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_INPUT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.reminders.EditReminderCommand;
import seedu.address.logic.commands.reminders.EditReminderCommand.EditReminderDescriptor;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.reminders.EditReminderCommandParser;
import seedu.address.model.reminder.Description;
import seedu.address.testutil.EditReminderDescriptorBuilder;

public class EditReminderCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditReminderCommand.MESSAGE_USAGE);

    private EditReminderCommandParser parser = new EditReminderCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_DESCRIPTION1, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditReminderCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-2" + VALID_DESCRIPTION_INPUT, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + VALID_DESCRIPTION_INPUT, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION_INPUT,
                Description.MESSAGE_CONSTRAINTS); // invalid description
        assertParseFailure(parser, "1" + INVALID_DATE_INPUT, ParserUtil.MESSAGE_INVALID_DATE); // invalid date
        assertParseFailure(parser, "1" + INVALID_TIME_INPUT, ParserUtil.MESSAGE_INVALID_TIME); // invalid time

        // invalid date followed by valid time
        assertParseFailure(parser, "1" + INVALID_DATE_INPUT + VALID_TIME_INPUT,
                ParserUtil.MESSAGE_INVALID_DATE);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION_INPUT + INVALID_DATE_INPUT + INVALID_TIME_INPUT,
                Description.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + VALID_DESCRIPTION_INPUT + VALID_DATE_INPUT
                + VALID_TIME_INPUT;
        EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder().withDescription(VALID_DESCRIPTION1)
                .withDate(VALID_DATE1).withTime(VALID_TIME1).build();
        EditReminderCommand expectedCommand = new EditReminderCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + VALID_DATE_INPUT + VALID_TIME_INPUT;

        EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder().withDate(VALID_DATE1)
                .withTime(VALID_TIME1).build();
        EditReminderCommand expectedCommand = new EditReminderCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // description
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + VALID_DESCRIPTION_INPUT;
        EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION1).build();
        EditReminderCommand expectedCommand = new EditReminderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // date
        userInput = targetIndex.getOneBased() + VALID_DATE_INPUT;
        descriptor = new EditReminderDescriptorBuilder().withDate(VALID_DATE1).build();
        expectedCommand = new EditReminderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // time
        userInput = targetIndex.getOneBased() + VALID_TIME_INPUT;
        descriptor = new EditReminderDescriptorBuilder().withTime(VALID_TIME1).build();
        expectedCommand = new EditReminderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
