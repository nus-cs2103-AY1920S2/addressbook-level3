package seedu.eylah.expensesplitter.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.eylah.expensesplitter.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.eylah.commons.model.UserPrefs;

public class ModelManagerTest {

    private SplitterModelManager modelManager = new SplitterModelManager();

    @Test
    public void constructor() {

        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new PersonAmountBook(), new PersonAmountBook(modelManager.getPersonAmountBook()));
        //Cannot do 1 for ReceiptBook because Receiptbook automatically
        //starts a receipt when intialized thus causing it not to be equals
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {

        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setPersonAmountBookFilePath(Paths.get("address/book/file/path"));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setPersonAmountBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }


    @Test
    public void setPersonAmountBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setPersonAmountBookFilePath(null));
    }

    @Test
    public void setPersonAmountBookFilePath_validPath_setsFoodBookFilePath() {

        Path path = Paths.get("address/book/file/path");
        modelManager.setPersonAmountBookFilePath(path);
        assertEquals(path, modelManager.getPersonAmountBookFilePath());

    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }






}
