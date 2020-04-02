package tatracker.testutil.module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tatracker.model.TaTracker;
import tatracker.model.module.Module;
import tatracker.testutil.group.TypicalGroups;

/**
 * A utility class containing a list of {@code Module} objects to be used in tests.
 */
public class TypicalModules {

    public static final Module CS2103T = new ModuleBuilder().withIdentifier("CS2103T")
            .withName("Software Engineering")
            .withGroup(TypicalGroups.MANY_STUDENTS)
            .withGroup(TypicalGroups.NO_STUDENTS)
            .withGroup(TypicalGroups.ONE_STUDENT)
            .build();

    public static final Module CS3243 = new ModuleBuilder().withIdentifier("CS3243")
            .withName("Introduction to Artificial Intelligence")
            .withGroup(TypicalGroups.MANY_STUDENTS)
            .withGroup(TypicalGroups.NO_STUDENTS)
            .withGroup(TypicalGroups.ONE_STUDENT)
            .build();

    public static final Module NO_GROUPS = new ModuleBuilder().withIdentifier("CS2100")
            .withName("Computer Organisation")
            .build();

    public static final Module ONE_GROUP_NO_STUDENTS = new ModuleBuilder().withIdentifier("CS2105")
            .withName("Introduction to Networks")
            .withGroup(TypicalGroups.NO_STUDENTS)
            .build();

    public static final Module ONE_GROUP_WITH_STUDENTS = new ModuleBuilder().withIdentifier("CS2106")
            .withName("Introduction to Operating Systems")
            .withGroup(TypicalGroups.MANY_STUDENTS)
            .build();

    private TypicalModules() {} // prevents instantiation

    /**
     * Returns an {@code TaTracker} with all the typical students.
     */
    public static TaTracker getTypicalTaTracker() {

        TaTracker tat = new TaTracker();

        for (Module module : getTypicalModules()) {
            tat.addModule(module);
        }

        return tat;
    }

    public static List<Module> getTypicalModules() {
        return new ArrayList<>(Arrays.asList(CS2103T, CS3243, ONE_GROUP_NO_STUDENTS, ONE_GROUP_WITH_STUDENTS));
    }
}
