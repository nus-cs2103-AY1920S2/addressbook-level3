package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.model.Model;

/**
 * Tests that a {@code Person}'s {@code Group} matches any of the keywords given.
 */
public class PersonExistPredicate implements Predicate<Person> {
    private final Person person;
    private final Model model;

    public PersonExistPredicate(Person person, Model model) {
        this.person = person;
        this.model = model;
    }

    @Override
    public boolean test(Person personToGet) {
        if (!person.isSamePerson(personToGet) && model.hasPerson(personToGet)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonExistPredicate // instanceof handles nulls
                && person.equals(((PersonExistPredicate) other).person)); // state check
    }

}
