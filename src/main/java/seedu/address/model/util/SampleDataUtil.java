package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.Inventory;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyInventory;
import seedu.address.model.good.Good;
import seedu.address.model.good.GoodName;
import seedu.address.model.good.GoodQuantity;
import seedu.address.model.offer.Offer;
import seedu.address.model.offer.Price;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Contains utility methods for populating {@code AddressBook} and {@code Inventory} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getOfferSet("banana 4.5")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getOfferSet("toilet paper 2", "tissue 70.50")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getOfferSet("hand sanitiser 3.25")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getOfferSet("instant noodle 0.45")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getOfferSet("facial mask 5.75")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getOfferSet("apple 50.3"))
        };
    }

    public static Good[] getSampleGoods() {
        return new Good[] {
            new Good(new GoodName("Fuji apple"), new GoodQuantity(15)),
            new Good(new GoodName("Cavendish banana"), new GoodQuantity(150)),
            new Good(new GoodName("Brazil citrus"), new GoodQuantity(20)),
            new Good(new GoodName("Malaysia durian"), new GoodQuantity(11)),
            new Good(new GoodName("Indonesia entawak"), new GoodQuantity(0)),
            new Good(new GoodName("India fig"), new GoodQuantity(10)),
            new Good(new GoodName("Spain grape"), new GoodQuantity(20)),
            new Good(new GoodName("Turkey hazelnut"), new GoodQuantity(20)),
            new Good(new GoodName("Africa imbe"), new GoodQuantity(20)),
            new Good(new GoodName("Philippines jackfruit"), new GoodQuantity(20)),
            new Good(new GoodName("New Zealand kiwi"), new GoodQuantity(20)),
            new Good(new GoodName("Mexico lemon"), new GoodQuantity(90))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    public static ReadOnlyInventory getSampleInventory() {
        Inventory sampleInventory = new Inventory();
        for (Good sampleGood : getSampleGoods()) {
            sampleInventory.addGood(sampleGood);
        }
        return sampleInventory;
    }

    /**
     * Returns an offer set containing the set of strings given.
     */
    public static Set<Offer> getOfferSet(String... strings) {
        return Arrays.stream(strings)
                .map(ParserUtil::splitOnLastWhitespace)
                .map(ParserUtil::getGoodPricePair)
                .map(x -> new Offer((GoodName) x[0], (Price) x[1]))
                .collect(Collectors.toSet());
    }
}
