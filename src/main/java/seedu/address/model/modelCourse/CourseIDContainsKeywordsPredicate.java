package seedu.address.model.modelCourse;

import java.util.List;
import java.util.function.Predicate;
import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class CourseIDContainsKeywordsPredicate implements Predicate<Course> {

  private final List<String> keywords;

  public CourseIDContainsKeywordsPredicate(List<String> keywords) {
    this.keywords = keywords;
  }

  @Override
  public boolean test(Course course) {
    return keywords.stream()
        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(course.getId().value, keyword));
  }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
        || (other instanceof CourseIDContainsKeywordsPredicate // instanceof handles nulls
        && keywords.equals(((CourseIDContainsKeywordsPredicate) other).keywords)); // state check
  }

}
