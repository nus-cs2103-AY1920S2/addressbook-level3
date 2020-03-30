package seedu.address.model.profile.course.module;

import java.util.ArrayList;
import java.util.List;

public class PrereqTreeNode {
    ModuleCode moduleCode;
    String type;
    List<PrereqTreeNode> prereqTreeNodes;

    public PrereqTreeNode(ModuleCode moduleCode) {
        this.moduleCode = moduleCode;
    }
    public PrereqTreeNode() {
        this.prereqTreeNodes = new ArrayList<>();
    }

    public void addPrereqTreeNode(PrereqTreeNode prereqTreeNode) {
        this.prereqTreeNodes.add(prereqTreeNode);
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() { // For debugging purposes
        if (moduleCode != null) {
            return moduleCode.toString();
        }
        String toReturn = "{";
        toReturn += type + ": [";
        for (PrereqTreeNode node: prereqTreeNodes) {
            toReturn += node.toString() + ", ";
        }
        toReturn += "]}";
        return toReturn;
    }
}