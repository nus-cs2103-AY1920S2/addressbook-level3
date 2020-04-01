package seedu.address.model.programmes;

import java.util.List;

import seedu.address.model.graduation.GraduationRequirement;
import seedu.address.model.module.ModuleCode;

public abstract class DegreeProgramme extends Programme {

    private DegreeType degreeType;

    public DegreeType getDegreeType() {
        return degreeType;
    }

    public void setDegreeType(DegreeType degreeType) {
        this.degreeType = degreeType;
    }

    public boolean isFulfilled(List<ModuleCode> moduleCodes) {
        for (GraduationRequirement requirement : this.graduationRequirementList) {
            if (!requirement.isFulfilled(moduleCodes)) {
                return false;
            }
        }
        return true;
    }
}
