package seedu.address.model.util;

import java.util.Optional;

import seedu.address.model.Planner;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.student.Enrollment;
import seedu.address.model.student.Major;
import seedu.address.model.student.Student;
import seedu.address.model.student.TimeTable;
import seedu.address.model.student.TimeTableMap;

/**
 * Contains utility methods for populating {@code Planner} with sample data.
 */
public class ModuleUtil {

    public static Module getModuleWithCode(ModuleCode moduleCode) {
        for (Module module : getSamplePlanner().getModuleList()) {
            if (module.getModuleCode().equals(moduleCode)) {
                return module;
            }
        }
        return null;
    }
    public static Module[] getSampleModules() {
        return new Module[] {
            new Module(new ModuleCode("CS2040")),
            new Module(new ModuleCode("CS2103T"))
        };
    }

    public static Planner getSamplePlanner() {
        Planner samplePlanner = new Planner();
        Student student = ModuleUtil.getSampleStudent();
        samplePlanner.addStudent(student);
        samplePlanner.activateStudent(student); // TODO: allow serialization of planner with no active student
        return samplePlanner;
    }

    public static Student getSampleStudent() {
        Student sampleStudent = new Student(new seedu.address.model.student.Name("Mark"), new Major("CS"),
            ModuleUtil.getSampleTimeTableMap());
        return sampleStudent;
    }

    public static TimeTableMap getSampleTimeTableMap() {
        TimeTableMap timeTableMap = new TimeTableMap();
        return timeTableMap;
    }

    /**
     * Returns a non-empty (@code TimeTableMap) which (@code Student) can immediately use.
     *
     * @return Non-empty (@code TimeTableMap)
     */
    public static TimeTable getSampleTimeTable() {
        TimeTable timeTable = new TimeTable();
        timeTable.addEnrollment(new Enrollment(new ModuleCode("CS2040"), Optional.empty(), 4));
        return timeTable;
    }
}
