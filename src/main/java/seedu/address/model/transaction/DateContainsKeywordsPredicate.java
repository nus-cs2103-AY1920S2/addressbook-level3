package seedu.address.model.transaction;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Tests that a {@code Transaction} matches any of the keywords given.
 */
public class DateContainsKeywordsPredicate extends WalletPredicate {
    private static final Logger logger = LogsCenter.getLogger(DateContainsKeywordsPredicate.class);

    public DateContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    @Override
    public boolean test(Transaction transaction) {
        logger.info(transaction.getDate().toString());

        return keywords.stream()
                .anyMatch(keyword -> {
                    Date date = null;
                    try {
                        date = ParserUtil.parseDate(keyword);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    return date.toString().equals(transaction.getDate().toString());
                });
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((DateContainsKeywordsPredicate) other).keywords)); // state check
    }
}
