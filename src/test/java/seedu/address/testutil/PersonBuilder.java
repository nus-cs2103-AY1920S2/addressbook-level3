package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.expenditure.Address;
import seedu.address.model.expenditure.Amount;
import seedu.address.model.expenditure.Expenditure;
import seedu.address.model.expenditure.Name;
import seedu.address.model.expenditure.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Expenditure objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Phone phone;
    private Amount amount;
    private Address address;
    private Set<Tag> tags;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        amount = new Amount(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code expenditureToCopy}.
     */
    public PersonBuilder(Expenditure expenditureToCopy) {
        name = expenditureToCopy.getName();
        phone = expenditureToCopy.getPhone();
        amount = expenditureToCopy.getAmount();
        address = expenditureToCopy.getAddress();
        tags = new HashSet<>(expenditureToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Expenditure} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Expenditure} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Expenditure} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Expenditure} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Expenditure} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.amount = new Amount(email);
        return this;
    }

    public Expenditure build() {
        return new Expenditure(name, phone, amount, address, tags);
    }

}
