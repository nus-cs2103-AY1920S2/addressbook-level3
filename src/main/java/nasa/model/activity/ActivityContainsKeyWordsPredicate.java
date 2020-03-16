package nasa.model.activity;

import nasa.commons.util.StringUtil;

import java.util.List;
import java.util.function.Predicate;

public class ActivityContainsKeyWordsPredicate implements Predicate<Activity> {
    private final List<String> keywords;

    public ActivityContainsKeyWordsPredicate(List<String> keywords) {
        this.keywords = keywords;
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
