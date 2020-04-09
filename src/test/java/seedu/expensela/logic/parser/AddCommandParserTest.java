package seedu.expensela.logic.parser;

import static seedu.expensela.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.expensela.logic.commands.CommandTestUtil.AMOUNT_DESC_AIRPODS;
import static seedu.expensela.logic.commands.CommandTestUtil.AMOUNT_DESC_MAX_INVESTMENT;
import static seedu.expensela.logic.commands.CommandTestUtil.AMOUNT_DESC_MIN_INVESTMENT;
import static seedu.expensela.logic.commands.CommandTestUtil.AMOUNT_DESC_PIZZA;
import static seedu.expensela.logic.commands.CommandTestUtil.CATEGORY_DESC_FOOD;
import static seedu.expensela.logic.commands.CommandTestUtil.CATEGORY_DESC_INVESTMENT;
import static seedu.expensela.logic.commands.CommandTestUtil.CATEGORY_DESC_SHOPPING;
import static seedu.expensela.logic.commands.CommandTestUtil.DATE_DESC_AIRPODS;
import static seedu.expensela.logic.commands.CommandTestUtil.DATE_DESC_INVESTMENT;
import static seedu.expensela.logic.commands.CommandTestUtil.DATE_DESC_PIZZA;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_1;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_10;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_11;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_12;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_13;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_14;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_15;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_16;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_17;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_19;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_2;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_20;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_21;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_22;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_23;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_24;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_25;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_26;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_27;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_28;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_29;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_3;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_30;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_31;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_32;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_33;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_34;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_35;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_36;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_37;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_39;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_4;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_40;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_41;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_42;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_5;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_6;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_7;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_8;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC_9;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC_1;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC_2;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC_3;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC_4;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC_5;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC_6;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC_7;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC_8;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_DATE_DESC_1;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_DATE_DESC_10;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_DATE_DESC_11;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_DATE_DESC_12;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_DATE_DESC_2;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_DATE_DESC_3;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_DATE_DESC_4;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_DATE_DESC_5;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_DATE_DESC_6;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_DATE_DESC_9;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_NAME_DESC_1;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_NAME_DESC_10;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_NAME_DESC_11;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_NAME_DESC_12;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_NAME_DESC_13;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_NAME_DESC_14;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_NAME_DESC_15;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_NAME_DESC_16;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_NAME_DESC_17;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_NAME_DESC_18;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_NAME_DESC_19;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_NAME_DESC_2;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_NAME_DESC_20;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_NAME_DESC_3;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_NAME_DESC_4;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_NAME_DESC_5;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_NAME_DESC_6;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_NAME_DESC_7;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_NAME_DESC_8;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_NAME_DESC_9;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_REMARK_DESC;
import static seedu.expensela.logic.commands.CommandTestUtil.NAME_DESC_AIRPODS;
import static seedu.expensela.logic.commands.CommandTestUtil.NAME_DESC_INVESTMENT;
import static seedu.expensela.logic.commands.CommandTestUtil.NAME_DESC_PIZZA;
import static seedu.expensela.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.expensela.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.expensela.logic.commands.CommandTestUtil.REMARK_DESC_AIRPODS;
import static seedu.expensela.logic.commands.CommandTestUtil.REMARK_DESC_INVESTMENT;
import static seedu.expensela.logic.commands.CommandTestUtil.REMARK_DESC_PIZZA;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_AMOUNT_AIRPODS;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_NAME_AIRPODS;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_REMARK_AIRPODS;
import static seedu.expensela.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.expensela.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.expensela.testutil.TypicalTransactions.AIRPODS;
import static seedu.expensela.testutil.TypicalTransactions.MAX_INVESTMENT;
import static seedu.expensela.testutil.TypicalTransactions.MIN_INVESTMENT;

import org.junit.jupiter.api.Test;

