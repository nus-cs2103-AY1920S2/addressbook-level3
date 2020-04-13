package seedu.address.model.item;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_ORBITAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_NAME_DUKE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_NAME_ORBITAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_JAVA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEBSITE_DUKE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEBSITE_ORBITAL;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalProject.DUKE;
import static seedu.address.testutil.TypicalProject.ORBITAL;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ProjectBuilder;

public class ProjectTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Project project = new ProjectBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> project.getTags().remove(0));
    }

    @Test
    public void isSameProject() {
        // same object -> returns true
        assertTrue(DUKE.isSame(DUKE));

        // null -> returns false
        assertFalse(DUKE.isSame(null));

        // different website and description -> returns false
        Project editedDuke = new ProjectBuilder(DUKE).withWebsite(VALID_WEBSITE_ORBITAL)
                .withDescription(VALID_DESCRIPTION_ORBITAL).build();
        assertFalse(DUKE.isSame(editedDuke));

        // different name -> returns false
        editedDuke = new ProjectBuilder(DUKE).withName(VALID_PROJECT_NAME_ORBITAL).build();
        assertFalse(DUKE.isSame(editedDuke));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Project orbitalCopy = new ProjectBuilder(ORBITAL).build();
        assertTrue(ORBITAL.equals(orbitalCopy));

        // same object -> returns true
        assertTrue(ORBITAL.equals(ORBITAL));

        // null -> returns false
        assertFalse(ORBITAL.equals(null));

        // different type -> returns false
        assertFalse(ORBITAL.equals(5));

        // different project -> returns false
        assertFalse(ORBITAL.equals(DUKE));

        // different name -> returns false
        Project editedOrbital = new ProjectBuilder(ORBITAL).withName(VALID_PROJECT_NAME_DUKE).build();
        assertFalse(ORBITAL.equals(editedOrbital));

        // different website -> returns false
        editedOrbital = new ProjectBuilder(ORBITAL).withWebsite(VALID_WEBSITE_DUKE).build();
        assertFalse(ORBITAL.equals(editedOrbital));

        // different time -> returns false
        editedOrbital = new ProjectBuilder(ORBITAL).withTime("05-2019").build();
        assertFalse(ORBITAL.equals(editedOrbital));

        // different tags -> returns false
        editedOrbital = new ProjectBuilder(ORBITAL).withTags(VALID_TAG_JAVA).build();
        assertFalse(ORBITAL.equals(editedOrbital));
    }

}
