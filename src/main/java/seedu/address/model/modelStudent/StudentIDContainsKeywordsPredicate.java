package seedu.address.model.modelStudent;

import java.util.List;
import java.util.function.Predicate;
import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class StudentIDContainsKeywordsPredicate implements Predicate<Student> {

  private final List<String> keywords;

  public StudentIDContainsKeywordsPredicate(List<String> keywords) {
    this.keywords = keywords;
  }

  @Override
  public boolean test(Student student) {
    return keywords.stream()
        .anyMatch(
            keyword -> StringUtil.containsWordIgnoreCase(student.getId().value, keyword));
  }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
        || (other instanceof StudentIDContainsKeywordsPredicate
        // instanceof handles nulls
        && keywords.equals(((StudentIDContainsKeywordsPredicate) other).keywords)); // state check
  }

}
