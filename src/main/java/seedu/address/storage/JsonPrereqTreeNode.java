package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.profile.course.module.ModuleCode;
import seedu.address.model.profile.course.module.PrereqTreeNode;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonPrereqTreeNode {
    private String moduleCode;
    protected List<JsonPrereqTreeNode> or;
    protected List<JsonPrereqTreeNode> and;

    @JsonCreator
    public JsonPrereqTreeNode(String moduleCode) {
        this.moduleCode = moduleCode;
    }
    @JsonCreator
    public JsonPrereqTreeNode(@JsonProperty("or") List<JsonPrereqTreeNode> or, @JsonProperty("and") List<JsonPrereqTreeNode> and) {
        if (or != null) {
            this.or = or;
        } else if (and != null) {
            this.and = and;
        }
    }

    public boolean isModule() {
        return this.moduleCode != null;
    }
    public boolean isOrNode() {
        return this.or != null;
    }
    public boolean isAndNode() {
        return this.and != null;
    }

    public PrereqTreeNode toModelType() throws IllegalValueException {
        PrereqTreeNode toReturn;
        if (isModule()) {
            toReturn = new PrereqTreeNode(new ModuleCode(moduleCode));
        } else if (isOrNode()) {
            toReturn = new PrereqTreeNode();
            toReturn.setType("or");
            for (JsonPrereqTreeNode node: or) {
                toReturn.addPrereqTreeNode(node.toModelType());
            }
        } else if (isAndNode()) {
            toReturn = new PrereqTreeNode();
            toReturn.setType("and");
            for (JsonPrereqTreeNode node: and) {
                toReturn.addPrereqTreeNode(node.toModelType());
            }
        } else {
            throw new IllegalValueException("Error parsing prerequisite tree");
        }

        return toReturn;
    }
}
