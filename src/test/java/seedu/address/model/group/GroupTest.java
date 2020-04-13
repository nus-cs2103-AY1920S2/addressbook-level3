package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.GroupBuilder;
import seedu.address.testutil.TypicalGroups;

public class GroupTest {

    private Group soc = TypicalGroups.SOC;
    private Group rc = TypicalGroups.RC;

    @Test
    public void equals() {
        //same object -> true
        assertTrue(soc.equals(soc));

        //same values -> true
        Group socCopy = new GroupBuilder().build();
        assertTrue(soc.equals(socCopy));

        // different values -> false
        assertFalse(soc.equals(rc));
    }

    @Test
    public void toString_test() {
        //without member IDs.
        String socExpectedRepresentation = "Name: SoC Friends";
        assertTrue(soc.toString().equals(socExpectedRepresentation));

        //with member IDs.
        String rcExpectedRepresentation = "Name: RC Friends. Members: 1, 2, 3";
        rc.setMemberIDs(new ArrayList<>(Arrays.asList(1, 2, 3)));
        assertTrue(rc.toString().equals(rcExpectedRepresentation));
    }
}

