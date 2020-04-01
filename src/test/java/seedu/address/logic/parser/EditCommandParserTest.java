package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_MISMATCH_FLAG_WITH_TIMESTAMP;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_FLAG;
import static seedu.address.commons.core.Messages.MESSAGE_NO_COD_FIELD_IN_RETURN_ORDER;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.COD_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.COMMENT_DESC_INSTRUCTION;
import static seedu.address.logic.commands.CommandTestUtil.DELIVERY_TIMESTAMP_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DELIVERY_TIMESTAMP_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DELIVERY_TIMESTAMP_DATE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DELIVERY_TIMESTAMP_DATE_ONLY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DELIVERY_TIMESTAMP_TIME;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DELIVERY_TIMESTAMP_TIME_ONLY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.RETURN_TIMESTAMP_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TID_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TID_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_GLASS;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_PLASTIC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COD_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMMENT_INSTRUCTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMMENT_NIL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIMESTAMP_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIMESTAMP_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_GLASS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WAREHOUSE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WAREHOUSE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.WAREHOUSE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.WAREHOUSE_DESC_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_ORDER;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.Parcel.comment.Comment;
import seedu.address.model.Parcel.itemtype.TypeOfItem;
import seedu.address.model.Parcel.ParcelAttributes.Address;
import seedu.address.model.Parcel.ParcelAttributes.Name;
import seedu.address.model.Parcel.ParcelAttributes.Phone;
import seedu.address.model.Parcel.ParcelAttributes.TimeStamp;
import seedu.address.testutil.EditParcelDescriptorBuilder;

public class EditCommandParserTest {

    private static final String COMMENT_EMPTY = " " + PREFIX_COMMENT;

    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private static final Flag ORDER_FLAG = CliSyntax.FLAG_ORDER_BOOK;
    private static final Flag RETURN_FLAG = CliSyntax.FLAG_RETURN_BOOK;
    private static final String ORDER_FLAG_INPUT = CliSyntax.FLAG_ORDER_BOOK.getFlag() + " ";
    private static final String RETURN_FLAG_INPUT = CliSyntax.FLAG_RETURN_BOOK.getFlag() + " ";

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, ORDER_FLAG_INPUT + VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, ORDER_FLAG_INPUT + "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, ORDER_FLAG_INPUT, MESSAGE_INVALID_FORMAT);

