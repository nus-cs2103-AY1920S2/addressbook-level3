package team.easytravel.model.listmanagers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.easytravel.testutil.Assert.assertThrows;
import static team.easytravel.testutil.activity.TypicalActivity.ACTIVITY_DISNEYLAND;
import static team.easytravel.testutil.activity.TypicalActivity.getTypicalActivityManager;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import team.easytravel.model.listmanagers.activity.Activity;
import team.easytravel.model.util.uniquelist.exceptions.DuplicateElementException;


class ActivityManagerTest {

    private final ActivityManager activityManager = new ActivityManager();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), activityManager.getActivityList());
    }

    @Test
    public void resetDataNullThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> activityManager.resetData(null));
    }

    @Test
    public void resetDataWithValidReadOnlyActivityManagerReplacesData() {
        ActivityManager newData = getTypicalActivityManager();
        activityManager.resetData(newData);
        assertEquals(newData, activityManager);
    }

    @Test
    public void resetDataWithDuplicateActivityThrowsDuplicateElementException() {
        List<Activity> newActivity = Arrays.asList(ACTIVITY_DISNEYLAND,
                ACTIVITY_DISNEYLAND);
        ActivityManagerStub newData = new ActivityManagerStub(newActivity);
        assertThrows(DuplicateElementException.class, () -> activityManager.resetData(newData));
    }

    @Test
    public void hasActivityNullActivity_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> activityManager
                .hasActivity(null));
    }

    @Test
    public void hasActivityNotInActivityManagerReturnsFalse() {
        assertFalse(activityManager.hasActivity(ACTIVITY_DISNEYLAND));
    }

    @Test
    public void hasActivityManagerInActivityManagerReturnsTrue() {
        activityManager.addActivity(ACTIVITY_DISNEYLAND);
        assertTrue(activityManager.hasActivity(ACTIVITY_DISNEYLAND));
    }

    @Test
    public void getActivityModifyListThrowsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> activityManager
                .getActivityList().remove(0));
    }


    /**
     * A stub ReadOnlyActivityManager whose activity list can violate interface constraints.
     */
    private static class ActivityManagerStub implements ReadOnlyActivityManager {
        private final ObservableList<Activity> activityBookings =
                FXCollections.observableArrayList();

        ActivityManagerStub(Collection<Activity> activities) {
            this.activityBookings.setAll(activities);
        }

        @Override
        public ObservableList<Activity> getActivityList() {
            return activityBookings;
        }
    }
}
