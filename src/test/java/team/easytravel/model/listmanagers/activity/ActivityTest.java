package team.easytravel.model.listmanagers.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import team.easytravel.model.util.attributes.Location;
import team.easytravel.model.util.attributes.Title;
import team.easytravel.testutil.Assert;

class ActivityTest {

    private final Activity activity =
            new Activity(new Title("Hot Spring"),
                    new Duration(3),
                    new Location("Hokkaido"),
                    new HashSet<>(),
                    Optional.empty());

    @Test
    public void constructorInvalidTitleThrowsIllegalArgumentException() {
        String invalidTitle = " ";
        Integer invalidDuration = 0;

        Assert.assertThrows(IllegalArgumentException.class, () -> new Activity(new Title(invalidTitle),
                new Duration(invalidDuration),
                new Location(null),
                new HashSet<>(),
                Optional.empty()));
    }

    @Test
    public void getTitle() {

        // Correct case
        Assertions.assertEquals(new Title("Hot Spring"), new Activity(
                new Title("Hot Spring"),
                new Duration(3),
                new Location("Hokkaido"),
                new HashSet<>(),
                Optional.empty()).getTitle());

        //Different case
        Assertions.assertNotEquals(new Title("Hot Hot"),
                new Activity(new Title("Hot Spring"),
                        new Duration(3),
                        new Location("Hokkaido"),
                        new HashSet<>(),
                        Optional.empty()).getTitle());

    }

    @Test
    public void getDuration() {

        // Correct case
        assertEquals(new Duration(3),
                new Activity(new Title("Hot Spring"),
                        new Duration(3),
                        new Location("Hokkaido"),
                        new HashSet<>(),
                        Optional.empty()).getDuration());

        //Different case
        assertNotEquals(new Duration(2),
                new Activity(new Title("Hot Spring"),
                        new Duration(3),
                        new Location("Hokkaido"),
                        new HashSet<>(),
                        Optional.empty()).getDuration());

    }

    @Test
    public void isSame() {
        // same object -> returns true
        assertTrue(activity.isSame(activity));

        // null -> returns false
        assertFalse(activity.isSame(null));

        // different name and quantity -> returns false
        Activity editedActivity = new Activity(new Title("See Deers"),
                new Duration(1),
                new Location("Nara"),
                new HashSet<>(),
                Optional.empty());
        assertFalse(editedActivity.isSame(activity));

        // different name -> returns false
        Activity secondEditedActivity = new Activity(new Title("Spring"),
                new Duration(3),
                new Location("Hokkaido"),
                new HashSet<>(),
                Optional.empty());
        assertFalse(editedActivity.isSame(secondEditedActivity));
    }

    @Test
    public void testEquals() {
        // Same case
        Activity editedActivity = new Activity(new Title("Hot Spring"),
                new Duration(3),
                new Location("Hokkaido"),
                new HashSet<>(),
                Optional.empty());
        assertEquals(activity, editedActivity);

        //Different case
        editedActivity = new Activity(new Title("See Deers"),
                new Duration(1),
                new Location("Nara"),
                new HashSet<>(),
                Optional.empty());
        assertNotEquals(editedActivity, activity);
    }

    @Test
    public void testHashCode() {

        //Same case
        Activity editedActivity = new Activity(new Title("Hot Spring"),
                new Duration(3),
                new Location("Hokkaido"),
                new HashSet<>(),
                Optional.empty());
        assertEquals(editedActivity.hashCode(), activity.hashCode());

        //Different case
        editedActivity = new Activity(new Title("See Deers"),
                new Duration(1),
                new Location("Nara"),
                new HashSet<>(),
                Optional.empty());
        assertNotEquals(editedActivity.hashCode(), activity.hashCode());
    }

    @Test
    public void testToString() {
        Activity editedActivity =
                new Activity(new Title("Hot Spring"),
                        new Duration(3),
                        new Location("Hokkaido"),
                        new HashSet<>(),
                        Optional.empty());
        assertEquals(activity.toString(), editedActivity.toString());

        editedActivity =
                new Activity(new Title("See Deers"),
                        new Duration(1),
                        new Location("Nara"),
                        new HashSet<>(),
                        Optional.empty());
        assertNotEquals(editedActivity.toString(), activity.toString());

    }
}
