package tatracker.testutil;

import static tatracker.testutil.student.TypicalStudents.MATRIC_ALICE;
import static tatracker.testutil.student.TypicalStudents.MATRIC_BENSON;
import static tatracker.testutil.student.TypicalStudents.MATRIC_CARL;

import tatracker.commons.core.index.Index;
import tatracker.model.student.Matric;

/**
 * A utility class containing a list of {@code Index} objects to be used in tests.
 */
public class TypicalIndexes {
    public static final Index INDEX_FIRST_STUDENT = Index.fromOneBased(1);
    public static final Index INDEX_SECOND_STUDENT = Index.fromOneBased(2);
    public static final Index INDEX_THIRD_STUDENT = Index.fromOneBased(3);

    public static final Matric MATRIC_FIRST_STUDENT = MATRIC_ALICE;
    public static final Matric MATRIC_SECOND_STUDENT = MATRIC_BENSON;
    public static final Matric MATRIC_THIRD_STUDENT = MATRIC_CARL;
    public static final Matric MATRIC_NONEXISTENT = new Matric("A9999999J");

    public static final Index INDEX_FIRST_SESSION = Index.fromOneBased(1);
    public static final Index INDEX_SECOND_SESSION = Index.fromOneBased(2);
    public static final Index INDEX_THIRD_SESSION = Index.fromOneBased(3);
}
