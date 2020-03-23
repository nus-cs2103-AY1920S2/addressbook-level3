package seedu.address.storage;

import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.COD_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.COD_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.COMMENT_DESC_INSTRUCTION;
import static seedu.address.logic.commands.CommandTestUtil.DELIVERY_TIMESTAMP_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DELIVERY_TIMESTAMP_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TID_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TID_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_PLASTIC;
import static seedu.address.logic.commands.CommandTestUtil.WAREHOUSE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.WAREHOUSE_DESC_BOB;
import static seedu.address.logic.commands.ImportCommand.OT_ORDER;

import seedu.address.testutil.CsvBuilder;

/**
 * Contains helper methods for csv file testing.
 */
public class CsvUtil {

    public static final String VALID_CSV_ORDER_AMY = new CsvBuilder().withOrderType(0, 0, 0, OT_ORDER)
        .withTid(0, 0, 0, TID_DESC_AMY).withName(0, 0, 0, NAME_DESC_AMY).withAddress(0, 0, 0, ADDRESS_DESC_AMY)
        .withPhone(0, 0, 0, PHONE_DESC_AMY).withDeliveryTimeStamp(0, 0, 0, DELIVERY_TIMESTAMP_DESC_AMY)
        .withWarehouse(0, 0, 0, WAREHOUSE_DESC_AMY).withCod(0, 0, 0, COD_DESC_AMY)
        .withComment(0, 0, 0, COMMENT_DESC_INSTRUCTION).withTypeOfItem(0, 0, 0, TYPE_DESC_PLASTIC).build();

    public static final String VALID_CSV_ORDER_BOB = new CsvBuilder().withOrderType(0, 0, 0, OT_ORDER)
            .withTid(0, 0, 0, TID_DESC_BOB).withName(0, 0, 0, NAME_DESC_BOB).withAddress(0, 0, 0, ADDRESS_DESC_BOB)
            .withPhone(0, 0, 0, PHONE_DESC_BOB).withDeliveryTimeStamp(0, 0, 0, DELIVERY_TIMESTAMP_DESC_BOB)
            .withWarehouse(0, 0, 0, WAREHOUSE_DESC_BOB).withCod(0, 0, 0, COD_DESC_BOB)
            .withComment(0, 0, 0, COMMENT_DESC_INSTRUCTION).withTypeOfItem(0, 0, 0, TYPE_DESC_PLASTIC).build();

    // Invalid format - white spaces in between order type prefix and value of the order type.
    public static final String INVALID_CSV_ORDER_AMY = new CsvBuilder().withOrderType(0, 3, 0, OT_ORDER)
            .withTid(0, 0, 0, TID_DESC_AMY).withName(0, 0, 0, NAME_DESC_AMY).withAddress(0, 0, 0, ADDRESS_DESC_AMY)
            .withPhone(0, 0, 0, PHONE_DESC_AMY).withDeliveryTimeStamp(0, 0, 0, DELIVERY_TIMESTAMP_DESC_AMY)
            .withWarehouse(0, 0, 0, WAREHOUSE_DESC_AMY).withCod(0, 0, 0, COD_DESC_AMY)
            .withComment(0, 0, 0, COMMENT_DESC_INSTRUCTION).withTypeOfItem(0, 0, 0, TYPE_DESC_PLASTIC).build();

    // Invalid format - white spaces in between order type prefix and value of the order type.
    public static final String INVALID_CSV_ORDER_BOB = new CsvBuilder().withOrderType(0, 3, 0, OT_ORDER)
            .withTid(0, 0, 0, TID_DESC_BOB).withName(0, 0, 0, NAME_DESC_BOB).withAddress(0, 0, 0, ADDRESS_DESC_BOB)
            .withPhone(0, 0, 0, PHONE_DESC_BOB).withDeliveryTimeStamp(0, 0, 0, DELIVERY_TIMESTAMP_DESC_BOB)
            .withWarehouse(0, 0, 0, WAREHOUSE_DESC_BOB).withCod(0, 0, 0, COD_DESC_BOB)
            .withComment(0, 0, 0, COMMENT_DESC_INSTRUCTION).withTypeOfItem(0, 0, 0, TYPE_DESC_PLASTIC).build();

    public static final String ADDTIONAL_NAME_CSV_ORDER_AMY = new CsvBuilder().withOrderType(0, 0, 0, OT_ORDER)
            .withTid(0, 0, 0, TID_DESC_AMY).withName(0, 0, 0, NAME_DESC_AMY).withAddress(0, 0, 0, ADDRESS_DESC_AMY)
            .withPhone(0, 0, 0, PHONE_DESC_AMY).withDeliveryTimeStamp(0, 0, 0, DELIVERY_TIMESTAMP_DESC_AMY)
            .withWarehouse(0, 0, 0, WAREHOUSE_DESC_AMY).withName(0, 0, 0, NAME_DESC_BOB).withCod(0, 0, 0, COD_DESC_AMY)
            .withComment(0, 0, 0, COMMENT_DESC_INSTRUCTION).withTypeOfItem(0, 0, 0, TYPE_DESC_PLASTIC).build();

    // white space in between any prefix and its value other than ot_order.
    public static final String VALID_WHITESPACE_IN_BETWEEN_ORDER_BOB = new CsvBuilder().withOrderType(0, 3, 0, OT_ORDER)
            .withTid(0, 3, 0, TID_DESC_BOB).withName(0, 3, 0, NAME_DESC_BOB).withAddress(0, 3, 0, ADDRESS_DESC_BOB)
            .withPhone(0, 3, 0, PHONE_DESC_BOB).withDeliveryTimeStamp(0, 3, 0, DELIVERY_TIMESTAMP_DESC_BOB)
            .withWarehouse(0, 3, 0, WAREHOUSE_DESC_BOB).withCod(0, 3, 0, COD_DESC_BOB)
            .withComment(0, 3, 0, COMMENT_DESC_INSTRUCTION).withTypeOfItem(0, 3, 0, TYPE_DESC_PLASTIC).build();

}
