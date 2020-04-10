package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.ModuleBook;
import seedu.address.model.nusmodule.ModuleCode;
import seedu.address.model.nusmodule.ModuleTask;
import seedu.address.model.nusmodule.NusModule;

/**
 * A utility class containing a list of {@code NusModule} objects to be used in tests.
 */
public class TypicalNusModules {
    public static final List<ModuleTask> EMPTY_TASKS = new ArrayList<>();

    public static final NusModule CS2103 = new NusModule(new ModuleCode("CS2103"),
            4, null, EMPTY_TASKS);

    public static final NusModule CS2030 = new NusModule(new ModuleCode("CS2030"),
            4, null, EMPTY_TASKS);

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
        return new ArrayList<>(Arrays.asList(CS2103, CS2030));
    }
}
