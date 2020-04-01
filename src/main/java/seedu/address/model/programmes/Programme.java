package seedu.address.model.programmes;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import seedu.address.model.graduation.CompoundGraduationRequirement;
import seedu.address.model.graduation.GraduationRequirement;
import seedu.address.model.module.ModuleCode;


public abstract class Programme {
    protected String name;
    protected List<GraduationRequirement> graduationRequirementList;

    public String getName() {
        return name;
    }

    public List<GraduationRequirement> getGraduationRequirementList() {
        return graduationRequirementList;
    }

    public List<GraduationRequirement> getTerminalGraduationRequirementList() {
        List<GraduationRequirement> terminalGraduationRequirementList = new ArrayList<>();
        PriorityQueue<GraduationRequirement> buffer = new PriorityQueue<>(graduationRequirementList);
        while (!buffer.isEmpty()) {
            GraduationRequirement graduationRequirement = buffer.poll();
            if (graduationRequirement instanceof CompoundGraduationRequirement) {
                buffer.add(graduationRequirement);
            }
        }
        return terminalGraduationRequirementList;
    }

    public abstract boolean isFulfilled(List<ModuleCode> moduleCodes);
}
