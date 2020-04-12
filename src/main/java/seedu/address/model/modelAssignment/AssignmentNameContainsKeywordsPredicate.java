package seedu.address.model.modelAssignment;

import seedu.address.commons.util.StringUtil;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Assignment}'s {@code Name} matches any of the keywords given.
 */
public class AssignmentNameContainsKeywordsPredicate implements Predicate<Assignment> {

    private final List<String> keywords;

    public AssignmentNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Assignment assignment) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(assignment.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AssignmentNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((AssignmentNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
