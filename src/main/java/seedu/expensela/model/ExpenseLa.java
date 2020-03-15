package seedu.expensela.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.expensela.model.monthlydata.MonthlyData;
import seedu.expensela.model.transaction.Transaction;
import seedu.expensela.model.transaction.TransactionList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class ExpenseLa implements ReadOnlyExpenseLa {

    private final MonthlyData monthlyData;
    private final TransactionList transactions;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        monthlyData = new MonthlyData(null, null, null, null);
        transactions = new TransactionList();
    }

    public ExpenseLa() {}

    /**
     * Creates an ExpenseLa using the Persons in the {@code toBeCopied}
     */
    public ExpenseLa(ReadOnlyExpenseLa toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code transactions}.
     * {@code transactions} must not contain duplicate transactions.
     */
    public void setPersons(List<Transaction> transactions) {
        this.transactions.setTransaction(transactions);
    }

    /**
     * Resets the existing data of this {@code ExpenseLa} with {@code newData}.
     */
    public void resetData(ReadOnlyExpenseLa newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Transaction transaction) {
        requireNonNull(transaction);
        return transactions.contains(transaction);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Transaction p) {
        transactions.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Transaction target, Transaction editedTransaction) {
        requireNonNull(editedTransaction);

        transactions.setTransaction(target, editedTransaction);
    }

    /**
     * Removes {@code key} from this {@code ExpenseLa}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Transaction key) {
        transactions.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return transactions.asUnmodifiableObservableList().size() + " transactions";
        // TODO: refine later
    }

    @Override
    public ObservableList<Transaction> getPersonList() {
        return transactions.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpenseLa // instanceof handles nulls
                && transactions.equals(((ExpenseLa) other).transactions));
    }

    @Override
    public int hashCode() {
        return transactions.hashCode();
    }
}
