package team.easytravel.model.listmanagers.packinglistitem;

import java.util.List;
import java.util.function.Predicate;

import team.easytravel.commons.util.StringUtil;


/**
 * The type Item category contains keywords predicate.
 */
public class ItemCategoryContainsKeywordsPredicate implements Predicate<PackingListItem> {
    private final List<String> keywords;

    /**
     * Instantiates a new Item category contains keywords predicate.
     *
     * @param keywords the keywords
     */
    public ItemCategoryContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(PackingListItem item) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(item.getItemCategory().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ItemCategoryContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((ItemCategoryContainsKeywordsPredicate) other).keywords)); // state check
    }
}
