package seedu.address.logic.commands.view;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.results.CommandResult;
import seedu.address.model.item.Project;
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.TypicalProject;

public class ViewProjectTest {

    @Test
    public void constructor_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ViewProjectCommand(null));
    }

    @Test
    public void execute_projectInModel_viewSuccessful() throws CommandException {
        Project validItem = TypicalProject.DUKE;
        ModelStub modelStub = new ModelStubWithProject(validItem);
        CommandResult commandResult = new ViewProjectCommand(Index.fromOneBased(1)).execute(modelStub);
        assertEquals(ViewProjectCommand.MESSAGE_VIEW_SUCCESS,
                commandResult.getFeedbackToUser());
        assertEquals(validItem.toString(),
                commandResult.getDataToUser());
    }

    @Test
    public void execute_projectInModel_indexOutOfBounds() {
        Project validItem = TypicalProject.DUKE;
        ModelStub modelStub = new ModelStubWithProject(validItem);
        assertThrows(CommandException.class, () -> new ViewProjectCommand(Index.fromOneBased(2)).execute(modelStub));
    }

    @Test
    public void equals() {
        ViewProjectCommand viewFirstIndex = new ViewProjectCommand(Index.fromOneBased(1));
        ViewProjectCommand viewSecondIndex = new ViewProjectCommand(Index.fromOneBased(2));

        // same object -> returns true
        assertEquals(viewFirstIndex, viewFirstIndex);

        // same values -> returns true
        ViewProjectCommand viewFirstIndexCopy = new ViewProjectCommand(Index.fromOneBased(1));
        assertEquals(viewFirstIndex, viewFirstIndexCopy);

        // different types -> returns false
        assertNotEquals(1, viewFirstIndex);

        // null -> returns false
        assertNotEquals(null, viewFirstIndex);

        // different Project -> returns false
        assertNotEquals(viewFirstIndex, viewSecondIndex);
    }

    /**
     * A Model stub that contains a single project.
     */
    private static class ModelStubWithProject extends ModelStub {
        private final Project item;

        ModelStubWithProject(Project item) {
            requireNonNull(item);
            this.item = item;
        }

        @Override
        public int getProjectSize() {
            return 1;
        }

        @Override
        public Project getProjectByIndex(Index index) {
            return item;
        }
    }
}
