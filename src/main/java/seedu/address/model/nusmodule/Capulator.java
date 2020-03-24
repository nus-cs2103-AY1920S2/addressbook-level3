package seedu.address.model.nusmodule;

import java.util.List;

/**
 * Represents a device that can help NUS students to calculate their current CAP based on their graded modules.
 */
public class Capulator {
    private List<NusModule> modules;

    public Capulator(List<NusModule> modules) {
        this.modules = modules;
    }

    /**
     * calculates the CAP based on graded NUS modules in records.
     * @return a double represents the CAP gotten.
     */
    public double calculateCap() {

        double sumOfGradePoints = 0;
        double sumOfModularCredits = 0;

        for (NusModule module: modules) {
            if (module.getGrade().isPresent() && !module.getGrade().get().isSued()) {
                sumOfModularCredits += module.modularCredit;
                sumOfGradePoints += module.modularCredit * module.getGradePoint();
            }
        }

        if (sumOfModularCredits == 0.0) {
            return 0;
        }

        return sumOfGradePoints / sumOfModularCredits;
    }
}
