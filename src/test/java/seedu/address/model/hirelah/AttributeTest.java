package seedu.address.model.hirelah;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

/*
 * AttributeTest
 *
 * CS2103 AY19/20 Semester 2
 * Team Project
 * HireLah!
 *
 * 01 Mar 2020
 *
 */

/**
 * <p>AttributeTest class tests the methods implemented in
 * the Attribute class.</p>
 * @author AY1920S2-W15-2
 */

public class AttributeTest {
    @Test
    public void constructor_invalidAttributeName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Attribute("1234"));
    }

    @Test
    public void isValidAttributeName() {
        // invalid name
        assertFalse(Attribute.isValidAttributeName("^")); // only non-alphanumeric characters
        assertFalse(Attribute.isValidAttributeName("curiosity*")); // contains non-alphanumeric characters
        assertFalse(Attribute.isValidAttributeName("12345")); // numbers only
        assertFalse(Attribute.isValidAttributeName("toughness 2")); // alphanumeric characters
        assertFalse(Attribute.isValidAttributeName("Mario Lorenzo Jr the 2nd")); // long names

        // valid name
        assertTrue(Attribute.isValidAttributeName("toughness")); // alphabets only
        assertTrue(Attribute.isValidAttributeName("Mario Lorenzo")); // with capital letters

    }
}
