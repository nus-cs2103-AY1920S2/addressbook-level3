package seedu.foodiebot.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.foodiebot.model.FoodieBot;
import seedu.foodiebot.model.ReadOnlyFoodieBot;
import seedu.foodiebot.model.canteen.Canteen;
import seedu.foodiebot.model.canteen.Name;
import seedu.foodiebot.model.tag.Tag;

/** Contains utility methods for populating {@code AddressBook} with sample data. */
public class SampleDataUtil {
    public static Canteen[] getSampleCanteens() {
        return new Canteen[] {
            new Canteen(new Name("The Deck"), 2, 800, "COM1", getTagSet("asian")),
            new Canteen(
                    new Name("Nus Flavors"),
                    2,
                    3200,
                    "COM1",
                    getTagSet("asian", "western", "muslim"))
        };
    }

    public static ReadOnlyFoodieBot getSampleFoodieBot() {
        FoodieBot sampleAb = new FoodieBot();
        for (Canteen sampleCanteen : getSampleCanteens()) {
            sampleAb.addCanteen(sampleCanteen);
        }
        return sampleAb;
    }

    /** Returns a tag set containing the list of strings given. */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings).map(Tag::new).collect(Collectors.toSet());
    }
}
