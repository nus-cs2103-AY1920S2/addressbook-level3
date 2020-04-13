package seedu.nova.model.progresstracker;

/**
 * Represents individual project
 */
public class Ip extends Project {
    private PtWeekList weekList;
    private String projectName;

    /**
     * Creates Ip object
     */
    public Ip() {
        weekList = new PtWeekList();
        projectName = "ip";
    }

    public String getProjectName() {
        return projectName;
    }

    public PtWeekList getWeekList() {
        return weekList;
    }

    public double getProgress() {
        double progress = Math.round(weekList.getProgressProject() * 100d) / 100d;
        return progress * 100;
    }
}
