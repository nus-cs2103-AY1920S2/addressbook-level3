package seedu.eylah.addressbook.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.eylah.addressbook.model.person.Name;
import seedu.eylah.addressbook.model.person.Person;
import seedu.eylah.addressbook.model.util.SampleDataUtil;
import seedu.eylah.diettracker.model.tag.Tag;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Annie Paul";

    private Name name;
    private Set<Tag> tags;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        tags = new HashSet<>(personToCopy.getTags());
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
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Builds a person.
     *
     * @return a Person.
     */

    public Person build() {
        return new Person(name, tags);
    }

}
