package tatracker.model.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import tatracker.testutil.Assert;

public class RatingTest {

    @Test
    public void constructor_invalidValue_throwsIllegalArgumentException() {
        int invalidValue = 6;
        Assert.assertThrows(IllegalArgumentException.class, () -> new Rating(invalidValue));
    }

    @Test
    public void isValidRating() {
        // negative value
        assertFalse(Rating.isValidRating(-1), "Rating cannot have negative values");

        // zero value
        assertFalse(Rating.isValidRating(0), "Rating cannot be zero");

        // value too large
        assertFalse(Rating.isValidRating(6), "Rating must be between 1 to 5");

        // valid values
        assertTrue(Rating.isValidRating(1), "Rating must be between 1 to 5");
        assertTrue(Rating.isValidRating(2), "Rating must be between 1 to 5");
        assertTrue(Rating.isValidRating(3), "Rating must be between 1 to 5");
        assertTrue(Rating.isValidRating(4), "Rating must be between 1 to 5");
        assertTrue(Rating.isValidRating(5), "Rating must be between 1 to 5");
    }

}
