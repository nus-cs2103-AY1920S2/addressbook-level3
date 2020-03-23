package nasa.model;

import javafx.collections.ObservableList;
import nasa.model.activity.Activity;
import nasa.model.module.ModuleCode;
import nasa.model.module.UniqueModuleList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static nasa.testutil.TypicalNasaBook.NASABOOK_TYPE_1;
import org.junit.jupiter.api.Test;

class ModelManagerTest {

    @Test
    void initialisation() {
        ModelManager modelManager = new ModelManager(NASABOOK_TYPE_1, new HistoryBook<UniqueModuleList>(), new UserPrefs());
        assertTrue(modelManager.getNasaBook().equals(NASABOOK_TYPE_1));

        assertTrue(modelManager.hasModule(new ModuleCode("CS2103T")));
        assertTrue(modelManager.hasModule(new ModuleCode("GEH1001")));
        assertTrue(modelManager.hasModule(new ModuleCode("CS2106")));
    }

    @Test
    void getFilteredActivityListTest() {
        ModelManager modelManager = new ModelManager(NASABOOK_TYPE_1, new HistoryBook<UniqueModuleList>(), new UserPrefs());
        ObservableList<Activity> list = modelManager.getFilteredActivityList(new ModuleCode("CS2103T"));
        assertEquals("Homework", list.get(0).getName().toString());
        assertEquals("Test", list.get(1).getName().toString());
        assertEquals("Prepare group meeting", list.get(2).getName().toString());
        assertEquals("Exam", list.get(3).getName().toString());
        assertEquals("MPSH", list.get(4).getName().toString());
    }

    @Test
    void addOnList_test() {
        ModelManager modelManager = new ModelManager(NASABOOK_TYPE_1, new HistoryBook<UniqueModuleList>(), new UserPrefs());
        ObservableList<Activity> list = modelManager.getFilteredActivityList(new ModuleCode("CS2103T"));
        modelManager.deleteModule(new ModuleCode("CS2103T"));
        modelManager.hasModule(new ModuleCode("CS2103T"));
        System.out.println(modelManager.getNasaBook().getModuleList());
    }

}