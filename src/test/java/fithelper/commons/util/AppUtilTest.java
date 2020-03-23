package fithelper.commons.util;

import static fithelper.testutil.Assert.assertThrows;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class AppUtilTest {

    @Test
    public void getImageexitingImage() {
        assertNotNull(AppUtil.getImage("/images/address_book_32.png"));
    }

    @Test
    public void getImagenullGiventhrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> AppUtil.getImage(null));
    }

    @Test
    public void checkArgumentfalseWithoutErrorMessagethrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> AppUtil.checkArgument(false));
    }

    @Test
    public void checkArgumentfalseWithErrorMessagethrowsIllegalArgumentException() {
        String errorMessage = "error message";
        assertThrows(IllegalArgumentException.class, errorMessage, () -> AppUtil.checkArgument(false, errorMessage));
    }
}
