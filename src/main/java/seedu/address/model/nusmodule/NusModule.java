package seedu.address.model.nusmodule;

import java.util.Optional;

/**
 * Represents a module in NUS.
 */
public class NusModule {
    public final ModuleCode moduleCode;
    public final int modularCredit;
    private boolean isTaking;
    private Optional<Grade> grade;

    public NusModule(ModuleCode moduleCode, int modularCredit, boolean isTaking, Optional<Grade> grade) {
        this.moduleCode = moduleCode;
        this.modularCredit = modularCredit;
        this.isTaking = isTaking;
        this.grade = grade;
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
