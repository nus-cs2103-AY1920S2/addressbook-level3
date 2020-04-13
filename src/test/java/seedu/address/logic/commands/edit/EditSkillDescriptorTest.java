package seedu.address.logic.commands.edit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_NAME_REACT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRONTEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TECH;
import static seedu.address.testutil.TypicalEditSkillDescriptor.GIT;
import static seedu.address.testutil.TypicalSkill.REACT;

import org.junit.jupiter.api.Test;

import seedu.address.model.item.field.Level;
import seedu.address.testutil.EditSkillDescriptorBuilder;

public class EditSkillDescriptorTest {
    @Test
    public void isAnyFieldMissing_noFieldEdited_returnsTrue() {
        assertFalse(new EditSkillDescriptorBuilder().build().isAnyFieldEdited());
    }

    @Test
    public void equals() {
        // same values -> returns true
        EditSkillDescriptor descriptorWithSameValues = new EditSkillDescriptor(GIT);
        assertEquals(GIT, descriptorWithSameValues);

        // same object -> returns true
        assertEquals(GIT, GIT);

        // null -> returns false
        assertNotEquals(null, GIT);

        // different types -> returns false
        assertNotEquals(5, GIT);

        // different values -> returns false
        assertNotEquals(GIT, REACT);

        // different name -> returns false
        EditSkillDescriptor editedGit = new EditSkillDescriptorBuilder(GIT)
                .withName(VALID_SKILL_NAME_REACT).build();
        assertNotEquals(GIT, editedGit);

        // different level -> returns false
        editedGit = new EditSkillDescriptorBuilder(GIT)
                .withLevel(Level.ADVANCED).build();
        assertNotEquals(GIT, editedGit);

        // different tags -> returns false
        editedGit = new EditSkillDescriptorBuilder(GIT)
                .withTags(VALID_TAG_TECH, VALID_TAG_FRONTEND).build();
        assertNotEquals(GIT, editedGit);
    }

    @Test
    public void field_isAnyNonNull_falseIfAllNull() {
        EditSkillDescriptor editSkillDescriptor = new EditSkillDescriptor();
        assertEquals(editSkillDescriptor.isAnyFieldEdited(), false);
    }
}
