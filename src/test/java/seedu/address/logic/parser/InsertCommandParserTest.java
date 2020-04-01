package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.COD_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.COD_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.COMMENT_DESC_INSTRUCTION;
import static seedu.address.logic.commands.CommandTestUtil.COMMENT_DESC_NIL;
import static seedu.address.logic.commands.CommandTestUtil.DELIVERY_TIMESTAMP_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DELIVERY_TIMESTAMP_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COD_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COMMENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DELIVERY_TIMESTAMP_DATE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DELIVERY_TIMESTAMP_DATE_ONLY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DELIVERY_TIMESTAMP_TIME;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DELIVERY_TIMESTAMP_TIME_ONLY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_WAREHOUSE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TID_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TID_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_GLASS;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_PLASTIC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COD_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIMESTAMP_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_GLASS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WAREHOUSE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.WAREHOUSE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.WAREHOUSE_DESC_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalOrders.AMY;
import static seedu.address.testutil.TypicalOrders.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.InsertCommand;
import seedu.address.model.parcel.comment.Comment;
import seedu.address.model.parcel.itemtype.TypeOfItem;
import seedu.address.model.parcel.order.CashOnDelivery;
import seedu.address.model.parcel.order.Order;
import seedu.address.model.parcel.parcelattributes.Address;
import seedu.address.model.parcel.parcelattributes.Email;
import seedu.address.model.parcel.parcelattributes.Name;
import seedu.address.model.parcel.parcelattributes.Phone;
import seedu.address.model.parcel.parcelattributes.TimeStamp;
import seedu.address.model.parcel.parcelattributes.TransactionId;
import seedu.address.model.parcel.parcelattributes.Warehouse;
import seedu.address.testutil.OrderBuilder;

