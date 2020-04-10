package team.easytravel.logic.commands.activity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.easytravel.logic.commands.CommandTestUtil.DESC_FINLAND;
import static team.easytravel.logic.commands.CommandTestUtil.DESC_JAPAN;
import static team.easytravel.logic.commands.CommandTestUtil.VALID_ACTIVITY_DURATION_JAPAN;
import static team.easytravel.logic.commands.CommandTestUtil.VALID_ACTIVITY_TITLE_JAPAN;
import static team.easytravel.logic.commands.CommandTestUtil.VALID_LOCATION_JAPAN;
import static team.easytravel.logic.commands.CommandTestUtil.VALID_TAG_EXPENSIVE;

import org.junit.jupiter.api.Test;

import team.easytravel.logic.commands.activity.EditActivityCommand.EditActivityDescriptor;
import team.easytravel.testutil.activity.EditActivityDescriptorBuilder;

public class EditActivityDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditActivityDescriptor descriptorWithSameValues = new EditActivityDescriptor(DESC_FINLAND);
        assertTrue(DESC_FINLAND.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_FINLAND.equals(DESC_FINLAND));

        // null -> returns false
        assertFalse(DESC_FINLAND.equals(null));

        // different types -> returns false
        assertFalse(DESC_FINLAND.equals(5));

        // different values -> returns false
        assertFalse(DESC_FINLAND.equals(DESC_JAPAN));

        // different name -> returns false
        EditActivityDescriptor editedJapan =
                new EditActivityDescriptorBuilder(DESC_FINLAND).withTitle(VALID_ACTIVITY_TITLE_JAPAN).build();
        assertFalse(DESC_FINLAND.equals(editedJapan));

        // different phone -> returns false
        editedJapan =
                new EditActivityDescriptorBuilder(DESC_FINLAND).withDuration(VALID_ACTIVITY_DURATION_JAPAN).build();
        assertFalse(DESC_FINLAND.equals(editedJapan));

        // different email -> returns false
        editedJapan = new EditActivityDescriptorBuilder(DESC_FINLAND).withLocation(VALID_LOCATION_JAPAN).build();
        assertFalse(DESC_FINLAND.equals(editedJapan));

        // different tags -> returns false
        editedJapan = new EditActivityDescriptorBuilder(DESC_FINLAND).withTags(VALID_TAG_EXPENSIVE).build();
        assertFalse(DESC_FINLAND.equals(editedJapan));
    }
}
