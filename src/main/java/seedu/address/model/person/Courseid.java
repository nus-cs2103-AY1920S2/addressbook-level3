package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's courseid number in the address book. Guarantees: immutable; is valid as
 * declared in {@link #isValidCourseid(String)}
 */
public class Courseid {


  public static final String MESSAGE_CONSTRAINTS =
      "Courseid numbers should only contain numbers, and it should be at least 1 digits long";
  public static final String VALIDATION_REGEX = "\\d{1,}";
  public final String value;

  /**
   * Constructs a {@code Courseid}.
   *
   * @param courseid A valid courseid number.
   */
  public Courseid(String courseid) {
    requireNonNull(courseid);
    checkArgument(isValidCourseid(courseid), MESSAGE_CONSTRAINTS);
    value = courseid;
  }

  /**
   * Returns true if a given string is a valid courseid number.
   */
  public static boolean isValidCourseid(String test) {
    return test.matches(VALIDATION_REGEX);
  }

  @Override
  public String toString() {
    return value;
  }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
        || (other instanceof Courseid // instanceof handles nulls
        && value.equals(((Courseid) other).value)); // state check
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

}
