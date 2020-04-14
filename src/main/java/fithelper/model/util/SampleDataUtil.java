package fithelper.model.util;

import fithelper.model.FitHelper;
import fithelper.model.ReadOnlyFitHelper;
import fithelper.model.ReadOnlyUserProfile;
import fithelper.model.ReadOnlyWeightRecords;
import fithelper.model.UserProfile;
import fithelper.model.WeightRecords;
import fithelper.model.diary.Content;
import fithelper.model.diary.Diary;
import fithelper.model.diary.DiaryDate;
import fithelper.model.entry.Calorie;
import fithelper.model.entry.Duration;
import fithelper.model.entry.Entry;
import fithelper.model.entry.Location;
import fithelper.model.entry.Name;
import fithelper.model.entry.Remark;
import fithelper.model.entry.Status;
import fithelper.model.entry.Time;
import fithelper.model.entry.Type;
import fithelper.model.profile.Address;
import fithelper.model.profile.Age;
import fithelper.model.profile.Gender;
import fithelper.model.profile.Height;
import fithelper.model.profile.Profile;
import fithelper.model.profile.TargetWeight;

/**
 * Contains utility methods for populating {@code FitHelper} with sample data.
 */
public class SampleDataUtil {

    /**
     * Gets sample food and sports entries.
     *
     * @return An array of food and sports entries.
     */
    public static Entry[] getSampleEntries() {
        return new Entry[]{
            new Entry(new Type("food"), new Name("Noodles"), new Time(new DiaryDate().toString() + "-15:30"),
                new Location("Utown canteen"), new Calorie("300"), new Status("Done"), new Remark("Too expensive"),
                new Duration("1")),
            new Entry(new Type("food"), new Name("Mala"), new Time(new DiaryDate().toString() + "-17:30"),
                new Location("Utown canteen"), new Calorie("150.5"), new Status("Done"),
                new Remark("Too expensive")),
            new Entry(new Type("food"), new Name("Apple juice"), new Time("2020-03-01-12:30"),
                new Location("Utown 711"), new Calorie("79")),
            new Entry(new Type("food"), new Name("Lemon juice"), new Time("2020-03-01-11:30"),
                new Location("Utown 711"), new Calorie("79"), new Duration("1")),
            new Entry(new Type("s"), new Name("Swimming"), new Time(new DiaryDate().toString() + "-20:20"),
                new Location("Sports Center"), new Calorie("450.5"), new Remark("Very tired.")),
            new Entry(new Type("s"), new Name("Jogging"), new Time("2020-03-01-20:20"),
                new Location("Track"), new Calorie("450.5"), new Remark("Very tired."), new Duration("1")),
            new Entry(new Type("sports"), new Name("Football"), new Time("2019-12-01-14:00"),
                new Location("Utown towngreen"), new Calorie("400")),
            new Entry(new Type("s"), new Name("Walking"), new Time("2020-02-01-19:20"),
                new Location("PGPR"), new Calorie("450.5"), new Remark("Feels good"), new Duration("1")),
        };
    }

    /**
     * Gets sample diaries.
     *
     * @return An array of sample diaries.
     */
    public static Diary[] getSampleDiaries() {
        return new Diary[] { new Diary(new DiaryDate(), new Content("Today is a sunny day. I went to the gym.")), };
    }

    /**
     * Gets sample FitHelper.
     *
     * @return A FitHelper only with the sample entries.
     */
    public static ReadOnlyFitHelper getSampleFitHelper() {
        FitHelper sampleFitHelper = new FitHelper();
        for (Entry sampleEntry : getSampleEntries()) {
            sampleFitHelper.addEntry(sampleEntry);
        }
        for (Diary sampleDiary : getSampleDiaries()) {
            sampleFitHelper.addDiary(sampleDiary);
        }
        return sampleFitHelper;
    }


    /**
     * Gets sample profile content.
     *
     * @return A profile with some sample value.
     */
    public static Profile getSampleProfile() {
        return new Profile(
                new fithelper.model.profile.Name("Alice"),
                new Gender("female"),
                new Age("19"),
                new Address("Utown CAPT"),
                new Height("168"),
                new TargetWeight("50")
        );
    }


    /**
     * Gets sample User Profile.
     *
     * @return A user profile only with the sample values for each attribute.
     */
    public static ReadOnlyUserProfile getSampleUserProfile() {
        UserProfile sampleUserProfile = new UserProfile();
        sampleUserProfile.setUserProfile(getSampleProfile());
        return sampleUserProfile;
    }



    /**
     * Gets sample WeightRecords which is empty.
     *
     * @return A empty Weight Records.
     */
    public static ReadOnlyWeightRecords getSampleWeightRecords() {
        WeightRecords sampleWeightRecords = new WeightRecords();
        return sampleWeightRecords;
    }


}
