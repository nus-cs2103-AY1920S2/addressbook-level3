package seedu.zerotoone.model.exercise;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.zerotoone.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ExerciseNameTest {

    // @Test
    // public void constructor_null_throwsNullPointerException() {
    //     assertThrows(NullPointerException.class, () -> new Name(null));
    // }

    // @Test
    // public void constructor_invalidName_throwsIllegalArgumentException() {
    //     String invalidName = "";
    //     assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    // }

    // @Test
    // public void isValidName() {
    //     // null name
    //     assertThrows(NullPointerException.class, () -> Name.isValidName(null));

    //     // invalid name
    //     assertFalse(Name.isValidName("")); // empty string
    //     assertFalse(Name.isValidName(" ")); // spaces only
    //     assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
    //     assertFalse(Name.isValidName("deadlift*")); // contains non-alphanumeric characters

    //     // valid name
    //     assertTrue(Name.isValidName("jumping jacks")); // alphabets only
    //     assertTrue(Name.isValidName("2 directional elbow circles")); // alphanumeric characters
    //     assertTrue(Name.isValidName("Bench Press")); // with capital letters
    //     assertTrue(Name.isValidName("Chest supported and seated cable row")); // long names
    // }
}
