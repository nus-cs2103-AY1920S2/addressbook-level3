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
 * A utility class containing a list of {@code Receipt} objects to be used in tests.
 */
public class TypicalReceipt {

    // default receipt
    public static final Receipt RECEIPT_ONE = new ReceiptBuilder().build();

    // fields below are to make more receipt
    private static final Item PASTA = new ItemBuilder().withName("pasta")
            .withPrice(new BigDecimal("15"))
            .withAmountPerPerson(new BigDecimal("5")).build();
    private static final Item LAZIJI = new ItemBuilder().withName("laziji")
            .withPrice(new BigDecimal("20"))
            .withAmountPerPerson(new BigDecimal("10")).build();
    private static final Item SOUP = new ItemBuilder().withName("soup")
            .withPrice(new BigDecimal("14"))
            .withAmountPerPerson(new BigDecimal("4.57")).build();

    private static final Person WILLY1 = new PersonBuilder().withName("willy")
            .withAmount(new BigDecimal("5")).build();
    private static final Person XIONG1 = new PersonBuilder().withName("xiong")
            .withAmount(new BigDecimal("5")).build();
    private static final Person AILAN1 = new PersonBuilder().withName("ailan")
            .withAmount(new BigDecimal("5")).build();
    private static final Person XIONG2 = new PersonBuilder().withName("xiong")
            .withAmount(new BigDecimal("10")).build();
    private static final Person AILAN2 = new PersonBuilder().withName("ailan")
            .withAmount(new BigDecimal("10")).build();
    private static final Person XIONG3 = new PersonBuilder().withName("xiong")
            .withAmount(new BigDecimal("4.67")).build();
    private static final Person AILAN3 = new PersonBuilder().withName("ailan")
            .withAmount(new BigDecimal("4.67")).build();
    private static final Person WILLY2 = new PersonBuilder().withName("willy")
            .withAmount(new BigDecimal("4.67")).build();

    private static final ArrayList<Person> PASTA_PERSON_LIST = new ArrayList<>(Arrays.asList(XIONG1, WILLY1, AILAN1));
    private static final ArrayList<Person> LAZIJI_PERSON_LIST = new ArrayList<>(Arrays.asList(XIONG2, AILAN2));
    private static final ArrayList<Person> SOUP_PERSON_LIST = new ArrayList<>(Arrays.asList(XIONG3, AILAN3, WILLY2));

    // made entries to simulate receipt
    public static final Entry PASTA_ENTRY = new EntryBuilder().withItem(PASTA)
            .withPersons(PASTA_PERSON_LIST).build();
    public static final Entry LAZIJI_ENTRY = new EntryBuilder().withItem(LAZIJI)
            .withPersons(LAZIJI_PERSON_LIST).build();
    public static final Entry SOUP_ENTRY = new EntryBuilder().withItem(SOUP)
            .withPersons(SOUP_PERSON_LIST).build();


    private TypicalReceipt() {} // prevents instantiation

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
        return new ArrayList<>(Arrays.asList(PASTA_ENTRY, LAZIJI_ENTRY, SOUP_ENTRY));
    }
}
