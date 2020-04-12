package seedu.recipe.model.plan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.recipe.testutil.Assert.assertThrows;
import static seedu.recipe.testutil.TypicalDates.DATE_IN_FUTURE;
import static seedu.recipe.testutil.TypicalDates.DATE_TODAY;
import static seedu.recipe.testutil.TypicalPlans.CHICKEN_TODAY_PLAN;
import static seedu.recipe.testutil.TypicalPlans.SANDWICH_PAST_PLAN;
import static seedu.recipe.testutil.TypicalPlans.getTypicalPlannedBook;
import static seedu.recipe.testutil.TypicalRecipes.BOILED_CHICKEN;
import static seedu.recipe.testutil.TypicalRecipes.CAESAR_SALAD;
import static seedu.recipe.testutil.TypicalRecipes.GRILLED_SANDWICH;
import static seedu.recipe.testutil.TypicalRecipes.TUNA_BREAD;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.recipe.model.ReadOnlyPlannedBook;

public class PlannedBookTest {
    private final PlannedBook plannedBook = new PlannedBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), plannedBook.getPlannedList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> plannedBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyPlannedBook_replacesData() {
        PlannedBook newData = getTypicalPlannedBook();
        plannedBook.resetData(newData);
        assertEquals(newData, plannedBook);
    }

    @Test
    public void resetData_withDuplicatePlans_throwsDuplicateRecipeException() {
        // Two plans with the same identity fields
        Plan caesarToday = new Plan(CAESAR_SALAD, DATE_TODAY);
        Plan caesarTodayCopy = new Plan(CAESAR_SALAD, DATE_TODAY);

        List<Plan> newPlans = Arrays.asList(caesarToday, caesarTodayCopy);
        PlannedBookStub newData = new PlannedBookStub(newPlans);

        assertThrows(DuplicatePlannedRecipeException.class, () -> plannedBook.resetData(newData));
    }

    @Test
    public void setRecipe_nullRecipe_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> plannedBook.setRecipe(null, null));
        assertThrows(NullPointerException.class, () -> plannedBook.setRecipe(CAESAR_SALAD, null));
        assertThrows(NullPointerException.class, () -> plannedBook.setRecipe(null, CAESAR_SALAD));
    }

    @Test
    public void setRecipe_recipeNotInPlans_bookRemainsSame() {
        PlannedBook editedPlannedBook = new PlannedBook();
        editedPlannedBook.setRecipe(CAESAR_SALAD, GRILLED_SANDWICH);
        PlannedBook expectedPlannedBook = new PlannedBook();
        assertEquals(expectedPlannedBook, editedPlannedBook);
    }

    @Test
    public void setRecipe_recipeInPlans_recipeUpdated() {
        PlannedBook editedPlannedBook = getTypicalPlannedBook();
        editedPlannedBook.setRecipe(CAESAR_SALAD, TUNA_BREAD);
        PlannedBook expectedPlannedBook = new PlannedBook();

        Plan editedPlan = new Plan(TUNA_BREAD, DATE_IN_FUTURE);
        expectedPlannedBook.addPlan(TUNA_BREAD, editedPlan);
        expectedPlannedBook.addPlan(GRILLED_SANDWICH, SANDWICH_PAST_PLAN);
        expectedPlannedBook.addPlan(BOILED_CHICKEN, CHICKEN_TODAY_PLAN);
        assertEquals(expectedPlannedBook, editedPlannedBook);
    }


    @Test
    public void getPlannedList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> plannedBook.getPlannedList().remove(0));
    }

    /**
     * A stub ReadOnlyPlannedBook whose plans list can violate interface constraints.
     */
    private static class PlannedBookStub implements ReadOnlyPlannedBook {
        private final ObservableList<Plan> plans = FXCollections.observableArrayList();

        PlannedBookStub(Collection<Plan> plans) {
            this.plans.setAll(plans);
        }

        @Override
        public ObservableList<Plan> getPlannedList() {
            return plans;
        }

        @Override
        public PlannedRecipeMap getPlannedMap() {
            throw new AssertionError("This method should not be called.");
        }
    }
}
