package nasa.model;

import static nasa.model.Model.PREDICATE_SHOW_ALL_MODULES;
import static nasa.testutil.Assert.assertThrows;
import static nasa.testutil.TypicalModules.CS2103T;
import static nasa.testutil.TypicalModules.CS2106;
import static nasa.testutil.TypicalModules.GEH1001;
import static nasa.testutil.TypicalNasaBook.NASABOOK_TYPE_1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;

import nasa.commons.core.GuiSettings;
import nasa.model.activity.Activity;
import nasa.model.module.ModuleCode;
import nasa.testutil.NasaBookBuilder;

class ModelManagerTest {

    /**
     * Initialized model manager with NasaBook, NASABOOK_TYPE_1
     */
    private ModelManager modelManager = new ModelManager(NASABOOK_TYPE_1, new HistoryBook<>(),
            new UserPrefs());

    /*
    @Test
    void initialisation() {
        assertTrue(modelManager.getNasaBook().equals(NASABOOK_TYPE_1));

        assertTrue(modelManager.hasModule(new ModuleCode("CS2103T")));
        assertTrue(modelManager.hasModule(new ModuleCode("GEH1001")));
        assertTrue(modelManager.hasModule(new ModuleCode("CS2106")));
    }

    @Test
    void getFilteredModuleListTest() {
        ObservableList<Activity> list = modelManager.getFilteredModuleList();

        assertEquals(new ModuleCode("CS2106"), list.get(0).getModuleCode());
        assertEquals(new ModuleCode("GEH1001"), list.get(1).getModuleCode());
        assertEquals(new ModuleCode("CS2102T"), list.get(2).getModuleCode());
    }

    @Test
    void getFilteredDeadlineListTest() {
        ObservableList<Activity> deadlineList = modelManager.getFilteredDeadlineList(new ModuleCode("CS2103T"));

        assertEquals(DEADLINE_1, deadlineList.get(0));
        assertEquals(DEADLINE_2, deadlineList.get(1));
        assertEquals(DEADLINE_3, deadlineList.get(2));
        assertEquals(DEADLINE_4, deadlineList.get(3));
    }

    @Test
    void getFilteredEventListTest() {
        ObservableList<Activity> eventList = modelManager.getFilteredEventList(new ModuleCode("CS2103T"));

        assertEquals(EVENT_1, eventList.get(0));
        assertEquals(EVENT_2, eventList.get(1));
        assertEquals(EVENT_3, eventList.get(2));
    }

    @Test
    public void constructor() {
        modelManager.deleteModule(GEH1001.getModuleCode());
        modelManager.deleteModule(CS2103T.getModuleCode());
        modelManager.deleteModule(CS2106.getModuleCode());
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new NasaBook(), new NasaBook(modelManager.getNasaBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setNasaBookFilePath(Paths.get("nasa/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setNasaBookFilePath(Paths.get("new/nasa/book/file/path"));
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
    public void setNasaBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setNasaBookFilePath(null));
    }

    @Test
    public void setNasaBookFilePath_validPath_setsNasaBookFilePath() {
        Path path = Paths.get("nasa/book/file/path");
        modelManager.setNasaBookFilePath(path);
        assertEquals(path, modelManager.getNasaBookFilePath());
    }

    @Test
    public void hasModule_nullModule_throwsNullPointerException() { 
        assertThrows(NullPointerException.class, () -> modelManager.hasModule((nasa.model.module.ModuleCode) null));
    }

    @Test
    public void hasModule_moduleNotInNasaBook_returnsFalse() {
        modelManager.deleteModule(GEH1001.getModuleCode()); // module code
        assertFalse(modelManager.hasModule(GEH1001.getModuleCode()));
    }

    @Test
    public void hasModule_moduleInNasaBook_returnsTrue() {
        assertTrue(modelManager.hasModule(GEH1001.getModuleCode())); // module code
    }

    @Test
    public void getFilteredModuleList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredModuleList().remove(0));
    }

    @Test
    public void equals() {
        NasaBook nasaBook = new NasaBookBuilder().build();
        NasaBook differentNasaBook = new NasaBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(nasaBook, new HistoryBook<>(), userPrefs);
        ModelManager modelManagerCopy = new ModelManager(nasaBook, new HistoryBook<>(), userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentNasaBook, new HistoryBook<>(), userPrefs)));

        // different filteredList -> returns false
        String[] keywords = GEH1001.getModuleName().toString().split("\\s+");
        //modelManager.updateFilteredModuleList((Predicate) new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        //assertFalse(modelManager.equals(new ModelManager(nasaBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setNasaBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(nasaBook, new HistoryBook<>(), differentUserPrefs)));
    }*/

}
