package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.nusmodule.Grade;
import seedu.address.model.nusmodule.ModuleCode;
import seedu.address.model.nusmodule.ModuleTask;
import seedu.address.model.nusmodule.NusModule;
import seedu.address.testutil.TypicalNusModules;

class ModuleBookTest {

    final NusModule cs2030 = TypicalNusModules.CS2030;
    final ModuleTask task = TypicalNusModules.TYPICAL_MODULE_TASK_1;

    @Test
    void addModule_hasModule_deleteModule() {
        ModuleBook moduleBook = new ModuleBook();
        moduleBook.addModule(cs2030);
        assertTrue(moduleBook.hasModule(cs2030.getModuleCode()));
        assertFalse(moduleBook.hasModule(new ModuleCode("cs2040")));
        moduleBook.deleteModule(cs2030.getModuleCode());
        assertFalse(moduleBook.hasModule(cs2030.getModuleCode()));
    }

    @Test
    void getModule() {
        ModuleBook moduleBook = new ModuleBook();
        moduleBook.addModule(cs2030);
        assertEquals(moduleBook.getModule(cs2030.getModuleCode()), cs2030);
    }

    @Test
    void gradeModule() {
        ModuleBook moduleBook = new ModuleBook();
        moduleBook.addModule(cs2030);
        moduleBook.gradeModule(cs2030.getModuleCode(), Grade.S);
        assertEquals(cs2030.getGrade().get(), Grade.S);
    }

    @Test
    void addModuleTask_doneModuleTask() {
        ModuleBook moduleBook = new ModuleBook();
        moduleBook.addModule(cs2030);
        moduleBook.addModuleTask(task);
        moduleBook.doneModuleTask(cs2030.getModuleCode(), Index.fromZeroBased(0));
        assertDoesNotThrow(() -> {});
    }

    @Test
    void getSizeOfModuleTaskList() {
        ModuleBook moduleBook = new ModuleBook();
        moduleBook.addModule(cs2030);
        moduleBook.addModuleTask(task);
        assertNotEquals(moduleBook.getSizeOfModuleTaskList(cs2030.getModuleCode()), 0);
    }
}
