package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class UserPrefsTest {
    public static final Path VALID_PATH = Paths.get("test");

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        UserPrefs userPref = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPref.setGuiSettings(null));
    }

    @Test
    public void setStudentTAbleFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setStudentTAbleFilePath(null));
    }

    @Test
    public void setStudentTAbleFilePath_validPath_success() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setStudentTAbleFilePath(VALID_PATH);
        assertEquals(VALID_PATH, userPrefs.getStudentTAbleFilePath());
    }

    @Test
    public void setConsultTAbleFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setConsultTAbleFilePath(null));
    }

    @Test
    public void setConsultTAbleFilePath_validPath_success() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setConsultTAbleFilePath(VALID_PATH);
        assertEquals(VALID_PATH, userPrefs.getConsultTAbleFilePath());
    }

    @Test
    public void setTutorialTAbleFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setTutorialTAbleFilePath(null));
    }

    @Test
    public void setTutorialTAbleFilePath_validPath_success() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setTutorialTAbleFilePath(VALID_PATH);
        assertEquals(VALID_PATH, userPrefs.getTutorialTAbleFilePath());
    }

    @Test
    public void setModTAbleFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setModTAbleFilePath(null));
    }

    @Test
    public void setModTAbleFilePath_validPath_success() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setModTAbleFilePath(VALID_PATH);
        assertEquals(VALID_PATH, userPrefs.getModTAbleFilePath());
    }

    @Test
    public void setReminderTAbleFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setReminderTAbleFilePath(null));
    }

    @Test
    public void setReminderTAbleFilePath_validPath_success() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setReminderTAbleFilePath(VALID_PATH);
        assertEquals(VALID_PATH, userPrefs.getReminderTAbleFilePath());
    }

}
