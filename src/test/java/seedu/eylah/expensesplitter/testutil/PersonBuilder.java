package seedu.eylah.expensesplitter.testutil;

import java.math.BigDecimal;

import seedu.eylah.expensesplitter.model.person.Amount;
import seedu.eylah.expensesplitter.model.person.Name;
import seedu.eylah.expensesplitter.model.person.Person;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Willy Seah";
    public static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(3.50);

    private Name name;
    private Amount amount;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        amount = new Amount(DEFAULT_AMOUNT);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code PersonToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        amount = personToCopy.getAmount();
    }


    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     * */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code Person} that we are building.
     */
    public PersonBuilder withAmount(BigDecimal amount) {
        this.amount = new Amount(amount);
        return this;
    }

    /**
     * Builds a Person with name and amount.
     */
    public Person build() {

        return new Person(name, amount);
    }
}
