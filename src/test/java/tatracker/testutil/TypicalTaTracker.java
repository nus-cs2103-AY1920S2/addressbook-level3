package tatracker.testutil;

import static tatracker.testutil.group.TypicalGroups.getTypicalGroups;
import static tatracker.testutil.module.TypicalModules.getTypicalModules;
import static tatracker.testutil.student.TypicalStudents.getTypicalStudents;

import tatracker.model.TaTracker;
import tatracker.model.group.Group;
import tatracker.model.group.GroupType;
import tatracker.model.module.Module;
import tatracker.model.student.Student;

/**
 * Creates typical TA-Trackers.
 */
public class TypicalTaTracker {
    public static TaTracker getTypicalTaTrackerWithStudents() {
        Module typicalCS3243 = new Module("CS3243", "Introduction to AI");
        Group typicalG06 = new Group("G06", GroupType.LAB);

        typicalCS3243.addGroup(typicalG06);

        for (Student student : getTypicalStudents()) {
            typicalG06.addStudent(student);
        }

        TaTracker tat = new TaTracker();
        tat.addModule(typicalCS3243);
        return tat;
    }

    /**
     * Returns an {@code TaTracker} with all the typical modules.
     */
    public static TaTracker getTypicalTaTrackerWithModules() {

        TaTracker tat = new TaTracker();

        for (Module module : getTypicalModules()) {
            tat.addModule(module);
        }

        return tat;
    }

    /**
     * Returns an {@code TaTracker} with all the typical groups.
     */
    public static TaTracker getTypicalTaTrackerWithGroups() {
        Module typicalCS3243 = new Module("CS3243", "Introduction to AI");

        for (Group group : getTypicalGroups()) {
            typicalCS3243.addGroup(group);
        }
        TaTracker tat = new TaTracker();
        tat.addModule(typicalCS3243);
        return tat;
    }

}
