package cookbuddy.commons.util;

import static cookbuddy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ImageUtilTest {

    @Test
    public void isValidPlaceHolderImagePath() {
        // valid path
        assertTrue(ImageUtil.isPlaceHolderImage("/images/recipe_placeholder.jpg"));

        // invalid path
        assertFalse(ImageUtil.isPlaceHolderImage("a\0"));

        // null path -> throws NullPointerException
        assertThrows(NullPointerException.class, () -> ImageUtil.isPlaceHolderImage((String) null));
    }

}