        // missing flags
        assertParseFailure(parser, VALID_ADDRESS_AMY, MESSAGE_MISSING_FLAG);
    }

    @Test
    public void parse_invalidPrefixWithFlags_failure() {
        // edit return order with delivery timestamp prefix instead of return timestamp prefix
        assertParseFailure(parser, RETURN_FLAG_INPUT + "1 " + DELIVERY_TIMESTAMP_DESC_AMY,
            MESSAGE_MISMATCH_FLAG_WITH_TIMESTAMP);

        // edit order with return timestamp prefix instead of delivery timestamp prefix.
        assertParseFailure(parser, ORDER_FLAG_INPUT + "1 " + RETURN_TIMESTAMP_DESC_AMY,
            MESSAGE_MISMATCH_FLAG_WITH_TIMESTAMP);

        // edit order with both return timestamp prefix and delivery timestamp prefix.
        assertParseFailure(parser, ORDER_FLAG_INPUT + "1 " + RETURN_TIMESTAMP_DESC_AMY
                + DELIVERY_TIMESTAMP_DESC_AMY,
            MESSAGE_MISMATCH_FLAG_WITH_TIMESTAMP);

        // edit return with cash on delivery prefix.
        assertParseFailure(parser, RETURN_FLAG_INPUT + "1 " + COD_DESC_AMY,
            MESSAGE_NO_COD_FIELD_IN_RETURN_ORDER);
    }

    @Test
    public void parse_invalidMultiplePrefix_failure() {
        assertParseFailure(parser, ORDER_FLAG_INPUT + RETURN_FLAG_INPUT + VALID_ADDRESS_AMY,
            EditCommand.MULTIPLE_FLAGS_DETECTED);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, ORDER_FLAG_INPUT + "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, ORDER_FLAG_INPUT + "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, ORDER_FLAG_INPUT + "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, ORDER_FLAG_INPUT + "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser,
            ORDER_FLAG_INPUT + "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser,
            ORDER_FLAG_INPUT + "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser,
            ORDER_FLAG_INPUT + "1" + INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS); // invalid address
        // No date input
        assertParseFailure(parser,
            ORDER_FLAG_INPUT + "1" + INVALID_DELIVERY_TIMESTAMP_TIME_ONLY, TimeStamp.MESSAGE_CONSTRAINTS);
        // No time input
        assertParseFailure(parser,
            ORDER_FLAG_INPUT + "1" + INVALID_DELIVERY_TIMESTAMP_DATE_ONLY, TimeStamp.MESSAGE_CONSTRAINTS);
        // Invalid time
        assertParseFailure(parser,
            ORDER_FLAG_INPUT + "1" + INVALID_DELIVERY_TIMESTAMP_TIME, TimeStamp.MESSAGE_CONSTRAINTS);
        // Invalid date
        assertParseFailure(parser,
            ORDER_FLAG_INPUT + "1" + INVALID_DELIVERY_TIMESTAMP_DATE, TimeStamp.MESSAGE_CONSTRAINTS);
        //Invalid itemType
        assertParseFailure(parser,
            ORDER_FLAG_INPUT + "1" + INVALID_TYPE_DESC, TypeOfItem.MESSAGE_CONSTRAINTS);

        // invalid phone followed by valid address
        assertParseFailure(parser,
            ORDER_FLAG_INPUT + "1" + INVALID_PHONE_DESC + ADDRESS_DESC_BOB, Phone.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser,
            ORDER_FLAG_INPUT + "1" + PHONE_DESC_BOB + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // input a empty comment after the prefix comment
        assertParseFailure(parser,
            ORDER_FLAG_INPUT + "1" + COMMENT_EMPTY + TYPE_DESC_GLASS + TYPE_DESC_PLASTIC,
            Comment.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser,
            ORDER_FLAG_INPUT + "1" + INVALID_NAME_DESC + VALID_ADDRESS_AMY + VALID_PHONE_AMY,
            Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_ORDER;
        String userInput = ORDER_FLAG_INPUT + targetIndex.getOneBased() + PHONE_DESC_BOB + TYPE_DESC_GLASS
            + COMMENT_DESC_INSTRUCTION + ADDRESS_DESC_AMY + NAME_DESC_AMY
            + DELIVERY_TIMESTAMP_DESC_AMY + TID_DESC_AMY + WAREHOUSE_DESC_AMY + COD_DESC_AMY;

        EditCommand.EditParcelDescriptor descriptor = new EditParcelDescriptorBuilder().withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_AMY).withTid(VALID_TID_AMY)
            .withTimeStamp(VALID_TIMESTAMP_AMY).withWarehouse(VALID_WAREHOUSE_AMY).withCash(VALID_COD_AMY)
            .withComment(VALID_COMMENT_INSTRUCTION).withItemType(VALID_TYPE_GLASS).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor, ORDER_FLAG);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_ORDER;
        String userInput = ORDER_FLAG_INPUT + targetIndex.getOneBased() + PHONE_DESC_BOB;

        EditCommand.EditParcelDescriptor descriptor = new EditParcelDescriptorBuilder().withPhone(VALID_PHONE_BOB)
            .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor, ORDER_FLAG);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_ORDER;
        String userInput = ORDER_FLAG_INPUT + targetIndex.getOneBased() + NAME_DESC_AMY;
        EditCommand.EditParcelDescriptor descriptor =
            new EditParcelDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor, ORDER_FLAG);
        assertParseSuccess(parser, userInput, expectedCommand);

        // TID
        userInput = ORDER_FLAG_INPUT + targetIndex.getOneBased() + TID_DESC_AMY;
        descriptor = new EditParcelDescriptorBuilder().withTid(VALID_TID_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor, ORDER_FLAG);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = ORDER_FLAG_INPUT + targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditParcelDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor, ORDER_FLAG);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = ORDER_FLAG_INPUT + targetIndex.getOneBased() + ADDRESS_DESC_AMY;
        descriptor = new EditParcelDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor, ORDER_FLAG);
        assertParseSuccess(parser, userInput, expectedCommand);

        // warehouse
        userInput = ORDER_FLAG_INPUT + targetIndex.getOneBased() + WAREHOUSE_DESC_AMY;
        descriptor = new EditParcelDescriptorBuilder().withWarehouse(VALID_WAREHOUSE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor, ORDER_FLAG);
        assertParseSuccess(parser, userInput, expectedCommand);

        // cashOnDelivery
        userInput = ORDER_FLAG_INPUT + targetIndex.getOneBased() + COD_DESC_AMY;
        descriptor = new EditParcelDescriptorBuilder().withCash(VALID_COD_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor, ORDER_FLAG);
        assertParseSuccess(parser, userInput, expectedCommand);

        // timeStamp
        userInput = ORDER_FLAG_INPUT + targetIndex.getOneBased() + DELIVERY_TIMESTAMP_DESC_AMY;
        descriptor = new EditParcelDescriptorBuilder().withTimeStamp(VALID_TIMESTAMP_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor, ORDER_FLAG);
        assertParseSuccess(parser, userInput, expectedCommand);

        // comment
        userInput = ORDER_FLAG_INPUT + targetIndex.getOneBased() + COMMENT_DESC_INSTRUCTION;
        descriptor = new EditParcelDescriptorBuilder().withComment(VALID_COMMENT_INSTRUCTION).build();
        expectedCommand = new EditCommand(targetIndex, descriptor, ORDER_FLAG);
        assertParseSuccess(parser, userInput, expectedCommand);

        // type of item
        userInput = ORDER_FLAG_INPUT + targetIndex.getOneBased() + TYPE_DESC_GLASS;
        descriptor = new EditParcelDescriptorBuilder().withItemType(VALID_TYPE_GLASS).build();
        expectedCommand = new EditCommand(targetIndex, descriptor, ORDER_FLAG);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_ORDER;
        String userInput = ORDER_FLAG_INPUT + targetIndex.getOneBased()
            + PHONE_DESC_AMY + ADDRESS_DESC_AMY + TID_DESC_AMY
            + VALID_COMMENT_NIL + TYPE_DESC_PLASTIC + PHONE_DESC_AMY + ADDRESS_DESC_AMY
            + TID_DESC_AMY + COMMENT_DESC_INSTRUCTION + PHONE_DESC_BOB
            + DELIVERY_TIMESTAMP_DESC_AMY + ADDRESS_DESC_BOB
            + DELIVERY_TIMESTAMP_DESC_BOB + TID_DESC_BOB + TYPE_DESC_GLASS;

        EditCommand.EditParcelDescriptor descriptor = new EditParcelDescriptorBuilder()
            .withPhone(VALID_PHONE_BOB).withTid(VALID_TID_BOB).withAddress(VALID_ADDRESS_BOB)
            .withTimeStamp(VALID_TIMESTAMP_BOB).withComment(VALID_COMMENT_INSTRUCTION)
            .withItemType(VALID_TYPE_GLASS)
            .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor, ORDER_FLAG);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_ORDER;
        String userInput = ORDER_FLAG_INPUT + targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        EditCommand.EditParcelDescriptor descriptor = new EditParcelDescriptorBuilder()
            .withPhone(VALID_PHONE_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor, ORDER_FLAG);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = ORDER_FLAG_INPUT + targetIndex.getOneBased()
            + WAREHOUSE_DESC_BOB + INVALID_PHONE_DESC + ADDRESS_DESC_BOB
            + PHONE_DESC_BOB;
        descriptor = new EditParcelDescriptorBuilder().withPhone(VALID_PHONE_BOB).withWarehouse(VALID_WAREHOUSE_BOB)
            .withAddress(VALID_ADDRESS_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor, ORDER_FLAG);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
