package seedu.address.model.nusmodule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TypicalNusModules;

class ModuleTaskTest {

    private static final ModuleTask task_cs2030_0202 = TypicalNusModules.TYPICAL_MODULE_TASK_1;
    private static final ModuleTask task_cs2030_0102 = TypicalNusModules.TYPICAL_MODULE_TASK_3;
    private static final ModuleTask task_st3131_high = TypicalNusModules.TYPICAL_MODULE_TASK_4;
    private static final ModuleTask task_st3131_very_high = TypicalNusModules.TYPICAL_MODULE_TASK_5;


    @Test
    void getModuleRelated() {
        assertEquals(task_cs2030_0102.getModuleRelated(), new ModuleCode("CS2030"));
        assertEquals(task_cs2030_0202.getModuleRelated(), new ModuleCode("CS2030"));
        assertEquals(task_st3131_high.getModuleRelated(), new ModuleCode("ST3131"));
        assertEquals(task_st3131_very_high.getModuleRelated(), new ModuleCode("ST3131"));
    }

    @Test
    void getDate() {
        assertEquals(task_cs2030_0102.getDate(), "01-02-2020");
        assertEquals(task_cs2030_0202.getDate(), "02-02-2020");
        assertEquals(task_st3131_high.getDate(), "02-02-2020");
        assertEquals(task_st3131_very_high.getDate(), "02-02-2020");
    }

    @Test
    void getPriority() {
        assertEquals(task_cs2030_0102.getPriority(), Priority.LOW);
        assertEquals(task_cs2030_0202.getPriority(), Priority.HIGH);
        assertEquals(task_st3131_high.getPriority(), Priority.HIGH);
        assertEquals(task_st3131_very_high.getPriority(), Priority.VERYHIGH);
    }

    @Test
    void testEquals() {
        ModuleTask moduleTask = new ModuleTask("assignment", new ModuleCode("CS2030"),
                "01-02-2020", Priority.LOW);

        // same values -> returns true
        assertTrue(task_cs2030_0102.equals(moduleTask));

        // same object -> returns true
        assertTrue(task_st3131_very_high.equals(task_st3131_very_high));

        // null -> returns false
        assertFalse(task_st3131_very_high.equals(null));

        // different types -> returns false
        assertFalse(task_st3131_very_high.equals("dsada"));

        // different priority value -> returns false
        assertFalse(task_st3131_very_high.equals(task_st3131_high));

        // different timing value -> returns false
        assertFalse(task_cs2030_0202.equals(task_cs2030_0102));

        // different module related value -> returns false
        assertFalse(task_cs2030_0202.equals(task_st3131_high));
    }
}
