package seedu.address.model.order;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class OrderContainsKeywordsPredicate implements Predicate<Order> {
    private final List<String> keywords;
    private boolean hasTID;
    private boolean hasName;
    private boolean hasPhone;
    private boolean hasAddress;
    private boolean hasTimeStamp;
    private boolean hasWarehouse;
    private boolean hasCOD;
    private boolean hasComment;
    private boolean hasItemType;
    private boolean isGeneralSearch = true;


    public OrderContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    public OrderContainsKeywordsPredicate(List<String> keywords, boolean hasTID, boolean hasName, boolean hasPhone,
                                          boolean hasAddress, boolean hasTimeStamp, boolean hasWarehouse,
                                          boolean hasCOD, boolean hasComment, boolean hasItemType) {
        this.keywords = keywords;
        this.hasTID = hasTID;
        this.hasName = hasName;
        this.hasPhone = hasPhone;
        this.hasAddress = hasAddress;
        this.hasTimeStamp = hasTimeStamp;
        this.hasWarehouse = hasWarehouse;
        this.hasCOD = hasCOD;
        this.hasComment = hasComment;
        this.hasItemType = hasItemType;
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
                        || StringUtil.containsWordIgnoreCase(order.getTimestamp().value, keyword)
                        || StringUtil.containsWordIgnoreCase(order.getWarehouse().address, keyword)
                        || StringUtil.containsWordIgnoreCase(order.getComment().commentMade, keyword)
                        || StringUtil.containsWordIgnoreCase(order.getCash().cashOnDelivery, keyword)
                        || StringUtil.containsWordIgnoreCase(order.getItemType().itemType, keyword)
                );
        }
        return keywords.stream()
            .anyMatch(keyword -> (
                (hasTID && StringUtil.containsWordIgnoreCase(order.getTid().tid, keyword))
                || (hasName && StringUtil.containsWordIgnoreCase(order.getName().fullName, keyword))
                || (hasPhone && StringUtil.containsWordIgnoreCase(order.getPhone().value, keyword))
                || (hasAddress && StringUtil.containsWordIgnoreCase(order.getAddress().value, keyword))
                || (hasTimeStamp && StringUtil.containsWordIgnoreCase(order.getTimestamp().value, keyword))
                || (hasWarehouse && StringUtil.containsWordIgnoreCase(order.getWarehouse().address, keyword))
                || (hasComment && StringUtil.containsWordIgnoreCase(order.getComment().commentMade, keyword))
                || (hasCOD && StringUtil.containsWordIgnoreCase(order.getCash().cashOnDelivery, keyword))
                || (hasItemType && StringUtil.containsWordIgnoreCase(order.getItemType().itemType, keyword))
            ));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OrderContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((OrderContainsKeywordsPredicate) other).keywords)); // state check
    }

}
