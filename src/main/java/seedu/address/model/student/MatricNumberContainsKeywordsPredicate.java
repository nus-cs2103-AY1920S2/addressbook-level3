package seedu.address.model.student;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Student}'s {@code MatricNumber} matches the matric number given.
 */
public class MatricNumberContainsKeywordsPredicate implements Predicate<Student> {
    private final List<String> keywords;

    public MatricNumberContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Student student) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(student.getMatricNumber().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MatricNumberContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((MatricNumberContainsKeywordsPredicate) other).keywords)); // state check
    }

}
