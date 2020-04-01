package seedu.address.model.programmes.specialisations.cs;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.graduation.SingleGraduationRequirement;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.programmes.specialisations.GenericSpecialisation;

public abstract class GenericCsSpecialisation extends GenericSpecialisation {

    protected List<ModuleCode> primaries;
    protected List<ModuleCode> electives;

    public List<ModuleCode> getPrimaries() {
        return primaries;
    }

    public void setPrimaries(List<ModuleCode> primaries) {
        this.primaries = primaries;
    }

    public List<ModuleCode> getElectives() {
        return electives;
    }

    public void setElectives(List<ModuleCode> electives) {
        this.electives = electives;
    }

    public boolean arePrimariesFulfilled(List<ModuleCode> moduleCodes) {
        int minModules = 3;
        int minimum4kModules = 1;
        int modules = 0;
        int current4kModules = 0;
        ArrayList<SingleGraduationRequirement> primaryRequirements = new ArrayList<>();
        for (ModuleCode primaries : getPrimaries()) {
            primaryRequirements.add(new SingleGraduationRequirement(primaries));
        }
        for (SingleGraduationRequirement primaryRequirement : primaryRequirements) {
            if (primaryRequirement.isFulfilled(moduleCodes)) {
                modules++;
                ArrayList<Character> valid4kCharacters = new ArrayList<>();
                valid4kCharacters.add('4');
                valid4kCharacters.add('5');
                valid4kCharacters.add('6');
                valid4kCharacters.add('7');
                if (valid4kCharacters.contains(primaryRequirement.getModuleCode().toString().charAt(2))) {
                    current4kModules++;
                }
            }
        }
        return modules >= minModules && current4kModules >= minimum4kModules;
    }

    public boolean areElectivesFulfilled(List<ModuleCode> moduleCodes) {
        int minModules = 0;
        int modules = 0;
        ArrayList<SingleGraduationRequirement> electiveRequirements = new ArrayList<>();
        for (ModuleCode electives : getElectives()) {
            electiveRequirements.add(new SingleGraduationRequirement(electives));
        }
        for (SingleGraduationRequirement electiveRequirement : electiveRequirements) {
            if (electiveRequirement.isFulfilled(moduleCodes)) {
                modules++;
            }
        }
        return modules >= minModules;
    }

    public boolean isFulfilled(List<ModuleCode> moduleCodes) {
        return arePrimariesFulfilled(moduleCodes) && areElectivesFulfilled(moduleCodes);
    }
}
