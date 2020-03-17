package seedu.zerotoone.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.zerotoone.model.exercise.Address;
import seedu.zerotoone.model.exercise.Email;
import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.exercise.Name;
import seedu.zerotoone.model.exercise.Phone;
import seedu.zerotoone.model.tag.Tag;
import seedu.zerotoone.model.util.SampleDataUtil;

/**
 * A utility class to help with building Exercise objects.
 */
public class ExerciseBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;

    public ExerciseBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the ExerciseBuilder with the data of {@code exerciseToCopy}.
     */
    public ExerciseBuilder(Exercise exerciseToCopy) {
        name = exerciseToCopy.getName();
        phone = exerciseToCopy.getPhone();
        email = exerciseToCopy.getEmail();
        address = exerciseToCopy.getAddress();
        tags = new HashSet<>(exerciseToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Exercise} that we are building.
     */
    public ExerciseBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Exercise} that we are building.
     */
    public ExerciseBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Exercise} that we are building.
     */
    public ExerciseBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Exercise} that we are building.
     */
    public ExerciseBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Exercise} that we are building.
     */
    public ExerciseBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Exercise build() {
        return new Exercise(name, phone, email, address, tags);
    }

}
