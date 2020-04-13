package seedu.address.model.person;

import java.util.List;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Tag} matches any of the keywords given.
 */
public class PeopleTagPredicate extends PeoplePredicate {

    public PeopleTagPredicate(List<String> keywords) {
        super(keywords);
    }

    @Override
    public boolean test(Person person) {
        return person.getTags().stream()
                .anyMatch(tag -> keywords.stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(tag.tagName, keyword)));
    }
}
