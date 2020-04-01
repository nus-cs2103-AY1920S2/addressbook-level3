package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.util.SampleDataUtil.getSamplePlanner;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.grades.Grade;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.UniqueModuleList;
import seedu.address.model.student.Student;
import seedu.address.model.time.StudentSemester;
import seedu.address.testutil.TypicalModules;

public class PlannerTest {

    private final Planner planner = new Planner();
    private final Planner emptyPlanner = new Planner(false);

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), emptyPlanner.getStudentList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> planner.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyPlanner_replacesData() {
        Planner newData = getSamplePlanner();
        planner.resetData(newData);
        assertEquals(newData, planner);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> planner.hasModule(null));
    }

    @Test
    public void hasModule_moduleNotInPlanner_returnsFalse() {
        assertFalse(planner.hasModule(TypicalModules.NON_EXISTING_MODULE));
    }

    @Test
    public void hasModule_moduleInPlanner_returnsTrue() {
        planner.addModule(TypicalModules.CS2040);
        assertTrue(planner.hasModule(TypicalModules.CS2040));
    }

    @Test
    public void getModuleList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> planner.getModuleList().remove(0));
    }

    /**
     * A stub ReadOnlyPlanner whose persons list can violate interface constraints.
     */
    private static class PlannerStub implements ReadOnlyPlanner {
        private final ObservableList<Module> modules = FXCollections.observableArrayList();

        PlannerStub(Collection<Module> modules) {
            this.modules.setAll(modules);
        }

        @Override
        public ObservableList<Module> getModuleList() {
            return modules;
        }

        @Override
        public Student getActiveStudent() {
            return null;
        }

        @Override
        public ObservableList<Student> getStudentList() {
            return null;
        }

        @Override
        public UniqueModuleList getModules() {
            return null;
        }

        @Override
        public ObservableList<ModuleCode> getActiveModuleCodes() {
            return null;
        }

        @Override
        public boolean hasSemester(StudentSemester semester) {
            return false;
        }

        @Override
        public Optional<Grade> getModuleGrade(ModuleCode moduleCode) {
            return Optional.empty();
        }

        @Override
        public ObservableList<ModuleCode> getEnrolledModulesList() {
            return null;
        }

        @Override
        public StudentSemester getActiveSemester() {
            return null;
        }

        @Override
        public int getActiveStudentIndex() {
            return -1;
        }
    }

}
