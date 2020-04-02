package seedu.address.model.modelAssignment;

import java.util.List;
import java.util.function.Predicate;
import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class AssignmentIDContainsKeywordsPredicate implements Predicate<Assignment> {

  private final List<String> keywords;

  public AssignmentIDContainsKeywordsPredicate(List<String> keywords) {
    this.keywords = keywords;
  }

  @Override
  public boolean test(Assignment assignment) {
    return keywords.stream()
        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(assignment.getId().value, keyword));
  }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
        || (other instanceof AssignmentIDContainsKeywordsPredicate // instanceof handles nulls
        && keywords.equals(((AssignmentIDContainsKeywordsPredicate) other).keywords)); // state check
  }

}
