package seedu.address.logic.commands.view;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.results.CommandResult;
import seedu.address.model.item.Skill;
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.TypicalSkill;

public class ViewSkillTest {

    @Test
    public void constructor_nullSkill_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ViewSkillCommand(null));
    }

    @Test
    public void execute_skillInModel_viewSuccessful() throws CommandException {
        Skill validItem = TypicalSkill.REACT;
        ModelStub modelStub = new ModelStubWithSkill(validItem);
        CommandResult commandResult = new ViewSkillCommand(Index.fromOneBased(1)).execute(modelStub);
        assertEquals(ViewSkillCommand.MESSAGE_VIEW_SUCCESS,
                commandResult.getFeedbackToUser());
        assertEquals(validItem.toString(),
                commandResult.getDataToUser());
    }

    @Test
    public void execute_skillInModel_indexOutOfBounds() {
        Skill validItem = TypicalSkill.REACT;
        ModelStub modelStub = new ModelStubWithSkill(validItem);
        assertThrows(CommandException.class, () -> new ViewSkillCommand(Index.fromOneBased(2)).execute(modelStub));
    }

    @Test
    public void equals() {
        ViewSkillCommand viewFirstIndex = new ViewSkillCommand(Index.fromOneBased(1));
        ViewSkillCommand viewSecondIndex = new ViewSkillCommand(Index.fromOneBased(2));

        // same object -> returns true
        assertEquals(viewFirstIndex, viewFirstIndex);

        // same values -> returns true
        ViewSkillCommand viewFirstIndexCopy = new ViewSkillCommand(Index.fromOneBased(1));
        assertEquals(viewFirstIndex, viewFirstIndexCopy);

        // different types -> returns false
        assertNotEquals(1, viewFirstIndex);

        // null -> returns false
        assertNotEquals(null, viewFirstIndex);

        // different Skill -> returns false
        assertNotEquals(viewFirstIndex, viewSecondIndex);
    }

    /**
     * A Model stub that contains a single skill.
     */
    private static class ModelStubWithSkill extends ModelStub {
        private final Skill item;

        ModelStubWithSkill(Skill item) {
            requireNonNull(item);
            this.item = item;
        }

        @Override
        public int getSkillSize() {
            return 1;
        }

        @Override
        public Skill getSkillByIndex(Index index) {
            return item;
        }
    }
}
