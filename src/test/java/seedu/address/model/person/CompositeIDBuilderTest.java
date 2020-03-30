package seedu.address.model.person;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.exceptions.CommandException;

import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalProgress.*;

public class CompositeIDBuilderTest {
    @Test
    public void createCompositeID_lessThanTwoID_failure () {
        CompositeIDBuilder testBuilder = new CompositeIDBuilder();
        assertThrows(CommandException.class, testBuilder::createCompositeID);
    }

    @Test
    public void addStudentID_duplicateID_failure () throws CommandException {
        CompositeIDBuilder testBuilder = new CompositeIDBuilder().addStudentID(student1);
        assertThrows(CommandException.class, () -> {
            testBuilder.addStudentID(student2);
        });
    }

    @Test
    public void addAssignmentID_duplicateID_failure () throws CommandException {
        CompositeIDBuilder testBuilder = new CompositeIDBuilder().addAssignmentID(assignment100);
        assertThrows(CommandException.class, () -> {
            testBuilder.addAssignmentID(assignment200);
        });
    }
}
