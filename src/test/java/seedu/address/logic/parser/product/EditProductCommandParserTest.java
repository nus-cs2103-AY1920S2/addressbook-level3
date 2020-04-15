package seedu.address.logic.parser.product;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_MULTIPLE_SAME_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_BAG;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRICE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUANTITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SALES_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_BAG;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_WATCH;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_BAG;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_WATCH;
import static seedu.address.logic.commands.CommandTestUtil.SALES_DESC_BAG;
import static seedu.address.logic.commands.CommandTestUtil.SALES_DESC_WATCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_BAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_WATCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_BAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_WATCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SALES_BAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SALES_WATCH;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.product.EditProductCommand;
import seedu.address.logic.commands.product.EditProductCommand.EditProductDescriptor;
import seedu.address.model.product.Price;
import seedu.address.model.util.Description;
import seedu.address.model.util.Money;
import seedu.address.model.util.Quantity;
import seedu.address.testutil.product.EditProductDescriptorBuilder;

public class EditProductCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditProductCommand.MESSAGE_USAGE);

    private EditProductCommandParser parser = new EditProductCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_DESCRIPTION_BAG, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditProductCommand.MESSAGE_USAGE));

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + DESCRIPTION_DESC_BAG, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + DESCRIPTION_DESC_BAG, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION_DESC,
                Description.MESSAGE_CONSTRAINTS); // invalid description
        assertParseFailure(parser, "1" + INVALID_PRICE_DESC, Price.MESSAGE_CONSTRAINTS); // invalid price
        assertParseFailure(parser, "1" + INVALID_QUANTITY_DESC,
                Quantity.MESSAGE_CONSTRAINTS_FORMAT); // invalid quantity
        assertParseFailure(parser, "1" + INVALID_SALES_DESC, Money.MESSAGE_CONSTRAINTS_FORMAT); // invalid sales

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_PRICE_DESC + QUANTITY_DESC_BAG, Price.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + PRICE_DESC_WATCH + INVALID_PRICE_DESC,
                String.format(MESSAGE_MULTIPLE_SAME_PREFIX, EditProductCommand.MESSAGE_USAGE));

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION_DESC
                        + INVALID_QUANTITY_DESC + VALID_SALES_BAG + VALID_PRICE_BAG,
                Description.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + PRICE_DESC_WATCH
                + QUANTITY_DESC_BAG + SALES_DESC_BAG + DESCRIPTION_DESC_BAG;

        EditProductDescriptor descriptor = new EditProductDescriptorBuilder().withDescription(VALID_DESCRIPTION_BAG)
                .withPrice(VALID_PRICE_WATCH).withQuantity(VALID_QUANTITY_BAG).withSales(VALID_SALES_BAG)
                .build();
        EditProductCommand expectedCommand = new EditProductCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PRICE_DESC_WATCH + QUANTITY_DESC_BAG;

        EditProductDescriptor descriptor = new EditProductDescriptorBuilder().withPrice(VALID_PRICE_WATCH)
                .withQuantity(VALID_QUANTITY_BAG).build();
        EditProductCommand expectedCommand = new EditProductCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_BAG;
        EditProductDescriptor descriptor = new EditProductDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_BAG).build();
        //        EditProductCommand expectedCommand = new EditProductCommand(targetIndex, descriptor);
        //        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PRICE_DESC_BAG;
        descriptor = new EditProductDescriptorBuilder().withPrice(VALID_PRICE_BAG).build();
        //        expectedCommand = new EditProductCommand(targetIndex, descriptor);
        //        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + QUANTITY_DESC_BAG;
        descriptor = new EditProductDescriptorBuilder().withQuantity(VALID_QUANTITY_BAG).build();
        //        expectedCommand = new EditProductCommand(targetIndex, descriptor);
        //        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + SALES_DESC_BAG;
        descriptor = new EditProductDescriptorBuilder().withSales(VALID_SALES_BAG).build();
        //        expectedCommand = new EditProductCommand(targetIndex, descriptor);
        //        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_showsErrorMessage() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PRICE_DESC_BAG + SALES_DESC_BAG + QUANTITY_DESC_BAG
                + PRICE_DESC_BAG + SALES_DESC_BAG + QUANTITY_DESC_BAG
                + PRICE_DESC_WATCH + SALES_DESC_WATCH + QUANTITY_DESC_WATCH;
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_MULTIPLE_SAME_PREFIX, EditProductCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_PRICE_DESC + PRICE_DESC_WATCH;
        EditProductDescriptor descriptor = new EditProductDescriptorBuilder().withPrice(VALID_PRICE_WATCH).build();
        //        EditProductCommand expectedCommand = new EditProductCommand(targetIndex, descriptor);
        //        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + QUANTITY_DESC_WATCH + INVALID_PRICE_DESC + SALES_DESC_WATCH
                + PRICE_DESC_WATCH;
        descriptor = new EditProductDescriptorBuilder().withPrice(VALID_PRICE_WATCH).withQuantity(VALID_QUANTITY_WATCH)
                .withSales(VALID_SALES_WATCH).build();
        //        expectedCommand = new EditProductCommand(targetIndex, descriptor);
        //        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
