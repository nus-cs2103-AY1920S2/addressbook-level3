package seedu.eylah.expensesplitter.testutil;

import java.math.BigDecimal;

import seedu.eylah.expensesplitter.model.item.Item;

/**
 * A utility class containing a list of {@code Item} objects to be used in tests.
 */
public class TypicalItem {

    public static final Item BEERTOWER = new ItemBuilder().withName("beertower")
            .withPrice(new BigDecimal("19.90")).build();
    public static final Item STEAMBOAT = new ItemBuilder().withName("steamboat")
            .withPrice(new BigDecimal("39.90")).build();
    public static final Item POPCORN = new ItemBuilder().withName("popcorn")
            .withPrice(new BigDecimal("9.85")).build();
    public static final Item CHICKENRICE = new ItemBuilder().withName("Chicken Rice")
            .withPrice(new BigDecimal("3.50")).build();
    public static final Item PIZZA = new ItemBuilder().withName("pizza")
            .withPrice(new BigDecimal("25")).build();


    /**
     * Prevents public from instantiating a Typical Item.
     */
    private TypicalItem () {

    }
}
