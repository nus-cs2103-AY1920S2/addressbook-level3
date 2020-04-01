package seedu.address.model.graduation;

import java.util.List;
import java.util.regex.Pattern;

import seedu.address.model.module.ModuleCode;

public class WildcardGraduationRequirement extends GraduationRequirement {

    protected String name;
    protected int minMCs;
    protected String regex;
    protected String label;


    public WildcardGraduationRequirement(String name, int minMCs, String regex) {
        this.name = name;
        this.minMCs = minMCs;
        this.regex = regex;
        this.label = "";
    }

    public WildcardGraduationRequirement(String name, int minMCs, String regex, String label) {
        this.name = name;
        this.minMCs = minMCs;
        this.regex = regex;
        this.label = label;
    }

    public boolean isFulfilled(List<ModuleCode> moduleCodes) {
        for (ModuleCode moduleCode : moduleCodes) {
            if (Pattern.matches(regex, moduleCode.value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "[X] " + name + "\n";
    }

    public String getString(List<ModuleCode> moduleCodes) {
        if (!label.equals("")) {
            return "[" + getStatusIcon(isFulfilled(moduleCodes)) + "] [" + label + "] " + name + "\n";
        }
        return "[" + getStatusIcon(isFulfilled(moduleCodes)) + "] " + name + "\n";
    }
}
