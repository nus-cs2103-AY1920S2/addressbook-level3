//@@author aakanksha-rai

package tatracker.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tatracker.testutil.group.TypicalGroups.MANY_STUDENTS;
import static tatracker.testutil.group.TypicalGroups.MANY_STUDENTS_COPY;
import static tatracker.testutil.group.TypicalGroups.NO_STUDENTS;
import static tatracker.testutil.group.TypicalGroups.ONE_STUDENT;

import org.junit.jupiter.api.Test;

import tatracker.model.student.Matric;
import tatracker.testutil.student.TypicalStudents;

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

    @Test
    public void sortStudentsAlphabetically() {
        Group group = MANY_STUDENTS;
        group.sortStudentsAlphabetically();

        //First Student
        assertEquals(group.get(0), TypicalStudents.ALICE);

        //Middle Student
        assertEquals(group.get(2), TypicalStudents.DANIEL);

        //Last Student
        assertEquals(group.get(5), TypicalStudents.GEORGE);
    }

    @Test
    public void sortStudentsByRatingAscending() {
        Group group = MANY_STUDENTS;
        group.sortStudentsByRatingAscending();

        //First Student
        assertEquals(group.get(0), TypicalStudents.ALICE);

        //Middle Student
        assertEquals(group.get(2), TypicalStudents.BENSON);

        //Last Student
        assertEquals(group.get(5), TypicalStudents.ELLE);
    }

    @Test
    public void sortStudentsByRatingDescending() {
        Group group = MANY_STUDENTS;
        group.sortStudentsByRatingDescending();

        //First Student
        assertEquals(group.get(0), TypicalStudents.ELLE);

        //Middle Student
        assertEquals(group.get(2), TypicalStudents.BENSON);

        //Last Student
        assertEquals(group.get(5), TypicalStudents.FIONA);
    }

    @Test
    public void sortStudentsByMatricNumber() {
        Group group = MANY_STUDENTS;
        group.sortStudentsByMatricNumber();

        //First Student
        assertEquals(group.get(0), TypicalStudents.BENSON);

        //Middle Student
        assertEquals(group.get(2), TypicalStudents.ELLE);

        //Last Student
        assertEquals(group.get(5), TypicalStudents.FIONA);
    }

    @Test
    public void hasStudent() {
        //First student in student list
        assertTrue(MANY_STUDENTS.hasStudent(TypicalStudents.ALICE));

        //Middle of the list
        assertTrue(MANY_STUDENTS.hasStudent(TypicalStudents.ELLE));

        //End of the list
        assertTrue(MANY_STUDENTS.hasStudent(TypicalStudents.DANIEL));

        //Not in the list
        assertFalse(MANY_STUDENTS.hasStudent(TypicalStudents.BOB));

        //List has no students
        assertFalse(NO_STUDENTS.hasStudent(TypicalStudents.ALICE));

        //Only one in the list
        assertTrue(ONE_STUDENT.hasStudent(TypicalStudents.ALICE));

        //Not in the list
        assertFalse(ONE_STUDENT.hasStudent(TypicalStudents.BOB));
    }

    @Test
    public void getIdentifier() {
        assertEquals("G03", ONE_STUDENT.getIdentifier());
    }

    @Test
    public void getGroupType() {
        assertEquals(GroupType.LAB, ONE_STUDENT.getGroupType());
        assertEquals(GroupType.TUTORIAL, NO_STUDENTS.getGroupType());
        assertEquals(GroupType.OTHER, MANY_STUDENTS.getGroupType());
    }

    @Test
    public void getStudent() {
        assertEquals(TypicalStudents.DANIEL, MANY_STUDENTS.getStudent(new Matric("A0192154M")));
    }

    @Test
    public void addStudent() {
        Group group = new Group("G03");
        group.addStudent(TypicalStudents.BOB);
        assertTrue(group.hasStudent(TypicalStudents.BOB));
        group.addStudent(TypicalStudents.ALICE);
        assertTrue(group.hasStudent(TypicalStudents.ALICE));
        group.addStudent(TypicalStudents.DANIEL);
        assertTrue(group.hasStudent(TypicalStudents.BOB));
        assertTrue(group.hasStudent(TypicalStudents.ALICE));
        assertTrue(group.hasStudent(TypicalStudents.DANIEL));
    }

    @Test
    public void deleteStudent() {
        Group group = new Group("G03");
        group.addStudent(TypicalStudents.BOB);
        group.addStudent(TypicalStudents.ALICE);
        group.addStudent(TypicalStudents.DANIEL);

        group.deleteStudent(TypicalStudents.BOB);
        assertFalse(group.hasStudent(TypicalStudents.BOB));

        group.deleteStudent(TypicalStudents.DANIEL);
        assertFalse(group.hasStudent(TypicalStudents.DANIEL));
        assertTrue(group.hasStudent(TypicalStudents.ALICE));

        group.deleteStudent(TypicalStudents.ALICE);
        assertFalse(group.hasStudent(TypicalStudents.ALICE));
    }
}
