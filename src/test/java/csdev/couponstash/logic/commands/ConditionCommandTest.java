package csdev.couponstash.logic.commands;

import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_CONDITION_AMY;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_CONDITION_BOB;
import static csdev.couponstash.logic.commands.CommandTestUtil.assertCommandFailure;

import static csdev.couponstash.logic.commands.ConditionCommand.MESSAGE_ARGUMENTS;
import static csdev.couponstash.testutil.TypicalCoupons.getTypicalCouponStash;
import static csdev.couponstash.testutil.TypicalIndexes.INDEX_FIRST_COUPON;
import static csdev.couponstash.testutil.TypicalIndexes.INDEX_SECOND_COUPON;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import csdev.couponstash.model.Model;
import csdev.couponstash.model.ModelManager;
import csdev.couponstash.model.UserPrefs;
import csdev.couponstash.model.coupon.Condition;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ConditionCommand.
 */
public class ConditionCommandTest {

    private Model model = new ModelManager(getTypicalCouponStash(), new UserPrefs());

    @Test
    public void execute() {

        final Condition condition = new Condition("Some remark");

        assertCommandFailure(new ConditionCommand(INDEX_FIRST_COUPON, condition), model,
                String.format(MESSAGE_ARGUMENTS, INDEX_FIRST_COUPON.getOneBased(), condition));
    }

    @Test
    public void equals() {
        final ConditionCommand standardCommand = new ConditionCommand(INDEX_FIRST_COUPON,
                new Condition(VALID_CONDITION_AMY));

        // same values -> returns true
        ConditionCommand commandWithSameValues = new ConditionCommand(INDEX_FIRST_COUPON,
                new Condition(VALID_CONDITION_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new ConditionCommand(INDEX_SECOND_COUPON,
                new Condition(VALID_CONDITION_AMY))));

        // different remark -> returns false
        assertFalse(standardCommand.equals(new ConditionCommand(INDEX_FIRST_COUPON,
                new Condition(VALID_CONDITION_BOB + " "))));
    }
}
