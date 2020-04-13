package seedu.address.model.group;

import java.util.ArrayList;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.person.ActivityList;
import seedu.address.model.person.EventDescriptor;
import seedu.address.model.person.FrequencyList;
import seedu.address.model.person.Name;
import seedu.address.model.person.PlaceList;
import seedu.address.model.person.RecentEvent;
import seedu.address.model.person.RecentEventList;
import seedu.address.model.person.Time;
import seedu.address.model.person.TimeList;

/**
 * Represents the Social Group that a person is part of and spends time with.
 */
public class Group {

    private Name name;
    private Time timeSpent;
    private ArrayList<Integer> memberIDs;
    private ArrayList<Integer> eventIDs;
    private final ActivityList activityList = new ActivityList(new ArrayList<>());
    private final PlaceList placeList = new PlaceList(new ArrayList<>());
    private final TimeList timeList = new TimeList(new ArrayList<>());
    private final FrequencyList activityList2;
    private final FrequencyList placeList2;
    private final RecentEventList recentEventList;

    public Group(Name name, PlaceList placeList, ActivityList activityList, TimeList timeList) {
        this.name = name;
        this.memberIDs = new ArrayList<>();
        this.eventIDs = new ArrayList<>();
        this.timeSpent = new Time(0, 0);
        this.placeList.setPlaceList(placeList.getPlaceList());
        this.activityList.setActivityList(activityList.getActivityList());
        this.timeList.setTimeList(timeList.getTimeList());
        activityList2 = new FrequencyList();
        activityList2.generate(this.activityList.getActivityList());
        placeList2 = new FrequencyList();
        placeList2.generate(this.placeList.getPlaceList());
        recentEventList = new RecentEventList();
        recentEventList.generate(this.placeList, this.activityList, this.timeList);
    }

    public Name getName() {
        return this.name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Time getTimeSpent() {
        return this.timeSpent;
    }

    public void addPerson(int id) {
        this.memberIDs.add(id);
    }

    public void deletePerson(int id) {
        this.memberIDs.remove(Integer.valueOf(id));
    }

    public void setTimeSpent(Time time) {
        this.timeSpent = time;
    }

    public ArrayList<Integer> getMembers() {
        return this.memberIDs;
    }

    public void setMemberIDs(ArrayList<Integer> members) {
        this.memberIDs = members;
    }

    public ArrayList<Integer> getEvents() {
        return this.eventIDs;
    }

    public void setEventIDs(ArrayList<Integer> events) {
        this.eventIDs = events;
    }

    public PlaceList getPlaceList() {
        return placeList;
    }

    public ActivityList getActivityList() {
        return activityList;
    }

    public TimeList getTimeList() {
        return timeList;
    }

    public ObservableList<RecentEvent> getRecentEventList() {
        return recentEventList.getList();
    }

    /**
     * Returns a string representation of member IDs.
     *
     * @return string representation
     */
    public String printEventIds() {
        String result = "Events: ";
        for (int i = 0; i < this.eventIDs.size(); i++) {
            result += this.eventIDs.get(i) + " ";
        }
        return result;
    }

    /**
     * Returns a String output with all the names in a single line separated by whitespace.
     *
     * @return
     */
    public String printMemberList() {
        String build = "Members: ";
        for (int i = 0; i < memberIDs.size(); i++) {
            if (i == memberIDs.size() - 1) {
                build += memberIDs.get(i);
            } else {
                build += memberIDs.get(i) + ", ";
            }
        }
        return build;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.memberIDs);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Group)) {
            return false;
        }

        Group g = (Group) o;

        return (this.getName().equals(g.getName()));
    }

    @Override
    public String toString() {
        if (this.memberIDs.isEmpty()) {
            return "Name: " + this.name;
        } else {
            return "Name: "
                    + this.name
                    + ". "
                    + printMemberList();
        }
    }

    public ObservableList<EventDescriptor> getPlaceList2() {
        return placeList2.getFrequencyList();
    }

    public ObservableList<EventDescriptor> getActivityList2() {
        return activityList2.getFrequencyList();
    }

}
