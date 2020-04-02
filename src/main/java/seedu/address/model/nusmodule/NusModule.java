package seedu.address.model.nusmodule;

import java.util.List;
import java.util.Optional;


/**
 * Represents a module in NUS.
 */
public class NusModule {
    public final ModuleCode moduleCode;
    public final int modularCredit;
    private boolean isTaking;
    private Optional<Grade> grade;
    private List<ModuleTask> tasks;

    public NusModule(ModuleCode moduleCode, int modularCredit, boolean isTaking,
                     Optional<Grade> grade, List<ModuleTask> tasks) {
        this.moduleCode = moduleCode;
        this.modularCredit = modularCredit;
        this.isTaking = isTaking;
        this.grade = grade;
        this.tasks = tasks;
    }

    public void addTask(ModuleTask task) {
        tasks.add(task);
    }

    public double getGradePoint() {
        return this.grade.get().getPoint();
    }

    public Optional<Grade> getGrade() {
        return this.grade;
    }

    public void setGrade(Grade grade) {
        this.grade = Optional.ofNullable(grade);
    }

    public ModuleCode getModuleCode() {
        return this.moduleCode;
    }

    @Override
    public String toString() {
        return moduleCode.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NusModule // instanceof handles nulls
                && moduleCode.equals(((NusModule) other).moduleCode)); // state check
    }
}
