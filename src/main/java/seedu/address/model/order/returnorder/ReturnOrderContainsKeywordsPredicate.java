package seedu.address.model.order.returnorder;

import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.ArgumentMultimap;

/**
 * Tests that a {@code ReturnOrder}'s {@code TransactionId}, {@code Name}, {@code Phone}, {@code Address},
 * {@code Email}, {@code TimeStamp}, {@code Warehouse}, {@code Comment} and {@code TypeOfItem}
 * matches any of the keywords given.
 */
public class ReturnOrderContainsKeywordsPredicate implements Predicate<ReturnOrder> {
    private final List<String> keywords;
    private final ArgumentMultimap argumentMultimap;
    private boolean isGeneralSearch = true;

    public ReturnOrderContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
        this.argumentMultimap = new ArgumentMultimap();
    }

    public ReturnOrderContainsKeywordsPredicate(List<String> keywords,
                                          ArgumentMultimap argumentMultimap) {
        this.keywords = keywords;
        this.argumentMultimap = argumentMultimap;
        this.isGeneralSearch = false;
    }

    @Override
    public boolean test(ReturnOrder returnOrder) {
        if (isGeneralSearch) {
            return keywords.stream()
                .anyMatch(keyword ->
                    StringUtil.containsWordIgnoreCase(returnOrder.getTid().tid, keyword)
                        || StringUtil.containsWordIgnoreCase(returnOrder.getName().fullName, keyword)
                        || StringUtil.containsWordIgnoreCase(returnOrder.getPhone().value, keyword)
                        || StringUtil.containsWordIgnoreCase(returnOrder.getEmail().value, keyword)
                        || StringUtil.containsWordIgnoreCase(returnOrder.getAddress().value, keyword)
                        || StringUtil.containsWordIgnoreCase(returnOrder.getTimestamp().value, keyword)
                        || StringUtil.containsWordIgnoreCase(returnOrder.getWarehouse().address, keyword)
                        || StringUtil.containsWordIgnoreCase(returnOrder.getComment().commentMade, keyword)
                        || StringUtil.containsWordIgnoreCase(returnOrder.getItemType().itemType, keyword)
                );
        }
        return keywords.stream()
            .anyMatch(keyword -> ((
                argumentMultimap.getHasTid()
                    && StringUtil.containsWordIgnoreCase(returnOrder.getTid().tid, keyword))
                || (argumentMultimap.getHasName()
                && StringUtil.containsWordIgnoreCase(returnOrder.getName().fullName, keyword))
                || (argumentMultimap.getHasPhone()
                && StringUtil.containsWordIgnoreCase(returnOrder.getPhone().value, keyword))
                || (argumentMultimap.getHasAddress()
                && StringUtil.containsWordIgnoreCase(returnOrder.getAddress().value, keyword))
                || (argumentMultimap.getHasReturnTimeStamp()
                && StringUtil.containsWordIgnoreCase(returnOrder.getTimestamp().value, keyword))
                || (argumentMultimap.getHasWarehouse()
                && StringUtil.containsWordIgnoreCase(returnOrder.getWarehouse().address, keyword))
                || (argumentMultimap.getHasComment()
                && StringUtil.containsWordIgnoreCase(returnOrder.getComment().commentMade, keyword))
                || (argumentMultimap.getHasItemType()
                && StringUtil.containsWordIgnoreCase(returnOrder.getItemType().itemType, keyword))
                || (argumentMultimap.getHasEmail()
                && StringUtil.containsWordIgnoreCase(returnOrder.getEmail().value, keyword))
            ));
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this // short circuit if same object
            || (obj instanceof ReturnOrderContainsKeywordsPredicate // instanceof handles nulls
            && new HashSet<>(keywords).equals(new HashSet<>(((ReturnOrderContainsKeywordsPredicate) obj).keywords))
            && argumentMultimap.equals(((ReturnOrderContainsKeywordsPredicate) obj).argumentMultimap)
            && this.isGeneralSearch == (((ReturnOrderContainsKeywordsPredicate) obj).isGeneralSearch)); // state check
    }
}
