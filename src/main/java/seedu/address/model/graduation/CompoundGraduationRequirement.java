package seedu.address.model.graduation;

import static seedu.address.model.graduation.AggregationType.ALL;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.util.ModuleUtil;

public class CompoundGraduationRequirement extends GraduationRequirement {

    protected AggregationType aggregationType;
    protected String name;
    protected int minMCs;
    protected List<GraduationRequirement> graduationRequirementList = new ArrayList<>();

    public CompoundGraduationRequirement(String name, int minMCs, List<GraduationRequirement> requirements) {
        this.name = name;
        this.minMCs = minMCs;
        this.graduationRequirementList = requirements;
        aggregationType = ALL;
    }

    public CompoundGraduationRequirement(String name, int minMCs, List<GraduationRequirement> requirements,
                                         AggregationType aggregationType) {
        this.name = name;
        this.minMCs = minMCs;
        this.graduationRequirementList = requirements;
        this.aggregationType = aggregationType;
    }

    public List<GraduationRequirement> getGraduationRequirementList() {
        return graduationRequirementList;
    }

    public boolean isFulfilled(List<ModuleCode> moduleCodes) {
        if (graduationRequirementList == null) {
            return false;
        }
        switch (aggregationType) {
        case ANY:
            for (GraduationRequirement requirement : graduationRequirementList) {
                if (requirement.isFulfilled(moduleCodes)) {
                    return true;
                }
            }
            return false;
        case ALL:
            for (GraduationRequirement requirement : graduationRequirementList) {
                if (!requirement.isFulfilled(moduleCodes)) {
                    return false;
                }
            }
            return true;
        case AT_LEAST_MC:
            int currentMc = 0;
            for (GraduationRequirement requirement : graduationRequirementList) {
                if (requirement.isFulfilled(moduleCodes)) {
                    Module module = ModuleUtil.getModuleWithCode(requirement.getModuleCode());
                    if (module != null) {
                        currentMc += module.getModuleCredit();
                    }
                }
            }
            return currentMc >= minMCs;
        default:
            return false;
        }
    }

    public String getString(List<ModuleCode> moduleCodes) {
        StringBuilder sb = new StringBuilder();
        String buffer;
        sb.append("[")
            .append(getStatusIcon(isFulfilled(moduleCodes)))
            .append("] [")
            .append(aggregationType.getAggregationType(minMCs))
            .append("] ")
            .append(name)
            .append("\n");
        if (graduationRequirementList != null) {
            Iterator<GraduationRequirement> iterator = graduationRequirementList.iterator();
            while (iterator.hasNext()) {
                buffer = iterator.next().getString(moduleCodes);
                for (String line : buffer.split("\n")) {
                    sb.append("    ").append(line).append("\n");
                }
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String buffer;
        sb.append("[X] " + name + "\n");
        if (graduationRequirementList != null) {
            Iterator<GraduationRequirement> iterator = graduationRequirementList.iterator();
            while (iterator.hasNext()) {
                buffer = iterator.next().toString();
                for (String line : buffer.split("\n")) {
                    sb.append("    " + line + "\n");
                }
            }
        }
        return sb.toString();
    }
}
