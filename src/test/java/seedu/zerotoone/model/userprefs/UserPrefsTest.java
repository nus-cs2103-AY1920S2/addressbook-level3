package seedu.zerotoone.model.userprefs;

import static seedu.zerotoone.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class UserPrefsTest {
    @Test
    public void setExerciseListFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setExerciseListFilePath(null));
    }
    
    @Test
    public void setWorkoutListFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setWorkoutListFilePath(null));
    }
    
    @Test
    public void setScheduleListFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setScheduleListFilePath(null));
    }
    
    @Test
    public void setLogListFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setLogListFilePath(null));
    }
}
