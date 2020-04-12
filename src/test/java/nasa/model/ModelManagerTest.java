package nasa.model;

import static nasa.model.Model.PREDICATE_SHOW_ALL_MODULES;
import static nasa.testutil.Assert.assertThrows;
import static nasa.testutil.ModuleBuilder.DEADLINE_1;
import static nasa.testutil.ModuleBuilder.DEADLINE_2;
import static nasa.testutil.ModuleBuilder.DEADLINE_3;
import static nasa.testutil.ModuleBuilder.DEADLINE_4;
import static nasa.testutil.ModuleBuilder.EVENT_1;
import static nasa.testutil.ModuleBuilder.EVENT_2;
import static nasa.testutil.ModuleBuilder.EVENT_3;
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
import nasa.model.activity.Deadline;
import nasa.model.activity.Event;
import nasa.model.module.Module;
import nasa.model.module.ModuleCode;
import nasa.model.module.ModuleName;
import nasa.model.module.exceptions.DuplicateModuleException;
import nasa.testutil.NasaBookBuilder;
import nasa.testutil.UiHistoryBuilder;

class ModelManagerTest {

    /**
     * Initialized model manager with NasaBook, NASABOOK_TYPE_1
     */
    private ModelManager modelManager = new ModelManager(NASABOOK_TYPE_1, new HistoryBook<>(),
            new UiHistoryBuilder().build(),
            new UserPrefs());

    @Test
    void initialisation() {
        assertTrue(modelManager.getNasaBook().equals(NASABOOK_TYPE_1));

        assertTrue(modelManager.hasModule(new ModuleCode("CS2103T")));
        assertTrue(modelManager.hasModule(new ModuleCode("GEH1001")));
        assertTrue(modelManager.hasModule(new ModuleCode("CS2106")));
    }

    @Test
    void getFilteredModuleListTest() {
        ObservableList<Module> list = modelManager.getFilteredModuleList();

        assertEquals(new ModuleCode("CS2106"), list.get(0).getModuleCode());
        assertEquals(new ModuleCode("GEH1001"), list.get(1).getModuleCode());
        assertEquals(new ModuleCode("CS2103T"), list.get(2).getModuleCode());
    }

    @Test
    void getFilteredDeadlineListTest() {
        ObservableList<Deadline> deadlineList = modelManager.getFilteredDeadlineList(new ModuleCode("CS2103T"));
        assertEquals(DEADLINE_1.getName(), deadlineList.get(0).getName());
        assertEquals(DEADLINE_2.getName(), deadlineList.get(1).getName());
        assertEquals(DEADLINE_3.getName(), deadlineList.get(2).getName());
        assertEquals(DEADLINE_4.getName(), deadlineList.get(3).getName());
    }

    @Test
    void getFilteredEventListTest() {
        ObservableList<Event> eventList = modelManager.getFilteredEventList(new ModuleCode("CS2103T"));

        assertEquals(EVENT_1.getName(), eventList.get(0).getName());
        assertEquals(EVENT_2.getName(), eventList.get(1).getName());
        assertEquals(EVENT_3.getName(), eventList.get(2).getName());
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
    public void addModule_uniqueModule_success() {
        Module cs1010 = new Module(new ModuleCode("CS1010"), new ModuleName("Test"));
        modelManager.addModule(cs1010);
        assertTrue(modelManager.hasModule(cs1010.getModuleCode()));
    }

    @Test
    public void addModule_duplicateModule_failure() {
        assertThrows(DuplicateModuleException.class, () -> modelManager.addModule(GEH1001));
    }

    @Test
    public void removeModule_success() {
        modelManager.deleteModule(GEH1001.getModuleCode());
        assertFalse(modelManager.hasModule(GEH1001.getModuleCode()));
    }

    @Test
    public void removeDeadline_success() {
        modelManager.addDeadline(GEH1001.getModuleCode(), DEADLINE_1);
        modelManager.removeDeadline(GEH1001.getModuleCode(), DEADLINE_1);
        assertFalse(modelManager.hasActivity(GEH1001.getModuleCode(), DEADLINE_1));
    }

    @Test
    public void removeEvent_success() {
        modelManager.addEvent(GEH1001.getModuleCode(), EVENT_1);
        modelManager.removeEvent(GEH1001.getModuleCode(), EVENT_1);
        assertFalse(modelManager.hasActivity(GEH1001.getModuleCode(), EVENT_1));
    }

    @Test
    public void hasActivity_success() {
        modelManager.addDeadline(CS2106.getModuleCode(), DEADLINE_3);
        assertTrue(modelManager.hasActivity(CS2106.getModuleCode(), DEADLINE_3));
    }

    @Test
    public void hasActivity_failure() {
        assertFalse(modelManager.hasActivity(CS2106.getModuleCode(), EVENT_2));
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
        modelManager = new ModelManager(nasaBook, new HistoryBook<>(), new HistoryBook<>(), userPrefs);
        ModelManager modelManagerCopy = new ModelManager(nasaBook, new HistoryBook<>(), new HistoryBook<>(), userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentNasaBook, new HistoryBook<>(), new HistoryBook<>(),
                userPrefs)));

        // different filteredList -> returns false
        String[] keywords = GEH1001.getModuleName().toString().split("\\s+");
        //modelManager.updateFilteredModuleList((Predicate) new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        //assertFalse(modelManager.equals(new ModelManager(nasaBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setNasaBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(nasaBook, new HistoryBook<>(), new HistoryBook<>(),
                differentUserPrefs)));
    }
}
