package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;


/**
 * A list of event durations stored as Strings.
 */
public class TimeList {
    public final List<String> timeList;

    public TimeList(List<String> times) {
        this.timeList = times;
    }

    public List<String> getTimeList() {
        return this.timeList;
    }

    /**
     * Adds a time to the current timeList
     *
     * @return new TimeList
     */
    public TimeList addTime(String time) {
        List<String> current = this.timeList;
        current.add(time);
        return new TimeList(current);
    }

    public void setTimeList(List<String> xs) {
        List<String> newList = new ArrayList<>();
        newList.addAll(this.timeList);
        newList.addAll(xs);
        this.timeList.clear();
        this.timeList.addAll(newList);
    }

    @Override
    public String toString() {
        String output = "";
        int i = 0;
        for (String s : timeList) {
            if (i < timeList.size() - 1) {
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

        if (!(other instanceof PlaceList)) {
            return false;
        }

        TimeList t = (TimeList) other;
        return this.timeList.equals(t.timeList);
    }

    @Override
    public int hashCode() {
        return timeList.hashCode();
    }
}
