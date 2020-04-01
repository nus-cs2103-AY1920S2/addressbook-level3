package seedu.expensela.logic.parser;

import static seedu.expensela.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.expensela.logic.commands.CommandTestUtil.AMOUNT_DESC_AIRPODS;
import static seedu.expensela.logic.commands.CommandTestUtil.AMOUNT_DESC_PIZZA;
import static seedu.expensela.logic.commands.CommandTestUtil.CATEGORY_DESC_FOOD;
import static seedu.expensela.logic.commands.CommandTestUtil.CATEGORY_DESC_SHOPPING;
import static seedu.expensela.logic.commands.CommandTestUtil.DATE_DESC_AIRPODS;
import static seedu.expensela.logic.commands.CommandTestUtil.DATE_DESC_PIZZA;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.expensela.logic.commands.CommandTestUtil.NAME_DESC_PIZZA;
import static seedu.expensela.logic.commands.CommandTestUtil.REMARK_DESC_AIRPODS;
import static seedu.expensela.logic.commands.CommandTestUtil.REMARK_DESC_PIZZA;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_AMOUNT_AIRPODS;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_AMOUNT_PIZZA;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_CATEGORY_FOOD;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_CATEGORY_SHOPPING;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_DATE_AIRPODS;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_DATE_PIZZA;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_NAME_PIZZA;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_REMARK_AIRPODS;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_REMARK_PIZZA;
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
import seedu.expensela.model.transaction.Category;
import seedu.expensela.model.transaction.Date;
import seedu.expensela.model.transaction.Name;
import seedu.expensela.testutil.EditTransactionDescriptorBuilder;

public class EditCommandParserTest {

    //private static final String CATEGORY_EMPTY = " " + PREFIX_CATEGORY;

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
        assertParseFailure(parser, "1 i/ string", EditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_AMOUNT_DESC, Amount.MESSAGE_CONSTRAINTS); // invalid amount
        assertParseFailure(parser, "1" + INVALID_DATE_DESC, Date.MESSAGE_CONSTRAINTS); // invalid date
        assertParseFailure(parser, "1" + INVALID_CATEGORY_DESC, Category.MESSAGE_CONSTRAINTS); // invalid category

        // invalid amount followed by valid date
        assertParseFailure(parser, "1" + INVALID_AMOUNT_DESC + DATE_DESC_PIZZA, Amount.MESSAGE_CONSTRAINTS);

        // valid amount followed by invalid amount. The test case for invalid amount followed by valid amount
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + AMOUNT_DESC_AIRPODS + INVALID_AMOUNT_DESC,
                Amount.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_AMOUNT_DESC + VALID_AMOUNT_PIZZA
                        + VALID_AMOUNT_PIZZA + VALID_REMARK_PIZZA + VALID_CATEGORY_FOOD, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_TRANSACTION;
        String userInput = targetIndex.getOneBased() + AMOUNT_DESC_PIZZA + DATE_DESC_PIZZA + NAME_DESC_PIZZA
                + CATEGORY_DESC_FOOD + REMARK_DESC_PIZZA;

        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder().withName(VALID_NAME_PIZZA)
                .withAmount(VALID_AMOUNT_PIZZA).withDate(VALID_DATE_PIZZA)
                .withRemark(VALID_REMARK_PIZZA).withCategory(VALID_CATEGORY_FOOD)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_TRANSACTION;
        String userInput = targetIndex.getOneBased() + AMOUNT_DESC_AIRPODS + DATE_DESC_PIZZA;

        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder().withAmount(VALID_AMOUNT_AIRPODS)
                .withDate(VALID_DATE_PIZZA)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_TRANSACTION;
        String userInput = targetIndex.getOneBased() + NAME_DESC_PIZZA;
        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder()
                .withName(VALID_NAME_PIZZA)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // amount
        userInput = targetIndex.getOneBased() + AMOUNT_DESC_PIZZA;
        descriptor = new EditTransactionDescriptorBuilder().withAmount(VALID_AMOUNT_PIZZA).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // date
        userInput = targetIndex.getOneBased() + DATE_DESC_PIZZA;
        descriptor = new EditTransactionDescriptorBuilder().withDate(VALID_DATE_PIZZA).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // remark
        userInput = targetIndex.getOneBased() + REMARK_DESC_PIZZA;
        descriptor = new EditTransactionDescriptorBuilder().withRemark(VALID_REMARK_PIZZA).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // category
        userInput = targetIndex.getOneBased() + CATEGORY_DESC_FOOD;
        descriptor = new EditTransactionDescriptorBuilder().withCategory(VALID_CATEGORY_FOOD).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_TRANSACTION;
        String userInput = targetIndex.getOneBased() + AMOUNT_DESC_PIZZA + DATE_DESC_PIZZA + REMARK_DESC_PIZZA
                + CATEGORY_DESC_FOOD + AMOUNT_DESC_PIZZA + DATE_DESC_PIZZA + REMARK_DESC_PIZZA + CATEGORY_DESC_FOOD
                + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING;

        EditCommand.EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder()
                .withAmount(VALID_AMOUNT_AIRPODS).withDate(VALID_DATE_AIRPODS)
                .withRemark(VALID_REMARK_AIRPODS).withCategory(VALID_CATEGORY_SHOPPING)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_TRANSACTION;
        String userInput = targetIndex.getOneBased() + INVALID_AMOUNT_DESC + AMOUNT_DESC_AIRPODS;
        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder()
                .withAmount(VALID_AMOUNT_AIRPODS)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + INVALID_AMOUNT_DESC + DATE_DESC_AIRPODS + REMARK_DESC_AIRPODS
                + CATEGORY_DESC_SHOPPING + AMOUNT_DESC_AIRPODS;
        descriptor = new EditTransactionDescriptorBuilder().withAmount(VALID_AMOUNT_AIRPODS)
                .withDate(VALID_DATE_AIRPODS).withRemark(VALID_REMARK_AIRPODS)
                .withCategory(VALID_CATEGORY_SHOPPING).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    /*
    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_TRANSACTION;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
    */
}
