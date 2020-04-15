package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.InventorySystem;
import seedu.address.model.ReadOnlyInventorySystem;
import seedu.address.model.customer.Address;
import seedu.address.model.customer.Customer;
import seedu.address.model.customer.Email;
import seedu.address.model.customer.Name;
import seedu.address.model.customer.Phone;
import seedu.address.model.product.CostPrice;
import seedu.address.model.product.Price;
import seedu.address.model.product.Product;
import seedu.address.model.product.ProductQuantity;
import seedu.address.model.tag.Tag;
import seedu.address.model.transaction.DateTime;
import seedu.address.model.transaction.Transaction;

/**
 * Contains utility methods for populating {@code InventorySystem} with sample data.
 */
public class SampleDataUtil {

    public static final Customer ALEX_YEOH = new Customer(new Name("Alex Yeoh"), new Phone("87438807"),
            new Email("alexyeoh@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
            getTagSet("friends"));

    public static final Customer BERNICE_YU = new Customer(new Name("Bernice Yu"), new Phone("99272758"),
            new Email("berniceyu@example.com"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
            getTagSet("colleagues", "friends"));

    public static final Customer CHARLOTTE = new Customer(new Name("Charlotte Oliveiro"), new Phone("93210283"),
            new Email("charlotte@example.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
            getTagSet("neighbours"));

    public static final Customer DAVID_LI = new Customer(new Name("David Li"), new Phone("91031282"),
            new Email("lidavid@example.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
            getTagSet("family"));

    public static final Customer IRFAN = new Customer(new Name("Irfan Ibrahim"), new Phone("92492021"),
            new Email("irfan@example.com"), new Address("Blk 47 Tampines Street 20, #17-35"),
            getTagSet("classmates"));

    public static final Customer ROY = new Customer(new Name("Roy Balakrishnan"), new Phone("92624417"),
            new Email("royb@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31"),
            getTagSet("colleagues"));

    public static final Customer VIVIAN_TAN = new Customer(new Name("Vivian Tan"), new Phone("86724359"),
            new Email("viviantan@example.com"), new Address("Blk 239 Tampines Street 31, #08-21"),
            getTagSet("classmates", "colleagues"));

    public static final Customer MARCUS_NEO = new Customer(new Name("Marcus Neo"), new Phone("95482145"),
            new Email("marcusneo@mail.com"), new Address("Blk 834 Bukit Panjang Street 19, #12-08"),
            getTagSet("celebrity"));

    public static final Product IPAD_PRO = new Product(new Description("iPad Pro"), new CostPrice("599"),
            new Price("1299"), new ProductQuantity(29), new Money(14289),
            new QuantityThreshold("8"), 0.725);

    public static final Product AIRPODS_PRO = new Product(new Description("Airpods Pro"), new CostPrice("129"),
            new Price("379"), new ProductQuantity(96), new Money(1516),
            new QuantityThreshold("20"), 0.96);

    public static final Product IPHONE_X = new Product(new Description("iPhone X"), new CostPrice("349"),
            new Price("1199"), new ProductQuantity(60), new Money(Money.DEFAULT_VALUE),
            new QuantityThreshold("12"), 1);

    public static final Product IPHONE_8PLUS = new Product(new Description("iPhone 8 Plus"), new CostPrice("299"),
            new Price("950"), new ProductQuantity(48), new Money(1900),
            new QuantityThreshold("10"), 0.96);

    public static final Product MACBOOK_AIR = new Product(new Description("Macbook Air"), new CostPrice("680"),
            new Price("1449"), new ProductQuantity(29), new Money(1499),
            new QuantityThreshold("6"), 0.967);

    public static final Product APPLE_WATCH = new Product(new Description("Apple Watch"), new CostPrice("109"),
            new Price("440"), new ProductQuantity(80), new Money(Money.DEFAULT_VALUE),
            new QuantityThreshold("16"), 1);

    public static final Product APPLE_PENCIL = new Product(new Description("Apple Pencil"), new CostPrice("59"),
            new Price("189"), new ProductQuantity(100), new Money(Money.DEFAULT_VALUE),
            new QuantityThreshold("20"), 1);

    public static Customer[] getSamplePersons() {
        return new Customer[] {
            ALEX_YEOH, BERNICE_YU, CHARLOTTE, DAVID_LI, IRFAN, VIVIAN_TAN, MARCUS_NEO
        };
    }

    public static Product[] getSampleProducts() {
        return new Product[] {
            IPAD_PRO, AIRPODS_PRO, IPHONE_X, IPHONE_8PLUS, MACBOOK_AIR, APPLE_WATCH, APPLE_PENCIL
        };
    }

    public static Transaction[] getSampleTransactions() {
        return new Transaction[] {
            new Transaction(ALEX_YEOH, IPAD_PRO, ALEX_YEOH.getId(), IPAD_PRO.getId(),
                new DateTime(DateTime.DEFAULT_VALUE), new ProductQuantity("2"), new Money("2598"),
                new Description("N/A")),
            new Transaction(BERNICE_YU, IPHONE_8PLUS, BERNICE_YU.getId(), IPHONE_8PLUS.getId(),
                new DateTime(DateTime.DEFAULT_VALUE), new ProductQuantity("2"), new Money("1900"),
                new Description("N/A")),
            new Transaction(DAVID_LI, IPAD_PRO, DAVID_LI.getId(), IPAD_PRO.getId(),
                new DateTime(DateTime.DEFAULT_VALUE), new ProductQuantity("4"), new Money("5196"),
                new Description("N/A")),
            new Transaction(VIVIAN_TAN, MACBOOK_AIR, VIVIAN_TAN.getId(), MACBOOK_AIR.getId(),
                new DateTime(DateTime.DEFAULT_VALUE), new ProductQuantity("1"), new Money("1449"),
                new Description("N/A")),
            new Transaction(IRFAN, AIRPODS_PRO, IRFAN.getId(), AIRPODS_PRO.getId(),
                new DateTime(DateTime.DEFAULT_VALUE), new ProductQuantity("4"), new Money("1516"),
                new Description("N/A")),
            new Transaction(CHARLOTTE, IPAD_PRO, CHARLOTTE.getId(), IPAD_PRO.getId(),
                new DateTime(DateTime.DEFAULT_VALUE), new ProductQuantity("5"), new Money("6495"),
                new Description("N/A"))
        };
    }

    public static ReadOnlyInventorySystem getSampleInventorySystem() {
        InventorySystem sampleAb = new InventorySystem();
        for (Customer sampleCustomer : getSamplePersons()) {
            sampleAb.addPerson(sampleCustomer);
        }
        for (Product sampleProduct : getSampleProducts()) {
            sampleAb.addProduct(sampleProduct);
        }
        for (Transaction sampleTransaction : getSampleTransactions()) {
            sampleAb.addTransaction(sampleTransaction);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
