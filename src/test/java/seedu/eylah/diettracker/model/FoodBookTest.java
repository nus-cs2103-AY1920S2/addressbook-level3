package seedu.eylah.diettracker.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.eylah.diettracker.logic.commands.CommandTestUtil.VALID_TAG_FASTFOOD;
import static seedu.eylah.diettracker.testutil.TypicalFood.PASTA;
import static seedu.eylah.diettracker.testutil.TypicalFood.getTypicalFoodBook;
import static seedu.eylah.testutil.Assert.assertThrows;
//import java.util.Arrays;

import java.util.Collection;
import java.util.Collections;
//import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.eylah.diettracker.model.food.Food;
//import seedu.eylah.diettracker.model.food.exceptions.DuplicateFoodException;
//import seedu.eylah.diettracker.testutil.FoodBuilder;

public class FoodBookTest {

    private final FoodBook foodBook = new FoodBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), foodBook.getFoodList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> foodBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyFoodBook_replacesData() {
        FoodBook newData = getTypicalFoodBook();
        foodBook.resetData(newData);
        assertEquals(newData, foodBook);
    }

    // @Test
    // public void resetData_withDuplicateFoods_throwsDuplicateFoodException() {
    //     // Two foods with the same identity fields
    //     Food editedAlice = new FoodBuilder(PASTA).withTags(VALID_TAG_FASTFOOD)
    //             .build();
    //     List<Food> newFoods = Arrays.asList(PASTA, editedAlice);
    //     FoodBookStub newData = new FoodBookStub(newFoods);

    //     assertThrows(DuplicateFoodException.class, () -> foodBook.resetData(newData));
    // }

    @Test
    public void hasFood_nullFood_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> foodBook.hasFood(null));
    }

    @Test
    public void hasFood_foodNotInFoodBook_returnsFalse() {
        assertFalse(foodBook.hasFood(PASTA));
    }

    @Test
    public void hasFood_foodInFoodBook_returnsTrue() {
        foodBook.addFood(PASTA);
        assertTrue(foodBook.hasFood(PASTA));
    }

    // @Test
    // public void hasFood_foodWithSameIdentityFieldsInFoodBook_returnsTrue() {
    //     foodBook.addFood(PASTA);
    //     Food editedAlice = new FoodBuilder(PASTA).withTags(VALID_TAG_FASTFOOD)
    //             .build();
    //     assertTrue(foodBook.hasFood(editedAlice));
    // }

    @Test
    public void getFoodList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> foodBook.getFoodList().remove(0));
    }

    /**
     * A stub ReadOnlyFoodBook whose foods list can violate interface constraints.
     */
    private static class FoodBookStub implements ReadOnlyFoodBook {
        private final ObservableList<Food> foods = FXCollections.observableArrayList();

        FoodBookStub(Collection<Food> foods) {
            this.foods.setAll(foods);
        }

        @Override
        public ObservableList<Food> getFoodList() {
            return foods;
        }
    }

}
