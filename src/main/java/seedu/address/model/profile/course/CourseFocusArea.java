package seedu.address.model.profile.course;

import java.util.List;

import seedu.address.model.profile.course.module.ModuleCode;

/**
 * Represents a Course's focus area.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class CourseFocusArea {

    private final String focusAreaName;
    private final List<ModuleCode> primaries;
    private final List<ModuleCode> electives;

    /**
     * Every field must be present and not null.
     */
    public CourseFocusArea(String focusAreaName, List<ModuleCode> primaries, List<ModuleCode> electives) {
        this.focusAreaName = focusAreaName;
        this.primaries = primaries;
        this.electives = electives;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append(focusAreaName + ": ");

        if (!primaries.isEmpty()) {
            output.append("\nArea Primaries");
            for (ModuleCode moduleCode : primaries) {
                output.append("\n");
                output.append(moduleCode);
            }
        }

        if (!electives.isEmpty()) {
            output.append("\nArea Electives");
            for (ModuleCode moduleCode : electives) {
                output.append("\n");
                output.append(moduleCode);
            }
        }

        output.append("\n");
        return output.toString();
    }
    // Implement getter setter
    // equals
}
