package com.notably.model.userpref;

import static com.notably.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class UserPrefModelTest {

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        UserPrefModel userPref = new UserPrefModelImpl();
        assertThrows(NullPointerException.class, () -> userPref.setGuiSettings(null));
    }

    @Test
    public void setBlockTreeFilePath_nullPath_throwsNullPointerException() {
        UserPrefModel userPrefs = new UserPrefModelImpl();
        assertThrows(NullPointerException.class, () -> userPrefs.setBlockDataFilePath(null));
    }

}
