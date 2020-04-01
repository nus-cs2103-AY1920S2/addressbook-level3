package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Planner;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;

/**
 * A utility class containing a list of {@code Module} objects to be used in tests.
 */
public class TypicalModules {

    public static final Module CS2040 = new ModuleBuilder().withModuleCode(new ModuleCode("CS2040")).build();
    public static final Module CS2103T = new ModuleBuilder().withModuleCode(new ModuleCode("CS2103T")).build();

    public static final Module NON_EXISTING_MODULE = new ModuleBuilder()
            .withModuleCode(new ModuleCode("XXXXXXX"))
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalModules() {
    } // prevents instantiation

    /**
     * Returns an {@code Planner} with all the typical persons.
     */
    public static Planner getTypicalPlanner() {
        Planner planner = new Planner();
        for (Module module : getTypicalModules()) {
            planner.addModule(module);
        }
        return planner;
    }

    public static List<Module> getTypicalModules() {
        return new ArrayList<>(Arrays.asList(CS2040, CS2103T));
    }
}
