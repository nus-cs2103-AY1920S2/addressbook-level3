package com.notably.model.block;

import static com.notably.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        assertFalse(Title.isValidTitle("-t Title-1")); // Contains '-' character
        assertFalse(Title.isValidTitle("Weird/Title")); // Contains '/' character
        assertFalse(Title.isValidTitle(".")); // Single period character = RelativePath to here
        assertFalse(Title.isValidTitle("..")); // Double period character = RelativePath to parent
        assertFalse(Title.isValidTitle("..1")); // Title starts wih period character

        // Valid title
        assertTrue(Title.isValidTitle("A")); // Single character title
        assertTrue(Title.isValidTitle("25")); // Numbers only
        assertTrue(Title.isValidTitle("weekly notes")); // Letters only (and non-leading whitespace)
        assertTrue(Title.isValidTitle("lecture 2")); // Alphanumeric characters
        assertTrue(Title.isValidTitle("Lecture Week 2")); // With uppercase characters
        assertTrue(Title.isValidTitle("2018 Year 1 Semester 1 CS2103 Week 2 Lecture Notes")); // Long title
        assertTrue(Title.isValidTitle("!\"#$%&'()*+,.:;<=>?@[\\]^_`{|}~")); // Accepted symbols
        assertTrue(Title.isValidTitle("1.2..3...4 1.2 .1")); // Use of period characters as long as not start of string
    }

    @Test
    public void equals_equalsIgnoreCase() {
        assertEquals(new Title("LECTURES"), new Title("lectures")); // All uppercase vs all lowercase
        assertEquals(new Title("WeEK 1 tUtorIal"), new Title("week 1 TUTORIAL")); // Mixed case
    }
}
