package seedu.address.testutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.person.ActivityList;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PlaceList;
import seedu.address.model.person.Time;
import seedu.address.model.person.TimeList;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final int DEFAULT_MINS = 0;
    public static final int DEFAULT_HOURS = 0;
    public static final List<String> DEFAULT_PLACELIST = new ArrayList<String>();
    public static final List<String> DEFAULT_ACTIVITYLIST = new ArrayList<String>();
    public static final List<String> DEFAULT_TIMELIST = new ArrayList<String>();

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private Time time;
    private PlaceList places;
    private ActivityList activities;
    private TimeList times;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        time = new Time(DEFAULT_MINS, DEFAULT_HOURS);
        places = new PlaceList(DEFAULT_PLACELIST);
        activities = new ActivityList(DEFAULT_ACTIVITYLIST);
        times = new TimeList(DEFAULT_TIMELIST);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        tags = new HashSet<>(personToCopy.getTags());
        time = personToCopy.getTime();
        places = personToCopy.getPlaceList2();
        activities = personToCopy.getActivityList2();
        times = personToCopy.getTimeList();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code Person} that we are building.
     */
    public PersonBuilder withTime(String time) {
        this.time = ParserUtil.parseTime(time);
        return this;
    }

    /**
     * Sets the {@code PlaceList} of the {@code Person} that we are building.
     */
    public PersonBuilder withPlaceList(String input) {
        this.places = ParserUtil.parsePlaces(input);
        return this;
    }

    /**
     * Sets the {@code ActivityList} of the {@code Person} that we are building.
     */
    public PersonBuilder withActivityList(String input) {
        this.activities = ParserUtil.parseActivities(input);
        return this;
    }

    /**
     * Sets the {@code TimeList} of the {@code Person} that we are building.
     */
    public PersonBuilder withTimeList(String input) {
        this.times = ParserUtil.parseTimes(input);
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, address, tags, time, places, activities, times);
    }

}
