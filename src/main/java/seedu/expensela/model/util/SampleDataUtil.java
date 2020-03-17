package seedu.expensela.model.util;

import seedu.expensela.model.AddressBook;
import seedu.expensela.model.ReadOnlyAddressBook;
import seedu.expensela.model.transaction.*;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Transaction[] getSamplePersons() {
        return new Transaction[] {
            new Transaction(new Name("Apple AirPods"), new Amount("188", true),
                new Date("2020-02-14"), new Remark("On discount for valentine's"), new Category("SHOPPING")),
            new Transaction(new Name("Sushi"), new Amount("6", true),
                new Date("2020-02-06"), new Remark("Snack after lunch"), new Category("FOOD")),
            new Transaction(new Name("Dominoes Pizza"), new Amount("34", true),
                new Date("2020-02-02"), new Remark("Party with friends"), new Category("FOOD"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Transaction sampleTransaction : getSamplePersons()) {
            sampleAb.addPerson(sampleTransaction);
        }
        return sampleAb;
    }

}
