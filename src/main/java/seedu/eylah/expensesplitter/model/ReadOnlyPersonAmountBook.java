package seedu.eylah.expensesplitter.model;

import javafx.collections.ObservableList;
import seedu.eylah.expensesplitter.model.person.Person;

/**
 * Unmodifiable view of an personamount book.
 */
public interface ReadOnlyPersonAmountBook {

    /**
     * Returns an unmodifiable view of the PersonAmount list.
     * This list will not contain any duplicate Persons.
     */
    ObservableList<Person> getPersonList();

    Person getPersonByIndex(int indexOfPerson);
}
