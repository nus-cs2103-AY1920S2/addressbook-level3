package team.easytravel.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static team.easytravel.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import team.easytravel.commons.exceptions.IllegalValueException;
import team.easytravel.model.util.attributes.tag.Tag;

class JsonAdaptedTagTest {

    @Test
    public void nullConstructor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()-> new JsonAdaptedTag((Tag) null));
    }

    @Test
    public void tagConstructor() throws IllegalValueException {
        assertEquals(("hello"), new JsonAdaptedTag(new Tag("hello")).getTagName());
    }

    @Test
    public void invalidTagName_throwsIllegalValueException() {
        assertThrows(IllegalValueException.class, ()-> new JsonAdaptedTag("%^%^").toModelType());
        assertThrows(IllegalValueException.class, ()-> new JsonAdaptedTag("H3||o").toModelType());
    }

    @Test
    public void toModelType() throws IllegalValueException {
        assertEquals(new Tag("Test"), new JsonAdaptedTag("Test").toModelType());
        assertEquals(new Tag("testing"), new JsonAdaptedTag("testing").toModelType());

        // Event where caps are different tags.
        assertNotEquals(new Tag("Testing"), new JsonAdaptedTag("testing").toModelType());

    }
}
