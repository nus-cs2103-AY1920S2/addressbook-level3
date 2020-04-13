package seedu.address.model.nusmodule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GradeTest {

    @Test
    void getGrade() {
        assertEquals(Grade.getGrade("A"), Grade.A);
        assertEquals(Grade.getGrade("B"), Grade.B);
        assertEquals(Grade.getGrade("C"), Grade.C);
        assertEquals(Grade.getGrade("F"), Grade.F);
        assertNull(Grade.getGrade("fewhfk"));
    }

    @Test
    void getGradeAfterSu() {
        assertEquals(Grade.getGradeAfterSu("A"), Grade.S);
        assertEquals(Grade.getGradeAfterSu("B"), Grade.S);
        assertEquals(Grade.getGradeAfterSu("C"), Grade.S);
        assertEquals(Grade.getGradeAfterSu("F"), Grade.U);
        assertEquals(Grade.getGradeAfterSu("dfsfsd"), Grade.U);
    }

    @Test
    void isSued() {
        assertFalse(Grade.A.isSued());
        assertTrue(Grade.S.isSued());
        assertTrue(Grade.U.isSued());
    }
}
