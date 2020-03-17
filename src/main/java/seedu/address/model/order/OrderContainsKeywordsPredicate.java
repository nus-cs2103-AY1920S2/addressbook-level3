package seedu.address.model.order;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class OrderContainsKeywordsPredicate implements Predicate<Order> {
    private final List<String> keywords;
    private boolean hasName;
    private boolean hasAddress;
    private boolean hasComment;
    private boolean hasPhone;
    private boolean hasTimeStamp;
    private boolean hasWarehouse;
    private boolean isGeneralSearch = true;


    public OrderContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    public OrderContainsKeywordsPredicate(List<String> keywords, boolean hasName, boolean hasAddress, boolean hasComment,
                                          boolean hasPhone, boolean hasTimeStamp, boolean hasWarehouse) {
        this.keywords = keywords;
        this.hasName = hasName;
        this.hasAddress = hasAddress;
        this.hasComment = hasComment;
        this.hasPhone = hasPhone;
        this.hasTimeStamp = hasTimeStamp;
        this.hasWarehouse = hasWarehouse;
        this.isGeneralSearch = false;
    }

    @Override
    public boolean test(Order order) {
        if (isGeneralSearch) {
            return keywords.stream()
                .anyMatch(keyword ->
                    StringUtil.containsWordIgnoreCase(order.getName().fullName, keyword)
                        || StringUtil.containsWordIgnoreCase(order.getAddress().value, keyword)
                        || StringUtil.containsWordIgnoreCase(order.getComment().commentMade, keyword)
                        || StringUtil.containsWordIgnoreCase(order.getPhone().value, keyword)
                        || StringUtil.containsWordIgnoreCase(order.getTimestamp().value, keyword)
                        || StringUtil.containsWordIgnoreCase(order.getWarehouse().address, keyword)
                );
        }
        return keywords.stream()
            .anyMatch(keyword ->
                    (hasName && StringUtil.containsWordIgnoreCase(order.getName().fullName, keyword))
                        || (hasAddress && StringUtil.containsWordIgnoreCase(order.getAddress().value, keyword))
                        || (hasComment && StringUtil.containsWordIgnoreCase(order.getComment().commentMade, keyword))
                        || (hasPhone && StringUtil.containsWordIgnoreCase(order.getPhone().value, keyword))
                        || (hasTimeStamp && StringUtil.containsWordIgnoreCase(order.getTimestamp().value, keyword))
                        || (hasWarehouse && StringUtil.containsWordIgnoreCase(order.getWarehouse().address, keyword))
            );
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OrderContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((OrderContainsKeywordsPredicate) other).keywords)); // state check
    }

}
