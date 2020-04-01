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
import static seedu.address.logic.commands.CommandTestUtil.VALID_WAREHOUSE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WAREHOUSE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.OrderBook;
import seedu.address.model.Parcel.order.Order;

/**
 * A utility class containing a list of {@code Order} objects to be used in tests.
 */
public class TypicalOrders {
    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    public static final Order ALICE = new OrderBuilder()
            .withTid("A93939393")
            .withName("Alice Pauline")
            .withAddress("123 Jurong West Ave 6 #08-111 S649520")
            .withTimeStamp("2020-05-20 1500")
            .withWarehouse("5 Toh Guan Rd E #02-30 S608831")
            .withCash("$1.01")
            .withPhone("94351253")
            .withEmail("example@example.com")
            .withItemType("glass").build();
    public static final Order BENSON = new OrderBuilder().withTid("B8484848")
            .withName("Benson Meier")
            .withEmail("example@example.com")
            .withAddress("311 Clementi Ave 2 #02-25 S120363")
            .withTimeStamp("2020-05-20 1500")
            .withWarehouse("5 Toh Guan Rd E #02-30 S608831")
            .withCash("$5").withPhone("98765432")
            .build();
    public static final Order CARL = new OrderBuilder().withTid("C8483883")
            .withName("Carl Kurz")
            .withPhone("95352563")
            .withEmail("example@example.com")
            .withCash("$1.20")
            .withAddress("Telok Blangah Heights #01-22 S100058")
            .withTimeStamp("2020-05-20 1500")
            .withWarehouse("5 Toh Guan Rd E #02-30 S608831").build();
    public static final Order DANIEL = new OrderBuilder().withTid("D93939393")
            .withName("Daniel Meier")
            .withPhone("87652533")
            .withEmail("example@example.com")
            .withCash("$5")
            .withAddress("Seletar S797580")
            .withTimeStamp("2020-05-20 1500")
            .withWarehouse("5 Toh Guan Rd E #02-30 S608831")
            .withItemType("bottle").build();
    public static final Order ELLE = new OrderBuilder().withTid("E939393")
            .withName("Elle Meyer")
            .withPhone("9482224")
            .withEmail("example@example.com")
            .withCash("$6")
            .withAddress("Upper Thomson Road S787130")
            .withTimeStamp("2020-05-20 1500")
            .withWarehouse("5 Toh Guan Rd E #02-30 S608831").build();
    public static final Order FIONA = new OrderBuilder().withTid("F01010101")
            .withName("Fiona Kunz")
            .withPhone("9482427")
            .withEmail("example@example.com")
            .withCash("$2")
            .withAddress("Jurong S600101")
            .withTimeStamp("2020-05-20 1500")
            .withWarehouse("5 Toh Guan Rd E #02-30 S608831").build();
    public static final Order GEORGE = new OrderBuilder().withTid("G9999")
            .withName("George Best")
            .withPhone("9482442")
            .withEmail("example@example.com")
            .withCash("$6")
            .withAddress("Little India S218202")
            .withTimeStamp("2020-05-20 1500")
            .withWarehouse("5 Toh Guan Rd E #02-30 S608831").withItemType("drinks").build();

    // Manually added
    public static final Order HOON = new OrderBuilder().withTid("H111111")
            .withName("Hoon Meier")
            .withPhone("8482424")
            .withEmail("example@example.com")
            .withCash("$5")
            .withAddress("little india S218202")
            .withTimeStamp("2020-05-20 1500")
            .withWarehouse("5 Toh Guan Rd E #02-30 S608831").build();
    public static final Order IDA = new OrderBuilder().withTid("I0000")
            .withName("Ida Mueller")
            .withPhone("8482131")
            .withEmail("example@example.com")
            .withCash("$1")
            .withAddress("Macpherson Road Chengkek S369225")
            .withTimeStamp("2020-05-20 1500")
            .withWarehouse("5 Toh Guan Rd E #02-30 S608831").build();

    // Manually added - Order's details found in {@code CommandTestUtil}
    public static final Order AMY = new OrderBuilder().withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withTid(VALID_TID_AMY)
            .withAddress(VALID_ADDRESS_AMY)
            .withTimeStamp(VALID_TIMESTAMP_AMY)
            .withCash(VALID_COD_AMY)
            .withComment(VALID_COMMENT_NIL)
            .withWarehouse(VALID_WAREHOUSE_AMY)
            .build();
    public static final Order BOB = new OrderBuilder().withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB)
            .withTid(VALID_TID_BOB)
            .withAddress(VALID_ADDRESS_BOB)
            .withTimeStamp(VALID_TIMESTAMP_BOB)
            .withCash(VALID_COD_BOB)
            .withWarehouse(VALID_WAREHOUSE_BOB)
            .withComment(VALID_COMMENT_NIL)
            .build();

    public static final Order ADDITIONAL_NAME_AMY = new OrderBuilder().withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
            .withTid(VALID_TID_AMY).withAddress(VALID_ADDRESS_AMY)
            .withTimeStamp(VALID_TIMESTAMP_AMY).withCash(VALID_COD_AMY)
            .withComment(VALID_COMMENT_NIL).withWarehouse(VALID_WAREHOUSE_AMY)
            .withName(VALID_NAME_BOB).build();

    public TypicalOrders() {
        //prevents instantiation
    }

    /**
     * Returns an {@code OrderBook} with all the typical orders.
     */
    public static OrderBook getTypicalOrderBook() {
        OrderBook ob = new OrderBook();
        for (Order order : getTypicalOrders()) {
            ob.addOrder(order);
        }
        return ob;
    }

    public static List<Order> getTypicalOrders() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
