package seedu.address.logic.parser.transaction;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_MULTIPLE_SAME_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.CUSTOMER_INDEX_DESC_AMY_BAG;
import static seedu.address.logic.commands.CommandTestUtil.DATETIME_DESC_AMY_BAG;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_AMY_BAG;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CUSTOMER_INDEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATETIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MONEY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRODUCT_INDEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUANTITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MONEY_DESC_AMY_BAG;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PRODUCT_INDEX_DESC_AMY_BAG;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_AMY_BAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.transaction.TypicalTransactionFactories.ONE_ONE_MARCH_FIRST_TWENTY_ONE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.transaction.AddTransactionCommand;
import seedu.address.model.transaction.DateTime;
import seedu.address.model.transaction.TransactionFactory;
import seedu.address.model.transaction.TransactionQuantity;
import seedu.address.model.util.Description;
import seedu.address.model.util.Money;
import seedu.address.testutil.transaction.TransactionFactoryBuilder;

public class AddTransactionCommandParserTest {
    private AddTransactionCommandParser parser = new AddTransactionCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        TransactionFactory expectedTransactionFactory = new TransactionFactoryBuilder(ONE_ONE_MARCH_FIRST_TWENTY_ONE)
                .withMoney(30).withQuantity(1).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + CUSTOMER_INDEX_DESC_AMY_BAG
                        + PRODUCT_INDEX_DESC_AMY_BAG + DATETIME_DESC_AMY_BAG + QUANTITY_DESC_AMY_BAG
                        + MONEY_DESC_AMY_BAG + DESCRIPTION_DESC_AMY_BAG,
                new AddTransactionCommand(expectedTransactionFactory));
    }

    @Test
    public void parse_multiplePrefixPresent_failure() {
        // multiple customer index - shows error message
        assertParseFailure(parser, CUSTOMER_INDEX_DESC_AMY_BAG + CUSTOMER_INDEX_DESC_AMY_BAG
                + PRODUCT_INDEX_DESC_AMY_BAG + DATETIME_DESC_AMY_BAG + QUANTITY_DESC_AMY_BAG
                + MONEY_DESC_AMY_BAG + DESCRIPTION_DESC_AMY_BAG,
                String.format(MESSAGE_MULTIPLE_SAME_PREFIX, AddTransactionCommand.MESSAGE_USAGE));

        // multiple product index - shows error message
        assertParseFailure(parser, CUSTOMER_INDEX_DESC_AMY_BAG + PRODUCT_INDEX_DESC_AMY_BAG
                + PRODUCT_INDEX_DESC_AMY_BAG + DATETIME_DESC_AMY_BAG + QUANTITY_DESC_AMY_BAG
                + MONEY_DESC_AMY_BAG + DESCRIPTION_DESC_AMY_BAG,
                String.format(MESSAGE_MULTIPLE_SAME_PREFIX, AddTransactionCommand.MESSAGE_USAGE));

        // multiple date time - shows error message
        assertParseFailure(parser, CUSTOMER_INDEX_DESC_AMY_BAG + PRODUCT_INDEX_DESC_AMY_BAG
                + DATETIME_DESC_AMY_BAG + DATETIME_DESC_AMY_BAG + QUANTITY_DESC_AMY_BAG
                + MONEY_DESC_AMY_BAG + DESCRIPTION_DESC_AMY_BAG,
                String.format(MESSAGE_MULTIPLE_SAME_PREFIX, AddTransactionCommand.MESSAGE_USAGE));

        // multiple quantity - shows error message
        assertParseFailure(parser, CUSTOMER_INDEX_DESC_AMY_BAG + PRODUCT_INDEX_DESC_AMY_BAG
                + DATETIME_DESC_AMY_BAG + QUANTITY_DESC_AMY_BAG + QUANTITY_DESC_AMY_BAG
                + MONEY_DESC_AMY_BAG + DESCRIPTION_DESC_AMY_BAG,
                String.format(MESSAGE_MULTIPLE_SAME_PREFIX, AddTransactionCommand.MESSAGE_USAGE));

        // multiple money - shows error message
        assertParseFailure(parser, CUSTOMER_INDEX_DESC_AMY_BAG + PRODUCT_INDEX_DESC_AMY_BAG
                + DATETIME_DESC_AMY_BAG + QUANTITY_DESC_AMY_BAG + MONEY_DESC_AMY_BAG
                + MONEY_DESC_AMY_BAG + DESCRIPTION_DESC_AMY_BAG,
                String.format(MESSAGE_MULTIPLE_SAME_PREFIX, AddTransactionCommand.MESSAGE_USAGE));

        // multiple description - shows error message
        assertParseFailure(parser, CUSTOMER_INDEX_DESC_AMY_BAG + PRODUCT_INDEX_DESC_AMY_BAG
                + DATETIME_DESC_AMY_BAG + QUANTITY_DESC_AMY_BAG + MONEY_DESC_AMY_BAG
                + DESCRIPTION_DESC_AMY_BAG + DESCRIPTION_DESC_AMY_BAG,
                String.format(MESSAGE_MULTIPLE_SAME_PREFIX, AddTransactionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_optionalFieldMissing_success() {
        TransactionFactory expectedTransactionFactory =
                new TransactionFactoryBuilder(ONE_ONE_MARCH_FIRST_TWENTY_ONE)
                        .withMoney(30).withQuantity(1).withDescription().build();
        assertParseSuccess(parser, CUSTOMER_INDEX_DESC_AMY_BAG + PRODUCT_INDEX_DESC_AMY_BAG
                + DATETIME_DESC_AMY_BAG + QUANTITY_DESC_AMY_BAG + MONEY_DESC_AMY_BAG,
                new AddTransactionCommand(expectedTransactionFactory));
    }

    @Test void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTransactionCommand.MESSAGE_USAGE);

        // missing customer index
        assertParseFailure(parser, PRODUCT_INDEX_DESC_AMY_BAG
                + DATETIME_DESC_AMY_BAG + QUANTITY_DESC_AMY_BAG + MONEY_DESC_AMY_BAG + DESCRIPTION_DESC_AMY_BAG,
                expectedMessage);

        // missing product index
        assertParseFailure(parser, CUSTOMER_INDEX_DESC_AMY_BAG
                + DATETIME_DESC_AMY_BAG + QUANTITY_DESC_AMY_BAG + MONEY_DESC_AMY_BAG + DESCRIPTION_DESC_AMY_BAG,
                expectedMessage);

        // missing quantity index
        assertParseFailure(parser, CUSTOMER_INDEX_DESC_AMY_BAG + PRODUCT_INDEX_DESC_AMY_BAG
                + DATETIME_DESC_AMY_BAG + MONEY_DESC_AMY_BAG + DESCRIPTION_DESC_AMY_BAG,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid customer index
        assertParseFailure(parser, INVALID_CUSTOMER_INDEX_DESC + PRODUCT_INDEX_DESC_AMY_BAG
                + DATETIME_DESC_AMY_BAG + QUANTITY_DESC_AMY_BAG + MONEY_DESC_AMY_BAG + DESCRIPTION_DESC_AMY_BAG,
                MESSAGE_INVALID_INDEX);

        // invalid product index
        assertParseFailure(parser, CUSTOMER_INDEX_DESC_AMY_BAG + INVALID_PRODUCT_INDEX_DESC
                + DATETIME_DESC_AMY_BAG + QUANTITY_DESC_AMY_BAG + MONEY_DESC_AMY_BAG + DESCRIPTION_DESC_AMY_BAG,
                MESSAGE_INVALID_INDEX);

        // invalid date time
        assertParseFailure(parser, CUSTOMER_INDEX_DESC_AMY_BAG + PRODUCT_INDEX_DESC_AMY_BAG
                + INVALID_DATETIME_DESC + QUANTITY_DESC_AMY_BAG + MONEY_DESC_AMY_BAG + DESCRIPTION_DESC_AMY_BAG,
                DateTime.MESSAGE_CONSTRAINTS);

        // invalid quantity
        assertParseFailure(parser, CUSTOMER_INDEX_DESC_AMY_BAG + PRODUCT_INDEX_DESC_AMY_BAG
                + DATETIME_DESC_AMY_BAG + INVALID_QUANTITY_DESC + MONEY_DESC_AMY_BAG + DESCRIPTION_DESC_AMY_BAG,
                TransactionQuantity.MESSAGE_CONSTRAINTS_FORMAT);

        // invalid money
        assertParseFailure(parser, CUSTOMER_INDEX_DESC_AMY_BAG + PRODUCT_INDEX_DESC_AMY_BAG
                        + DATETIME_DESC_AMY_BAG + QUANTITY_DESC_AMY_BAG + INVALID_MONEY_DESC + DESCRIPTION_DESC_AMY_BAG,
                Money.MESSAGE_CONSTRAINTS_FORMAT);

        // invalid description
        assertParseFailure(parser, CUSTOMER_INDEX_DESC_AMY_BAG + PRODUCT_INDEX_DESC_AMY_BAG
                        + DATETIME_DESC_AMY_BAG + QUANTITY_DESC_AMY_BAG + MONEY_DESC_AMY_BAG + INVALID_DESCRIPTION_DESC,
                Description.MESSAGE_CONSTRAINTS);
    }
}
