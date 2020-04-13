package seedu.address.testutil;

import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.User;

/**
 * A utility class to help with building User objects.
 */
public class UserBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";

    private Name name;
    private Phone phone;
    private Email email;

    public UserBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
    }

    /**
     * Initializes the UserBuilder with the data of {@code userToCopy}.
     */
    public UserBuilder(User userToCopy) {
        name = userToCopy.getName();
        phone = userToCopy.getPhone();
        email = userToCopy.getEmail();
    }

    /**
     * Sets the {@code Name} of the {@code User} that we are building.
     */
    public UserBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code User} that we are building.
     */
    public UserBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code User} that we are building.
     */
    public UserBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public User build() {
        return new User(name, phone, email);
    }

}
