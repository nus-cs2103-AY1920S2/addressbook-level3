package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Group} matches any of the keywords given.
 */
public class PersonExistPredicate implements Predicate<Person> {
    private final Index index;

    public PersonExistPredicate(Index index) {
        this.index = index;
    }

    @Override
    public boolean test(Person person) {
        if (person.getIndex().getZeroBased()  == index.getZeroBased()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonExistPredicate // instanceof handles nulls
                && index.equals(((PersonExistPredicate) other).index)); // state check
    }

}
