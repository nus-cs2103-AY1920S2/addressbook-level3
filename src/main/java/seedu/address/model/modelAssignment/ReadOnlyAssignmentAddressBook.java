package seedu.address.model.modelAssignment;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAssignmentAddressBook {

  /**
   * Returns an unmodifiable view of the assignment list. This list will not contain any duplicate
   * assignment.
   */
  ObservableList<Assignment> getAssignmentList();

}
