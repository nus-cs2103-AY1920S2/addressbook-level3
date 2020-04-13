package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalWallet.ALLOWANCE;
import static seedu.address.testutil.TypicalWallet.DUCK_RICE;
import static seedu.address.testutil.TypicalWallet.TA_JOB;
import static seedu.address.testutil.TypicalWallet.getTypicalWallet;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.PeopleNamePredicate;
import seedu.address.model.transaction.exceptions.TransactionNotFoundException;
import seedu.address.testutil.AddressBookBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
    }

    // =========== UserPrefs ==================================================================================

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void setUserDataFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserDataFilePath(null));
    }

    @Test
    public void setUserDataFilePath_validPath_setsUserDataFilePath() {
        Path path = Paths.get("user/data/file/path");
        modelManager.setUserDataFilePath(path);
        assertEquals(path, modelManager.getUserDataFilePath());
    }

    @Test
    public void setWalletFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setWalletFilePath(null));
    }

    @Test
    public void setWalletFilePath_validPath_setsWalletFilePath() {
        Path path = Paths.get("wallet/file/path");
        modelManager.setWalletFilePath(path);
        assertEquals(path, modelManager.getWalletFilePath());
    }

    // =========== AddressBook ================================================================================

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    // =========== Wallet =====================================================================================

    @Test
    public void hasIncome_nullIncome_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasIncome(null));
    }

    @Test
    public void hasIncome_incomeNotInWallet_returnsFalse() {
        assertFalse(modelManager.hasIncome(TA_JOB));
    }

    @Test
    public void hasIncome_incomeInWallet_returnsTrue() {
        modelManager.addIncome(TA_JOB);
        assertTrue(modelManager.hasIncome(TA_JOB));
    }

    @Test
    public void addIncome_nullIncome_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.addIncome(null));
    }

    @Test
    public void deleteIncome_targetIncomeNotInList_throwsTransactionNotFoundException() {
        assertThrows(TransactionNotFoundException.class, () -> modelManager.deleteIncome(TA_JOB));
    }

    @Test
    public void deleteIncome_targetIncomeInList_deletesIncome() {
        modelManager.addIncome(TA_JOB);
        modelManager.deleteIncome(TA_JOB);
        ModelManager expectedModelManager = new ModelManager();
        assertEquals(expectedModelManager, modelManager);
    }

    @Test
    public void setIncome_nullTargetIncome_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setIncome(null, TA_JOB));
    }

    @Test
    public void setIncome_nullEditedIncome_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setIncome(TA_JOB, null));
    }

    @Test
    public void setIncome_targetIncomeNotInList_throwsTransactionNotFoundException() {
        assertThrows(TransactionNotFoundException.class, () -> modelManager.setIncome(TA_JOB, TA_JOB));
    }

    @Test
    public void setIncome_validIncome_success() {
        modelManager.addIncome(TA_JOB);
        modelManager.setIncome(TA_JOB, TA_JOB);
        ModelManager expectedModelManager = new ModelManager();
        expectedModelManager.addIncome(TA_JOB);
        assertEquals(expectedModelManager, modelManager);
    }

    @Test
    public void hasExpense_nullExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasExpense(null));
    }

    @Test
    public void hasExpense_expenseNotInWallet_returnsFalse() {
        assertFalse(modelManager.hasExpense(DUCK_RICE));
    }

    @Test
    public void hasExpense_expenseInWallet_returnsTrue() {
        modelManager.addExpense(DUCK_RICE);
        assertTrue(modelManager.hasExpense(DUCK_RICE));
    }

    @Test
    public void addExpense_nullExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.addExpense(null));
    }

    @Test
    public void deleteExpense_targetExpenseNotInList_throwsTransactionNotFoundException() {
        assertThrows(TransactionNotFoundException.class, () -> modelManager.deleteExpense(DUCK_RICE));
    }

    @Test
    public void deleteExpense_targetExpenseInList_deletesExpense() {
        modelManager.addExpense(DUCK_RICE);
        modelManager.deleteExpense(DUCK_RICE);
        ModelManager expectedModelManager = new ModelManager();
        assertEquals(expectedModelManager, modelManager);
    }

    @Test
    public void deleteTransaction_targetExpenseInList_deletesExpense() {
        modelManager.addExpense(DUCK_RICE);
        modelManager.deleteTransaction(DUCK_RICE);
        ModelManager expectedModelManager = new ModelManager();
        assertEquals(expectedModelManager, modelManager);
    }

    @Test
    public void deleteTransaction_targetIncomeInList_deletesIncome() {
        modelManager.addIncome(ALLOWANCE);
        modelManager.deleteTransaction(ALLOWANCE);
        ModelManager expectedModelManager = new ModelManager();
        assertEquals(expectedModelManager, modelManager);
    }

    @Test
    public void setExpense_nullTargetExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setExpense(null, DUCK_RICE));
    }

    @Test
    public void setExpense_nullEditedExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setExpense(DUCK_RICE, null));
    }

    @Test
    public void setExpense_targetExpenseNotInList_throwsTransactionNotFoundException() {
        assertThrows(TransactionNotFoundException.class, () -> modelManager.setExpense(DUCK_RICE, DUCK_RICE));
    }

    @Test
    public void setExpense_validExpense_success() {
        modelManager.addExpense(DUCK_RICE);
        modelManager.setExpense(DUCK_RICE, DUCK_RICE);
        ModelManager expectedModelManager = new ModelManager();
        expectedModelManager.addExpense(DUCK_RICE);
        assertEquals(expectedModelManager, modelManager);
    }

    // =========== Util Methods ===============================================================================

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void getFilteredTransactionList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredTransactionList().remove(0));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();
        Wallet wallet = getTypicalWallet();
        Wallet differentWallet = new Wallet();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, wallet, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, wallet, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different wallet -> returns false
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentWallet, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new PeopleNamePredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
