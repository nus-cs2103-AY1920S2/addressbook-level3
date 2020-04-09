package seedu.address.logic.commands.AddCommand;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.commandAdd.AddStaffCommand;

import static seedu.address.testutil.Assert.assertThrows;

public class AddStaffCommandTest {

    @Test
    public void constructor_nullStaff_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddStaffCommand(null));
    }
}
