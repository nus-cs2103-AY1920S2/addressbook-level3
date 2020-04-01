package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.ReturnOrderBook;
import seedu.address.model.parcel.returnorder.ReturnOrder;

/**
 * A utility class containing a list of {@code ReturnOrder} objects to be used in tests.
 */
public class TypicalReturnOrders {
    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    public static final ReturnOrder ALICE_RETURN = new ReturnOrderBuilder().withTid("A93939393")
            .withName("Alice Pauline")
            .withAddress("123 Jurong West Ave 6 #08-111 S649520")
            .withEmail("example@example.com")
            .withTimeStamp("2020-05-20 1500")
            .withWarehouse("5 Toh Guan Rd E #02-30 S608831")
            .withPhone("94351253")
            .withItemType("glass")
            .build();

    public static final ReturnOrder BENSON_RETURN = new ReturnOrderBuilder().withTid("B8484848")
            .withAddress("311 Clementi Ave 2 #02-25 S120363")
            .withName("Benson Meier")
            .withEmail("example@example.com")
            .withTimeStamp("2020-05-20 1500")
            .withWarehouse("5 Toh Guan Rd E #02-30 S608831")
            .withPhone("98765432")
            .build();

    public static final ReturnOrder CARL_RETURN = new ReturnOrderBuilder().withTid("C8483883")
            .withName("Carl Kurz")
            .withEmail("example@example.com")
            .withPhone("95352563")
            .withAddress("Telok Blangah Heights #01-22 S100058")
            .withTimeStamp("2020-05-20 1500")
            .withWarehouse("5 Toh Guan Rd E #02-30 S608831")
            .build();

    public static final ReturnOrder DANIEL_RETURN = new ReturnOrderBuilder().withTid("D93939393")
            .withName("Daniel Meier")
            .withPhone("87652533")
            .withAddress("Seletar S797580")
            .withTimeStamp("2020-05-20 1500")
            .withEmail("example@example.com")
            .withWarehouse("5 Toh Guan Rd E #02-30 S608831")
            .withItemType("bottle")
            .build();

    public static final ReturnOrder ELLE_RETURN = new ReturnOrderBuilder().withTid("E939393")
            .withName("Elle Meyer")
            .withPhone("9482224")
            .withEmail("example@example.com")
            .withAddress("Upper Thomson Road S787130")
            .withTimeStamp("2020-05-20 1500")
            .withWarehouse("5 Toh Guan Rd E #02-30 S608831")
            .build();

    public static final ReturnOrder FIONA_RETURN = new ReturnOrderBuilder().withTid("F01010101")
            .withName("Fiona Kunz")
            .withPhone("9482427")
            .withEmail("example@example.com")
            .withAddress("Jurong S600101")
            .withTimeStamp("2020-05-20 1500")
            .withWarehouse("5 Toh Guan Rd E #02-30 S608831")
            .build();

    public static final ReturnOrder GEORGE_RETURN = new ReturnOrderBuilder().withTid("G9999")
            .withName("George Best")
            .withPhone("9482442")
            .withAddress("Little India S218202")
            .withTimeStamp("2020-05-20 1500")
            .withWarehouse("5 Toh Guan Rd E #02-30 S608831")
            .withItemType("drinks")
            .withEmail("example@example.com")
            .build();

    // Manually added
    public static final ReturnOrder HOON_RETURN = new ReturnOrderBuilder().withTid("H111111")
            .withName("Hoon Meier")
            .withPhone("8482424")
            .withAddress("little india S218202")
            .withTimeStamp("2020-05-20 1500")
            .withWarehouse("5 Toh Guan Rd E #02-30 S608831")
            .withEmail("example@example.com")
            .build();

    public static final ReturnOrder IDA_RETURN = new ReturnOrderBuilder().withTid("I0000")
            .withName("Ida Mueller")
            .withPhone("8482131")
            .withAddress("Macpherson Road Chengkek S369225")
            .withTimeStamp("2020-05-20 1500")
            .withWarehouse("5 Toh Guan Rd E #02-30 S608831")
            .withEmail("example@example.com")
            .build();

    // Manually added - Order's details found in {@code CommandTestUtil}
    public static final ReturnOrder AMY_RETURN = new ReturnOrderBuilder()
            .withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY)
            .withTid(VALID_TID_AMY)
            .withAddress(VALID_ADDRESS_AMY)
            .withTimeStamp(VALID_TIMESTAMP_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withComment(VALID_COMMENT_NIL)
            .withItemType(VALID_TYPE_PLASTIC)
            .withWarehouse(VALID_WAREHOUSE_AMY)
            .build();

    public static final ReturnOrder BOB_RETURN = new ReturnOrderBuilder().withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withTid(VALID_TID_BOB)
            .withAddress(VALID_ADDRESS_BOB)
            .withTimeStamp(VALID_TIMESTAMP_BOB)
            .withEmail(VALID_EMAIL_BOB)
            .withWarehouse(VALID_WAREHOUSE_BOB)
            .withComment(VALID_COMMENT_NIL)
            .withItemType(VALID_TYPE_PLASTIC)
            .build();
    // Extra Bob name after Amy name
    public static final ReturnOrder ADDITIONAL_NAME_AMY_RETURN = new ReturnOrderBuilder()
            .withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withName(VALID_NAME_BOB)
            .withTid(VALID_TID_AMY).withAddress(VALID_ADDRESS_AMY).withTimeStamp(VALID_TIMESTAMP_AMY)
            .withEmail(VALID_EMAIL_AMY).withItemType(VALID_TYPE_PLASTIC)
            .withComment(VALID_COMMENT_NIL).withWarehouse(VALID_WAREHOUSE_AMY)
            .build();

    private TypicalReturnOrders() {
    } // prevents instantiation

    /**
     * Returns an {@code ReturnOrderBook} with all the typical return orders.
     */
    public static ReturnOrderBook getTypicalReturnOrderBook() {
        ReturnOrderBook ab = new ReturnOrderBook();
        for (ReturnOrder returnOrder : getTypicalReturnOrders()) {
            ab.addReturnOrder(returnOrder);
        }
        return ab;
    }

    public static List<ReturnOrder> getTypicalReturnOrders() {
        return new ArrayList<>(Arrays.asList(ALICE_RETURN, BENSON_RETURN, CARL_RETURN, DANIEL_RETURN,
                ELLE_RETURN, FIONA_RETURN, GEORGE_RETURN));
    }
}
