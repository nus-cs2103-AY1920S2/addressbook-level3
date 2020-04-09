package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COD_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COD_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMMENT_NIL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIMESTAMP_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIMESTAMP_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_PLASTIC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WAREHOUSE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WAREHOUSE_BOB;
import static seedu.address.logic.commands.ImportCommand.OT_ORDER;
import static seedu.address.logic.commands.ImportCommand.OT_RETURN;

/**
 * Contains helper methods for csv file testing.
 */
public class CsvUtil {

    //================================ Delivery Orders without order type =============================================

    public static final String VALID_CSV_ORDER_AMY = new CsvBuilder().withOutOrderTypePrefix(OT_ORDER)
            .withTid(0, 0, 0, VALID_TID_AMY).withName(0, 0, 0, VALID_NAME_AMY).withAddress(0, 0, 0, VALID_ADDRESS_AMY)
            .withPhone(0, 0, 0, VALID_PHONE_AMY).withDeliveryTimeStamp(0, 0, 0, VALID_TIMESTAMP_AMY)
            .withWarehouse(0, 0, 0, VALID_WAREHOUSE_AMY).withCod(0, 0, 0, VALID_COD_AMY)
            .withComment(0, 0, 0, VALID_COMMENT_NIL).withEmail(0, 0, 0, VALID_EMAIL_AMY).build();

    public static final String VALID_CSV_ORDER_BOB = new CsvBuilder().withOutOrderTypePrefix(OT_ORDER)
            .withTid(0, 0, 0, VALID_TID_BOB).withName(0, 0, 0, VALID_NAME_BOB).withAddress(0, 0, 0, VALID_ADDRESS_BOB)
            .withPhone(0, 0, 0, VALID_PHONE_BOB).withDeliveryTimeStamp(0, 0, 0, VALID_TIMESTAMP_BOB)
            .withWarehouse(0, 0, 0, VALID_WAREHOUSE_BOB).withCod(0, 0, 0, VALID_COD_BOB)
            .withComment(0, 0, 0, VALID_COMMENT_NIL).withEmail(0, 0, 0, VALID_EMAIL_BOB).build();

    // Invalid format - order type did not start with order
    public static final String INVALID_CSV_ORDER_AMY = new CsvBuilder()
            .withOutOrderTypePrefix("dsd" + OT_ORDER).withTid(0, 0, 0, VALID_TID_AMY).withName(0, 0, 0, VALID_NAME_AMY)
            .withAddress(0, 0, 0, VALID_ADDRESS_AMY).withPhone(0, 0, 0, VALID_PHONE_AMY)
            .withDeliveryTimeStamp(0, 0, 0, VALID_TIMESTAMP_AMY).withWarehouse(0, 0, 0, VALID_WAREHOUSE_AMY)
            .withCod(0, 0, 0, VALID_COD_AMY).withComment(0, 0, 0, VALID_COMMENT_NIL)
            .withEmail(0, 0, 0, VALID_EMAIL_AMY).withTypeOfItem(0, 0, 0, VALID_TYPE_PLASTIC).build();

    // Invalid format - order type did not equal to order
    public static final String INVALID_CSV_ORDER_BOB = new CsvBuilder()
            .withOutOrderTypePrefix(OT_ORDER + "sdd").withTid(0, 0, 0, VALID_TID_BOB).withName(0, 0, 0, VALID_NAME_BOB)
            .withAddress(0, 0, 0, VALID_ADDRESS_BOB).withPhone(0, 0, 0, VALID_PHONE_BOB)
            .withDeliveryTimeStamp(0, 0, 0, VALID_TIMESTAMP_BOB).withWarehouse(0, 0, 0, VALID_WAREHOUSE_BOB)
            .withCod(0, 0, 0, VALID_COD_BOB).withComment(0, 0, 0, VALID_COMMENT_NIL)
            .withEmail(0, 0, 0, VALID_EMAIL_BOB).withTypeOfItem(0, 0, 0, VALID_TYPE_PLASTIC).build();

    public static final String ADDTIONAL_NAME_CSV_ORDER_AMY = new CsvBuilder()
            .withOutOrderTypePrefix(OT_ORDER).withTid(0, 0, 0, VALID_TID_AMY).withName(0, 0, 0, VALID_NAME_AMY)
            .withAddress(0, 0, 0, VALID_ADDRESS_AMY).withPhone(0, 0, 0, VALID_PHONE_AMY)
            .withDeliveryTimeStamp(0, 0, 0, VALID_TIMESTAMP_AMY).withWarehouse(0, 0, 0, VALID_WAREHOUSE_AMY)
            .withName(0, 0, 0, VALID_NAME_BOB).withCod(0, 0, 0, VALID_COD_AMY)
            .withComment(0, 0, 0, VALID_COMMENT_NIL).withEmail(0, 0, 0, VALID_EMAIL_AMY).build();

    // white space in between any prefix and its value other than ot_order.
    public static final String VALID_WHITESPACE_IN_BETWEEN_ORDER_BOB = new CsvBuilder()
            .withOutOrderTypePrefix(OT_ORDER).withTid(0, 3, 0, VALID_TID_BOB).withName(0, 3, 0, VALID_NAME_BOB)
            .withAddress(0, 3, 0, VALID_ADDRESS_BOB).withPhone(3, 3, 3, VALID_PHONE_BOB)
            .withDeliveryTimeStamp(3, 3, 0, VALID_TIMESTAMP_BOB).withWarehouse(0, 3, 3, VALID_WAREHOUSE_BOB)
            .withCod(0, 3, 0, VALID_COD_BOB).withComment(0, 3, 0, VALID_COMMENT_NIL)
            .withEmail(0, 0, 0, VALID_EMAIL_BOB).build();

