package team.easytravel.testutil.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import team.easytravel.model.listmanagers.ActivityManager;
import team.easytravel.model.listmanagers.activity.Activity;
import team.easytravel.model.listmanagers.activity.Duration;
import team.easytravel.model.util.attributes.Location;
import team.easytravel.model.util.attributes.Title;
import team.easytravel.model.util.attributes.tag.Tag;


/**
 * A utility class containing a list of {@code Activity} objects to be used in tests.
 */
public class TypicalActivity {

    public static final Activity ACTIVITY_DISNEYLAND =
            new Activity(new Title("Hot Spring"), new Duration(3), new Location("Hokkaido"),
                    getTagSet("relaxation"), Optional.empty());

    public static final Activity ACTIVITY_UNIVERSALSTUDIOS = new Activity(
            new Title("Universal Studios"), new Duration(10), new Location("Florida"),
            new HashSet<>(), Optional.empty());

    public static final Activity ACTIVITY_PEAK = new Activity(
            new Title("Peak"), new Duration(1), new Location("Hong Kong"),
            new HashSet<>(), Optional.empty()
    );

    //Dont Touch these Activities below, used for storage
    public static final Activity ACTIVITY_BUNGEE_JUMP = new Activity(
            new Title("Bungee Jump"), new Duration(1), new Location("New Zealand"),
            new HashSet<>(), Optional.empty()
    );

    public static final Activity ACTIVITY_FLY_KITE = new Activity(
            new Title("Fly Kite"), new Duration(1), new Location("Singapore"),
            new HashSet<>(), Optional.empty()
    );

    private TypicalActivity() {
    }

    /**
     * Returns a tag set containing the list of strings given.
     *
     * @param strings the strings
     * @return the tag set
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns an {@code TypicalActivity} with all the typical Activity.
     */

    public static ActivityManager getTypicalActivityManager() {
        ActivityManager am = new ActivityManager();
        for (Activity activity : getTypicalActivity()) {
            am.addActivity(activity);
        }
        return am;
    }

    public static List<Activity> getTypicalActivity() {
        return new ArrayList<>(Arrays.asList(ACTIVITY_DISNEYLAND, ACTIVITY_PEAK,
                ACTIVITY_UNIVERSALSTUDIOS));
    }


}
