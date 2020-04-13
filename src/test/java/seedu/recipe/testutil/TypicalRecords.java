package seedu.recipe.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.recipe.model.cooked.CookedRecordBook;
import seedu.recipe.model.cooked.Record;

/**
 * A utility class containing a list of {@code Record} objects to be used in tests.
 */
public class TypicalRecords {

    public static final Record CAESAR_SALAD = new RecordBuilder().withName("Caesar Salad").withDate("2020-03-05")
            .withGoals("Herbivore").build();

    public static final Record GRILLED_SANDWICH = new RecordBuilder().withName("Grilled Sandwich")
            .withDate("2020-03-06").build();

    public static final Record BOILED_CHICKEN = new RecordBuilder().withName("Boiled Chicken")
            .withDate("2020-03-07").build();

    public static final Record CHOCOLATE_CAKE = new RecordBuilder().withName("Chocolate Cake")
            .withDate("2020-03-08").build();

    public static final Record OMELET = new RecordBuilder().withName("Omelet")
            .withDate("2020-03-15").build();

    public static final Record STEAMED_EGG = new RecordBuilder().withName("Steamed Egg")
            .withDate("2020-03-25").build();

    public static final Record TUNA_BREAD = new RecordBuilder().withName("Tuna Bread")
            .withDate("2020-03-25").build();

    // Manually added
    public static final Record FISH_TACO = new RecordBuilder().withName("Fish Taco")
            .withDate("2020-04-05").withGoals("Bulk like the Hulk").build();

    public static final Record VEGETARIAN_PIZZA = new RecordBuilder().withName("Vegetarian Pizza")
            .withDate("2020-04-07").build();


    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalRecords() {} // prevents instantiation

    /**
     * Returns an {@code RecordBook} with all the typical Records.
     */
    public static CookedRecordBook getTypicalRecordBook() {
        CookedRecordBook rb = new CookedRecordBook();
        for (Record record : getTypicalRecords()) {
            rb.addRecord(record);
        }
        return rb;
    }

    public static List<Record> getTypicalRecords() {
        return new ArrayList<>(Arrays.asList(CAESAR_SALAD, GRILLED_SANDWICH, BOILED_CHICKEN, CHOCOLATE_CAKE,
                OMELET, STEAMED_EGG, TUNA_BREAD));
    }
}
