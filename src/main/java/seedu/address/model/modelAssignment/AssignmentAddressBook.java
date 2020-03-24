package seedu.address.model.modelAssignment;

import javafx.collections.ObservableList;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Wraps all data at the address-book level Duplicates are not allowed (by .isSamePerson
 * comparison)
 */
public class AssignmentAddressBook implements ReadOnlyAssignmentAddressBook {

  private final UniqueAssignmentList assignments;

  /*
   * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
   * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
   *
   * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
   *   among constructors.
   */ {
    assignments = new UniqueAssignmentList();
  }

  public AssignmentAddressBook() {
  }

  /**
   * Creates an AddressBook using the Persons in the {@code toBeCopied}
   */
  public AssignmentAddressBook(ReadOnlyAssignmentAddressBook toBeCopied) {
    this();
    resetData(toBeCopied);
  }

  //// list overwrite operations

  /**
   * Replaces the contents of the assignment list with {@code assignments}. {@code assignments} must not contain
   * duplicate assignments.
   */
  public void setAssignments(List<Assignment> assignments) {
    this.assignments.setAssignments(assignments);
  }

  /**
   * Resets the existing data of this {@code AddressBook} with {@code newData}.
   */
  public void resetData(ReadOnlyAssignmentAddressBook newData) {
    requireNonNull(newData);

    setAssignments(newData.getAssignmentList());
  }

  //// assignment-level operations

  /**
   * Returns true if a assignment with the same identity as {@code assignment} exists in the address book.
   */
  public boolean hasAssignments(Assignment assignment) {
    requireNonNull(assignment);
    return assignments.contains(assignment);
  }

  /**
   * Adds a assignment to the address book. The assignment must not already exist in the address book.
   */
  public void addAssignment(Assignment p) {
    assignments.add(p);
  }

  /**
   * Replaces the given assignment {@code target} in the list with {@code editedAssignment}. {@code target}
   * must exist in the address book. The assignment identity of {@code editedAssignment} must not be the
   * same as another existing assignment in the address book.
   */
  public void setAssignment(Assignment target, Assignment editedAssignment) {
    requireNonNull(editedAssignment);

    assignments.setAssignment(target, editedAssignment);
  }

  /**
   * Removes {@code key} from this {@code AddressBook}. {@code key} must exist in the address book.
   */
  public void removeAssignment(Assignment key) {
    assignments.remove(key);
  }

  //// util methods

  @Override
  public String toString() {
    return assignments.asUnmodifiableObservableList().size() + " assignments";
    // TODO: refine later
  }

  @Override
  public ObservableList<Assignment> getAssignmentList() {
    return assignments.asUnmodifiableObservableList();
  }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
        || (other instanceof AssignmentAddressBook
        // instanceof handles nulls
        && assignments.equals(((AssignmentAddressBook) other).assignments));
  }

  @Override
  public int hashCode() {
    return assignments.hashCode();
  }
}
