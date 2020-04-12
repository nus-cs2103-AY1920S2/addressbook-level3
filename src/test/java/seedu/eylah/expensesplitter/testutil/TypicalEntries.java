package seedu.eylah.expensesplitter.testutil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import seedu.eylah.expensesplitter.model.ReceiptBook;
import seedu.eylah.expensesplitter.model.item.Item;
import seedu.eylah.expensesplitter.model.person.Person;
import seedu.eylah.expensesplitter.model.receipt.Entry;
import seedu.eylah.expensesplitter.model.receipt.Receipt;

/**
 * A utility class containing a list of {@code Entry} objects to be used in tests.
 */
public class TypicalEntries {

    // default entry
    public static final Entry ENTRY_ONE = new EntryBuilder().build();

    // fields below are to make more entries
    private static final Item PIZZA = new ItemBuilder().withName("pizza")
            .withPrice(new BigDecimal("25"))
            .withAmountPerPerson(new BigDecimal("8.33")).build();
    private static final Item PASTA = new ItemBuilder().withName("Pasta")
            .withPrice(new BigDecimal("5.00")).build();
    private static final Item NOODLES = new ItemBuilder().withName("Noodles")
            .withPrice(new BigDecimal("10.00")).build();
    private static final Item CHICKENRICE = new ItemBuilder().build();
    private static final Person ANNABELLE = new PersonBuilder().withName("Annabelle").build();
    private static final Person BOBBY = new PersonBuilder().withName("Bobby").build();
    private static final Person ANNA = new PersonBuilder().withName("anna")
            .withAmount(new BigDecimal("8.33")).build();
    private static final Person BRANDON = new PersonBuilder().withName("brandon")
            .withAmount(new BigDecimal("8.33")).build();
    private static final Person CHARLIE = new PersonBuilder().withName("charlie")
            .withAmount(new BigDecimal("8.33")).build();
    private static final ArrayList<Person> PERSONS_LIST_TWO = new ArrayList<>(Arrays.asList(ANNABELLE, BOBBY));
    private static final ArrayList<Person> PERSONS_LIST_THREE = new ArrayList<>(Arrays.asList(ANNABELLE));
    private static final ArrayList<Person> PERSONS_LIST_FOUR = new ArrayList<>(Arrays.asList(ANNA));
    private static final ArrayList<Person> PERSONS_LIST_FIVE = new ArrayList<>(Arrays.asList(ANNA, BRANDON, CHARLIE));


    // made entries to simulate receipt
    public static final Entry ENTRY_TWO = new EntryBuilder().withItem(PIZZA)
            .withPersons(PERSONS_LIST_TWO).build();
    public static final Entry ENTRY_THREE = new EntryBuilder().withItem(PASTA)
            .withPersons(PERSONS_LIST_THREE).build();
    public static final Entry ENTRY_FOUR = new EntryBuilder().withItem(NOODLES)
            .withPersons(PERSONS_LIST_THREE).build();
    public static final Entry ENTRY_FIVE = new EntryBuilder().withItem(CHICKENRICE)
            .withPersons(PERSONS_LIST_FOUR).build();
    public static final Entry ENTRY_SIX = new EntryBuilder().withItem(PIZZA)
            .withPersons(PERSONS_LIST_FIVE).build();

    public static ReceiptBook getTypicalReceiptBook() {
        ReceiptBook receiptBook = new ReceiptBook();
        Receipt receipt = new Receipt();
        for (Entry entry : getTypicalEntries()) {
            receipt.addEntry(entry);
        }
        receiptBook.addReceipt(receipt);
        return receiptBook;
    }

    public static ArrayList<Entry> getTypicalEntries() {
        return new ArrayList<>(Arrays.asList(ENTRY_ONE, ENTRY_TWO, ENTRY_THREE, ENTRY_FOUR));
    }

    public static String getTypicalReceiptBookToString() {
        ReceiptBook receiptBook = new ReceiptBook();
        Receipt receipt = new Receipt();
        for (Entry entry : getTypicalEntries()) {
            receipt.addEntry(entry);
        }
        receiptBook.addReceipt(receipt);
        return receiptBook.getReceiptList().get(0).toString();

    }
}
