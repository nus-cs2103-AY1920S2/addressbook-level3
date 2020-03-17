package seedu.address.model.comment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class CommentTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Comment(null));
    }

    @Test
    public void constructor_invalidComment_throwsIllegalArgumentException() {
        String invalidComment = "";
        assertThrows(IllegalArgumentException.class, () -> new Comment(invalidComment));
    }

    @Test
    public void isValidAddress() {
        // null address
        assertThrows(NullPointerException.class, () -> Comment.isValidComment(null));

        // invalid addresses
        assertFalse(Comment.isValidComment("")); // empty string
        assertFalse(Comment.isValidComment(" ")); // spaces only

        // valid addresses
        assertTrue(Comment.isValidComment("Leave at the riser"));
        assertTrue(Comment.isValidComment("-")); // one character
        assertTrue(Comment.isValidComment("Please give my neighbour! Her contact is 94561546.")); // long address
    }
}
