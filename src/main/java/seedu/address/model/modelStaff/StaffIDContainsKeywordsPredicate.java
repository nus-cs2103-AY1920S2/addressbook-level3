package seedu.address.model.modelStaff;

import java.util.List;
import java.util.function.Predicate;
import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class StaffIDContainsKeywordsPredicate implements Predicate<Staff> {

  private final List<String> keywords;

  public StaffIDContainsKeywordsPredicate(List<String> keywords) {
    this.keywords = keywords;
  }

  @Override
  public boolean test(Staff staff) {
    return keywords.stream()
        .anyMatch(
            keyword -> StringUtil.containsWordIgnoreCase(staff.getId().value, keyword));
  }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
        || (other instanceof StaffIDContainsKeywordsPredicate
        // instanceof handles nulls
        && keywords.equals(((StaffIDContainsKeywordsPredicate) other).keywords)); // state check
  }

}
