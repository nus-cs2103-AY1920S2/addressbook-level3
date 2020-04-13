package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;

/**
 * A list of activities
 */
public class ActivityList {
    public final List<String> activityList;

    public ActivityList(List<String> activities) {
        this.activityList = activities;
    }

    public List<String> getActivityList() {
        return this.activityList;
    }

    /**
     * Adds an activity to the current activityList
     * @return new ActivityList
     */
    public ActivityList addActivity(String activity) {
        List<String> toEdit = this.activityList;
        toEdit.add(activity);
        return new ActivityList(toEdit);
    }

    public void setActivityList(List<String> oldList) {
        List<String> newList = new ArrayList<>();
        newList.addAll(this.activityList);
        newList.addAll(oldList);
        this.activityList.clear();
        this.activityList.addAll(newList);
    }

    @Override
    public String toString() {
        String output = "";
        int i = 0;
        for (String s : activityList) {
            if (i < activityList.size() - 1) {
                output += s + ", ";
            } else {
                output += s;
            }
        }
        return output;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ActivityList)) {
            return false;
        }

        ActivityList a = (ActivityList) other;
        return this.activityList.equals(a.activityList);
    }

    @Override
    public int hashCode() {
        return activityList.hashCode();
    }
}
