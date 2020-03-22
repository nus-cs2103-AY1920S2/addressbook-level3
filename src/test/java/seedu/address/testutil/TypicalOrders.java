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
import seedu.address.model.order.Order;

/**
 * A utility class containing a list of {@code Order} objects to be used in tests.
 */
public class TypicalOrders {

    public static final Order ALICE = new OrderBuilder().withTid("A93939393")
            .withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withTimeStamp("2020-02-20 1500")
            .withWarehouse("5 Toh Guan Rd E, #02-30 S608831")
            .withCash("$1.01")
            .withPhone("94351253")
            .withEmail("example@example.com")
            .withItemType("glass").build();
    public static final Order BENSON = new OrderBuilder().withTid("B8484848")
            .withName("Benson Meier")
            .withEmail("example@example.com")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withTimeStamp("2020-02-20 1500")
            .withWarehouse("5 Toh Guan Rd E, #02-30 S608831")
            .withCash("$5").withPhone("98765432")
            .build();
    public static final Order CARL = new OrderBuilder().withTid("C8483883").withName("Carl Kurz")
            .withPhone("95352563")
            .withEmail("example@example.com")
            .withCash("$1.20").withAddress("wall street").withTimeStamp("2020-02-20 1500")
            .withWarehouse("5 Toh Guan Rd E, #02-30 S608831").build();
    public static final Order DANIEL = new OrderBuilder().withTid("D93939393").withName("Daniel Meier")
            .withPhone("87652533")
            .withEmail("example@example.com")
            .withCash("$5").withAddress("10th street").withTimeStamp("2020-02-20 1500")
            .withWarehouse("5 Toh Guan Rd E, #02-30 S608831").withItemType("bottle").build();
    public static final Order ELLE = new OrderBuilder().withTid("E939393")
            .withName("Elle Meyer")
            .withEmail("example@example.com")
            .withPhone("9482224")
            .withCash("$6").withAddress("michegan ave").withTimeStamp("2020-02-20 1500")
            .withWarehouse("5 Toh Guan Rd E, #02-30 S608831").build();
    public static final Order FIONA = new OrderBuilder().withTid("F01010101")
            .withName("Fiona Kunz")
            .withEmail("example@example.com")
            .withPhone("9482427").withCash("$2")
            .withAddress("little tokyo").withTimeStamp("2020-02-20 1500")
            .withWarehouse("5 Toh Guan Rd E, #02-30 S608831").build();
    public static final Order GEORGE = new OrderBuilder().withTid("G9999").withName("George Best")
            .withPhone("9482442")
            .withEmail("example@example.com")
            .withCash("$6").withAddress("4th street").withTimeStamp("2020-02-20 1500")
            .withWarehouse("5 Toh Guan Rd E, #02-30 S608831").withItemType("drinks").build();

    // Manually added
    public static final Order HOON = new OrderBuilder().withTid("H111111").withName("Hoon Meier")
            .withPhone("8482424")
            .withEmail("example@example.com")
            .withCash("$5").withAddress("little india").withTimeStamp("2020-02-20 1500")
            .withWarehouse("5 Toh Guan Rd E, #02-30 S608831").build();
    public static final Order IDA = new OrderBuilder().withTid("I0000").withName("Ida Mueller")
            .withPhone("8482131")
            .withEmail("example@example.com")
            .withCash("$1").withAddress("chicago ave").withTimeStamp("2020-02-20 1500")
            .withWarehouse("5 Toh Guan Rd E, #02-30 S608831").build();

    // Manually added - Order's details found in {@code CommandTestUtil}
    public static final Order AMY = new OrderBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withTid(VALID_TID_AMY).withAddress(VALID_ADDRESS_AMY)
            .withTimeStamp(VALID_TIMESTAMP_AMY)
            .withCash(VALID_COD_AMY)
            .withComment(VALID_COMMENT_NIL).withWarehouse(VALID_WAREHOUSE_AMY)
            .build();
    public static final Order BOB = new OrderBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withTid(VALID_TID_BOB).withAddress(VALID_ADDRESS_BOB)
            .withTimeStamp(VALID_TIMESTAMP_BOB)
            .withCash(VALID_COD_BOB)
            .withWarehouse(VALID_WAREHOUSE_BOB)
            .withComment(VALID_COMMENT_NIL)
            .build();

    //============================= Return Orders ===================================================

