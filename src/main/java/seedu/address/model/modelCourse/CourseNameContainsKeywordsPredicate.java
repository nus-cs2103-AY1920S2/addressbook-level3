package seedu.address.model.modelCourse;

import seedu.address.commons.util.StringUtil;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Course}'s {@code Name} matches any of the keywords given.
 */
public class CourseNameContainsKeywordsPredicate implements Predicate<Course> {

    private final List<String> keywords;

    public CourseNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Course course) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(course.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CourseNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((CourseNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
