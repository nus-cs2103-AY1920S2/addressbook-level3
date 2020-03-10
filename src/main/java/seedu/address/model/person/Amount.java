package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's salary number in the address book. Guarantees: immutable; is valid as
 * declared in {@link #isValidAmount(String)}
 */
public class Amount {


  public static final String MESSAGE_CONSTRAINTS =
      "Amount numbers should only contain numbers, and it should be at least 1 digits long";
  public static final String VALIDATION_REGEX = "\\d{1,}";
  public final String value;

  /**
   * Constructs a {@code Amount}.
   *
   * @param amount A valid amount number.
   */
  public Amount(String amount) {
    requireNonNull(amount);
    checkArgument(isValidAmount(amount), MESSAGE_CONSTRAINTS);
    value = amount;
  }

  /**
   * Returns true if a given string is a valid salary number.
   */
  public static boolean isValidAmount(String test) {
    return test.matches(VALIDATION_REGEX);
  }

  @Override
  public String toString() {
    return value;
  }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
        || (other instanceof Amount // instanceof handles nulls
        && value.equals(((Amount) other).value)); // state check
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

}
