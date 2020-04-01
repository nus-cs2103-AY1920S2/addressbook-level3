package seedu.address.model.modelTeacher;

import java.util.List;
import java.util.function.Predicate;
import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class TeacherIDContainsKeywordsPredicate implements Predicate<Teacher> {

  private final List<String> keywords;

  public TeacherIDContainsKeywordsPredicate(List<String> keywords) {
    this.keywords = keywords;
  }

  @Override
  public boolean test(Teacher teacher) {
    return keywords.stream()
        .anyMatch(
            keyword -> StringUtil.containsWordIgnoreCase(teacher.getID().value, keyword));
  }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
        || (other instanceof TeacherIDContainsKeywordsPredicate
        // instanceof handles nulls
        && keywords.equals(((TeacherIDContainsKeywordsPredicate) other).keywords)); // state check
  }

}
