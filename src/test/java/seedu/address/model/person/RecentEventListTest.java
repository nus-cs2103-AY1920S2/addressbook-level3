package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class RecentEventListTest {

    private ArrayList<String> strings = new ArrayList<>();
    private PlaceList places = new PlaceList(new ArrayList<>());
    private ActivityList activities = new ActivityList(new ArrayList<>());
    private TimeList times = new TimeList(new ArrayList<>());

    /**
     * Fills all the list fields with an arbitrary string.
     */
    private void fillLists() {
        strings.add("abc");
        places.setPlaceList(strings);
        activities.setActivityList(strings);
        times.setTimeList(strings);
    }

    @Test
    public void generate() {
        fillLists();
        RecentEventList list1 = new RecentEventList();
        RecentEventList list2 = new RecentEventList();
        list1.generate(places, activities, times);
        list2.generate(places, activities, times);
        assertEquals(list1, list2);
    }
}
