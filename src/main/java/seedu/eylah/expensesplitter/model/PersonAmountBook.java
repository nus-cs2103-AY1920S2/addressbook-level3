package seedu.eylah.expensesplitter.model;

import static java.util.Objects.requireNonNull;
import static seedu.eylah.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.eylah.expensesplitter.model.person.Amount;
import seedu.eylah.expensesplitter.model.person.Name;
import seedu.eylah.expensesplitter.model.person.Person;
import seedu.eylah.expensesplitter.model.person.UniquePersonList;

/**
 * Wraps all data at the expense splitter level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class PersonAmountBook implements ReadOnlyPersonAmountBook {

    private final UniquePersonList persons;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
    }

    public PersonAmountBook() {}

    /**
     * Creates an PersonAmountBook using the Persons in the {@code toBeCopied}
     */
    public PersonAmountBook(ReadOnlyPersonAmountBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Resets the existing data of this {@code PersonAmountBook} with {@code newData}.
     */
    public void resetData(ReadOnlyPersonAmountBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }


    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the personAmountBook.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the person amount book.
     * The person must not already exist in the personAmountBook.
     * Person is duplicated so that a new Person is added instead of the
     * existing Person in the Receipt.
     */
    public void addPerson(Person p) {
        Name name = p.getName();
        Amount amount = p.getAmount();
        persons.add(new Person(name, amount));
    }

    /**
     * Adds {@code amount} to the current amount of the {@code person}.
     */
    public void addAmount(Person person, Amount amount) {
        requireAllNonNull(person, amount);
        persons.addAmount(person, amount);
    }

    public Person getPerson(Person person) {
        requireNonNull(person);
        return persons.getPerson(person);
    }



    /**
     * Removes {@code key} from this {@code PersonAmountBook}.
     * {@code key} must exist in the personamount book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    /**
     * Removes Amount from a Person
     * @param person
     * @param amount
     */
    public void removeAmount(Person person, Amount amount) {
        persons.removeAmount(person, amount);

        /*
        This handles the deleting of Person when their amount is 0. Cannot use enhanced for loop because
        that will throw java.ConcurrentException because you are editing the personList while you're doing enhanced
        for loop.
         */
        for (int i = 0; i < persons.asUnmodifiableObservableList().size(); i++) {
            if (persons.getPersonUsingIndex(i).getAmount().amount.doubleValue() == 0.0) {
                removePerson(getPersonByIndex(i));
            }
        }
    }

    //// util methods

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        ObservableList<Person> personsList = persons.asUnmodifiableObservableList();
        builder.append("Person : Amount\n");
        for (Person person : personsList) {
            int index = personsList.indexOf(person) + 1;
            builder.append("    ")
                    .append(index)
                    .append(". ")
                    .append(person.getName())
                    .append(" : ")
                    .append(person.getAmount())
                    .append("\n");
        }
        return builder.toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonAmountBook // instanceof handles null
                && persons.equals(((PersonAmountBook) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }

    @Override
    public Person getPersonByIndex(int indexOfPerson) {
        requireNonNull(indexOfPerson);
        return persons.getPersonUsingIndex(indexOfPerson);
    }

}
