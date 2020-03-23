package fithelper.model.entry;

import static fithelper.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TypeTest {
    @Test
    public void constructornullthrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void isValidType() {
        // null type
        //assertThrows(NullPointerException.class, () -> Type.isValidType(null));

        // invalid type
        //assertFalse(Type.isValidType(" ")); // spaces only
        assertFalse(Type.isValidType("sport")); // singular sport
        assertFalse(Type.isValidType("Sports")); // upper case sports
        assertFalse(Type.isValidType("Food")); // upper case food
        assertFalse(Type.isValidType("Not sure")); // other undefined type

        // valid type
        assertTrue(Type.isValidType("food")); // food type
        assertTrue(Type.isValidType("sports")); // sports type
    }
}
