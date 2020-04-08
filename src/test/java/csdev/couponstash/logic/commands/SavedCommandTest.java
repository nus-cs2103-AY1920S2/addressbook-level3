package csdev.couponstash.logic.commands;

import org.junit.jupiter.api.Test;

import csdev.couponstash.model.Model;
import csdev.couponstash.model.ModelManager;
import csdev.couponstash.model.UserPrefs;
import csdev.couponstash.testutil.TypicalCoupons;

/**
 * Unit tests for Saved Command.
 */
public class SavedCommandTest {
    private Model model = new ModelManager(TypicalCoupons.getTypicalCouponStash(), new UserPrefs());
    private Model expectedModel = new ModelManager(TypicalCoupons.getTypicalCouponStash(), new UserPrefs());

    @Test
    public void execute_noDatesSpecified_success() {

    }

    @Test
    public void execute_oneDateSpecified_success() {

    }

    @Test
    public void execute_dateRangeSpecified_success() {

    }

    @Test
    public void execute_startDateEqualsEndDate_throwsCommandException() {

    }

    @Test
    public void execute_dateProvidedWithoutPrefix_throwsCommandException() {

    }

    @Test
    public void execute_startDateWithoutEndDate_throwsCommandException() {

    }
}
