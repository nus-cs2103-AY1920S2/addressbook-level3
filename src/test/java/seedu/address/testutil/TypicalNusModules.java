package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import seedu.address.model.ModuleBook;
import seedu.address.model.nusmodule.Grade;
import seedu.address.model.nusmodule.ModuleCode;
import seedu.address.model.nusmodule.ModuleTask;
import seedu.address.model.nusmodule.NusModule;
import seedu.address.model.nusmodule.Priority;

/**
 * A utility class containing a list of {@code NusModule} objects to be used in tests.
 */
public class TypicalNusModules {
    public static final List<ModuleTask> EMPTY_TASKS = new ArrayList<>();

    public static final NusModule CS2103 = new NusModule(new ModuleCode("CS2103"),
            4, Optional.empty(), EMPTY_TASKS);

    public static final NusModule CS2030 = new NusModule(new ModuleCode("CS2030"),
            4, Optional.empty(), EMPTY_TASKS);

    public static final NusModule ST3131 = new NusModule(new ModuleCode("ST3131"),
            4, Optional.of(Grade.getGrade("S")), EMPTY_TASKS);

    public static final ModuleTask TYPICAL_MODULE_TASK_1 = new ModuleTask("assignment", CS2030.getModuleCode(),
            "02-02-2020", Priority.HIGH);

    public static final ModuleTask TYPICAL_MODULE_TASK_2 = new ModuleTask("assignment", new ModuleCode("GET1036"),
            "01-02-2020", Priority.HIGH);

    public static final ModuleTask TYPICAL_MODULE_TASK_3 = new ModuleTask("assignment", CS2030.getModuleCode(),
            "01-02-2020", Priority.LOW);

    public static final ModuleTask TYPICAL_MODULE_TASK_4 = new ModuleTask("assignment", ST3131.getModuleCode(),
            "02-02-2020", Priority.HIGH);

    public static final ModuleTask TYPICAL_MODULE_TASK_5 = new ModuleTask("assignment", ST3131.getModuleCode(),
            "02-02-2020", Priority.VERYHIGH);

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static ModuleBook getTypicalModuleBook() {
        ModuleBook mb = new ModuleBook();
        for (NusModule module : getTypicalNusModules()) {
            mb.addModule(module);
        }
        return mb;
    }

    public static List<NusModule> getTypicalNusModules() {
        CS2030.addTask(TYPICAL_MODULE_TASK_1);
        CS2030.addTask(TYPICAL_MODULE_TASK_3);
        ST3131.addTask(TYPICAL_MODULE_TASK_4);
        ST3131.addTask(TYPICAL_MODULE_TASK_5);
        return new ArrayList<>(Arrays.asList(CS2103, CS2030, ST3131));
    }
}
