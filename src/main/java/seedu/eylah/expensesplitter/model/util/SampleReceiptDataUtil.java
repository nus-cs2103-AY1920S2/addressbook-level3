package seedu.eylah.expensesplitter.model.util;

import java.math.BigDecimal;
import java.util.ArrayList;

import seedu.eylah.expensesplitter.model.ReadOnlyReceiptBook;
import seedu.eylah.expensesplitter.model.ReceiptBook;
import seedu.eylah.expensesplitter.model.item.Item;
import seedu.eylah.expensesplitter.model.item.ItemName;
import seedu.eylah.expensesplitter.model.item.ItemPrice;
import seedu.eylah.expensesplitter.model.person.Amount;
import seedu.eylah.expensesplitter.model.person.Name;
import seedu.eylah.expensesplitter.model.person.Person;
import seedu.eylah.expensesplitter.model.receipt.Entry;
import seedu.eylah.expensesplitter.model.receipt.Receipt;

/**
 * Contains utility methods for populating {@code Receipt} with sample data.
 */
public class SampleReceiptDataUtil {

    public static Receipt[] getSampleReceipt() {
        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<Person> personList = new ArrayList<>();
        personList.add(new Person(new Name("Willy"), new Amount(new BigDecimal("3"))));
        personList.add(new Person(new Name("Shi"), new Amount(new BigDecimal("3"))));
        personList.add(new Person(new Name("Ailan"), new Amount(new BigDecimal("3"))));
        entries.add(new Entry(new Item(new ItemName("pasta"), new ItemPrice(new BigDecimal("9.0")),
                new Amount(new BigDecimal("3"))), personList));
        entries.add(new Entry(new Item(new ItemName("cheese fries"), new ItemPrice(new BigDecimal("9.0")),
                new Amount(new BigDecimal("3"))), personList));

        return new Receipt[] { new Receipt(entries, false) };
    }

    public static ReadOnlyReceiptBook getSampleReceiptBook() {
        ReceiptBook sampleRb = new ReceiptBook();
        for (Receipt sampleReceipt : getSampleReceipt()) {
            sampleRb.addReceipt(sampleReceipt);
        }
        return sampleRb;
    }
}