import seedu.expensela.logic.commands.AddCommand;
import seedu.expensela.model.transaction.Amount;
import seedu.expensela.model.transaction.Category;
import seedu.expensela.model.transaction.Date;
import seedu.expensela.model.transaction.Name;
import seedu.expensela.model.transaction.Transaction;
import seedu.expensela.testutil.TransactionBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Transaction expectedTransaction = new TransactionBuilder(AIRPODS).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_AIRPODS + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, new AddCommand(expectedTransaction));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_PIZZA + NAME_DESC_AIRPODS + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, new AddCommand(expectedTransaction));

        // multiple amount - last amount accepted
        assertParseSuccess(parser, NAME_DESC_AIRPODS + AMOUNT_DESC_PIZZA + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, new AddCommand(expectedTransaction));

        // multiple date - last date accepted
        assertParseSuccess(parser, NAME_DESC_AIRPODS + AMOUNT_DESC_AIRPODS + DATE_DESC_PIZZA + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, new AddCommand(expectedTransaction));

        // multiple remark - last remark accepted
        assertParseSuccess(parser, NAME_DESC_AIRPODS + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS + REMARK_DESC_PIZZA
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, new AddCommand(expectedTransaction));

        // multiple tags - all accepted
        Transaction expectedTransactionMultipleTags = new TransactionBuilder(AIRPODS)
                .build();
        assertParseSuccess(parser, NAME_DESC_AIRPODS + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS + REMARK_DESC_AIRPODS
                + CATEGORY_DESC_FOOD + CATEGORY_DESC_SHOPPING, new AddCommand(expectedTransactionMultipleTags));

    }



    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, "add" + VALID_NAME_AIRPODS + AMOUNT_DESC_AIRPODS + REMARK_DESC_AIRPODS,
                expectedMessage);

        // missing amount prefix
        assertParseFailure(parser, "add" + NAME_DESC_AIRPODS + VALID_AMOUNT_AIRPODS + REMARK_DESC_AIRPODS,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser, "add" + NAME_DESC_AIRPODS + AMOUNT_DESC_AIRPODS + VALID_REMARK_AIRPODS,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, "add" + VALID_NAME_AIRPODS + VALID_AMOUNT_AIRPODS + VALID_REMARK_AIRPODS,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Name.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, INVALID_NAME_DESC_1 + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Name.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, INVALID_NAME_DESC_2 + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Name.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, INVALID_NAME_DESC_3 + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Name.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, INVALID_NAME_DESC_4 + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Name.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, INVALID_NAME_DESC_5 + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Name.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, INVALID_NAME_DESC_6 + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Name.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, INVALID_NAME_DESC_7 + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Name.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, INVALID_NAME_DESC_8 + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Name.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, INVALID_NAME_DESC_9 + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Name.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, INVALID_NAME_DESC_10 + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Name.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, INVALID_NAME_DESC_11 + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Name.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, INVALID_NAME_DESC_12 + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Name.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, INVALID_NAME_DESC_13 + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Name.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, INVALID_NAME_DESC_14 + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Name.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, INVALID_NAME_DESC_15 + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Name.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, INVALID_NAME_DESC_16 + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Name.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, INVALID_NAME_DESC_17 + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Name.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, INVALID_NAME_DESC_18 + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Name.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, INVALID_NAME_DESC_19 + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Name.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, INVALID_NAME_DESC_20 + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Name.MESSAGE_CONSTRAINTS);

        // invalid amount
        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_1 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_2 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_3 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_4 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_5 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_6 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_7 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_8 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_9 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_10 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_1 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_2 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_3 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_4 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_5 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_6 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_7 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_8 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_9 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_10 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_11 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_12 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_13 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_14 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_15 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_16 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_17 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_19 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_20 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_21 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_22 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_23 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_24 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_25 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_26 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_27 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_28 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_29 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_30 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_31 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_32 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_33 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_34 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_35 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_36 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_37 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_39 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_40 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_41 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS_TOOLARGE);

        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC_42 + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS_TOOSMALL);

        // invalid date
        assertParseFailure(parser, NAME_DESC_AIRPODS + AMOUNT_DESC_AIRPODS + INVALID_DATE_DESC
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Date.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + AMOUNT_DESC_AIRPODS + INVALID_DATE_DESC_1
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Date.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + AMOUNT_DESC_AIRPODS + INVALID_DATE_DESC_2
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Date.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + AMOUNT_DESC_AIRPODS + INVALID_DATE_DESC_3
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Date.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + AMOUNT_DESC_AIRPODS + INVALID_DATE_DESC_4
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Date.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + AMOUNT_DESC_AIRPODS + INVALID_DATE_DESC_5
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Date.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + AMOUNT_DESC_AIRPODS + INVALID_DATE_DESC_6
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Date.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + AMOUNT_DESC_AIRPODS + INVALID_DATE_DESC_9
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Date.MESSAGE_CONSTRAINTS_1);

        assertParseFailure(parser, NAME_DESC_AIRPODS + AMOUNT_DESC_AIRPODS + INVALID_DATE_DESC_10
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Date.MESSAGE_CONSTRAINTS_1);

        assertParseFailure(parser, NAME_DESC_AIRPODS + AMOUNT_DESC_AIRPODS + INVALID_DATE_DESC_11
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Date.MESSAGE_CONSTRAINTS_1);

        assertParseFailure(parser, NAME_DESC_AIRPODS + AMOUNT_DESC_AIRPODS + INVALID_DATE_DESC_12
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Date.MESSAGE_CONSTRAINTS_1);



        //invalid category
        assertParseFailure(parser, NAME_DESC_AIRPODS + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + INVALID_CATEGORY_DESC, Category.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + INVALID_CATEGORY_DESC_1, Category.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + INVALID_CATEGORY_DESC_2, Category.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + INVALID_CATEGORY_DESC_3, Category.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + INVALID_CATEGORY_DESC_4, Category.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + INVALID_CATEGORY_DESC_5, Category.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + INVALID_CATEGORY_DESC_6, Category.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + INVALID_CATEGORY_DESC_7, Category.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_AIRPODS + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + INVALID_CATEGORY_DESC_8, Category.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC + DATE_DESC_AIRPODS
                + INVALID_REMARK_DESC + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_AIRPODS + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }


    @Test
    public void parse_edgeCases_success() {
        Transaction expectedTransaction = new TransactionBuilder(MAX_INVESTMENT).build();
        Transaction expectedTransaction1 = new TransactionBuilder(MIN_INVESTMENT).build();

        // Max amount
        assertParseSuccess(parser, NAME_DESC_INVESTMENT + AMOUNT_DESC_MAX_INVESTMENT + DATE_DESC_INVESTMENT
                + REMARK_DESC_INVESTMENT + CATEGORY_DESC_INVESTMENT, new AddCommand(expectedTransaction));

        // Min Amount
        assertParseSuccess(parser, NAME_DESC_INVESTMENT + AMOUNT_DESC_MIN_INVESTMENT + DATE_DESC_INVESTMENT
                + REMARK_DESC_INVESTMENT + CATEGORY_DESC_INVESTMENT, new AddCommand(expectedTransaction1));
    }
}
