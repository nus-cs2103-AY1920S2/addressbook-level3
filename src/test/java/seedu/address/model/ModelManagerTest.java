package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_FACILITATORS;
import static seedu.address.model.Model.PREDICATE_SHOW_NO_FACILITATORS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalFacilitators.ALICE;
import static seedu.address.testutil.TypicalFacilitators.BENSON;
import static seedu.address.testutil.TypicalModules.CS2101;
import static seedu.address.testutil.TypicalModules.CS2103T;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.facilitator.ModuleCodesContainKeywordPredicate;
import seedu.address.model.facilitator.NameContainsKeywordsPredicate;
import seedu.address.model.module.ModuleCode;
import seedu.address.testutil.ModManagerBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new ModManager(), new ModManager(modelManager.getModManager()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setModManagerFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setModManagerFilePath(Paths.get("new/address/book/file/path"));
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
    public void setModManagerFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setModManagerFilePath(null));
    }

    @Test
    public void setModManagerFilePath_validPath_setsModManagerFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setModManagerFilePath(path);
        assertEquals(path, modelManager.getModManagerFilePath());
    }

    @Test
    public void hasModuleCode_nullModuleCode_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasModuleCode(null));
    }

    @Test
    public void hasModuleCode_moduleCodeNotInModManager_returnsFalse() {
        assertFalse(modelManager.hasModuleCode("CS2103T"));
    }

    @Test
    public void hasModuleCode_moduleCodeInModManager_returnsTrue() {
        modelManager.addModule(CS2103T);
        assertTrue(modelManager.hasModuleCode("CS2103T"));
    }

    @Test
    public void hasModule_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasModule(null));
    }

    @Test
    public void hasModule_moduleNotInModManager_returnsFalse() {
        assertFalse(modelManager.hasModule(CS2103T));
    }

    @Test
    public void hasModule_moduleInModManager_returnsTrue() {
        modelManager.addModule(CS2103T);
        assertTrue(modelManager.hasModule(CS2103T));
    }

    @Test
    public void getModule_emptyModule_returnsOptionalEmpty() {
        assertEquals(Optional.empty(), modelManager.getModule());
    }

    @Test
    public void getModule_nonEmptyModule_returnsModule() {
        modelManager.updateModule(Optional.of(CS2103T));
        assertEquals(Optional.of(CS2103T), modelManager.getModule());
    }

    @Test
    public void findModule_moduleNotFound_returnsOptionalEmpty() {
        assertEquals(Optional.empty(), modelManager.findModule(new ModuleCode("CS2103T")));
    }

    @Test
    public void findModule_moduleFound_returnsModule() {
        modelManager.addModule(CS2103T);
        assertEquals(Optional.of(CS2103T), modelManager.findModule(new ModuleCode("CS2103T")));
    }

    @Test
    public void getFilteredModuleList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredModuleList().remove(0));
    }

    @Test
    public void hasFacilitator_nullFacilitator_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasFacilitator(null));
    }

    @Test
    public void hasFacilitator_facilitatorNotInModManager_returnsFalse() {
        assertFalse(modelManager.hasFacilitator(ALICE));
    }

    @Test
    public void hasFacilitator_facilitatorInModManager_returnsTrue() {
        modelManager.addFacilitator(ALICE);
        assertTrue(modelManager.hasFacilitator(ALICE));
    }

    @Test
    public void getFilteredFacilitatorList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredFacilitatorList().remove(0));
    }

    @Test
    public void getFacilitatorListForModule_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFacilitatorListForModule().remove(0));
    }

    @Test
    public void hasTask_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasTask(null));
    }

    @Test
    public void getFilteredTaskList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredTaskList().remove(0));
    }

    @Test
    public void getTaskListForModule_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getTaskListForModule().remove(0));
    }

    @Test
    public void equals() {
        ModManager modManager = new ModManagerBuilder().withFacilitator(ALICE).withFacilitator(BENSON)
                .withModule(CS2103T).withModule(CS2101).build();
        ModManager differentModManager = new ModManager();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(modManager, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(modManager, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different modManager -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentModManager, userPrefs)));

        // different filteredFacilitatorList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredFacilitatorList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(modManager, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredFacilitatorList(PREDICATE_SHOW_ALL_FACILITATORS);

        // different facilitatorListForModule -> returns false
        String keyword = CS2103T.getModuleCode().value;
        modelManager.updateFacilitatorListForModule(new ModuleCodesContainKeywordPredicate(keyword));
        assertFalse(modelManager.equals(new ModelManager(modManager, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFacilitatorListForModule(PREDICATE_SHOW_NO_FACILITATORS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setModManagerFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(modManager, differentUserPrefs)));
    }
}