    public static final Order ALICE_RETURN = new OrderBuilder().withTid("A93939393")
            .withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("example@example.com")
            .withTimeStamp("2020-02-20 1500")
            .withWarehouse("5 Toh Guan Rd E, #02-30 S608831")
            .withCash("$1.01")
            .withPhone("94351253")
            .withItemType("glass").build();
    public static final Order BENSON_RETURN = new OrderBuilder().withTid("B8484848")
            .withName("Benson Meier")
            .withEmail("example@example.com")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withTimeStamp("2020-02-20 1500")
            .withWarehouse("5 Toh Guan Rd E, #02-30 S608831")
            .withCash("$5").withPhone("98765432")
            .build();
    public static final Order CARL_RETURN = new OrderBuilder().withTid("C8483883").withName("Carl Kurz")
            .withEmail("example@example.com").withPhone("95352563").withCash("$1.20").withAddress("wall street")
            .withTimeStamp("2020-02-20 1500").withWarehouse("5 Toh Guan Rd E, #02-30 S608831").build();
    public static final Order DANIEL_RETURN = new OrderBuilder().withTid("D93939393").withName("Daniel Meier")
            .withPhone("87652533").withCash("$5").withAddress("10th street").withTimeStamp("2020-02-20 1500")
            .withEmail("example@example.com").withWarehouse("5 Toh Guan Rd E, #02-30 S608831").withItemType("bottle")
            .build();
    public static final Order ELLE_RETURN = new OrderBuilder().withTid("E939393")
            .withName("Elle Meyer").withPhone("9482224").withEmail("example@example.com")
            .withCash("$6").withAddress("michegan ave").withTimeStamp("2020-02-20 1500")
            .withWarehouse("5 Toh Guan Rd E, #02-30 S608831").build();
    public static final Order FIONA_RETURN = new OrderBuilder().withTid("F01010101")
            .withName("Fiona Kunz").withPhone("9482427").withCash("$2").withEmail("example@example.com")
            .withAddress("little tokyo").withTimeStamp("2020-02-20 1500")
            .withWarehouse("5 Toh Guan Rd E, #02-30 S608831").build();
    public static final Order GEORGE_RETURN = new OrderBuilder().withTid("G9999").withName("George Best")
            .withPhone("9482442").withCash("$6").withAddress("4th street").withTimeStamp("2020-02-20 1500")
            .withWarehouse("5 Toh Guan Rd E, #02-30 S608831").withItemType("drinks").withEmail("example@example.com")
            .build();

    // Manually added
    public static final Order HOON_RETURN = new OrderBuilder().withTid("H111111").withName("Hoon Meier")
            .withPhone("8482424").withCash("$5").withAddress("little india").withTimeStamp("2020-02-20 1500")
            .withWarehouse("5 Toh Guan Rd E, #02-30 S608831").withEmail("example@example.com").build();
    public static final Order IDA_RETURN = new OrderBuilder().withTid("I0000").withName("Ida Mueller")
            .withPhone("8482131").withCash("$1").withAddress("chicago ave").withTimeStamp("2020-02-20 1500")
            .withWarehouse("5 Toh Guan Rd E, #02-30 S608831").withEmail("example@example.com").build();

    // Manually added - Order's details found in {@code CommandTestUtil}
    public static final Order AMY_RETURN = new OrderBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withTid(VALID_TID_AMY).withAddress(VALID_ADDRESS_AMY).withTimeStamp(VALID_TIMESTAMP_AMY)
            .withCash(VALID_COD_AMY).withEmail(VALID_EMAIL_AMY)
            .withComment(VALID_COMMENT_NIL).withWarehouse(VALID_WAREHOUSE_AMY)
            .build();
    public static final Order BOB_RETURN = new OrderBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withTid(VALID_TID_BOB).withAddress(VALID_ADDRESS_BOB).withTimeStamp(VALID_TIMESTAMP_BOB)
            .withCash(VALID_COD_BOB).withEmail(VALID_EMAIL_BOB)
            .withWarehouse(VALID_WAREHOUSE_BOB)
            .withComment(VALID_COMMENT_NIL)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalOrders() {} // prevents instantiation

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

    /**
     * Returns an {@code OrderBook} with all the typical return orders.
     */
    public static OrderBook getTypicalReturnOrderBook() {
        OrderBook ab = new OrderBook();
        for (Order order : getTypicalReturnOrders()) {
            ab.addOrder(order);
        }
        return ab;
    }

    public static List<Order> getTypicalReturnOrders() {
        return new ArrayList<>(Arrays.asList(ALICE_RETURN, BENSON_RETURN, CARL_RETURN, DANIEL_RETURN,
                ELLE_RETURN, FIONA_RETURN, GEORGE_RETURN));
    }

    public static List<Order> getTypicalOrders() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
