package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2103T;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.module.Module;

/**
 * A utility class containing a list of {@code Module} objects to be used in tests.
 */
public class TypicalModules {
    public static final Module CS2103T = new ModuleBuilder().withModuleCode(VALID_MODULE_CODE_CS2103T)
            .withDescription(VALID_DESCRIPTION_CS2103T).build();
    public static final Module CS2101 = new ModuleBuilder().withModuleCode(VALID_MODULE_CODE_CS2101)
            .withDescription(VALID_DESCRIPTION_CS2101).build();
    public static final Module ES2660 = new ModuleBuilder().withModuleCode("ES2660")
            .withDescription("Communicating in the Information Age").build();
    public static final Module ST2334 = new ModuleBuilder().withModuleCode("ST2334")
            .withDescription("Probability and Statistics").build();
    public static final Module MA1521 = new ModuleBuilder().withModuleCode("MA1521")
            .withDescription("Calculus for Computing").build();
    public static final Module GEQ1000 = new ModuleBuilder().withModuleCode("GEQ1000")
            .withDescription("Asking Questions").build();
    public static final Module CS4223 = new ModuleBuilder().withModuleCode("CS4223")
            .withDescription("Multi-core Architectures").build();

    private TypicalModules() {} // prevents instantiation

    public static List<Module> getTypicalModules() {
        return new ArrayList<>(Arrays.asList(CS2103T, CS2101, ES2660, ST2334, MA1521, GEQ1000, CS4223));
    }
}
