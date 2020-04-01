package seedu.address.model.user;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import seedu.address.model.module.ModuleCode;
import seedu.address.model.student.Student;
import seedu.address.model.student.TimeTable;
import seedu.address.model.time.StudentSemester;

public class User {
    private Student student;
    private Map<StudentSemester, TimeTable> timeTables;
    private Set<ModuleCode> exemptedModules;

    public User(Student student) {
        this.student = student;
        timeTables = new HashMap<>();
        exemptedModules = new HashSet<>();
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Map<StudentSemester, TimeTable> getTimeTables() {
        return timeTables;
    }

    public void setTimeTables(Map<StudentSemester, TimeTable> timeTables) {
        this.timeTables = timeTables;
    }

    public Set<ModuleCode> getExemptedModules() {
        return exemptedModules;
    }

    public void setExemptedModules(Set<ModuleCode> exemptedModules) {
        this.exemptedModules = exemptedModules;
    }
}
