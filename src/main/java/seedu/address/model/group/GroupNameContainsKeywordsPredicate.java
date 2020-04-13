package seedu.address.model.group;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
/**
 * Checks if a {@code Group}'s {@code Name} matches any of the given keywords.
 */
public class GroupNameContainsKeywordsPredicate implements Predicate<Group> {
    private final List<String> keywords;

    public GroupNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Group group) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(group.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof GroupNameContainsKeywordsPredicate)) {
            return false;
        }

        GroupNameContainsKeywordsPredicate nameContainsKeywordsPredicate = (GroupNameContainsKeywordsPredicate) other;
        return this.keywords.equals(nameContainsKeywordsPredicate.keywords);
    }
}
