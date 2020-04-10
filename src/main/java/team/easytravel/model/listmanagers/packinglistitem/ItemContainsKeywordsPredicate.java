package team.easytravel.model.listmanagers.packinglistitem;

import java.util.List;
import java.util.function.Predicate;

import team.easytravel.commons.util.StringUtil;

/**
 * The type Item contains keywords predicate.
 */
public class ItemContainsKeywordsPredicate implements Predicate<PackingListItem> {
    private final List<String> keywords;

    /**
     * Instantiates a new Item contains keywords predicate.
     *
     * @param keywords the keywords
     */
    public ItemContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(PackingListItem item) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(item.getItemName().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ItemContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((ItemContainsKeywordsPredicate) other).keywords)); // state check
    }
}
