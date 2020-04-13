package seedu.address.model.item;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_NAME_REACT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalSkill.GIT;
import static seedu.address.testutil.TypicalSkill.REACT;

import org.junit.jupiter.api.Test;

import seedu.address.model.item.field.Level;
import seedu.address.testutil.SkillBuilder;

public class SkillTest {
    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Skill skill = new SkillBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> skill.getTags().remove(0));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Skill gitCopy = new SkillBuilder(GIT).build();
        assertTrue(GIT.equals(gitCopy));

        // same object -> returns true
        assertTrue(GIT.equals(GIT));

        // null -> returns false
        assertFalse(GIT.equals(null));

        // different type -> returns false
        assertFalse(GIT.equals(5));

        // different skill -> returns false
        assertFalse(GIT.equals(REACT));

        // different name -> returns false
        Skill editedGit = new SkillBuilder(GIT).withName(VALID_SKILL_NAME_REACT).build();
        assertFalse(GIT.equals(editedGit));

        // different level -> returns false
        editedGit = new SkillBuilder(GIT).withLevel(Level.ADVANCED).build();
        assertFalse(GIT.equals(editedGit));

    }
}
