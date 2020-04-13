package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person} matches any of the keywords given.
 */
public abstract class PeoplePredicate implements Predicate<Person> {

    protected final List<String> keywords;

    public PeoplePredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PeoplePredicate // instanceof handles nulls
                && keywords.equals(((PeoplePredicate) other).keywords)); // state check
    }
}
