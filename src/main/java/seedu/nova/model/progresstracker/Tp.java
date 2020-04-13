package seedu.nova.model.progresstracker;

/**
 * Represents team project
 */
public class Tp extends Project {
    private PtWeekList weekList;
    private String projectName;

    /**
     * Creates Tp object
     */
    public Tp() {
        weekList = new PtWeekList();
        projectName = "tp";
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

