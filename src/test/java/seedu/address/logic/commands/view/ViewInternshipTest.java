package seedu.address.logic.commands.view;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.results.CommandResult;
import seedu.address.model.item.Internship;
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.TypicalInternship;

public class ViewInternshipTest {

    @Test
    public void constructor_nullInternship_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ViewInternshipCommand(null));
    }

    @Test
    public void execute_internshipInModel_viewSuccessful() throws CommandException {
        Internship validItem = TypicalInternship.GOOGLE;
        ModelStub modelStub = new ModelStubWithInternship(validItem);
        CommandResult commandResult = new ViewInternshipCommand(Index.fromOneBased(1)).execute(modelStub);
        assertEquals(ViewInternshipCommand.MESSAGE_VIEW_SUCCESS,
                commandResult.getFeedbackToUser());
        assertEquals(validItem.toString(),
                commandResult.getDataToUser());
    }

    @Test
    public void execute_internshipInModel_indexOutOfBounds() {
        Internship validItem = TypicalInternship.GOOGLE;
        ModelStub modelStub = new ModelStubWithInternship(validItem);
        assertThrows(CommandException.class, () -> new ViewInternshipCommand(Index.fromOneBased(2)).execute(modelStub));
    }

    @Test
    public void equals() {
        ViewInternshipCommand viewFirstIndex = new ViewInternshipCommand(Index.fromOneBased(1));
        ViewInternshipCommand viewSecondIndex = new ViewInternshipCommand(Index.fromOneBased(2));

        // same object -> returns true
        assertEquals(viewFirstIndex, viewFirstIndex);

        // same values -> returns true
        ViewInternshipCommand viewFirstIndexCopy = new ViewInternshipCommand(Index.fromOneBased(1));
        assertEquals(viewFirstIndex, viewFirstIndexCopy);

        // different types -> returns false
        assertNotEquals(1, viewFirstIndex);

        // null -> returns false
        assertNotEquals(null, viewFirstIndex);

        // different Internship -> returns false
        assertNotEquals(viewFirstIndex, viewSecondIndex);
    }

    /**
     * A Model stub that contains a single internship.
     */
    private static class ModelStubWithInternship extends ModelStub {
        private final Internship item;

        ModelStubWithInternship(Internship item) {
            requireNonNull(item);
            this.item = item;
        }

        @Override
        public int getInternshipSize() {
            return 1;
        }

        @Override
        public Internship getInternshipByIndex(Index index) {
            return item;
        }
    }
}
