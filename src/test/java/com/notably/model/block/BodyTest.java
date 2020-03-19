package com.notably.model.block;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.notably.testutil.Assert.assertThrows;

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
        assertTrue(Body.isValidBody("25110048")); // Numbers only
        assertTrue(Body.isValidBody("lorem ipsum dolor sit amet, consectetur adipiscing elit")); // Letters only
        assertTrue(Body.isValidBody("lecture 2 begins with 4 examples")); // Alphanumeric characters
        assertTrue(Body.isValidBody("Lecture Week 2")); // With uppercase characters
        assertTrue(Body.isValidBody("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")); // With non-alphanumeric characters
        assertTrue(Body.isValidBody("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")); // Long body
    }
}
