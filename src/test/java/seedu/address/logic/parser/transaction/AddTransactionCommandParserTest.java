package seedu.address.logic.parser.transaction;

import static seedu.address.commons.core.Messages.MESSAGE_MULTIPLE_SAME_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.CUSTOMER_INDEX_DESC_AMY_BAG;
import static seedu.address.logic.commands.CommandTestUtil.DATETIME_DESC_AMY_BAG;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_AMY_BAG;
import static seedu.address.logic.commands.CommandTestUtil.MONEY_DESC_AMY_BAG;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PRODUCT_INDEX_DESC_AMY_BAG;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_AMY_BAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.transaction.TypicalTransactionFactories.ONE_ONE_MARCH_FIRST_TWENTY_ONE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.transaction.AddTransactionCommand;
import seedu.address.model.transaction.TransactionFactory;
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

}
