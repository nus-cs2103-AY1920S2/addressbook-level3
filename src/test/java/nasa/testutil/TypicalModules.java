package nasa.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nasa.model.NasaBook;
import nasa.model.module.Module;

/**
 * A utility class containing a list of {@code Module} objects to be used in tests.
 */
public class TypicalModules {

    //Default module
    public static final Module CS2103T = new ModuleBuilder().build();
    public static final Module CS2106 = new ModuleBuilder().withCode("CS2106")
            .withName("Operating System")
            .withRemoveAll()
            .withAddActivity(new DeadlineBuilder().withName("Lab Assignment 1").withNote("Lab1 IPC").build())
            .withAddActivity(new DeadlineBuilder().withName("Lab Assignment 2").withNote("Lab2 Processes").build())
            .withAddActivity(new DeadlineBuilder().withName("Lab Assignment 3")
                .withNote("Lab3 Synchronization").build())
            .build();
    public static final Module GEH1001 = new ModuleBuilder().withCode("GEH1001")
            .withName("Globalization and new media")
            .withRemoveAll()
            .withAddActivity(new DeadlineBuilder().withName("Midterm").withNote("Midterm Essay").build())
            .withAddActivity(new DeadlineBuilder().withName("Finals").withNote("Final Essay").build())
            .withAddActivity(new DeadlineBuilder().withName("Quiz 1").withNote("Quiz1").build())
            .withAddActivity(new DeadlineBuilder().withName("Quiz 2").withNote("Quiz2").build())
            .withAddActivity(new DeadlineBuilder().withName("Quiz 3").withNote("Quiz3").build())
            .build();

    private TypicalModules() {} // prevents instantiation

    /**
     * Returns an {@code NasaBook} with all the typical modules.
     */
    public static NasaBook getTypicalNasaBook() {
        NasaBook nb = new NasaBook();
        for (Module module : getTypicalModules()) {
            nb.addModule(module.getDeepCopyModule());
        }
        return nb;
    }

    public static List<Module> getTypicalModules() {
        return new ArrayList<>(Arrays.asList(CS2103T, CS2106));
    }
}
