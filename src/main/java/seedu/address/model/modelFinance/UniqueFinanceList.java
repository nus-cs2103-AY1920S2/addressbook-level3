package seedu.address.model.modelFinance;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicateFinanceException;
import seedu.address.model.person.exceptions.FinanceNotFoundException;

/**
 * A list of finances that enforces uniqueness between its elements and does not allow nulls. A
 * finance is considered unique by comparing using {@code Finance#isSameFinance(Finance)}. As such,
 * adding and updating of finances uses Finance#isSameFinance(Finance) for equality so as to ensure
 * that the finance being added or updated is unique in terms of identity in the UniqueFinanceList.
 * However, the removal of a finance uses finance#equals(Object) so as to ensure that the finance
 * with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Finance#isSameFinance(Finance)
 */
public class UniqueFinanceList implements Iterable<Finance> {

  private final ObservableList<Finance> internalList = FXCollections.observableArrayList();
  private final ObservableList<Finance> internalUnmodifiableList =
      FXCollections.unmodifiableObservableList(internalList);

  /**
   * Returns true if the list contains an equivalent finance as the given argument.
   */
  public boolean contains(Finance toCheck) {
    requireNonNull(toCheck);
    return internalList.stream().anyMatch(toCheck::isSameFinance);
  }

  /**
   * Adds a finance to the list. The finance must not already exist in the list.
   */
  public void add(Finance toAdd) {
    requireNonNull(toAdd);
    if (contains(toAdd)) {
      throw new DuplicateFinanceException();
    }
    internalList.add(toAdd);
  }

  /**
   * Replaces the finance {@code target} in the list with {@code editedFinance}. {@code target} must
   * exist in the list. The Finance identity of {@code editedFinance} must not be the same as
   * another existing finance in the list.
   */
  public void setFinance(Finance target, Finance editedFinance) {
    requireAllNonNull(target, editedFinance);

    int index = internalList.indexOf(target);
    if (index == -1) {
      throw new FinanceNotFoundException();
    }

    if (!target.isSameFinance(editedFinance) && contains(editedFinance)) {
      throw new DuplicateFinanceException();
    }

    internalList.set(index, editedFinance);
  }

  /**
   * Removes the equivalent finance from the list. The finance must exist in the list.
   */
  public void remove(Finance toRemove) {
    requireNonNull(toRemove);
    if (!internalList.remove(toRemove)) {
      throw new FinanceNotFoundException();
    }
  }

  public void setFinances(UniqueFinanceList replacement) {
    requireNonNull(replacement);
    internalList.setAll(replacement.internalList);
  }

  /**
   * Replaces the contents of this list with {@code finances}. {@code finances} must not contain
   * duplicate finances.
   */
  public void setFinances(List<Finance> finances) {
    requireAllNonNull(finances);
    if (!financesAreUnique(finances)) {
      throw new DuplicateFinanceException();
    }

    internalList.setAll(finances);
  }

  /**
   * Returns the backing list as an unmodifiable {@code ObservableList}.
   */
  public ObservableList<Finance> asUnmodifiableObservableList() {
    return internalUnmodifiableList;
  }

  @Override
  public Iterator<Finance> iterator() {
    return internalList.iterator();
  }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
        || (other instanceof UniqueFinanceList
        // instanceof handles nulls
        && internalList.equals(((UniqueFinanceList) other).internalList));
  }

  @Override
  public int hashCode() {
    return internalList.hashCode();
  }

  /**
   * Returns true if {@code finances} contains only unique finances.
   */
  private boolean financesAreUnique(List<Finance> finances) {
    for (int i = 0; i < finances.size() - 1; i++) {
      for (int j = i + 1; j < finances.size(); j++) {
        if (finances.get(i).isSameFinance(finances.get(j))) {
          return false;
        }
      }
    }
    return true;
  }
}
