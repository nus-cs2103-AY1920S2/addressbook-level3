package team.easytravel.model.listmanagers.activity;

import java.util.List;
import java.util.function.Predicate;

import team.easytravel.commons.util.StringUtil;


/**
 * The type Item category contains keywords predicate.
 */
public class ActivityTagContainsPredicate implements Predicate<Activity> {
    private final List<String> keywords;

    /**
     * Instantiates a new Item category contains keywords predicate.
     *
     * @param keywords the keywords
     */
    public ActivityTagContainsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Activity activity) {
        return keywords.stream().anyMatch(keyword -> activity.getTags().stream()
                .anyMatch(tag ->StringUtil.containsWordIgnoreCase(tag.tagName, keyword)));
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ActivityTagContainsPredicate // instanceof handles nulls
                && keywords.equals(((ActivityTagContainsPredicate) other).keywords)); // state check
    }

}

