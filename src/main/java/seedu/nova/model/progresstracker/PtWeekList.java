package seedu.nova.model.progresstracker;

import java.util.ArrayList;

/**
 * Handles week objects
 */
public class PtWeekList {
    private ArrayList<PtWeek> weekList;

    /**
     * Creates PtWeekList object
     */
    public PtWeekList() {
        weekList = new ArrayList<>();

        //create 13 weeks since one semester only has 13 weeks
        for (int i = 0; i < 13; i++) {
            PtWeek week = new PtWeek(i + 1);
            weekList.add(week);
        }
    }

    public ArrayList<PtWeek> getWeekList() {
        return weekList;
    }

    public int getNumWeeks() {
        return weekList.size();
    }

    public PtWeek getWeek(int num) {
        if (weekList.size() >= num) {
            return weekList.get(num - 1);
        } else {
            return null;
        }
    }

    public double totalTasks(PtTaskList ptTaskList) {
        return ptTaskList.getNumTask();
    }

    public double totalDone(PtTaskList ptTaskList) {
        return ptTaskList.getNumDone();
    }

    /**
     * Calculates the progress for the project
     * @return a double representing the percentage of tasks done in the project
     */
    public double getProgressProject() {
        double totalTaskDone = 0;
        double totalTasks = 0;

        //Get progress of each week
        for (int i = 0; i < weekList.size(); i++) {
            PtWeek current = weekList.get(i);
            PtTaskList taskList = current.getTaskList();

            totalTasks = totalTasks + totalTasks(taskList);
            totalTaskDone = totalTaskDone + totalDone(taskList);
        }

        if (totalTasks == 0) {
            return 0;
        } else {
            return totalTaskDone / totalTasks;
        }
    }
}
