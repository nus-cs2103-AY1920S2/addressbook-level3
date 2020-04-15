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
import static seedu.address.logic.commands.CommandTestUtil.PRODUCT_INDEX_DESC_AMY_BAG;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_AMY_BAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATETIME_AMY_BAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_AMY_BAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONEY_AMY_BAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_AMY_BAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.transaction.EditTransactionCommand;
import seedu.address.logic.commands.transaction.EditTransactionCommand.EditTransactionDescriptor;
import seedu.address.model.transaction.DateTime;
import seedu.address.model.transaction.TransactionQuantity;
import seedu.address.model.util.Description;
import seedu.address.model.util.Money;
import seedu.address.testutil.transaction.EditTransactionDescriptorBuilder;

public class EditTransactionCommandParserTest {

    private EditTransactionCommandParser parser =
            new EditTransactionCommandParser();

    @Test
    public void parse_missingFields_failure() {
        // no index specified
        assertParseFailure(parser, CUSTOMER_INDEX_DESC_AMY_BAG,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTransactionCommand.MESSAGE_USAGE));

        // no field specified
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTransactionCommand.MESSAGE_USAGE));

        // nothing specified
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTransactionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + CUSTOMER_INDEX_DESC_AMY_BAG,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTransactionCommand.MESSAGE_USAGE));

        // zero index
        assertParseFailure(parser, "0" + CUSTOMER_INDEX_DESC_AMY_BAG,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTransactionCommand.MESSAGE_USAGE));

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string" + CUSTOMER_INDEX_DESC_AMY_BAG,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTransactionCommand.MESSAGE_USAGE));

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string" + CUSTOMER_INDEX_DESC_AMY_BAG,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTransactionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid customer index
        assertParseFailure(parser, "1" + INVALID_CUSTOMER_INDEX_DESC,
                MESSAGE_INVALID_INDEX);

        // invalid product index
        assertParseFailure(parser, "1" + INVALID_PRODUCT_INDEX_DESC,
                MESSAGE_INVALID_INDEX);

        // invalid date time
        assertParseFailure(parser, "1" + INVALID_DATETIME_DESC,
                DateTime.MESSAGE_CONSTRAINTS);

        // invalid quantity
        assertParseFailure(parser, "1" + INVALID_QUANTITY_DESC,
                TransactionQuantity.MESSAGE_CONSTRAINTS_FORMAT);

        // invalid money
        assertParseFailure(parser, "1" + INVALID_MONEY_DESC,
                Money.MESSAGE_CONSTRAINTS_FORMAT);

        // invalid description
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION_DESC,
                Description.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = Index.fromOneBased(1);
        String userInput = targetIndex.getOneBased() + CUSTOMER_INDEX_DESC_AMY_BAG
                + PRODUCT_INDEX_DESC_AMY_BAG + DATETIME_DESC_AMY_BAG + QUANTITY_DESC_AMY_BAG
                + MONEY_DESC_AMY_BAG + DESCRIPTION_DESC_AMY_BAG;

        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder()
                .withCustomerIndex(Index.fromOneBased(1)).withProductIndex(Index.fromOneBased(1))
                .withDateTime(VALID_DATETIME_AMY_BAG).withQuantity(VALID_QUANTITY_AMY_BAG)
                .withMoney(VALID_MONEY_AMY_BAG).withDescription(VALID_DESCRIPTION_AMY_BAG).build();

        EditTransactionCommand expectedCommand = new EditTransactionCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = Index.fromOneBased(1);
        String userInput = targetIndex.getOneBased() + PRODUCT_INDEX_DESC_AMY_BAG
                + DATETIME_DESC_AMY_BAG + QUANTITY_DESC_AMY_BAG;

        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder()
                .withProductIndex(Index.fromOneBased(1)).withDateTime(VALID_DATETIME_AMY_BAG)
                .withQuantity(VALID_QUANTITY_AMY_BAG).build();

        EditTransactionCommand expectedCommand = new EditTransactionCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        Index targetIndex = Index.fromOneBased(1);
        String userInput = targetIndex.getOneBased() + CUSTOMER_INDEX_DESC_AMY_BAG
                + PRODUCT_INDEX_DESC_AMY_BAG + DATETIME_DESC_AMY_BAG + DATETIME_DESC_AMY_BAG
                + QUANTITY_DESC_AMY_BAG + MONEY_DESC_AMY_BAG + DESCRIPTION_DESC_AMY_BAG;

        assertParseFailure(parser, userInput,
                String.format(MESSAGE_MULTIPLE_SAME_PREFIX, EditTransactionCommand.MESSAGE_USAGE));
    }
}
