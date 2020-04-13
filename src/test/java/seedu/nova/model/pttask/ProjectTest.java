package seedu.nova.model.pttask;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.nova.model.progresstracker.Project;

public class ProjectTest {
    @Test
    public void isValidProject() {
        assertFalse(Project.isValidProject(""));
        assertFalse(Project.isValidProject("ipp"));
        assertFalse(Project.isValidProject("tpp"));

        assertTrue(Project.isValidProject("ip"));
        assertTrue(Project.isValidProject("IP"));
        assertTrue(Project.isValidProject("iP"));
        assertTrue(Project.isValidProject("Ip"));
        assertTrue(Project.isValidProject("tp"));
        assertTrue(Project.isValidProject("TP"));
        assertTrue(Project.isValidProject("tP"));
        assertTrue(Project.isValidProject("Tp"));
    }
}
