package fithelper.model.entry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import fithelper.commons.util.StringUtil;

/**
 * Tests that a {@code Entry}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Entry> {

    private static List<String> nullWords = new ArrayList<String>();
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
        nullWords.add("wugc2iuegv98yquy>%$wvjugf398ywiugd");
    }

    public static NameContainsKeywordsPredicate getVaguePredicate() {
        return new NameContainsKeywordsPredicate(nullWords);
    }

    @Override
    public boolean test(Entry entry) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(entry.getName().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
