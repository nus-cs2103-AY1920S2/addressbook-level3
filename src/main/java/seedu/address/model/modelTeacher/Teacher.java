package seedu.address.model.modelTeacher;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Salary;
import seedu.address.model.tag.Tag;

/**
 * Represents a Teacher in the address book. Guarantees: details are present and not null, field
 * values are validated, immutable.
 */
public class Teacher {

  // Identity fields
  private final Name name;
  private final Phone phone;
  private final Email email;
  private final Salary salary;
  // Data fields
  private final Address address;
  private final Set<Tag> tags = new HashSet<>();

  /**
   * Every field must be present and not null.
   */
  public Teacher(Name name, Phone phone, Email email, Salary salary, Address address,
      Set<Tag> tags) {
    requireAllNonNull(name, phone, email, address, tags);
    this.name = name;
    this.phone = phone;
    this.email = email;
    this.salary = salary;
    this.address = address;
    this.tags.addAll(tags);
  }

  public Name getName() {
    return name;
  }

  public Phone getPhone() {
    return phone;
  }

  public Email getEmail() {
    return email;
  }

  public Salary getSalary() {
    return salary;
  }

  public Address getAddress() {
    return address;
  }

  /**
   * Returns an immutable tag set, which throws {@code UnsupportedOperationException} if
   * modification is attempted.
   */
  public Set<Tag> getTags() {
    return Collections.unmodifiableSet(tags);
  }

  /**
   * Returns true if both teachers of the same name have at least one other identity field that is
   * the same. This defines a weaker notion of equality between two teachers.
   */
  public boolean isSameTeacher(Teacher otherTeacher) {
    if (otherTeacher == this) {
      return true;
    }

    return otherTeacher != null
        && otherTeacher.getName().equals(getName())
        && (otherTeacher.getPhone().equals(getPhone()) || (
        otherTeacher.getSalary().equals(getSalary()) || otherTeacher.getEmail()
            .equals(getEmail())));
  }

  /**
   * Returns true if both teachers have the same identity and data fields. This defines a stronger
   * notion of equality between two teachers.
   */
  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }

    if (!(other instanceof Teacher)) {
      return false;
    }

    Teacher otherTeacher = (Teacher) other;
    return otherTeacher.getName().equals(getName())
        && otherTeacher.getPhone().equals(getPhone())
        && otherTeacher.getEmail().equals(getEmail())
        && otherTeacher.getSalary().equals(getSalary())
        && otherTeacher.getAddress().equals(getAddress())
        && otherTeacher.getTags().equals(getTags());
  }

  @Override
  public int hashCode() {
    // use this method for custom fields hashing instead of implementing your own
    return Objects.hash(name, phone, email, salary, address, tags);
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append(getName())
        .append(" Phone: ")
        .append(getPhone())
        .append(" Email: ")
        .append(getEmail())
        .append(" Address: ")
        .append(getSalary())
        .append(" Salary: ")
        .append(getAddress())
        .append(" Tags: ");
    getTags().forEach(builder::append);
    return builder.toString();
  }

}