    //==================================== Return Orders without order type prefix ====================================

    public static final String VALID_CSV_RETURN_ORDER_AMY = new CsvBuilder()
            .withOutOrderTypePrefix(OT_RETURN).withTid(0, 0, 0, VALID_TID_AMY)
            .withName(0, 0, 0, VALID_NAME_AMY).withAddress(0, 0, 0, VALID_ADDRESS_AMY)
            .withPhone(0, 0, 0, VALID_PHONE_AMY).withReturnTimeStamp(0, 0, 0, VALID_TIMESTAMP_AMY)
            .withWarehouse(0, 0, 0, VALID_WAREHOUSE_AMY).withComment(0, 0, 0, VALID_COMMENT_NIL)
            .withTypeOfItem(0, 0, 0, VALID_TYPE_PLASTIC).withEmail(0, 0, 0, VALID_EMAIL_AMY).build();

    public static final String VALID_CSV_RETURN_ORDER_BOB = new CsvBuilder()
            .withOutOrderTypePrefix(OT_RETURN).withTid(0, 0, 0, VALID_TID_BOB).withName(0, 0, 0, VALID_NAME_BOB)
            .withAddress(0, 0, 0, VALID_ADDRESS_BOB).withPhone(0, 0, 0, VALID_PHONE_BOB)
            .withReturnTimeStamp(0, 0, 0, VALID_TIMESTAMP_BOB).withWarehouse(0, 0, 0, VALID_WAREHOUSE_BOB)
            .withComment(0, 0, 0, VALID_COMMENT_NIL).withEmail(0, 0, 0, VALID_EMAIL_BOB)
            .withTypeOfItem(0, 0, 0, VALID_TYPE_PLASTIC).build();

    // Invalid format - order type did not start with return
    public static final String INVALID_CSV_RETURN_ORDER_AMY = new CsvBuilder()
            .withOutOrderTypePrefix("sds" + OT_RETURN).withTid(0, 0, 0, VALID_TID_AMY).withName(0, 0, 0, VALID_NAME_AMY)
            .withAddress(0, 0, 0, VALID_ADDRESS_AMY).withPhone(0, 0, 0, VALID_PHONE_AMY)
            .withReturnTimeStamp(0, 0, 0, VALID_TIMESTAMP_AMY).withWarehouse(0, 0, 0, VALID_WAREHOUSE_AMY)
            .withComment(0, 0, 0, VALID_COMMENT_NIL).withTypeOfItem(0, 0, 0, VALID_TYPE_PLASTIC)
            .withEmail(0, 0, 0, VALID_EMAIL_AMY).build();

    // Invalid format - order type did not equal to return
    public static final String INVALID_CSV_RETURN_ORDER_BOB = new CsvBuilder()
            .withOutOrderTypePrefix(OT_RETURN + " ds").withTid(0, 0, 0, VALID_TID_BOB).withName(0, 0, 0, VALID_NAME_BOB)
            .withAddress(0, 0, 0, VALID_ADDRESS_BOB).withPhone(0, 0, 0, VALID_PHONE_BOB)
            .withReturnTimeStamp(0, 0, 0, VALID_TIMESTAMP_BOB).withWarehouse(0, 0, 0, VALID_WAREHOUSE_BOB)
            .withComment(0, 0, 0, VALID_COMMENT_NIL).withEmail(0, 0, 0, VALID_EMAIL_BOB)
            .withTypeOfItem(0, 0, 0, VALID_TYPE_PLASTIC).build();

    public static final String ADDTIONAL_NAME_CSV_RETURN_ORDER_AMY = new CsvBuilder()
            .withOutOrderTypePrefix(OT_RETURN).withTid(0, 0, 0, VALID_TID_AMY).withName(0, 0, 0, VALID_NAME_AMY)
            .withAddress(0, 0, 0, VALID_ADDRESS_AMY).withPhone(0, 0, 0, VALID_PHONE_AMY)
            .withReturnTimeStamp(0, 0, 0, VALID_TIMESTAMP_AMY).withWarehouse(0, 0, 0, VALID_WAREHOUSE_AMY)
            .withComment(0, 0, 0, VALID_COMMENT_NIL).withName(0, 0, 0, VALID_NAME_BOB)
            .withTypeOfItem(0, 0, 0, VALID_TYPE_PLASTIC).withEmail(0, 0, 0, VALID_EMAIL_AMY).build();

    // white space in between any prefix and its value other than ot_order.
    public static final String VALID_WHITESPACE_IN_BETWEEN_RETURN_ORDER_BOB = new CsvBuilder()
            .withOutOrderTypePrefix(OT_RETURN).withTid(0, 3, 3, VALID_TID_BOB).withName(0, 3, 0, VALID_NAME_BOB)
            .withAddress(0, 3, 0, VALID_ADDRESS_BOB).withPhone(0, 3, 0, VALID_PHONE_BOB)
            .withEmail(0, 0, 0, VALID_EMAIL_BOB).withReturnTimeStamp(0, 3, 3, VALID_TIMESTAMP_BOB)
            .withWarehouse(0, 3, 3, VALID_WAREHOUSE_BOB).withComment(0, 3, 0, VALID_COMMENT_NIL)
            .withTypeOfItem(0, 0, 0, VALID_TYPE_PLASTIC).build();


}
