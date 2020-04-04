package seedu.address.model.modelFinance;

import java.util.List;
import java.util.function.Predicate;
import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class FinanceIDContainsKeywordsPredicate implements Predicate<Finance> {

  private final List<String> keywords;

  public FinanceIDContainsKeywordsPredicate(List<String> keywords) {
    this.keywords = keywords;
  }

  @Override
  public boolean test(Finance finance) {
    return keywords.stream()
        .anyMatch(
            keyword -> StringUtil.containsWordIgnoreCase(finance.getId().value, keyword));
  }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
        || (other instanceof FinanceIDContainsKeywordsPredicate
        // instanceof handles nulls
        && keywords.equals(((FinanceIDContainsKeywordsPredicate) other).keywords)); // state check
  }

}
