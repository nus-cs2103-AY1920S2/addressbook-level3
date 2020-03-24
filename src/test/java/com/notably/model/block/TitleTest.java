package com.notably.model.block;

import static com.notably.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TitleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Title(null));
    }

    @Test
    public void constructor_invalidTitle_throwsIllegalArgumentException() {
        String invalidTitle = "";
        assertThrows(IllegalArgumentException.class, () -> new Title(invalidTitle));
    }

    @Test
    public void isValidTitle() {
        // Null title
        assertThrows(NullPointerException.class, () -> Title.isValidTitle(null));

        // Invalid title
        assertFalse(Title.isValidTitle("")); // Empty string
        assertFalse(Title.isValidTitle(" ")); // Spaces only
        assertFalse(Title.isValidTitle(" Week 2 Lecture")); // Leading whitespace
        assertFalse(Title.isValidTitle("Week 2 Lecture ")); // Trailing whitespace
        assertFalse(Title.isValidTitle("Week 2: Lecture")); // Contains non-alphanumeric characters

        // Valid title
        assertTrue(Title.isValidTitle("25")); // Numbers only
        assertTrue(Title.isValidTitle("weekly notes")); // Letters only (and non-leading whitespace)
        assertTrue(Title.isValidTitle("lecture 2")); // Alphanumeric characters
        assertTrue(Title.isValidTitle("Lecture Week 2")); // With uppercase characters
        assertTrue(Title.isValidTitle("2018 Year 1 Semester 1 CS2103 Week 2 Lecture Notes")); // Long title
    }
}
