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
            .addDeadline(new DeadlineBuilder().withName("Lab Assignment 1").withNote("Lab1 IPC").build())
            .addDeadline(new DeadlineBuilder().withName("Lab Assignment 2").withNote("Lab2 Processes").build())
            .addDeadline(new DeadlineBuilder().withName("Lab Assignment 3")
                .withNote("Lab3 Synchronization").build())
            .addEvent(new EventBuilder().build())
            .addEvent(new EventBuilder().withName("Workshop").build())
            .build();
    public static final Module GEH1001 = new ModuleBuilder().withCode("GEH1001")
            .withName("Globalization and new media")
            .withRemoveAll()
            .addDeadline(new DeadlineBuilder().withName("Midterm").withNote("Midterm Essay").build())
            .addDeadline(new DeadlineBuilder().withName("Finals").withNote("Final Essay").build())
            .addDeadline(new DeadlineBuilder().withName("Quiz 1").withNote("Quiz1").build())
            .addDeadline(new DeadlineBuilder().withName("Quiz 2").withNote("Quiz2").build())
            .addDeadline(new DeadlineBuilder().withName("Quiz 3").withNote("Quiz3").build())
            .addEvent(new EventBuilder().build())
            .addEvent(new EventBuilder().withName("Workshop").build())
            .build();
    public static final Module CS2107 = new ModuleBuilder().withCode("CS2107")
            .withName("Computer Security")
            .withRemoveAll()
            .build();
    public static final Module CS2102 = new ModuleBuilder().withCode("CS2102")
            .withName("Database")
            .withRemoveAll()
            .build();
    public static final Module CS2105 = new ModuleBuilder().withCode("CS2105")
            .withName("Computer Networks")
            .withRemoveAll()
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
        return new ArrayList<>(Arrays.asList(CS2103T, GEH1001));
    }
}
