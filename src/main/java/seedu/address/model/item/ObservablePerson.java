package seedu.address.model.item;

import static java.util.Objects.requireNonNull;

import java.util.Observable;

/**
 * A wrapper class for the person contained in the resume book.
 */
public class ObservablePerson extends Observable {

    private Person internalPerson;

    public ObservablePerson(Person person) {
        this.internalPerson = person;
    }

    public void setPerson(Person person) {
        requireNonNull(person);
        this.internalPerson = person;
        this.setChanged();
        // Triggers all the observers
        this.notifyObservers();
    }

    public Person getInternalPerson() {
        return internalPerson;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof ObservablePerson
            && ((ObservablePerson) other).getInternalPerson().equals(this.internalPerson));
    }
}
