package seedu.expensela.logic.parser;

import static seedu.expensela.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.expensela.logic.commands.CommandTestUtil.ADDRESS_DESC_PIZZA;
import static seedu.expensela.logic.commands.CommandTestUtil.ADDRESS_DESC_AIRPODS;
import static seedu.expensela.logic.commands.CommandTestUtil.EMAIL_DESC_PIZZA;
import static seedu.expensela.logic.commands.CommandTestUtil.EMAIL_DESC_AIRPODS;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.expensela.logic.commands.CommandTestUtil.NAME_DESC_PIZZA;
import static seedu.expensela.logic.commands.CommandTestUtil.PHONE_DESC_PIZZA;
import static seedu.expensela.logic.commands.CommandTestUtil.PHONE_DESC_AIRPODS;
import static seedu.expensela.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.expensela.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_REMARK_PIZZA;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_REMARK_AIRPODS;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_NAME_PIZZA;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_AMOUNT_PIZZA;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_AMOUNT_AIRPODS;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.expensela.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.expensela.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.expensela.testutil.TypicalIndexes.INDEX_FIRST_TRANSACTION;
import static seedu.expensela.testutil.TypicalIndexes.INDEX_SECOND_TRANSACTION;
import static seedu.expensela.testutil.TypicalIndexes.INDEX_THIRD_TRANSACTION;

import org.junit.jupiter.api.Test;

import seedu.expensela.commons.core.index.Index;
import seedu.expensela.logic.commands.EditCommand;
import seedu.expensela.logic.commands.EditCommand.EditTransactionDescriptor;
import seedu.expensela.model.transaction.Amount;
import seedu.expensela.model.transaction.Date;
import seedu.expensela.model.transaction.Name;
import seedu.expensela.testutil.EditTransactionDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_PIZZA, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_PIZZA, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_PIZZA, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Amount.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC, Date.MESSAGE_CONSTRAINTS); // invalid address

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + EMAIL_DESC_PIZZA, Amount.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + PHONE_DESC_AIRPODS + INVALID_PHONE_DESC, Amount.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_REMARK_PIZZA
                        + VALID_AMOUNT_PIZZA, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_TRANSACTION;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_AIRPODS + TAG_DESC_HUSBAND
                + EMAIL_DESC_PIZZA + ADDRESS_DESC_PIZZA + NAME_DESC_PIZZA + TAG_DESC_FRIEND;

        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder().withName(VALID_NAME_PIZZA)
                .withPhone(VALID_AMOUNT_AIRPODS).withAddress(VALID_REMARK_PIZZA)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_TRANSACTION;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_AIRPODS + EMAIL_DESC_PIZZA;

        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder().withPhone(VALID_AMOUNT_AIRPODS)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_TRANSACTION;
        String userInput = targetIndex.getOneBased() + NAME_DESC_PIZZA;
        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder().withName(VALID_NAME_PIZZA).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_PIZZA;
        descriptor = new EditTransactionDescriptorBuilder().withPhone(VALID_AMOUNT_PIZZA).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_PIZZA;
        descriptor = new EditTransactionDescriptorBuilder().build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_PIZZA;
        descriptor = new EditTransactionDescriptorBuilder().withAddress(VALID_REMARK_PIZZA).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditTransactionDescriptorBuilder().build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_TRANSACTION;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_PIZZA + ADDRESS_DESC_PIZZA + EMAIL_DESC_PIZZA
                + TAG_DESC_FRIEND + PHONE_DESC_PIZZA + ADDRESS_DESC_PIZZA + EMAIL_DESC_PIZZA + TAG_DESC_FRIEND
                + PHONE_DESC_AIRPODS + ADDRESS_DESC_AIRPODS + EMAIL_DESC_AIRPODS + TAG_DESC_HUSBAND;

        EditCommand.EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder().withPhone(VALID_AMOUNT_AIRPODS)
                .withAddress(VALID_REMARK_AIRPODS)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_TRANSACTION;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_AIRPODS;
        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder().withPhone(VALID_AMOUNT_AIRPODS).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AIRPODS + INVALID_PHONE_DESC + ADDRESS_DESC_AIRPODS
                + PHONE_DESC_AIRPODS;
        descriptor = new EditTransactionDescriptorBuilder().withPhone(VALID_AMOUNT_AIRPODS)
                .withAddress(VALID_REMARK_AIRPODS).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_TRANSACTION;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
