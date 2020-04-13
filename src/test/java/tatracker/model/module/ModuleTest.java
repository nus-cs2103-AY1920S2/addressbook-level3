//@@author aakanksha-rai

package tatracker.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tatracker.testutil.group.TypicalGroups.NO_STUDENTS;
import static tatracker.testutil.group.TypicalGroups.ONE_STUDENT;
import static tatracker.testutil.module.TypicalModules.CS2103T;
import static tatracker.testutil.module.TypicalModules.CS2103T_COPY;
import static tatracker.testutil.module.TypicalModules.CS3243;

import org.junit.jupiter.api.Test;

public class ModuleTest {

    @Test
    public void equals() {
        //Same object --> True
        assertTrue(CS2103T.equals(CS2103T));

        //Same group code --> True
        assertTrue(CS2103T.equals(CS2103T_COPY));

        //Different groups --> False
        assertFalse(CS2103T.equals(CS3243));
    }

    @Test
    public void setName() {
        Module module = new Module("CS2100", "Comp Organisation");
        module.setName("Computer Organisation");

        //Identifier value should change
        assertTrue(module.getName().equals("Computer Organisation"));
    }


    @Test
    public void hasGroup() {
        assertTrue(CS2103T.hasGroup(ONE_STUDENT));
    }

    @Test
    public void getIdentifier() {
        assertEquals("CS2103T", CS2103T.getIdentifier());
    }

    @Test
    public void getName() {
        assertEquals("Software Engineering", CS2103T.getName());
    }

    @Test
    public void getGroup() {
        assertEquals(ONE_STUDENT, CS2103T.getGroup("G03"));
    }

    @Test
    public void addGroup() {
        Module module = new Module("CS2103", "Software Engineering");
        module.addGroup(NO_STUDENTS);
        assertTrue(module.hasGroup(NO_STUDENTS));
    }

    @Test
    public void deleteGroup() {
        Module module = new Module("CS2103", "Software Engineering");
        module.addGroup(NO_STUDENTS);

        module.deleteGroup(NO_STUDENTS);
        assertFalse(module.hasGroup(NO_STUDENTS));

    }

}
