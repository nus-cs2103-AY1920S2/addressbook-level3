package seedu.address.model.modelFinance;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import seedu.address.model.person.Amount;
import seedu.address.model.person.Name;
import seedu.address.model.tag.Tag;

/**
 * Represents a Finance in the address book. Guarantees: details are present and not null, field
 * values are validated, immutable.
 */
public class Finance {

  // Identity fields
  private final Name name;
  private final Amount amount;
  private final Set<Tag> tags = new HashSet<>();

  /**
   * Every field must be present and not null.
   */
  public Finance(Name name, Amount amount, Set<Tag> tags) {
    requireAllNonNull(name, amount, tags);
    this.name = name;
    this.amount = amount;
    this.tags.addAll(tags);
  }

  public Name getName() {
    return name;
  }

  public Amount getAmount() {
    return amount;
  }

  /**
   * Returns an immutable tag set, which throws {@code UnsupportedOperationException} if
   * modification is attempted.
   */
  public Set<Tag> getTags() {
    return Collections.unmodifiableSet(tags);
  }

  /**
   * Returns true if both finances of the same name have at least one other identity field that is
   * the same. This defines a weaker notion of equality between two finances.
   */
  public boolean isSameFinance(Finance otherFinance) {
    if (otherFinance == this) {
      return true;
    }

    return otherFinance != null
        && otherFinance.getName().equals(getName())
        && otherFinance.getAmount().equals(getAmount());
  }

  /**
   * Returns true if both finances have the same identity and data fields. This defines a stronger
   * notion of equality between two finances.
   */
  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }

    if (!(other instanceof Finance)) {
      return false;
    }

    Finance otherFinance = (Finance) other;
    return otherFinance.getName().equals(getName())
        && otherFinance.getAmount().equals(getAmount())
        && otherFinance.getTags().equals(getTags());
  }

  @Override
  public int hashCode() {
    // use this method for custom fields hashing instead of implementing your own
    return Objects.hash(name, amount, tags);
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append(getName())
        .append("Amount: ")
        .append(getAmount())
        .append(" Tags: ");
    getTags().forEach(builder::append);
    return builder.toString();
  }

}
