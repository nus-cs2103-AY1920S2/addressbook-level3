package csdev.couponstash.model.coupon;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ConditionTest {

    @Test
    public void equals() {
        Condition condition = new Condition("Must spend over $100");

        // same object -> returns true
        assertTrue(condition.equals(condition));

        // same values -> returns true
        Condition conditionCopy = new Condition(condition.value);
        assertTrue(condition.equals(conditionCopy));

        // different types -> returns false
        assertFalse(condition.equals(1));

        // null -> returns false
        assertFalse(condition.equals(null));

        // different remark -> returns false
        Condition differentCondition = new Condition("Bye");
        assertFalse(condition.equals(differentCondition));
    }
}
