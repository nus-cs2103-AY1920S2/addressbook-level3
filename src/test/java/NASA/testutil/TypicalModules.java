package NASA.testutil;

import NASA.model.module.Module;

/**
 * Contains examples of modules use for testing
 */
public class TypicalModules {

    //Default module
    public static final Module CS2103T =  new ModuleBuilder().build();
    public static final Module CS2106 =  new ModuleBuilder().withCode("CS2106")
            .withName("Operating System")
            .withRemoveAll()
            .withAddActivity(new ActivityBuilder().withName("deadline").withNote("Lab1 IPC").build())
            .withAddActivity(new ActivityBuilder().withName("deadline").withNote("Lab2 Processes").build())
            .withAddActivity(new ActivityBuilder().withName("deadline").withNote("Lab3 Synchronization").build())
            .build();
    public static final Module GEH1001 = new ModuleBuilder().withCode("GEH1001")
            .withName("Globalization and new media")
            .withRemoveAll()
            .withAddActivity(new ActivityBuilder().withName("deadline").withNote("Midterm Essay").build())
            .withAddActivity(new ActivityBuilder().withName("deadline").withNote("Final Essay").build())
            .withAddActivity(new ActivityBuilder().withName("deadline").withNote("Quiz1").build())
            .withAddActivity(new ActivityBuilder().withName("deadline").withNote("Quiz2").build())
            .withAddActivity(new ActivityBuilder().withName("deadline").withNote("Quiz3").build())
            .build();

    private TypicalModules() {} // prevents instantiation
}
