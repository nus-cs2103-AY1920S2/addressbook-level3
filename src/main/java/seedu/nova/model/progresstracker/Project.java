package seedu.nova.model.progresstracker;

/**
 * Represents a project
 */
public abstract class Project {
    public static final String MESSAGE_CONSTRAINTS = "Project name must be ip or tp only and should not be blank";

    public abstract PtWeekList getWeekList();

    public abstract String getProjectName();

    /**
     * Checks if is Ip or Tp
     * @param projectName name of project
     * @return true if is Ip or Tp but false if it's other projects
     */
    public static boolean isValidProject(String projectName) {
        projectName = projectName.toLowerCase();

        if (projectName.equals("ip") || projectName.equals("tp")) {
            return true;
        } else {
            return false;
        }
    }

}
