package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Course in the address book. Guarantees: immutable; is valid as declared in
 * {@link #isValidCourse(String)}
 */
public class AssignedCourse {

  public static final String MESSAGE_CONSTRAINTS =
      "Courses should only contain alphanumeric characters and spaces, and it should not be blank";

  /*
   * The first character of the address must not be a whitespace,
   * otherwise " " (a blank string) becomes a valid input.
   */
  public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

  public final String value;

  /**
   * Constructs a {@code Assignment}.
   *
   * @param course A valid course.
   */
  public AssignedCourse(String course) {
    requireNonNull(course);
    checkArgument(isValidCourse(course), MESSAGE_CONSTRAINTS);
    value = course;
  }

  /**
   * Returns true if a given string is a valid course.
   */
  public static boolean isValidCourse(String test) {
    return test.matches(VALIDATION_REGEX);
  }


  @Override
  public String toString() {
    return value;
  }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
        || (other instanceof AssignedCourse // instanceof handles nulls
        && value.equals(((AssignedCourse) other).value)); // state check
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

}
