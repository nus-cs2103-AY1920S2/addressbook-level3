package fithelper.model.entry;

import static fithelper.logic.commands.CommandTestUtil.VALID_CALORIE_SPORTS;
import static fithelper.logic.commands.CommandTestUtil.VALID_LOCATION_SPORTS;
import static fithelper.logic.commands.CommandTestUtil.VALID_NAME_SPORTS;
import static fithelper.logic.commands.CommandTestUtil.VALID_TIME_SPORTS;
import static fithelper.logic.commands.CommandTestUtil.VALID_TYPE_SPORTS;
import static fithelper.testutil.TypicalEntriesUtil.FOOD;
import static fithelper.testutil.TypicalEntriesUtil.SPORTS;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fithelper.testutil.EntryBuilder;

public class EntryTest {
    @Test
    public void isSameEntry() {
        // same object -> returns true
        assertTrue(FOOD.isSameEntry(FOOD));

        // null -> returns false
        assertFalse(FOOD.isSameEntry(null));

        // different location and calorie -> returns false
        Entry editedFood = new EntryBuilder(FOOD).withLocation(VALID_LOCATION_SPORTS)
                .withCalorie(VALID_CALORIE_SPORTS).build();
        assertFalse(FOOD.isSameEntry(editedFood));

        // different name -> returns false
        editedFood = new EntryBuilder(FOOD).withName(VALID_NAME_SPORTS).build();
        assertFalse(FOOD.isSameEntry(editedFood));

        // same name, same calorie, different attributes -> returns false
        editedFood = new EntryBuilder(FOOD).withLocation(VALID_LOCATION_SPORTS).withTime(VALID_TIME_SPORTS)
                .withType(VALID_TYPE_SPORTS).build();
        assertFalse(FOOD.isSameEntry(editedFood));

        // same name, same location, different attributes -> returns false
        editedFood = new EntryBuilder(FOOD).withCalorie(VALID_CALORIE_SPORTS).withTime(VALID_TIME_SPORTS)
                .withType(VALID_TYPE_SPORTS).build();
        assertFalse(FOOD.isSameEntry(editedFood));

        // same name, same calorie, same type, different attributes -> returns false
        editedFood = new EntryBuilder(FOOD).withLocation(VALID_LOCATION_SPORTS).withType(VALID_TYPE_SPORTS).build();
        assertFalse(FOOD.isSameEntry(editedFood));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Entry foodCopy = new EntryBuilder(FOOD).build();
        assertTrue(FOOD.equals(foodCopy));

        // same object -> returns true
        assertTrue(FOOD.equals(FOOD));

        // different type -> returns false
        assertFalse(FOOD.equals(5));

        // different entry -> returns false
        assertFalse(FOOD.equals(SPORTS));

        // different name -> returns false
        Entry editedFood = new EntryBuilder(FOOD).withName(VALID_NAME_SPORTS).build();
        assertFalse(FOOD.equals(editedFood));

        // different time -> returns false
        editedFood = new EntryBuilder(FOOD).withTime(VALID_TIME_SPORTS).build();
        assertFalse(FOOD.equals(editedFood));

        // different calorie -> returns false
        editedFood = new EntryBuilder(FOOD).withCalorie(VALID_CALORIE_SPORTS).build();
        assertFalse(FOOD.equals(editedFood));

        // different location -> returns false
        editedFood = new EntryBuilder(FOOD).withLocation(VALID_LOCATION_SPORTS).build();
        assertFalse(FOOD.equals(editedFood));

        // different types -> returns false
        editedFood = new EntryBuilder(FOOD).withType(VALID_TYPE_SPORTS).build();
        assertFalse(FOOD.equals(editedFood));
    }
}

