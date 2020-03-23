package seedu.address.testutil.product;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_WATCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_BAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_WATCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_BAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_WATCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SALES_BAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SALES_WATCH;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.InventorySystem;
import seedu.address.model.product.Product;

/**
 * A utility class containing a list of {@code Customer} objects to be used in tests.
 */
public class TypicalProducts {

    public static final Product ABACUS = new ProductBuilder().withDescription("Abacus")
            .withPrice("12").withQuantity("1")
            .withSales("94351253").build();
    public static final Product BOOK = new ProductBuilder().withDescription("Beige Book")
            .withPrice("311")
            .withQuantity("2").withSales("98").build();
    public static final Product CAMERA = new ProductBuilder().withDescription("Camera").withPrice("93")
            .withQuantity("2").withSales("123").build();
    public static final Product DISC = new ProductBuilder().withDescription("Disc").withPrice("83")
            .withQuantity("3").withSales("154").build();
    public static final Product EGG = new ProductBuilder().withDescription("Egg").withPrice("94")
            .withQuantity("5").withSales("543").build();
    public static final Product FAN = new ProductBuilder().withDescription("Fan").withPrice("94")
            .withQuantity("4").withSales("678").build();
    public static final Product GAME = new ProductBuilder().withDescription("Game").withPrice("92")
            .withQuantity("8").withSales("468").build();

    // Manually added
    public static final Product HAT = new ProductBuilder().withDescription("Hat").withPrice("84")
            .withQuantity("22").withSales("456").build();
    public static final Product IPAD = new ProductBuilder().withDescription("Ipad").withPrice("81")
            .withQuantity("37").withSales("854").build();

    // Manually added - Customer's details found in {@code CommandTestUtil}
    public static final Product WATCH = new ProductBuilder().withDescription(VALID_DESCRIPTION_WATCH)
            .withPrice(VALID_PRICE_WATCH)
            .withQuantity(VALID_QUANTITY_WATCH).withSales(VALID_SALES_WATCH).build();
    public static final Product BAG = new ProductBuilder().withDescription(VALID_DESCRIPTION_BAG)
            .withPrice(VALID_PRICE_BAG)
            .withQuantity(VALID_QUANTITY_BAG).withSales(VALID_SALES_BAG).build();

    public static final String KEYWORD_MATCHING_BEIGE = "Beige"; // A keyword that matches MEIER

    private TypicalProducts() {} // prevents instantiation

    /**
     * Returns an {@code InventorySystem} with all the typical persons.
     */
    public static InventorySystem getTypicalAddressBook() {
        InventorySystem ab = new InventorySystem();
        for (Product product : getTypicalProducts()) {
            ab.addProduct(product);
        }
        return ab;
    }

    public static List<Product> getTypicalProducts() {
        return new ArrayList<>(Arrays.asList(ABACUS, BAG, BOOK, CAMERA, DISC, EGG, FAN, GAME));
    }
}
