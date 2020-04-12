package nasa.model.activity;

import java.util.List;
import java.util.function.Predicate;

import nasa.commons.util.StringUtil;

/**
 * Predicate to allow activities to be filtered.
 */
public class ActivityContainsKeyWordsPredicate implements Predicate<Activity> {
    private final List<String> keywords;

    public ActivityContainsKeyWordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    @Override
    public boolean test(Activity activity) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(activity.getName().name, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ActivityContainsKeyWordsPredicate // instanceof handles nulls
                && keywords.equals(((ActivityContainsKeyWordsPredicate) other).keywords)); // state check
    }
}
