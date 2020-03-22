package seedu.address.model.modelCourseStudent;

import java.util.List;
import java.util.function.Predicate;
import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class CourseStudentNameContainsKeywordsPredicate implements Predicate<CourseStudent> {

  private final List<String> keywords;

  public CourseStudentNameContainsKeywordsPredicate(List<String> keywords) {
    this.keywords = keywords;
  }

  @Override
  public boolean test(CourseStudent courseStudent) {
    return keywords.stream()
        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(courseStudent.getName().fullName, keyword));
  }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
        || (other instanceof CourseStudentNameContainsKeywordsPredicate
        // instanceof handles nulls
        && keywords.equals(((CourseStudentNameContainsKeywordsPredicate) other).keywords)); // state check
  }

}