public class InsertCommandParserTest {
    private InsertCommandParser parser = new InsertCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Order expectedOrder = new OrderBuilder(BOB).withItemType(VALID_TYPE_GLASS).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB
                + COMMENT_DESC_NIL + TYPE_DESC_GLASS, new InsertCommand(expectedOrder));

        // multiple TIDs - last TID accepted
        assertParseSuccess(parser, TID_DESC_AMY + TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB
                + COMMENT_DESC_NIL + TYPE_DESC_GLASS, new InsertCommand(expectedOrder));

        // multiple names - last name accepted
        assertParseSuccess(parser, TID_DESC_BOB + NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB
                + COMMENT_DESC_NIL + TYPE_DESC_GLASS, new InsertCommand(expectedOrder));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB
                + COMMENT_DESC_NIL + TYPE_DESC_GLASS,
                new InsertCommand(expectedOrder));

        // multiple emails - last email accepted
        assertParseSuccess(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB
                        + COD_DESC_BOB + COMMENT_DESC_NIL + TYPE_DESC_GLASS,
                new InsertCommand(expectedOrder));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB
                + WAREHOUSE_DESC_BOB + COD_DESC_BOB + COMMENT_DESC_NIL + TYPE_DESC_GLASS,
                new InsertCommand(expectedOrder));

        // multiple timeStamps - last timeStamp accepted
        assertParseSuccess(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_AMY + DELIVERY_TIMESTAMP_DESC_BOB
                + WAREHOUSE_DESC_BOB + COD_DESC_BOB + COMMENT_DESC_NIL + TYPE_DESC_GLASS,
                new InsertCommand(expectedOrder));

        // multiple warehouses - last warehouse accepted
        assertParseSuccess(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB
                + WAREHOUSE_DESC_AMY + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB
                + TYPE_DESC_GLASS, new InsertCommand(expectedOrder));

        // multiple cashOnDeliveries - last cashOnDelivery accepted
        assertParseSuccess(parser, TID_DESC_BOB + NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_AMY
                + COD_DESC_BOB + COMMENT_DESC_NIL + TYPE_DESC_GLASS, new InsertCommand(expectedOrder));

        // multiple comment - last comment accepted
        assertParseSuccess(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB
                + COMMENT_DESC_INSTRUCTION
                + COMMENT_DESC_NIL + TYPE_DESC_GLASS, new InsertCommand(expectedOrder));

        // multiple item types - all accepted
        assertParseSuccess(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB + COMMENT_DESC_NIL
                + TYPE_DESC_PLASTIC + TYPE_DESC_GLASS, new InsertCommand(expectedOrder));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero item types and comment
        Order expectedOrder = new OrderBuilder(AMY).build();
        assertParseSuccess(parser, TID_DESC_AMY + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + DELIVERY_TIMESTAMP_DESC_AMY + WAREHOUSE_DESC_AMY + COD_DESC_AMY, new InsertCommand(expectedOrder));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, InsertCommand.MESSAGE_USAGE);

        // missing TID prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        // missing name prefix
        assertParseFailure(parser, TID_DESC_BOB + VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB
                + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + VALID_ADDRESS_BOB + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        // missing timeStamp prefix
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + VALID_ADDRESS_BOB + VALID_TIMESTAMP_BOB + WAREHOUSE_DESC_BOB, expectedMessage);

        // missing warehouse prefix
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB + COD_DESC_BOB, expectedMessage);

        // missing cashOnDelivery prefix
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TID_BOB + VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB
                + VALID_ADDRESS_BOB + VALID_TIMESTAMP_BOB + VALID_WAREHOUSE_BOB + VALID_COD_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid TID
        assertParseFailure(parser, INVALID_TID_DESC + NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB
                        + WAREHOUSE_DESC_BOB + COD_DESC_BOB + COMMENT_DESC_NIL + TYPE_DESC_PLASTIC
                        + TYPE_DESC_GLASS,
                TransactionId.MESSAGE_CONSTRAINTS);

        // invalid name
        assertParseFailure(parser, TID_DESC_BOB + INVALID_NAME_DESC + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB
                        + WAREHOUSE_DESC_BOB + COD_DESC_BOB + COMMENT_DESC_NIL + TYPE_DESC_PLASTIC + TYPE_DESC_GLASS,
                Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB
                    + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB
                    + COMMENT_DESC_NIL + TYPE_DESC_PLASTIC + TYPE_DESC_GLASS,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC
                        + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB
                        + COMMENT_DESC_NIL + TYPE_DESC_PLASTIC + TYPE_DESC_GLASS,
                Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + INVALID_ADDRESS_DESC + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB
                        + COD_DESC_BOB + COMMENT_DESC_NIL
                        + TYPE_DESC_PLASTIC + TYPE_DESC_GLASS,
                Address.MESSAGE_CONSTRAINTS);

        // invalid timeStamp
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INVALID_DELIVERY_TIMESTAMP_DATE + WAREHOUSE_DESC_BOB + COD_DESC_BOB
                + COMMENT_DESC_NIL + TYPE_DESC_PLASTIC + TYPE_DESC_GLASS, TimeStamp.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INVALID_DELIVERY_TIMESTAMP_DATE_ONLY + WAREHOUSE_DESC_BOB
                + COD_DESC_BOB + COMMENT_DESC_NIL + TYPE_DESC_PLASTIC + TYPE_DESC_GLASS, TimeStamp.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INVALID_DELIVERY_TIMESTAMP_TIME + WAREHOUSE_DESC_BOB + COD_DESC_BOB
                + COMMENT_DESC_NIL + TYPE_DESC_PLASTIC
                + TYPE_DESC_GLASS, TimeStamp.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INVALID_DELIVERY_TIMESTAMP_TIME_ONLY + WAREHOUSE_DESC_BOB + COD_DESC_BOB
                + COMMENT_DESC_NIL + TYPE_DESC_PLASTIC
                + TYPE_DESC_GLASS, TimeStamp.MESSAGE_CONSTRAINTS);

        // invalid warehouse
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB + INVALID_WAREHOUSE_DESC
                        + COD_DESC_BOB + TYPE_DESC_PLASTIC,
                Warehouse.MESSAGE_CONSTRAINTS);

        // invalid cashOnDelivery
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + INVALID_COD_DESC,
                CashOnDelivery.MESSAGE_CONSTRAINTS);

        // invalid comment
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + INVALID_COMMENT_DESC + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB
                        + COD_DESC_BOB + TYPE_DESC_PLASTIC + TYPE_DESC_GLASS,
                Comment.MESSAGE_CONSTRAINTS);

        // invalid item type
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB + COMMENT_DESC_NIL
                + INVALID_TYPE_DESC, TypeOfItem.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, TID_DESC_BOB + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_ADDRESS_DESC + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB
                + COD_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB
                + COMMENT_DESC_NIL + TYPE_DESC_PLASTIC + TYPE_DESC_GLASS,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, InsertCommand.MESSAGE_USAGE));
    }
}
