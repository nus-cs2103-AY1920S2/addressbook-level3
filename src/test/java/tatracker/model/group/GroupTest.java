package tatracker.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tatracker.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static tatracker.logic.commands.CommandTestUtil.VALID_MATRIC_BOB;
import static tatracker.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static tatracker.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static tatracker.logic.commands.CommandTestUtil.VALID_RATING_BOB;
import static tatracker.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static tatracker.testutil.Assert.assertThrows;
import static tatracker.testutil.group.TypicalGroups.MANY_STUDENTS;
import static tatracker.testutil.group.TypicalGroups.MANY_STUDENTS_COPY;
import static tatracker.testutil.group.TypicalGroups.ONE_STUDENT;
import static tatracker.testutil.group.TypicalGroups.NO_STUDENTS;
import static tatracker.testutil.student.TypicalStudents.BOB;

import org.junit.jupiter.api.Test;

import tatracker.testutil.group.GroupBuilder;

public class GroupTest {
    @Test
    public void equals() {
        //Same object --> True
        assertTrue(ONE_STUDENT.equals(ONE_STUDENT));

        //Same group code --> True
        assertTrue(MANY_STUDENTS.equals(MANY_STUDENTS_COPY));

        //Different groups --> False
        assertFalse(NO_STUDENTS.equals(MANY_STUDENTS));
    }

    @Test
    public void setIdentifier() {
        Group group = new Group("G03");
        group.setIdentifier("G04");

        //Identifier value should change
        assertTrue(group.getIdentifier().equals("G04"));
    }

    @Test
    public void setGroupType() {
        Group group = new Group("G03", GroupType.LAB);
        group.setGroupType(GroupType.TUTORIAL);

        //GroupType should change
        assertTrue(group.getGroupType().equals(GroupType.TUTORIAL));
    }
}
