package seedu.address.testutil.product;

import static seedu.address.logic.commands.CommandTestUtil.VALID_COSTPRICE_BAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COSTPRICE_WATCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_WATCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_BAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_WATCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_BAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_WATCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SALES_BAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SALES_WATCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_THRESHOLD_BAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_THRESHOLD_WATCH;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.InventorySystem;
import seedu.address.model.product.Product;

/**
 * A utility class containing a list of {@code Customer} objects to be used in tests.
 */
public class TypicalProducts {

    public static final String ABACUS_ID = "56d39090-b1b6-4642-9e13-dfa8eae19289";
    public static final String BOOK_ID = "56d39091-b1b6-4642-9e13-dfa8eae19289";
    public static final String CAMERA_ID = "56d39092-b1b6-4642-9e13-dfa8eae19289";
    public static final String DISC_ID = "56d39093-b1b6-4642-9e13-dfa8eae19289";
    public static final String EGG_ID = "56d39094-b1b6-4642-9e13-dfa8eae19289";
    public static final String FAN_ID = "56d39095-b1b6-4642-9e13-dfa8eae19289";
    public static final String GAME_ID = "56d39096-b1b6-4642-9e13-dfa8eae19289";
    public static final String HAT_ID = "56d39097-b1b6-4642-9e13-dfa8eae19289";
    public static final String IPAD_ID = "56d39098-b1b6-4642-9e13-dfa8eae19289";
    public static final String WATCH_ID = "56d39099-b1b6-4642-9e13-dfa8eae19289";
    public static final String BAG_ID = "56d39100-b1b6-4642-9e13-dfa8eae19289";

    public static final Product ABACUS = new ProductBuilder(ABACUS_ID).withDescription("Abacus")
            .withPrice("12").withQuantity("1").withCostPrice("1").withThreshold("1")
            .withMoney("100000").build();
    public static final Product BOOK = new ProductBuilder(BOOK_ID).withDescription("Beige Book")
            .withPrice("311").withCostPrice("1").withThreshold("1")
            .withQuantity("2").withMoney("98").build();
    public static final Product CAMERA = new ProductBuilder(CAMERA_ID).withDescription("Camera").withPrice("93")
            .withQuantity("2").withMoney("123").withThreshold("1").withCostPrice("1").build();
    public static final Product DISC = new ProductBuilder(DISC_ID).withDescription("Disc").withPrice("83")
            .withQuantity("3").withMoney("154").withThreshold("1").withCostPrice("1").build();
    public static final Product EGG = new ProductBuilder(EGG_ID).withDescription("Egg").withPrice("94")
            .withQuantity("5").withMoney("543").withThreshold("1").withCostPrice("1").build();
    public static final Product FAN = new ProductBuilder(FAN_ID).withDescription("Fan").withPrice("94")
            .withQuantity("4").withMoney("678").withThreshold("1").withCostPrice("1").build();
    public static final Product GAME = new ProductBuilder(GAME_ID).withDescription("Game").withPrice("92")
            .withQuantity("8").withMoney("468").withThreshold("1").withCostPrice("1").build();

    // Manually added
    public static final Product HAT = new ProductBuilder(HAT_ID).withDescription("Hat").withPrice("84")
            .withQuantity("22").withMoney("456").withThreshold("1").withCostPrice("1").build();
    public static final Product IPAD = new ProductBuilder(IPAD_ID).withDescription("Ipad").withPrice("81")
            .withQuantity("37").withMoney("854").withThreshold("1").withCostPrice("1").build();

    // Manually added - Customer's details found in {@code CommandTestUtil}
    public static final Product WATCH = new ProductBuilder(WATCH_ID)
            .withDescription(VALID_DESCRIPTION_WATCH)
            .withCostPrice(VALID_COSTPRICE_WATCH)
            .withPrice(VALID_PRICE_WATCH)
            .withQuantity(VALID_QUANTITY_WATCH)
            .withMoney(VALID_SALES_WATCH)
            .withThreshold(VALID_THRESHOLD_WATCH)
            .build();
    public static final Product BAG = new ProductBuilder(BAG_ID)
            .withDescription(VALID_DESCRIPTION_BAG)
            .withCostPrice(VALID_COSTPRICE_BAG)
            .withPrice(VALID_PRICE_BAG)
            .withQuantity(VALID_QUANTITY_BAG)
            .withMoney(VALID_SALES_BAG)
            .withThreshold(VALID_THRESHOLD_BAG)
            .build();

    public static final String KEYWORD_MATCHING_BEIGE = "Beige"; // A keyword that matches MEIER

    private TypicalProducts() {} // prevents instantiation

    /**
     * Returns an {@code InventorySystem} with all the typical persons.
     */
    public static InventorySystem getTypicalInventorySystem() {
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
