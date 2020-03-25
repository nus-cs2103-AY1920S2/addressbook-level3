package seedu.recipe.model.goal;

import static seedu.recipe.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class GoalTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Goal(null));
    }

    @Test
    public void constructor_invalidGoalName_throwsIllegalArgumentException() {
        String invalidGoalName = "";
        assertThrows(IllegalArgumentException.class, () -> new Goal(invalidGoalName));
    }

    @Test
    public void isValidGoalName() {
        // null goal name
        assertThrows(NullPointerException.class, () -> Goal.isValidGoalName(null));
    }

}
