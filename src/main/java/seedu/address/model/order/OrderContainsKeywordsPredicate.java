package seedu.address.model.order;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Order}'s {@code TransactionId}, {@code Name}, {@code Phone}, {@code Address}, {@code TimeStamp},
 * {@code Warehouse}, {@code CashOnDeliver}, {@code Comment} and {@code TypeOfItem} matches any of the keywords given.
 */
public class OrderContainsKeywordsPredicate implements Predicate<Order> {
    private final List<String> keywords;
    private final KeywordContainsOrderPrefix keywordContainsOrderPrefix;
    private boolean isGeneralSearch = true;


    public OrderContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
        this.keywordContainsOrderPrefix = new KeywordContainsOrderPrefix();
    }

    public OrderContainsKeywordsPredicate(List<String> keywords,
                                          KeywordContainsOrderPrefix keywordContainsOrderPrefix) {
        this.keywords = keywords;
        this.keywordContainsOrderPrefix = keywordContainsOrderPrefix;
        this.isGeneralSearch = false;
    }

    @Override
    public boolean test(Order order) {
        if (isGeneralSearch) {
            return keywords.stream()
                .anyMatch(keyword ->
                    StringUtil.containsWordIgnoreCase(order.getTid().tid, keyword)
                        || StringUtil.containsWordIgnoreCase(order.getName().fullName, keyword)
                        || StringUtil.containsWordIgnoreCase(order.getPhone().value, keyword)
                        || StringUtil.containsWordIgnoreCase(order.getAddress().value, keyword)
                        || StringUtil.containsWordIgnoreCase(order.getTimeStamp().value, keyword)
                        || StringUtil.containsWordIgnoreCase(order.getWarehouse().address, keyword)
                        || StringUtil.containsWordIgnoreCase(order.getComment().commentMade, keyword)
                        || StringUtil.containsWordIgnoreCase(order.getCash().cashOnDelivery, keyword)
                        || StringUtil.containsWordIgnoreCase(order.getItemType().itemType, keyword)
                );
        }
        return keywords.stream()
            .anyMatch(keyword -> ((
                keywordContainsOrderPrefix.getHasTid()
                    && StringUtil.containsWordIgnoreCase(order.getTid().tid, keyword))
                || (keywordContainsOrderPrefix.getHasName()
                    && StringUtil.containsWordIgnoreCase(order.getName().fullName, keyword))
                || (keywordContainsOrderPrefix.getHasPhone()
                    && StringUtil.containsWordIgnoreCase(order.getPhone().value, keyword))
                || (keywordContainsOrderPrefix.getHasAddress()
                    && StringUtil.containsWordIgnoreCase(order.getAddress().value, keyword))
                || (keywordContainsOrderPrefix.getHasTimeStamp()
                    && StringUtil.containsWordIgnoreCase(order.getTimeStamp().value, keyword))
                || (keywordContainsOrderPrefix.getHasWarehouse()
                    && StringUtil.containsWordIgnoreCase(order.getWarehouse().address, keyword))
                || (keywordContainsOrderPrefix.getHasComment()
                    && StringUtil.containsWordIgnoreCase(order.getComment().commentMade, keyword))
                || (keywordContainsOrderPrefix.getHasCod()
                    && StringUtil.containsWordIgnoreCase(order.getCash().cashOnDelivery, keyword))
                || (keywordContainsOrderPrefix.getHasItemType()
                    && StringUtil.containsWordIgnoreCase(order.getItemType().itemType, keyword))
            ));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OrderContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((OrderContainsKeywordsPredicate) other).keywords)); // state check
    }

}
