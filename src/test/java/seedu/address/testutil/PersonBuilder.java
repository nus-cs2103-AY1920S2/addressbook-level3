package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.transaction.Debt;
import seedu.address.model.transaction.Loan;
import seedu.address.model.transaction.TransactionList;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";

    private Name name;
    private Phone phone;
    private Email email;
    private TransactionList<Debt> debts;
    private TransactionList<Loan> loans;
    private Set<Tag> tags;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        debts = new TransactionList<>();
        loans = new TransactionList<>();
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        debts = personToCopy.getDebts();
        loans = personToCopy.getLoans();
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
    public PersonBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
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
     * Sets the {@code debts} of the {@code Person} that we are building.
     */
    public PersonBuilder withDebts(Debt... debts) {
        this.debts = SampleDataUtil.getDebtList(debts);
        if (debts.length != 0) {
            this.tags.add(new Tag("Debt"));
        } else {
            this.tags.remove(new Tag("Debt"));
        }
        return this;
    }

    /**
     * Sets the {@code loans} of the {@code Person} that we are building.
     */
    public PersonBuilder withLoans(Loan... loans) {
        this.loans = SampleDataUtil.getLoanList(loans);
        if (loans.length != 0) {
            this.tags.add(new Tag("Loan"));
        } else {
            this.tags.remove(new Tag("Loan"));
        }
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, debts, loans, tags);
    }

}
