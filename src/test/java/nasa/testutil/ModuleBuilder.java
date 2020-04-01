package nasa.testutil;

import nasa.model.activity.Activity;
import nasa.model.activity.UniqueActivityList;
import nasa.model.module.Module;
import nasa.model.module.ModuleCode;
import nasa.model.module.ModuleName;

/**
 * A utility class to help with building Module objects.
 */
public class ModuleBuilder {

    public static final String DEFAULT_MODULE_NAME = "SOFTWARE ENGINEERING";
    public static final String DEFAULT_MODULE_CODE = "CS2103T";
    public static final Activity TASK_1 = new DeadlineBuilder()
            .withName("Homework")
            .withDate("01-04-2021 01:00")
            .withDueDate("06-04-2021 01:00")
            .withNote("Do models for project")
            .build();
    public static final Activity TASK_2 = new DeadlineBuilder()
            .withName("Test")
            .withDate("22-07-2021 01:00")
            .withDueDate("02-08-2021 01:00")
            .withNote("Do tutorials")
            .build();
    public static final Activity TASK_3 = new DeadlineBuilder()
            .withName("Prepare group meeting")
            .withDate("04-04-2021 01:00")
            .withDueDate("22-04-2021 01:00")
            .withNote("Prepare for group meetings")
            .build();
    public static final Activity TASK_4 = new DeadlineBuilder()
            .withName("Exam")
            .withDate("01-05-2021 01:00")
            .withDueDate("13-05-2021 01:00")
            .withNote("Chapter 1")
            .build();
    public static final Activity TASK_5 = new EventBuilder()
            .withName("MPSH")
            .withFromDate("01-04-2021 01:00")
            .withToDate("04-04-2021 01:00")
            .withNote("Booked MPSH")
            .build();


    private ModuleName name;
    private ModuleCode code;
    private UniqueActivityList activityList;

    public ModuleBuilder() {
        name = new ModuleName(DEFAULT_MODULE_NAME);
        code = new ModuleCode(DEFAULT_MODULE_CODE);
        activityList = new UniqueActivityList();
        activityList.add(TASK_1);
        activityList.add(TASK_2);
        activityList.add(TASK_3);
        activityList.add(TASK_4);
        activityList.add(TASK_5);
    }

    public ModuleBuilder(Module moduleToCopy) {
        name = moduleToCopy.getModuleName();
        code = moduleToCopy.getModuleCode();
        UniqueActivityList newActivityList = new UniqueActivityList();
        newActivityList.setActivities(moduleToCopy.getDeepCopyList());
        activityList = newActivityList;
    }

    /**
     * Sets the {@code ModuleName} of the {@code Module} that we are building.
     */
    public ModuleBuilder withName(String name) {
        this.name = new ModuleName(name);
        return this;
    }

    /**
     * Sets the {@code ModuleCode} of the {@code Module} that we are building.
     */
    public ModuleBuilder withCode(String code) {
        this.code = new ModuleCode(code);
        return this;
    }

    /**
     * Build module with activity.
     * @param activity activity to be added
     * @return ModuleBuilder
     */
    public ModuleBuilder withAddActivity(Activity activity) {
        this.activityList.add(activity);
        return this;
    }

    /**
     * Remove every activity.
     * @return ModuleBuilder
     */
    public ModuleBuilder withRemoveAll() {
        this.activityList.removeAll();
        return this;
    }

    /**
     * Build module based on code and name.
     * @return Module
     */
    public Module build() {
        Module module = new Module(code, name);
        module.setActivities(activityList.getDeepCopyList());
        return module;
    }

}
