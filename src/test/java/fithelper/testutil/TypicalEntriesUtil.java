package fithelper.testutil;

import static fithelper.logic.commands.CommandTestUtil.VALID_CALORIE_FOOD;
import static fithelper.logic.commands.CommandTestUtil.VALID_CALORIE_SPORTS;
import static fithelper.logic.commands.CommandTestUtil.VALID_LOCATION_FOOD;
import static fithelper.logic.commands.CommandTestUtil.VALID_LOCATION_SPORTS;
import static fithelper.logic.commands.CommandTestUtil.VALID_NAME_FOOD;
import static fithelper.logic.commands.CommandTestUtil.VALID_NAME_SPORTS;
import static fithelper.logic.commands.CommandTestUtil.VALID_TIME_FOOD;
import static fithelper.logic.commands.CommandTestUtil.VALID_TIME_SPORTS;
import static fithelper.logic.commands.CommandTestUtil.VALID_TYPE_FOOD;
import static fithelper.logic.commands.CommandTestUtil.VALID_TYPE_SPORTS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fithelper.model.FitHelper;
import fithelper.model.diary.DiaryDate;
import fithelper.model.entry.Entry;

/**
 * A utility class containing a list of {@code Entry} objects to be used in tests.
 */
public class TypicalEntriesUtil {
    public static final Entry CAKE = new EntryBuilder().withType("food")
            .withName("Cake").withTime("2020-03-31-06:15").withLocation("home")
            .withCalorie("500").build();
    public static final Entry BURGER = new EntryBuilder().withType("food")
            .withName("Burger").withTime("2019-04-05-06:50").withLocation("super snack")
            .withCalorie("550").build();
    public static final Entry ENTRY1 = new EntryBuilder().withType("food")
            .withName("Noodles").withTime(new DiaryDate().toString() + "-15:30")
            .withLocation("Utown canteen").withCalorie("300").build();
    public static final Entry ENTRY2 = new EntryBuilder().withType("food").withName("Mala")
            .withTime(new DiaryDate().toString() + "-17:30")
            .withLocation("Utown canteen").withCalorie("150.5").build();
    public static final Entry ENTRY3 = new EntryBuilder().withType("food").withName("Apple juice")
            .withTime("2020-03-01-12:30").withLocation("Utown 711").withCalorie("79").build();
    public static final Entry ENTRY4 = new EntryBuilder().withType("food").withName("Lemon juice")
            .withTime("2020-03-01-11:30").withLocation("Utown 711")
            .withCalorie("79").build();
    public static final Entry ENTRY5 = new EntryBuilder().withType("sports").withName("Running")
            .withTime("2020-04-02-13:00").withLocation("Utown gym")
            .withCalorie("300").build();

    // Manually added - Entries's details found in {@code CommandTestUtil}
    public static final Entry FOOD = new EntryBuilder().withName(VALID_NAME_FOOD).withCalorie(VALID_CALORIE_FOOD)
            .withLocation(VALID_LOCATION_FOOD).withTime(VALID_TIME_FOOD).withType(VALID_TYPE_FOOD).build();
    public static final Entry SPORTS = new EntryBuilder().withName(VALID_NAME_SPORTS).withCalorie(VALID_CALORIE_SPORTS)
            .withLocation(VALID_LOCATION_SPORTS).withTime(VALID_TIME_SPORTS).withType(VALID_TYPE_SPORTS).build();

    private TypicalEntriesUtil() {} // prevents instantiation

    /**
     * Returns an {@code FitHelper} with all the typical entries.
     */
    public static FitHelper getTypicalFitHelper() {
        FitHelper fh = new FitHelper();
        for (Entry entry: getTypicalEntries()) {
            fh.addEntry(entry);
        }
        return fh;
    }

    public static List<Entry> getTypicalEntries() {
        return new ArrayList<>(Arrays.asList(FOOD, SPORTS, ENTRY1, ENTRY2, ENTRY3, ENTRY4, ENTRY5));
    }
}
