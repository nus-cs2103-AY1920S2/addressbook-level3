package seedu.delino.logic.parser;

import static seedu.delino.commons.core.Messages.MESSAGE_EMPTY_PREFIXES;
import static seedu.delino.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.delino.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.delino.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.delino.logic.commands.CommandTestUtil.COD_DESC_AMY;
import static seedu.delino.logic.commands.CommandTestUtil.COD_DESC_BOB;
import static seedu.delino.logic.commands.CommandTestUtil.COMMENT_DESC_INSTRUCTION;
import static seedu.delino.logic.commands.CommandTestUtil.COMMENT_DESC_NIL;
import static seedu.delino.logic.commands.CommandTestUtil.DELIVERY_TIMESTAMP_DESC_AMY;
import static seedu.delino.logic.commands.CommandTestUtil.DELIVERY_TIMESTAMP_DESC_BOB;
import static seedu.delino.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.delino.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.delino.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.delino.logic.commands.CommandTestUtil.INVALID_COD_DESC;
import static seedu.delino.logic.commands.CommandTestUtil.INVALID_COMMENT_DESC;
import static seedu.delino.logic.commands.CommandTestUtil.INVALID_DELIVERY_TIMESTAMP_DATE;
import static seedu.delino.logic.commands.CommandTestUtil.INVALID_DELIVERY_TIMESTAMP_DATE_ONLY;
import static seedu.delino.logic.commands.CommandTestUtil.INVALID_DELIVERY_TIMESTAMP_TIME;
import static seedu.delino.logic.commands.CommandTestUtil.INVALID_DELIVERY_TIMESTAMP_TIME_ONLY;
import static seedu.delino.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.delino.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.delino.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.delino.logic.commands.CommandTestUtil.INVALID_TID_DESC;
import static seedu.delino.logic.commands.CommandTestUtil.INVALID_TYPE_DESC;
import static seedu.delino.logic.commands.CommandTestUtil.INVALID_WAREHOUSE_DESC;
import static seedu.delino.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.delino.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.delino.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.delino.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.delino.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.delino.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.delino.logic.commands.CommandTestUtil.TID_DESC_AMY;
import static seedu.delino.logic.commands.CommandTestUtil.TID_DESC_BOB;
import static seedu.delino.logic.commands.CommandTestUtil.TYPE_DESC_GLASS;
import static seedu.delino.logic.commands.CommandTestUtil.TYPE_DESC_METAL;
import static seedu.delino.logic.commands.CommandTestUtil.TYPE_DESC_PLASTIC;
import static seedu.delino.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.delino.logic.commands.CommandTestUtil.VALID_COD_BOB;
import static seedu.delino.logic.commands.CommandTestUtil.VALID_COMMENT_INSTRUCTION;
import static seedu.delino.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.delino.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.delino.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.delino.logic.commands.CommandTestUtil.VALID_TID_BOB;
import static seedu.delino.logic.commands.CommandTestUtil.VALID_TIMESTAMP_BOB;
import static seedu.delino.logic.commands.CommandTestUtil.VALID_TYPE_GLASS;
import static seedu.delino.logic.commands.CommandTestUtil.VALID_TYPE_METAL;
import static seedu.delino.logic.commands.CommandTestUtil.VALID_WAREHOUSE_BOB;
import static seedu.delino.logic.commands.CommandTestUtil.WAREHOUSE_DESC_AMY;
import static seedu.delino.logic.commands.CommandTestUtil.WAREHOUSE_DESC_BOB;
import static seedu.delino.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.delino.logic.parser.CliSyntax.PREFIX_COD;
import static seedu.delino.logic.parser.CliSyntax.PREFIX_DELIVERY_TIMESTAMP;
import static seedu.delino.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.delino.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.delino.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.delino.logic.parser.CliSyntax.PREFIX_TID;
import static seedu.delino.logic.parser.CliSyntax.PREFIX_WAREHOUSE;
import static seedu.delino.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.delino.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.delino.testutil.TypicalOrders.AMY;
import static seedu.delino.testutil.TypicalOrders.BOB;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.delino.logic.commands.InsertCommand;
import seedu.delino.model.parcel.optionalparcelattributes.Comment;
import seedu.delino.model.parcel.optionalparcelattributes.TypeOfItem;
import seedu.delino.model.parcel.order.CashOnDelivery;
import seedu.delino.model.parcel.order.Order;
import seedu.delino.model.parcel.parcelattributes.Address;
import seedu.delino.model.parcel.parcelattributes.Email;
import seedu.delino.model.parcel.parcelattributes.Name;
import seedu.delino.model.parcel.parcelattributes.Phone;
import seedu.delino.model.parcel.parcelattributes.TimeStamp;
import seedu.delino.model.parcel.parcelattributes.TransactionId;
import seedu.delino.model.parcel.parcelattributes.Warehouse;
import seedu.delino.testutil.OrderBuilder;
//@@author Amoscheong97
public class InsertCommandParserTest {

