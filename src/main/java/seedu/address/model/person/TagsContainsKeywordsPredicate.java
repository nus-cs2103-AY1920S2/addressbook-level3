package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.ModelManager;

/**
 * Tests that a {@code Person}'s {@code Tag}s matches any of the keywords given.
 */
public class TagsContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    // for sarah's use
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    public TagsContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        // we want to find whether they keyword matches any of the tags
        // DEBUGGING INFO
        /*
        logger.info("This is the person's tags concatenated into one line: " + person.getName().fullName);
        logger.info(person.getTagsForPredicate());
        logger.info("These are the keywords we are checking up against");
        for (int i = 0; i < keywords.size(); i++) {
            logger.info(keywords.get(i));
            logger.info(Boolean.toString(person.getTagsForPredicate().contains(keywords.get(i))));
        }
        logger.info("END");
        */

        //return keywords.stream()
                //.anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getTagsForPredicate(), keyword));
        return keywords.stream().anyMatch(keyword -> person.getTagsForPredicate().contains(keyword));
    }

    // what is this equals here for?
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagsContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TagsContainsKeywordsPredicate) other).keywords)); // state check
    }


}
