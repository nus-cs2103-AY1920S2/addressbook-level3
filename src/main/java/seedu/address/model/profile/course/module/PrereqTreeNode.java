package seedu.address.model.profile.course.module;

import seedu.address.storage.JsonPrereqTreeNode;

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

    public boolean hasFulfilledPrereqs(List<ModuleCode> modulesTaken) {
        if (moduleCode != null) {
            // Kept the first condition mainly as precaution
            return modulesTaken.contains(moduleCode)
                    || modulesTaken.stream().map(ModuleCode::removeSuffix).anyMatch(modCode->modCode.equals(moduleCode));
        }
        boolean check;
        if (type.equals("or")) {
            check = false;
            for (PrereqTreeNode node: prereqTreeNodes) {
                if (node.hasFulfilledPrereqs(modulesTaken)) { // As long as 1 module fulfils prereq, requirement is fulfilled
                    check = true;
                }
            }
        } else if (type.equals("and")) {
            check = true;
            for (PrereqTreeNode node: prereqTreeNodes) {
                if (!node.hasFulfilledPrereqs(modulesTaken)) { // As long as 1 module does not fulfil prereq, requirement is not fulfilled
                    check = false;
                }
            }
        } else {
            throw new IllegalArgumentException("Wrong type in PrereqTreeNode.java, fix code");
        }
        return check;
    }

    public JsonPrereqTreeNode toJson() {
        if (moduleCode != null) {
            return new JsonPrereqTreeNode(moduleCode.toString());
        }
        List<JsonPrereqTreeNode> listNodes = new ArrayList<>();
        for (PrereqTreeNode node: prereqTreeNodes) {
            listNodes.add(node.toJson());
        }
        if (type.equals("or")) {
            return new JsonPrereqTreeNode(listNodes, null);
        } else if (type.equals("and")) {
            return new JsonPrereqTreeNode(null, listNodes);
        }
        throw new IllegalArgumentException("Error parsing prereqTreeNode object in PrereqTreeNode.java, to fix");
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