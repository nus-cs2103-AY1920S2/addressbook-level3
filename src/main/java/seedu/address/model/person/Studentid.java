package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's studentid number in the address book. Guarantees: immutable; is valid as
 * declared in {@link #isValidStudentid(String)}
 */
public class Studentid {


  public static final String MESSAGE_CONSTRAINTS =
      "Studentid numbers should only contain numbers, and it should be at least 1 digits long";
  public static final String VALIDATION_REGEX = "\\d{1,}";
  public final String value;

  /**
   * Constructs a {@code Studentid}.
   *
   * @param studentid A valid studentid number.
   */
  public Studentid(String studentid) {
    requireNonNull(studentid);
    checkArgument(isValidStudentid(studentid), MESSAGE_CONSTRAINTS);
    value = studentid;
  }

  /**
   * Returns true if a given string is a valid studentid number.
   */
  public static boolean isValidStudentid(String test) {
    return test.matches(VALIDATION_REGEX);
  }

  @Override
  public String toString() {
    return value;
  }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
        || (other instanceof Studentid // instanceof handles nulls
        && value.equals(((Studentid) other).value)); // state check
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

}
