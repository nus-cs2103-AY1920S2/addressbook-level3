package seedu.expensela.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.expensela.model.AddressBook;
import seedu.expensela.model.ReadOnlyAddressBook;
import seedu.expensela.model.tag.Tag;
import seedu.expensela.model.transaction.Amount;
import seedu.expensela.model.transaction.Date;
import seedu.expensela.model.transaction.Name;
import seedu.expensela.model.transaction.Transaction;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Transaction[] getSamplePersons() {
        return new Transaction[] {
            new Transaction(new Name("Alex Yeoh"), new Amount("87438807", true),
                new Date("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Transaction(new Name("Bernice Yu"), new Amount("99272758", true),
                new Date("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Transaction(new Name("Charlotte Oliveiro"), new Amount("93210283", true),
                new Date("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Transaction(new Name("David Li"), new Amount("91031282", true),
                new Date("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Transaction(new Name("Irfan Ibrahim"), new Amount("92492021", true),
                new Date("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Transaction(new Name("Roy Balakrishnan"), new Amount("92624417", true),
                new Date("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Transaction sampleTransaction : getSamplePersons()) {
            sampleAb.addPerson(sampleTransaction);
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
