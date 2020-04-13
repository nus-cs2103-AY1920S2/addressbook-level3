package seedu.jelphabot.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jelphabot.model.Model.PREDICATE_SHOW_ALL_TASKS;
import static seedu.jelphabot.testutil.Assert.assertThrows;
import static seedu.jelphabot.testutil.TypicalReminders.ASSESSMENT_REMINDER;
import static seedu.jelphabot.testutil.TypicalTasks.ASSESSMENT;
import static seedu.jelphabot.testutil.TypicalTasks.BOOK_REPORT;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.jelphabot.commons.core.GuiSettings;
import seedu.jelphabot.model.task.predicates.DescriptionContainsKeywordsPredicate;
import seedu.jelphabot.testutil.JelphaBotBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new JelphaBot(), new JelphaBot(modelManager.getJelphaBot()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setJelphaBotFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setJelphaBotFilePath(Paths.get("new/address/book/file/path"));
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
    public void setJelphaBotFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setJelphaBotFilePath(null));
    }

    @Test
    public void setJelphaBotFilePath_validPath_setsJelphaBotFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setJelphaBotFilePath(path);
        assertEquals(path, modelManager.getJelphaBotFilePath());
    }

    @Test
    public void hasTask_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasTask(null));
    }

    @Test
    public void hasReminder_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasReminder(null));
    }

    @Test
    public void hasTask_taskNotInJelphaBot_returnsFalse() {
        assertFalse(modelManager.hasTask(ASSESSMENT));
    }

    @Test
    public void hasReminder_reminderNotInJelphaBot_returnsFalse() {
        assertFalse(modelManager.hasReminder(ASSESSMENT_REMINDER));
    }

    @Test
    public void hasTask_taskInJelphaBot_returnsTrue() {
        modelManager.addTask(ASSESSMENT);
        assertTrue(modelManager.hasTask(ASSESSMENT));
    }

    @Test
    public void hasReminder_reminderInJelphaBot_returnsTrue() {
        modelManager.addTask(ASSESSMENT);
        modelManager.addReminder(ASSESSMENT_REMINDER);
        assertTrue(modelManager.hasReminder(ASSESSMENT_REMINDER));
    }

    @Test
    public void getFilteredTaskList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredTaskList().remove(0));
    }

    @Test
    public void getFiltererdReminderTaskList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredReminderList().remove(0));
    }

    @Test
    public void getFilteredReminderShowsTaskList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getReminderShowsTaskList().remove(0));
    }

    @Test
    public void getFilteredCalendarTaskList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredCalendarTaskList().remove(0));
    }

    @Test
    public void equals() {
        JelphaBot jelphaBot = new JelphaBotBuilder().withTask(ASSESSMENT).withTask(BOOK_REPORT).build();
        JelphaBot differentJelphaBot = new JelphaBot();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(jelphaBot, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(jelphaBot, userPrefs);
        assertEquals(modelManager, modelManagerCopy);

        // same object -> returns true
        assertEquals(modelManager, modelManager);

        // null -> returns false
        assertNotEquals(null, modelManager);

        // different types -> returns false
        assertNotEquals(5, modelManager);

        // different jelphaBot -> returns false
        assertNotEquals(modelManager, new ModelManager(differentJelphaBot, userPrefs));

        // different filteredList -> returns false
        String[] keywords = ASSESSMENT.getDescription().fullDescription.split("\\s+");
        modelManager.updateFilteredTaskList(new DescriptionContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertNotEquals(modelManager, new ModelManager(jelphaBot, userPrefs));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setJelphaBotFilePath(Paths.get("differentFilePath"));
        assertNotEquals(modelManager, new ModelManager(jelphaBot, differentUserPrefs));
    }
}
