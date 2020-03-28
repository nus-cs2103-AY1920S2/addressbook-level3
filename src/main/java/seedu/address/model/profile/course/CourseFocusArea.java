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
        output.append(focusAreaName);
        output.append("\n");

        if (!primaries.isEmpty()) {
            output.append("\n");
            output.append("Area Primaries:");
            for (ModuleCode moduleCode : primaries) {
                output.append("\n");
                output.append(moduleCode);
            }
        }

        output.append("\n");
        if (!electives.isEmpty()) {
            output.append("\n");
            output.append("Area Electives:");
            for (ModuleCode moduleCode : electives) {
                output.append("\n");
                output.append(moduleCode);
            }
        }

        output.append("\n");
        return output.toString();
    }

    public String getFocusAreaName() {
        return focusAreaName;
    }
    // Implement getter setter
    // equals

    public String getPrimaries() {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (ModuleCode mc : primaries) {
            sb.append(i + ". ");
            sb.append(mc.toString() + "\n");
            i++;
        }
        return sb.toString();
    }

    public String getElectives() {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (ModuleCode mc : electives) {
            sb.append(i + ". ");
            sb.append(mc.toString() + "\n");
            i++;
        }
        return sb.toString();
    }

    public List<ModuleCode> getPrim() {
        return this.primaries;
    }

    public List<ModuleCode> getElec() {
        return this.electives;
    }
}
