package seedu.address.model.parcel;

import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.model.parcel.order.Order;

/**
 * Tests that a {@code Order}'s {@code TransactionId}, {@code Name}, {@code Phone}, {@code Address}, {@code TimeStamp},
 * {@code Warehouse}, {@code CashOnDeliver}, {@code Email}, {@code Comment}
 * and {@code TypeOfItem} matches any of the keywords given.
 */
public class OrderContainsKeywordsPredicate implements Predicate<Order> {
    private final List<String> keywords;
    private final ArgumentMultimap argumentMultimap;
    private boolean isGeneralSearch = true;


    public OrderContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
        this.argumentMultimap = new ArgumentMultimap();
    }

    public OrderContainsKeywordsPredicate(List<String> keywords,
                                          ArgumentMultimap argumentMultimap) {
        this.keywords = keywords;
        this.argumentMultimap = argumentMultimap;
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
                        || StringUtil.containsWordIgnoreCase(order.getEmail().value, keyword)
                        || StringUtil.containsWordIgnoreCase(order.getAddress().value, keyword)
                        || StringUtil.containsWordIgnoreCase(order.getTimestamp().value, keyword)
                        || StringUtil.containsWordIgnoreCase(order.getWarehouse().address, keyword)
                        || StringUtil.containsWordIgnoreCase(order.getComment().commentMade, keyword)
                        || StringUtil.containsWordIgnoreCase(order.getCash().cashOnDelivery, keyword)
                        || StringUtil.containsWordIgnoreCase(order.getItemType().itemType, keyword)
                );
        }
        return keywords.stream()
            .anyMatch(keyword -> ((
                argumentMultimap.getHasTid()
                    && StringUtil.containsWordIgnoreCase(order.getTid().tid, keyword))
                || (argumentMultimap.getHasName()
                    && StringUtil.containsWordIgnoreCase(order.getName().fullName, keyword))
                || (argumentMultimap.getHasPhone()
                    && StringUtil.containsWordIgnoreCase(order.getPhone().value, keyword))
                || (argumentMultimap.getHasAddress()
                    && StringUtil.containsWordIgnoreCase(order.getAddress().value, keyword))
                || (argumentMultimap.getHasDeliveryTimeStamp()
                    && StringUtil.containsWordIgnoreCase(order.getTimestamp().value, keyword))
                || (argumentMultimap.getHasWarehouse()
                    && StringUtil.containsWordIgnoreCase(order.getWarehouse().address, keyword))
                || (argumentMultimap.getHasComment()
                    && StringUtil.containsWordIgnoreCase(order.getComment().commentMade, keyword))
                || (argumentMultimap.getHasCod()
                    && StringUtil.containsWordIgnoreCase(order.getCash().cashOnDelivery, keyword))
                || (argumentMultimap.getHasItemType()
                    && StringUtil.containsWordIgnoreCase(order.getItemType().itemType, keyword))
                || (argumentMultimap.getHasEmail()
                    && StringUtil.containsWordIgnoreCase(order.getEmail().value, keyword))
            ));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OrderContainsKeywordsPredicate // instanceof handles nulls
                && new HashSet<>(keywords).equals(new HashSet<>(((OrderContainsKeywordsPredicate) other).keywords))
                && argumentMultimap.equals(((OrderContainsKeywordsPredicate) other).argumentMultimap)
                && isGeneralSearch == (((OrderContainsKeywordsPredicate) other).isGeneralSearch)); // state check
    }

}
