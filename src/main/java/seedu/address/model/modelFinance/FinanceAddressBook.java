package seedu.address.model.modelFinance;

import static java.util.Objects.requireNonNull;

import java.util.List;
import javafx.collections.ObservableList;

/**
 * Wraps all data at the address-book level Duplicates are not allowed (by .isSamePerson
 * comparison)
 */
public class FinanceAddressBook implements ReadOnlyFinanceAddressBook {

  private final UniqueFinanceList finances;

  /*
   * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
   * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
   *
   * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
   *   among constructors.
   */ {
    finances = new UniqueFinanceList();
  }

  public FinanceAddressBook() {
  }

  /**
   * Creates an AddressBook using the Persons in the {@code toBeCopied}
   */
  public FinanceAddressBook(ReadOnlyFinanceAddressBook toBeCopied) {
    this();
    resetData(toBeCopied);
  }

  //// list overwrite operations

  /**
   * Replaces the contents of the finance list with {@code finances}. {@code finances} must not
   * contain duplicate finances.
   */
  public void setFinances(List<Finance> finances) {
    this.finances.setFinances(finances);
  }

  /**
   * Resets the existing data of this {@code AddressBook} with {@code newData}.
   */
  public void resetData(ReadOnlyFinanceAddressBook newData) {
    requireNonNull(newData);

    setFinances(newData.getFinanceList());
  }

  //// finance-level operations

  /**
   * Returns true if a finance with the same identity as {@code finance} exists in the address
   * book.
   */
  public boolean hasFinances(Finance finance) {
    requireNonNull(finance);
    return finances.contains(finance);
  }

  /**
   * Adds a finance to the address book. The finance must not already exist in the address book.
   */
  public void addFinance(Finance p) {
    finances.add(p);
  }

  /**
   * Replaces the given finance {@code target} in the list with {@code editedFinance}. {@code
   * target} must exist in the address book. The finance identity of {@code editedFinance} must not
   * be the same as another existing finance in the address book.
   */
  public void setFinance(Finance target, Finance editedFinance) {
    requireNonNull(editedFinance);

    finances.setFinance(target, editedFinance);
  }

  /**
   * Removes {@code key} from this {@code AddressBook}. {@code key} must exist in the address book.
   */
  public void removeFinance(Finance key) {
    finances.remove(key);
  }

  //// util methods

  @Override
  public String toString() {
    return finances.asUnmodifiableObservableList().size() + " finances";
    // TODO: refine later
  }

  @Override
  public ObservableList<Finance> getFinanceList() {
    return finances.asUnmodifiableObservableList();
  }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
        || (other instanceof FinanceAddressBook
        // instanceof handles nulls
        && finances.equals(((FinanceAddressBook) other).finances));
  }

  @Override
  public int hashCode() {
    return finances.hashCode();
  }
}
