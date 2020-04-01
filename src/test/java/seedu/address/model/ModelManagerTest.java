package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.module.Module;
import seedu.address.testutil.PlannerBuilder;
import seedu.address.testutil.TypicalModules;
import seedu.address.testutil.TypicalStudents;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new Planner(), new Planner(modelManager.getPlanner()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setPlannerFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setPlannerFilePath(Paths.get("new/address/book/file/path"));
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
    public void setPlannerFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setPlannerFilePath(null));
    }

    @Test
    public void setPlannerFilePath_validPath_setsPlannerFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setPlannerFilePath(path);
        assertEquals(path, modelManager.setPlannerFilePath(path));
    }


    @Test
    public void hasStudent_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasStudent(null));
    }

    @Test
    public void hasStudent_studentNotInPlanner_returnsFalse() {
        assertFalse(modelManager.hasStudent(TypicalStudents.BOB));
    }

    @Test
    public void hasStudent_studentInPlanner_returnsTrue() {
        modelManager.addStudent(TypicalStudents.BOB);
        assertTrue(modelManager.hasStudent(TypicalStudents.BOB));
    }

    @Test
    public void hasModule_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasModule(null));
    }

    @Test
    public void hasModule_moduleNotInPlanner_returnsFalse() {
        assertFalse(modelManager.hasModule(new Module()));
    }

    @Test
    public void hasModule_moduleInPlanner_returnsTrue() {
        modelManager.addModule(TypicalModules.CS2040);
        assertTrue(modelManager.hasModule(TypicalModules.CS2040));
    }

    @Test
    public void getFilteredModuleList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredModuleList().remove(0));
    }

    @Test
    public void equals() {
        Planner planner = new PlannerBuilder()
                .withModule(TypicalModules.CS2040)
                .withStudent(TypicalStudents.BOB)
                .build();
        Planner differentPlanner = new Planner();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(planner, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(planner, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different planner -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentPlanner, userPrefs)));

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setPlannerFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(planner, differentUserPrefs)));
    }
}
