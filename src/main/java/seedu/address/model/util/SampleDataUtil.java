package seedu.address.model.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.model.AddressBook;
import seedu.address.model.RestaurantBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyRestaurantBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.restaurant.Cuisine;
import seedu.address.model.restaurant.Hours;
import seedu.address.model.restaurant.Location;
import seedu.address.model.restaurant.Price;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static final ArrayList<Remark> EMPTY_REMARK = new ArrayList<>();
    public static final ArrayList<Remark> SOME_REMARK = new ArrayList<>();
    public static final ArrayList<seedu.address.model.restaurant.Remark> SOMEMORE_REMARK = new ArrayList<>();
    public static final Birthday EMPTY_BIRTHDAY = new Birthday("");

    public static Person[] getSamplePersons() {
        SOME_REMARK.add(new Remark("Likes seafood"));
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), SOME_REMARK,
                    new Birthday("01-25"), getTagSet("friends"), new Index(0)),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), EMPTY_REMARK, EMPTY_BIRTHDAY,
                    getTagSet("colleagues", "friends"), new Index(1)),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), EMPTY_REMARK, new Birthday("05-18"),
                    getTagSet("neighbours"), new Index(2)),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), EMPTY_REMARK, EMPTY_BIRTHDAY,
                    getTagSet("family"), new Index(3)),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), EMPTY_REMARK, new Birthday("10-12"),
                    getTagSet("classmates"), new Index(4)),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), EMPTY_REMARK, EMPTY_BIRTHDAY,
                    getTagSet("colleagues"), new Index(5))
        };
    }

    public static Restaurant[] getSampleRestaurants() {
        SOMEMORE_REMARK.add(new seedu.address.model.restaurant.Remark("Place is clean"));
        return new Restaurant[] {
                new Restaurant(new seedu.address.model.restaurant.Name("McDonalds"), new Location("West Coast"), new Hours("0000:0000"),
                        new Price("$"), new Cuisine("Fast Food"), SOMEMORE_REMARK)
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    public static ReadOnlyRestaurantBook getSampleRestaurantBook() {
        RestaurantBook sampleRb = new RestaurantBook();
        for (Restaurant sampleRestaurant : getSampleRestaurants()) {
            sampleRb.addRestaurant(sampleRestaurant);
        }
        return sampleRb;
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