    public static final String NEWLINE_INSERT = "\n%1$s";
    public static final String NEWLINE = "\n";
    private InsertCommandParser parser = new InsertCommandParser();
    private String emptyMessage = MESSAGE_EMPTY_PREFIXES;
    private String missingTid;
    private String missingName;
    private String missingPhone;
    private String missingEmail;
    private String missingAddress;
    private String missingTimeStamp;
    private String missingWarehouse;
    private String missingCash;
    private String missingAll;

    @BeforeEach
    public void init() {
        missingTid = MESSAGE_EMPTY_PREFIXES + PREFIX_TID + NEWLINE_INSERT;
        missingName = MESSAGE_EMPTY_PREFIXES + PREFIX_NAME + NEWLINE_INSERT;
        missingPhone = MESSAGE_EMPTY_PREFIXES + PREFIX_PHONE + NEWLINE_INSERT;
        missingEmail = MESSAGE_EMPTY_PREFIXES + PREFIX_EMAIL + NEWLINE_INSERT;
        missingAddress = MESSAGE_EMPTY_PREFIXES + PREFIX_ADDRESS + NEWLINE_INSERT;
        missingTimeStamp = MESSAGE_EMPTY_PREFIXES + PREFIX_DELIVERY_TIMESTAMP + NEWLINE_INSERT;
        missingWarehouse = MESSAGE_EMPTY_PREFIXES + PREFIX_WAREHOUSE + NEWLINE_INSERT;
        missingCash = MESSAGE_EMPTY_PREFIXES + PREFIX_COD + NEWLINE_INSERT;
        missingAll = MESSAGE_EMPTY_PREFIXES
                + PREFIX_TID
                + NEWLINE
                + PREFIX_NAME
                + NEWLINE
                + PREFIX_PHONE
                + NEWLINE
                + PREFIX_EMAIL
                + NEWLINE
                + PREFIX_ADDRESS
                + NEWLINE
                + PREFIX_DELIVERY_TIMESTAMP
                + NEWLINE
                + PREFIX_WAREHOUSE
                + NEWLINE
                + PREFIX_COD
                + NEWLINE_INSERT;
    }

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
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB
                        + COD_DESC_BOB + COMMENT_DESC_NIL + TYPE_DESC_GLASS,
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
    public void parse_commentFieldMissing_success() {
        // only has itemType
        Order expectedOrder = new OrderBuilder(AMY).withItemType(VALID_TYPE_METAL).build();
        assertParseSuccess(parser, TID_DESC_AMY + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + DELIVERY_TIMESTAMP_DESC_AMY + WAREHOUSE_DESC_AMY + COD_DESC_AMY + TYPE_DESC_METAL,
                new InsertCommand(expectedOrder));
    }

    @Test
    public void parse_itemTypeMissing_success() {
        // only has comment
        Order expectedOrder = new OrderBuilder(AMY).withComment(VALID_COMMENT_INSTRUCTION).build();
        assertParseSuccess(parser, TID_DESC_AMY + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + DELIVERY_TIMESTAMP_DESC_AMY + WAREHOUSE_DESC_AMY + COD_DESC_AMY + COMMENT_DESC_INSTRUCTION,
                new InsertCommand(expectedOrder));
    }

