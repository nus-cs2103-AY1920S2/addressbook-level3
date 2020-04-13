package seedu.address.testutil;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.group.Group;
import seedu.address.model.person.ActivityList;
import seedu.address.model.person.Name;
import seedu.address.model.person.PlaceList;
import seedu.address.model.person.Time;
import seedu.address.model.person.TimeList;

/**
 * A utility class to help with building Group Objects.
 */
public class GroupBuilder {

    public static final String DEFAULT_NAME = "SoC Friends";
    public static final int DEFAULT_MINS = 1;
    public static final int DEFAULT_HOURS = 1;
    public static final ArrayList<Integer> DEFAULT_LIST = new ArrayList<>();
    public static final List<String> DEFAULT_PLACELIST = new ArrayList<>();
    public static final List<String> DEFAULT_ACTIVITYLIST = new ArrayList<>();
    public static final List<String> DEFAULT_TIMELIST = new ArrayList<>();

    private Name name;
    private Time timeSpent;
    private ArrayList<Integer> memberIds;
    private ArrayList<Integer> eventIDs;
    private PlaceList places;
    private ActivityList activities;
    private TimeList times;

    public GroupBuilder() {
        this.name = new Name(DEFAULT_NAME);
        this.timeSpent = new Time(DEFAULT_MINS, DEFAULT_HOURS);
        this.memberIds = DEFAULT_LIST;
        this.eventIDs = DEFAULT_LIST;
        places = new PlaceList(DEFAULT_PLACELIST);
        activities = new ActivityList(DEFAULT_ACTIVITYLIST);
        times = new TimeList(DEFAULT_TIMELIST);
    }

    public GroupBuilder(Group groupToCopy) {
        name = groupToCopy.getName();
        timeSpent = groupToCopy.getTimeSpent();
        memberIds = groupToCopy.getMembers();
        eventIDs = groupToCopy.getEvents();
        places = groupToCopy.getPlaceList();
        activities = groupToCopy.getActivityList();
        times = groupToCopy.getTimeList();
    }

    /**
     * Sets the code for name.
     * @param name
     * @return
     */
    public GroupBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the code for group Id.
     * @param id
     * @return
     */
    public GroupBuilder withId(int id) {
        return this;
    }

    /**
     * Sets the code for time.
     * @param time
     * @return
     */
    public GroupBuilder withTime(String time) {
        this.timeSpent = ParserUtil.parseTime(time);
        return this;
    }

    /**
     * Sets the code for withMembers.
     * @param memberIds
     * @return
     */
    public GroupBuilder withMembers(ArrayList<Integer> memberIds) {
        this.memberIds = memberIds;
        return this;
    }

    /**
     * Sets the code for withEvents.
     * @param eventIDs
     * @return
     */
    public GroupBuilder withEvents(ArrayList<Integer> eventIDs) {
        this.eventIDs = eventIDs;
        return this;
    }

    /**
     * Sets the {@code PlaceList} of the {@code Person} that we are building.
     */
    public GroupBuilder withPlaceList(String input) {
        this.places = ParserUtil.parsePlaces(input);
        return this;
    }

    /**
     * Sets the {@code ActivityList} of the {@code Person} that we are building.
     */
    public GroupBuilder withActivityList(String input) {
        this.activities = ParserUtil.parseActivities(input);
        return this;
    }

    /**
     * Builds a group with params in this class.
     * @return
     */
    public Group build() {
        Group group = new Group(name, places, activities, times);
        group.setTimeSpent(timeSpent);
        group.setMemberIDs(memberIds);
        group.setEventIDs(eventIDs);
        return group;
    }

}
