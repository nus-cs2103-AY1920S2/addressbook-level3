package seedu.address.model.util;

import java.util.ArrayList;
import java.util.Arrays;
//import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.ActivityList;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
//import seedu.address.model.person.Place;
import seedu.address.model.person.PlaceList;
import seedu.address.model.person.Time;
import seedu.address.model.person.TimeList;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static final Time INIT_TIME = new Time(0, 0);
    public static final PlaceList INIT_PLACELIST = new PlaceList(new ArrayList<String>());
    public static final ActivityList INIT_ACTIVITYLIST = new ActivityList(new ArrayList<String>());
    public static final TimeList INIT_TIMELIST = new TimeList(new ArrayList<String>());
    /*public static final Set<Place> INIT_PLACESET = new HashSet<>();*/

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends"), INIT_TIME, INIT_PLACELIST, INIT_ACTIVITYLIST, INIT_TIMELIST/*, INIT_PLACESET*/),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends"), INIT_TIME, INIT_PLACELIST, INIT_ACTIVITYLIST, INIT_TIMELIST
                        /*, INIT_PLACESET*/),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours"), INIT_TIME, INIT_PLACELIST,
                        INIT_ACTIVITYLIST, INIT_TIMELIST/*, INIT_PLACESET*/),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family"), INIT_TIME, INIT_PLACELIST,
                        INIT_ACTIVITYLIST, INIT_TIMELIST/*, INIT_PLACESET*/),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates"), INIT_TIME, INIT_PLACELIST,
                        INIT_ACTIVITYLIST, INIT_TIMELIST/*, INIT_PLACESET*/),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"), INIT_TIME, INIT_PLACELIST,
                        INIT_ACTIVITYLIST, INIT_TIMELIST/*, INIT_PLACESET*/)
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings).map(Tag::new).collect(Collectors.toSet());
    }
}