    @Test
    public void parse_oneCompulsoryFieldMissing_failure() {
        String expectedMessage = String.format(missingTid, InsertCommand.MESSAGE_USAGE);
        //----------------------------- One Prefix Missing----------------------------------------------------------

        // missing TID prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        // missing name prefix
        expectedMessage = String.format(missingName, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, TID_DESC_BOB + VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        // missing phone prefix
        expectedMessage = String.format(missingPhone, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        // missing email prefix
        expectedMessage = String.format(missingEmail, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB
                + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        // missing address prefix
        expectedMessage = String.format(missingAddress, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + VALID_ADDRESS_BOB + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        // missing timeStamp prefix
        expectedMessage = String.format(missingTimeStamp, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + VALID_TIMESTAMP_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        // missing warehouse prefix
        expectedMessage = String.format(missingWarehouse, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB + COD_DESC_BOB, expectedMessage);

        // missing cashOnDelivery prefix
        expectedMessage = String.format(missingCash, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB, expectedMessage);
    }
    @Test
    public void parse_twoCompulsoryFieldsMissing_failure() {
        //-------------------------------- Two prefixes missing -------------------------------------------------------
        String expectedMessage;
        //missing TID and Address prefixes
        String tidAndAddress = emptyMessage + PREFIX_TID + NEWLINE + PREFIX_ADDRESS + NEWLINE_INSERT;
        expectedMessage = String.format(tidAndAddress, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB
                + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        //missing TID and Email prefixes
        String tidAndEmail = emptyMessage + PREFIX_TID + NEWLINE + PREFIX_EMAIL + NEWLINE_INSERT;
        expectedMessage = String.format(tidAndEmail, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB
                + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        //missing TID and Name prefixes
        String tidAndName = emptyMessage + PREFIX_TID + NEWLINE + PREFIX_NAME + NEWLINE_INSERT;
        expectedMessage = String.format(tidAndName, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        //missing TID and Phone prefixes
        String tidAndPhone = emptyMessage + PREFIX_TID + NEWLINE + PREFIX_PHONE + NEWLINE_INSERT;
        expectedMessage = String.format(tidAndPhone, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        //missing TID and TimeStamp prefixes
        String tidAndTimeStamp = emptyMessage + PREFIX_TID + NEWLINE + PREFIX_DELIVERY_TIMESTAMP + NEWLINE_INSERT;
        expectedMessage = String.format(tidAndTimeStamp, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        //missing TID and Warehouse prefixes
        String tidAndWarehouse = emptyMessage + PREFIX_TID + NEWLINE + PREFIX_WAREHOUSE + NEWLINE_INSERT;
        expectedMessage = String.format(tidAndWarehouse, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + DELIVERY_TIMESTAMP_DESC_BOB + VALID_WAREHOUSE_BOB + COD_DESC_BOB, expectedMessage);

        //missing TID and CashOnDelivery prefixes
        String tidAndCod = emptyMessage + PREFIX_TID + NEWLINE + PREFIX_COD + NEWLINE_INSERT;
        expectedMessage = String.format(tidAndCod, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + VALID_COD_BOB, expectedMessage);

        //missing Name and Phone prefixes
        String nameAndPhone = emptyMessage + PREFIX_NAME + NEWLINE + PREFIX_PHONE + NEWLINE_INSERT;
        expectedMessage = String.format(nameAndPhone, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, TID_DESC_BOB + VALID_NAME_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        //missing Name and Email prefixes
        String nameAndEmail = emptyMessage + PREFIX_NAME + NEWLINE + PREFIX_EMAIL + NEWLINE_INSERT;
        expectedMessage = String.format(nameAndEmail, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, TID_DESC_BOB + VALID_NAME_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB
                + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);


        //missing Name and Address prefixes
        String nameAndAddress = emptyMessage + PREFIX_NAME + NEWLINE + PREFIX_ADDRESS + NEWLINE_INSERT;
        expectedMessage = String.format(nameAndAddress, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, TID_DESC_BOB + VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB
                + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        //missing Name and TimeStamp prefixes
        String nameAndTimeStamp = emptyMessage + PREFIX_NAME + NEWLINE + PREFIX_DELIVERY_TIMESTAMP + NEWLINE_INSERT;
        expectedMessage = String.format(nameAndTimeStamp, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, TID_DESC_BOB + VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        //missing Name and CashOnDelivery prefixes
        String nameAndCashOnDelivery = emptyMessage + PREFIX_NAME + NEWLINE + PREFIX_COD + NEWLINE_INSERT;
        expectedMessage = String.format(nameAndCashOnDelivery, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, TID_DESC_BOB + VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + VALID_COD_BOB, expectedMessage);

        //missing Email and Address prefixes
        String emailAndAddress = emptyMessage + PREFIX_EMAIL + NEWLINE + PREFIX_ADDRESS + NEWLINE_INSERT;
        expectedMessage = String.format(emailAndAddress, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        //missing Email and Timestamp prefixes
        String emailAndTimestamp = emptyMessage + PREFIX_EMAIL + NEWLINE + PREFIX_DELIVERY_TIMESTAMP + NEWLINE_INSERT;
        expectedMessage = String.format(emailAndTimestamp, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB
                + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        //missing Email and Warehouse prefixes
        String emailAndWarehouse = emptyMessage + PREFIX_EMAIL + NEWLINE + PREFIX_WAREHOUSE + NEWLINE_INSERT;
        expectedMessage = String.format(emailAndWarehouse, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB
                + DELIVERY_TIMESTAMP_DESC_BOB + VALID_WAREHOUSE_BOB + COD_DESC_BOB, expectedMessage);

        //missing Email and Cash prefixes
        String emailAndCod = emptyMessage + PREFIX_EMAIL + NEWLINE + PREFIX_COD + NEWLINE_INSERT;
        expectedMessage = String.format(emailAndCod, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB
                + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB, expectedMessage);

        //missing Address and Timestamp prefixes
        String addressAndTimestamp = emptyMessage + PREFIX_ADDRESS + NEWLINE + PREFIX_DELIVERY_TIMESTAMP
                + NEWLINE_INSERT;
        expectedMessage = String.format(addressAndTimestamp, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB
                + VALID_TIMESTAMP_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        //missing Address and Warehouse prefixes
        String addressAndWarehouse = emptyMessage + PREFIX_ADDRESS + NEWLINE + PREFIX_WAREHOUSE
                + NEWLINE_INSERT;
        expectedMessage = String.format(addressAndWarehouse, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB
                + DELIVERY_TIMESTAMP_DESC_BOB + VALID_WAREHOUSE_BOB + COD_DESC_BOB, expectedMessage);

        //missing Address and Cash prefixes
        String addressAndCash = emptyMessage + PREFIX_ADDRESS + NEWLINE + PREFIX_COD
                + NEWLINE_INSERT;
        expectedMessage = String.format(addressAndCash, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB
                + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + VALID_COD_BOB, expectedMessage);

        //missing TimeStamp and CashOnDelivery prefixes
        String timeStampAndCashOnDelivery = emptyMessage + PREFIX_DELIVERY_TIMESTAMP + NEWLINE
                + PREFIX_COD + NEWLINE_INSERT;
        expectedMessage = String.format(timeStampAndCashOnDelivery, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + WAREHOUSE_DESC_BOB, expectedMessage);

        //missing TimeStamp and Warehouse prefixes
        String timeStampAndWarehouse = emptyMessage + PREFIX_DELIVERY_TIMESTAMP + NEWLINE
                + PREFIX_WAREHOUSE + NEWLINE_INSERT;
        expectedMessage = String.format(timeStampAndWarehouse, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + COD_DESC_BOB, expectedMessage);

        //missing Warehouse and Cash prefixes
        String warehouseAndCash = emptyMessage + PREFIX_WAREHOUSE + NEWLINE + PREFIX_COD
                + NEWLINE_INSERT;
        expectedMessage = String.format(addressAndTimestamp, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB
                + VALID_TIMESTAMP_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);
    }

    @Test
    public void parse_threeCompulsoryFieldsMissing_failure() {
        //-------------------------------- Three prefixes missing -----------------------------------------------------
        String expectedMessage;

        // Missing TID, Name and Phone prefixes
        String tidNamePhone = emptyMessage + PREFIX_TID + NEWLINE
                + PREFIX_NAME + NEWLINE + PREFIX_PHONE + NEWLINE_INSERT;
        expectedMessage = String.format(tidNamePhone, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, VALID_TID_BOB + VALID_NAME_BOB + VALID_PHONE_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB
                + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);


        // Missing TID, Name and Email prefixes
        String tidNameEmail = emptyMessage + PREFIX_TID + NEWLINE
                + PREFIX_NAME + NEWLINE + PREFIX_EMAIL + NEWLINE_INSERT;
        expectedMessage = String.format(tidNameEmail, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, VALID_TID_BOB + VALID_NAME_BOB + PHONE_DESC_BOB
                + VALID_EMAIL_BOB + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB
                + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        // Missing TID, Name and Address prefixes
        String tidNameAddress = emptyMessage + PREFIX_TID + NEWLINE
                + PREFIX_NAME + NEWLINE + PREFIX_ADDRESS + NEWLINE_INSERT;
        expectedMessage = String.format(tidNameAddress, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, VALID_TID_BOB + VALID_NAME_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + VALID_ADDRESS_BOB + DELIVERY_TIMESTAMP_DESC_BOB
                + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        // Missing TID, Name and Timestamp prefixes
        String tidNameTimestamp = emptyMessage + PREFIX_TID + NEWLINE
                + PREFIX_NAME + NEWLINE + PREFIX_DELIVERY_TIMESTAMP + NEWLINE_INSERT;
        expectedMessage = String.format(tidNameTimestamp, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, VALID_TID_BOB + VALID_NAME_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + VALID_TIMESTAMP_BOB
                + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        // Missing TID, Name and Warehouse prefixes
        String tidNameWarehouse = emptyMessage + PREFIX_TID + NEWLINE
                + PREFIX_NAME + NEWLINE + PREFIX_WAREHOUSE + NEWLINE_INSERT;
        expectedMessage = String.format(tidNameWarehouse, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, VALID_TID_BOB + VALID_NAME_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB
                + VALID_WAREHOUSE_BOB + COD_DESC_BOB, expectedMessage);

        // Missing TID, Name and Cash prefixes
        String tidNameCash = emptyMessage + PREFIX_TID + NEWLINE
                + PREFIX_NAME + NEWLINE + PREFIX_COD + NEWLINE_INSERT;
        expectedMessage = String.format(tidNameCash, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, VALID_TID_BOB + VALID_NAME_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB
                + WAREHOUSE_DESC_BOB + VALID_COD_BOB, expectedMessage);

        // Missing TID, Phone and Email prefixes
        String tidPhoneEmail = emptyMessage + PREFIX_TID + NEWLINE
                + PREFIX_PHONE + NEWLINE + PREFIX_EMAIL + NEWLINE_INSERT;
        expectedMessage = String.format(tidPhoneEmail, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, VALID_TID_BOB + NAME_DESC_BOB + VALID_PHONE_BOB
                + VALID_EMAIL_BOB + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB
                + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        // Missing TID, Phone and Address prefixes
        String tidPhoneAddress = emptyMessage + PREFIX_TID + NEWLINE
                + PREFIX_PHONE + NEWLINE + PREFIX_ADDRESS + NEWLINE_INSERT;
        expectedMessage = String.format(tidPhoneAddress, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, VALID_TID_BOB + NAME_DESC_BOB + VALID_PHONE_BOB
                + EMAIL_DESC_BOB + VALID_ADDRESS_BOB + DELIVERY_TIMESTAMP_DESC_BOB
                + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        // Missing TID, Phone and Timestamp prefixes
        String tidPhoneTimestamp = emptyMessage + PREFIX_TID + NEWLINE
                + PREFIX_PHONE + NEWLINE + PREFIX_DELIVERY_TIMESTAMP + NEWLINE_INSERT;
        expectedMessage = String.format(tidPhoneTimestamp, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, VALID_TID_BOB + NAME_DESC_BOB + VALID_PHONE_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + VALID_TIMESTAMP_BOB
                + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        // Missing TID, Phone and Warehouse prefixes
        String tidPhoneWarehouse = emptyMessage + PREFIX_TID + NEWLINE
                + PREFIX_PHONE + NEWLINE + PREFIX_WAREHOUSE + NEWLINE_INSERT;
        expectedMessage = String.format(tidPhoneWarehouse, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, VALID_TID_BOB + NAME_DESC_BOB + VALID_PHONE_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB
                + VALID_WAREHOUSE_BOB + COD_DESC_BOB, expectedMessage);

        // Missing TID, Phone and Cash prefixes
        String tidPhoneCash = emptyMessage + PREFIX_TID + NEWLINE
                + PREFIX_PHONE + NEWLINE + PREFIX_COD + NEWLINE_INSERT;
        expectedMessage = String.format(tidPhoneCash, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, VALID_TID_BOB + NAME_DESC_BOB + VALID_PHONE_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB
                + WAREHOUSE_DESC_BOB + VALID_COD_BOB, expectedMessage);

        // Missing TID, Email and Address prefixes
        String tidEmailAddress = emptyMessage + PREFIX_TID + NEWLINE
                + PREFIX_EMAIL + NEWLINE + PREFIX_ADDRESS + NEWLINE_INSERT;
        expectedMessage = String.format(tidEmailAddress, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, VALID_TID_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + VALID_EMAIL_BOB + VALID_ADDRESS_BOB + DELIVERY_TIMESTAMP_DESC_BOB
                + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        // Missing TID, Email and Timestamp prefixes
        String tidEmailTimestamp = emptyMessage + PREFIX_TID + NEWLINE
                + PREFIX_EMAIL + NEWLINE + PREFIX_DELIVERY_TIMESTAMP + NEWLINE_INSERT;
        expectedMessage = String.format(tidEmailTimestamp, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, VALID_TID_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + VALID_EMAIL_BOB + ADDRESS_DESC_BOB + VALID_TIMESTAMP_BOB
                + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        // Missing TID, Email and Warehouse prefixes
        String tidEmailWarehouse = emptyMessage + PREFIX_TID + NEWLINE
                + PREFIX_EMAIL + NEWLINE + PREFIX_WAREHOUSE + NEWLINE_INSERT;
        expectedMessage = String.format(tidEmailWarehouse, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, VALID_TID_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + VALID_EMAIL_BOB + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB
                + VALID_WAREHOUSE_BOB + COD_DESC_BOB, expectedMessage);

        // Missing TID, Email and Cash prefixes
        String tidEmailCash = emptyMessage + PREFIX_TID + NEWLINE
                + PREFIX_EMAIL + NEWLINE + PREFIX_COD + NEWLINE_INSERT;
        expectedMessage = String.format(tidEmailCash, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, VALID_TID_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + VALID_EMAIL_BOB + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB
                + WAREHOUSE_DESC_BOB + VALID_COD_BOB, expectedMessage);

        // Missing TID, Address and Timestamp prefixes
        String tidAddressTimestamp = emptyMessage + PREFIX_TID + NEWLINE
                + PREFIX_ADDRESS + NEWLINE + PREFIX_DELIVERY_TIMESTAMP + NEWLINE_INSERT;
        expectedMessage = String.format(tidAddressTimestamp, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, VALID_TID_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + VALID_ADDRESS_BOB + VALID_TIMESTAMP_BOB
                + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        // Missing TID, Address and Warehouse prefixes
        String tidAddressWarehouse = emptyMessage + PREFIX_TID + NEWLINE
                + PREFIX_ADDRESS + NEWLINE + PREFIX_WAREHOUSE + NEWLINE_INSERT;
        expectedMessage = String.format(tidAddressWarehouse, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, VALID_TID_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + VALID_ADDRESS_BOB + DELIVERY_TIMESTAMP_DESC_BOB
                + VALID_WAREHOUSE_BOB + COD_DESC_BOB, expectedMessage);

        // Missing TID, Address and Cash prefixes
        String tidAddressCod = emptyMessage + PREFIX_TID + NEWLINE
                + PREFIX_ADDRESS + NEWLINE + PREFIX_COD + NEWLINE_INSERT;
        expectedMessage = String.format(tidAddressCod, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, VALID_TID_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + VALID_ADDRESS_BOB + DELIVERY_TIMESTAMP_DESC_BOB
                + WAREHOUSE_DESC_BOB + VALID_COD_BOB, expectedMessage);

        // Missing TID, Timestamp, Warehouse prefixes
        String tidTimestampWarehouse = emptyMessage + PREFIX_TID + NEWLINE
                + PREFIX_DELIVERY_TIMESTAMP + NEWLINE + PREFIX_WAREHOUSE + NEWLINE_INSERT;
        expectedMessage = String.format(tidTimestampWarehouse, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, VALID_TID_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + VALID_TIMESTAMP_BOB
                + VALID_WAREHOUSE_BOB + COD_DESC_BOB, expectedMessage);

        // Missing TID, Timestamp, Cash prefixes
        String tidTimestampCash = emptyMessage + PREFIX_TID + NEWLINE
                + PREFIX_DELIVERY_TIMESTAMP + NEWLINE + PREFIX_COD + NEWLINE_INSERT;
        expectedMessage = String.format(tidTimestampCash, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, VALID_TID_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + VALID_TIMESTAMP_BOB
                + WAREHOUSE_DESC_BOB + VALID_COD_BOB, expectedMessage);

        // Missing TID, Warehouse, Cash prefixes
        String tidWarehouseCash = emptyMessage + PREFIX_TID + NEWLINE
                + PREFIX_WAREHOUSE + NEWLINE + PREFIX_COD + NEWLINE_INSERT;
        expectedMessage = String.format(tidWarehouseCash, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, VALID_TID_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB
                + VALID_WAREHOUSE_BOB + VALID_COD_BOB, expectedMessage);

        // Missing Name, Phone, Email prefixes
        String namePhoneEmail = emptyMessage + PREFIX_NAME + NEWLINE
                + PREFIX_PHONE + NEWLINE + PREFIX_EMAIL + NEWLINE_INSERT;
        expectedMessage = String.format(namePhoneEmail, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, TID_DESC_BOB + VALID_NAME_BOB + VALID_PHONE_BOB
                + VALID_EMAIL_BOB + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB
                + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        // Missing Name, Phone, Address prefixes
        String namePhoneAddress = emptyMessage + PREFIX_NAME + NEWLINE
                + PREFIX_PHONE + NEWLINE + PREFIX_ADDRESS + NEWLINE_INSERT;
        expectedMessage = String.format(namePhoneAddress, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, TID_DESC_BOB + VALID_NAME_BOB + VALID_PHONE_BOB
                + EMAIL_DESC_BOB + VALID_ADDRESS_BOB + DELIVERY_TIMESTAMP_DESC_BOB
                + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        // Missing Name, Phone, Timestamp prefixes
        String namePhoneTimestamp = emptyMessage + PREFIX_NAME + NEWLINE
                + PREFIX_PHONE + NEWLINE + PREFIX_DELIVERY_TIMESTAMP + NEWLINE_INSERT;
        expectedMessage = String.format(namePhoneTimestamp, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, TID_DESC_BOB + VALID_NAME_BOB + VALID_PHONE_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + VALID_TIMESTAMP_BOB
                + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        // Missing Name, Phone, Warehouse prefixes
        String namePhoneWarehouse = emptyMessage + PREFIX_NAME + NEWLINE
                + PREFIX_PHONE + NEWLINE + PREFIX_WAREHOUSE + NEWLINE_INSERT;
        expectedMessage = String.format(namePhoneWarehouse, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, TID_DESC_BOB + VALID_NAME_BOB + VALID_PHONE_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB
                + VALID_WAREHOUSE_BOB + COD_DESC_BOB, expectedMessage);

        // Missing Name, Phone, Cash prefixes
        String namePhoneCash = emptyMessage + PREFIX_NAME + NEWLINE
                + PREFIX_PHONE + NEWLINE + PREFIX_COD + NEWLINE_INSERT;
        expectedMessage = String.format(namePhoneCash, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, TID_DESC_BOB + VALID_NAME_BOB + VALID_PHONE_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB
                + WAREHOUSE_DESC_BOB + VALID_COD_BOB, expectedMessage);

        // Missing Name, Email, Address prefixes
        String nameEmailAddress = emptyMessage + PREFIX_NAME + NEWLINE
                + PREFIX_EMAIL + NEWLINE + PREFIX_ADDRESS + NEWLINE_INSERT;
        expectedMessage = String.format(nameEmailAddress, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, TID_DESC_BOB + VALID_NAME_BOB + PHONE_DESC_BOB
                + VALID_EMAIL_BOB + VALID_ADDRESS_BOB + DELIVERY_TIMESTAMP_DESC_BOB
                + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        // Missing Name, Email, Timestamp prefixes
        String nameEmailTimestamp = emptyMessage + PREFIX_NAME + NEWLINE
                + PREFIX_EMAIL + NEWLINE + PREFIX_DELIVERY_TIMESTAMP + NEWLINE_INSERT;
        expectedMessage = String.format(nameEmailTimestamp, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, TID_DESC_BOB + VALID_NAME_BOB + PHONE_DESC_BOB
                + VALID_EMAIL_BOB + ADDRESS_DESC_BOB + VALID_TIMESTAMP_BOB
                + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        // Missing Name, Email, Warehouse prefixes
        String nameEmailWarehouse = emptyMessage + PREFIX_NAME + NEWLINE
                + PREFIX_EMAIL + NEWLINE + PREFIX_WAREHOUSE + NEWLINE_INSERT;
        expectedMessage = String.format(nameEmailWarehouse, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, TID_DESC_BOB + VALID_NAME_BOB + PHONE_DESC_BOB
                + VALID_EMAIL_BOB + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB
                + VALID_WAREHOUSE_BOB + COD_DESC_BOB, expectedMessage);

        // Missing Name, Email, Cash prefixes
        String nameEmailCod = emptyMessage + PREFIX_NAME + NEWLINE
                + PREFIX_EMAIL + NEWLINE + PREFIX_COD + NEWLINE_INSERT;
        expectedMessage = String.format(nameEmailCod, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, TID_DESC_BOB + VALID_NAME_BOB + PHONE_DESC_BOB
                + VALID_EMAIL_BOB + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB
                + WAREHOUSE_DESC_BOB + VALID_COD_BOB, expectedMessage);

        // Missing Email, Address and Warehouse prefixes
        String emailAddressWarehouse = emptyMessage + PREFIX_EMAIL + NEWLINE
                + PREFIX_ADDRESS + NEWLINE + PREFIX_WAREHOUSE + NEWLINE_INSERT;
        expectedMessage = String.format(emailAddressWarehouse, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + VALID_EMAIL_BOB + VALID_ADDRESS_BOB + DELIVERY_TIMESTAMP_DESC_BOB
                + VALID_WAREHOUSE_BOB + COD_DESC_BOB, expectedMessage);

        // Missing Email, Address and Cash prefixes
        String emailAddressCod = emptyMessage + PREFIX_EMAIL + NEWLINE
                + PREFIX_ADDRESS + NEWLINE + PREFIX_COD + NEWLINE_INSERT;
        expectedMessage = String.format(emailAddressCod, InsertCommand.MESSAGE_USAGE);
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + VALID_EMAIL_BOB + VALID_ADDRESS_BOB + DELIVERY_TIMESTAMP_DESC_BOB
                + WAREHOUSE_DESC_BOB + VALID_COD_BOB, expectedMessage);

    }

    @Test
    public void parse_allCompulsoryFieldMissing_failure() {
        String expectedMessage;

        // all prefixes missing
        expectedMessage = String.format(missingAll, InsertCommand.MESSAGE_USAGE);
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
                TransactionId.MESSAGE_CONSTRAINTS + NEWLINE);

        // invalid name
        assertParseFailure(parser, TID_DESC_BOB + INVALID_NAME_DESC + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB
                        + WAREHOUSE_DESC_BOB + COD_DESC_BOB + COMMENT_DESC_NIL + TYPE_DESC_PLASTIC + TYPE_DESC_GLASS,
                Name.MESSAGE_CONSTRAINTS + NEWLINE);

        // invalid phone
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB
                        + COMMENT_DESC_NIL + TYPE_DESC_PLASTIC + TYPE_DESC_GLASS,
                Phone.MESSAGE_CONSTRAINTS + NEWLINE);

        // invalid email
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC
                        + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB
                        + COMMENT_DESC_NIL + TYPE_DESC_PLASTIC + TYPE_DESC_GLASS,
                Email.MESSAGE_CONSTRAINTS + NEWLINE);

        // invalid address
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + INVALID_ADDRESS_DESC + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB
                        + COD_DESC_BOB + COMMENT_DESC_NIL
                        + TYPE_DESC_PLASTIC + TYPE_DESC_GLASS,
                Address.MESSAGE_CONSTRAINTS + NEWLINE);

        // invalid timeStamp
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INVALID_DELIVERY_TIMESTAMP_DATE + WAREHOUSE_DESC_BOB + COD_DESC_BOB
                + COMMENT_DESC_NIL + TYPE_DESC_PLASTIC + TYPE_DESC_GLASS, TimeStamp.MESSAGE_CONSTRAINTS + NEWLINE);

        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INVALID_DELIVERY_TIMESTAMP_DATE_ONLY + WAREHOUSE_DESC_BOB
                + COD_DESC_BOB + COMMENT_DESC_NIL + TYPE_DESC_PLASTIC
                + TYPE_DESC_GLASS, TimeStamp.MESSAGE_CONSTRAINTS + NEWLINE);

        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INVALID_DELIVERY_TIMESTAMP_TIME + WAREHOUSE_DESC_BOB + COD_DESC_BOB
                + COMMENT_DESC_NIL + TYPE_DESC_PLASTIC
                + TYPE_DESC_GLASS, TimeStamp.MESSAGE_CONSTRAINTS + NEWLINE);

        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INVALID_DELIVERY_TIMESTAMP_TIME_ONLY + WAREHOUSE_DESC_BOB + COD_DESC_BOB
                + COMMENT_DESC_NIL + TYPE_DESC_PLASTIC
                + TYPE_DESC_GLASS, TimeStamp.MESSAGE_CONSTRAINTS + NEWLINE);

        // invalid warehouse
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB + INVALID_WAREHOUSE_DESC
                        + COD_DESC_BOB + TYPE_DESC_PLASTIC,
                Warehouse.MESSAGE_CONSTRAINTS + NEWLINE);

        // invalid cashOnDelivery
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + INVALID_COD_DESC,
                CashOnDelivery.MESSAGE_CONSTRAINTS + NEWLINE);

        // invalid comment
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + INVALID_COMMENT_DESC + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB
                        + COD_DESC_BOB + TYPE_DESC_PLASTIC + TYPE_DESC_GLASS,
                Comment.MESSAGE_CONSTRAINTS + NEWLINE);

        // invalid item type
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB + COMMENT_DESC_NIL
                + INVALID_TYPE_DESC, TypeOfItem.MESSAGE_CONSTRAINTS + NEWLINE);

        // two invalid values, both invalid values reported
        assertParseFailure(parser, TID_DESC_BOB + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_ADDRESS_DESC + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB
                + COD_DESC_BOB, Name.MESSAGE_CONSTRAINTS + NEWLINE + Address.MESSAGE_CONSTRAINTS + NEWLINE);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB
                        + COD_DESC_BOB + COMMENT_DESC_NIL + TYPE_DESC_PLASTIC + TYPE_DESC_GLASS,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, InsertCommand.MESSAGE_USAGE));
    }
}
