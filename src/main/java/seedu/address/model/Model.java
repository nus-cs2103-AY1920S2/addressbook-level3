package seedu.address.model;

import java.nio.file.Path;
import java.time.Month;
import java.time.Year;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Person;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Budget;
import seedu.address.model.transaction.Date;
import seedu.address.model.transaction.Expense;
import seedu.address.model.transaction.Income;
import seedu.address.model.transaction.Transaction;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Transaction> PREDICATE_SHOW_ALL_TRANSACTIONS = unused -> true;


    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Returns the user prefs' user data file path.
     */
    Path getUserDataFilePath();

    /**
     * Sets the user prefs' user data file path.
     */
    void setUserDataFilePath(Path userDataFilePath);

    /**
     * Returns the user prefs' wallet file path.
     */
    Path getWalletFilePath();

    /**
     * Sets the user prefs' wallet file path.
     */
    void setWalletFilePath(Path walletFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Returns the AddressBook
     */
    ReadOnlyWallet getWallet();

    /**
     * Replaces user data with new {@code userData}.
     */
    void setUserData(ReadOnlyUserData userData);

    /**
     * Returns the UserData.
     */
    ReadOnlyUserData getUserData();

    /**
     * Returns true if the user has not enter the user data.
     */
    boolean isUserDataNull();

    /**
     * Replaces address book data with the data in {@code wallet}.
     */
    public void setWallet(ReadOnlyWallet wallet);

    /**
     * Returns true if the given {@code income} exists in the Wallet.
     */
    boolean hasIncome(Income income);

    /**
     * Adds the given {@code income} to the Wallet.
     */
    void addIncome(Income income);

    /**
     * Deletes the given income.
     * The income must exist in the Wallet.
     */
    void deleteIncome(Income target);

    /**
     * Replaces the given income {@code target} with {@code editedIncome}.
     * {@code target} must exist in the Wallet.
     */
    void setIncome(Income target, Income editedIncome);

    /**
     * Returns true if the given {@code expense} exists in the Wallet.
     */
    boolean hasExpense(Expense expense);

    /**
     * Adds the given {@code expense} to the Wallet.
     */
    void addExpense(Expense expense);

    /**
     * Deletes the given expense.
     * The expense must exist in the Wallet.
     */
    void deleteExpense(Expense target);

    /**
     * Replaces the given expense {@code target} with {@code editedExpense}.
     * {@code target} must exist in the Wallet.
     */
    void setExpense(Expense target, Expense editedExpense);

    /**
     * Returns the total expenditure in the month of the specified date.
     */
    Amount getTotalExpenditureInMonth(Date date);

    /**
     * Sets a budget as {@code budget}.
     */
    void setBudget(Budget budget);

    /**
     * Sets the default budget as {@code budget}.
     */
    void setDefaultBudget(Budget budget);

    /**
     * Returns whether the budget for a specified month and year has been exceeded.
     */
    boolean hasExceededBudget(Month month, Year year);

    /**
     * Returns the budget specified for the month and year, if any.
     */
    Budget getBudget(Month month, Year year);

    /**
     * Deletes the given transaction.
     * The transaction must exist in the Wallet.
     */
    public void deleteTransaction(Transaction transactionToDelete);

    /**
     * Returns an unmodifiable view of the filtered Expense list
     */
    ObservableList<Expense> getFilteredExpenseList();

    /**
     * Returns an unmodifiable view of the filtered Income list
     */
    ObservableList<Income> getFilteredIncomeList();

    /**
     * Replaces the given transaction {@code target} with {@code editedTransaction}.
     * {@code target} must exist in the Wallet.
     */
    void setTransaction(Transaction target, Transaction editedTransaction);

    /**
     * Returns an unmodifiable view of the filtered Transaction list
     */
    ObservableList<Transaction> getFilteredTransactionList();

    /**
     * Returns an unmodifiable view of the Transaction list
     */
    ObservableList<Transaction> getTransactionList();

    /**
     * Updates the filter of the filtered transaction list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTransactionList(Predicate<Transaction> predicate);
}
