package seedu.address.logic.parser.transaction;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CUSTOMER_NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DATETIME_DESC_AMY_BAG;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_WATCH;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CUSTOMER_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATETIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MONEY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRODUCT_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MONEY_DESC_AMY_BAG;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PRODUCT_DESCRIPTION_DESC_BAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATETIME_AMY_BAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONEY_AMY_BAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.transaction.FindTransactionCommand;
import seedu.address.model.customer.Name;
import seedu.address.model.transaction.CustomerContainsKeywordPredicate;
import seedu.address.model.transaction.DateTime;
import seedu.address.model.transaction.DateTimeEqualsPredicate;
import seedu.address.model.transaction.JointTransactionPredicate;
import seedu.address.model.transaction.MoneyEqualsPredicate;
import seedu.address.model.transaction.ProductContainsKeywordPredicate;
import seedu.address.model.util.Description;
import seedu.address.model.util.Money;

public class FindTransactionCommandParserTest {

    private FindTransactionCommandParser parser = new FindTransactionCommandParser();

    @Test
    public void parse_emptyArg_failure() {
        assertParseFailure(parser, "    ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindTransactionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid customer name
        assertParseFailure(parser, INVALID_CUSTOMER_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // invalid product description
        assertParseFailure(parser, INVALID_PRODUCT_DESCRIPTION_DESC,
                Description.MESSAGE_CONSTRAINTS);

        // invalid date time
        assertParseFailure(parser, INVALID_DATETIME_DESC,
                DateTime.MESSAGE_CONSTRAINTS);

        // invalid money
        assertParseFailure(parser, INVALID_MONEY_DESC,
                Money.MESSAGE_CONSTRAINTS_FORMAT);

    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        String userInput = NAME_DESC_AMY + DESCRIPTION_DESC_WATCH
                + DATETIME_DESC_AMY_BAG + DATETIME_DESC_AMY_BAG;

        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTransactionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = CUSTOMER_NAME_DESC_AMY + PRODUCT_DESCRIPTION_DESC_BAG
                + DATETIME_DESC_AMY_BAG + MONEY_DESC_AMY_BAG;

        String[] customerKeywords = VALID_NAME_AMY.split("\\s+");
        CustomerContainsKeywordPredicate namePredicate =
                new CustomerContainsKeywordPredicate(Arrays.asList(customerKeywords));

        String[] productKeywords = VALID_DESCRIPTION_BAG.split("\\s+");
        ProductContainsKeywordPredicate descriptionPredicate =
                new ProductContainsKeywordPredicate(Arrays.asList(productKeywords));

        DateTimeEqualsPredicate dateTimePredicate = new DateTimeEqualsPredicate(new DateTime(VALID_DATETIME_AMY_BAG));
        MoneyEqualsPredicate moneyPredicate = new MoneyEqualsPredicate(new Money(VALID_MONEY_AMY_BAG));

        JointTransactionPredicate jointPredicate =
                new JointTransactionPredicate(Arrays.asList(
                        namePredicate, descriptionPredicate, dateTimePredicate, moneyPredicate
                ));

        FindTransactionCommand expectedCommand = new FindTransactionCommand(jointPredicate);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        String userInput = DATETIME_DESC_AMY_BAG + MONEY_DESC_AMY_BAG;

        DateTimeEqualsPredicate dateTimePredicate = new DateTimeEqualsPredicate(new DateTime(VALID_DATETIME_AMY_BAG));
        MoneyEqualsPredicate moneyPredicate = new MoneyEqualsPredicate(new Money(VALID_MONEY_AMY_BAG));

        JointTransactionPredicate jointPredicate =
                new JointTransactionPredicate(Arrays.asList(
                        dateTimePredicate, moneyPredicate
                ));

        FindTransactionCommand expectedCommand = new FindTransactionCommand(jointPredicate);

        assertParseSuccess(parser, userInput, expectedCommand);

    }
}

