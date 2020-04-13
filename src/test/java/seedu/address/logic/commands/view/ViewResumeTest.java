package seedu.address.logic.commands.view;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.results.CommandResult;
import seedu.address.model.item.Resume;
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.TypicalResume;

public class ViewResumeTest {

    @Test
    public void constructor_nullResume_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ViewResumeCommand(null));
    }

    @Test
    public void execute_resumeInModel_viewSuccessful() throws CommandException {
        Resume validItem = TypicalResume.ME_RESUME;
        ModelStub modelStub = new ModelStubWithResume(validItem);
        CommandResult commandResult = new ViewResumeCommand(Index.fromOneBased(1)).execute(modelStub);
        assertEquals(ViewResumeCommand.MESSAGE_VIEW_SUCCESS,
                commandResult.getFeedbackToUser());
        assertEquals(validItem.toString(),
                commandResult.getDataToUser());
    }

    @Test
    public void execute_resumeInModel_indexOutOfBounds() {
        Resume validItem = TypicalResume.ME_RESUME;
        ModelStub modelStub = new ModelStubWithResume(validItem);
        assertThrows(CommandException.class, () -> new ViewResumeCommand(Index.fromOneBased(2)).execute(modelStub));
    }

    @Test
    public void equals() {
        ViewResumeCommand viewFirstIndex = new ViewResumeCommand(Index.fromOneBased(1));
        ViewResumeCommand viewSecondIndex = new ViewResumeCommand(Index.fromOneBased(2));

        // same object -> returns true
        assertEquals(viewFirstIndex, viewFirstIndex);

        // same values -> returns true
        ViewResumeCommand viewFirstIndexCopy = new ViewResumeCommand(Index.fromOneBased(1));
        assertEquals(viewFirstIndex, viewFirstIndexCopy);

        // different types -> returns false
        assertNotEquals(1, viewFirstIndex);

        // null -> returns false
        assertNotEquals(null, viewFirstIndex);

        // different resume -> returns false
        assertNotEquals(viewFirstIndex, viewSecondIndex);
    }

    /**
     * A Model stub that contains a single resume.
     */
    private static class ModelStubWithResume extends ModelStub {
        private final Resume item;

        ModelStubWithResume(Resume item) {
            requireNonNull(item);
            this.item = item;
        }

        @Override
        public int getResumeSize() {
            return 1;
        }

        @Override
        public Resume getResumeByIndex(Index index) {
            return item;
        }
    }
}
