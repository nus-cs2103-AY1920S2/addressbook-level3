package team.easytravel.model.listmanagers.activity;

import java.util.List;
import java.util.function.Predicate;

import team.easytravel.commons.util.StringUtil;


/**
 * The type Item category contains keywords predicate.
 */
public class ActivityContainKeywordPredicate implements Predicate<Activity> {
    private final List<String> keywords;

    /**
     * Instantiates a new Item category contains keywords predicate.
     *
     * @param keywords the keywords
     */
    public ActivityContainKeywordPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Activity activity) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(activity.getTitle().value, keyword)
                        ||
                        StringUtil.containsWordIgnoreCase(activity.getLocation().value, keyword)
                        ||
                        StringUtil.containsWordIgnoreCase(Integer.toString(activity.getDuration().value), keyword));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ActivityContainKeywordPredicate // instanceof handles nulls
                && keywords.equals(((ActivityContainKeywordPredicate) other).keywords)); // state check
    }
}
