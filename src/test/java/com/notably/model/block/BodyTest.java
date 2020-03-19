package com.notably.model.block;

import static com.notably.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BodyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Body(null));
    }

    @Test
    public void isValidBody() {
        // Null body
        assertThrows(NullPointerException.class, () -> Body.isValidBody(null));

        // Invalid body
        assertFalse(Body.isValidBody(" ")); //Single whitespace character
        assertFalse(Body.isValidBody("     ")); //Multiple whitespace character
        // TODO: By v1.3, identify any narrowing scopes for Body content, if body is to contain markdown

        // Valid body
        assertTrue(Body.isValidBody("")); // Empty body (for Body initialization without content)
        assertTrue(Body.isValidBody("01032020")); // Numbers only
        assertTrue(Body.isValidBody("This is ")); // Letters only (Upper and lower case)
        assertTrue(Body.isValidBody("lecture 2 begins with 4 examples")); // Alphanumeric characters
        assertTrue(Body.isValidBody("  Today is another day")); // Leading whitespace
        assertTrue(Body.isValidBody("Lorem ipsum dolor sit amet, consectetur 44 adipiscing elit, sed do "
            + "eiusmod! Tempor & incididunt ut labore et aliqua.")); // Long body with non-alphanumeric characters
    }
}
