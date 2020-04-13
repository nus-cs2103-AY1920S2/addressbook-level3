package seedu.eylah.expensesplitter.model;

import static seedu.eylah.expensesplitter.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.eylah.commons.model.UserPrefs;

public class UserPrefsTest {

    @Test
    public void setPersonAmountBookFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setPersonAmountBookFilePath(null));
    }
}
