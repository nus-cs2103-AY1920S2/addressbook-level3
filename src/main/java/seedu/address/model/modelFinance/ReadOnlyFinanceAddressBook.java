package seedu.address.model.modelFinance;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyFinanceAddressBook {

  /**
   * Returns an unmodifiable view of the teachers list. This list will not contain any duplicate
   * teachers.
   */
  ObservableList<Finance> getFinanceList();

}
