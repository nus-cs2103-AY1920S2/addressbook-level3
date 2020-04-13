package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalUser.ALICE;
import static seedu.address.testutil.TypicalUser.BOB;

import org.junit.jupiter.api.Test;

public class UserDataTest {

    private final UserData userData = new UserData(ALICE);

    @Test
    public void equals() {
        // same user data -> returns true
        assertTrue(userData.equals(userData));

        // copied user data -> returns true
        assertTrue(userData.equals(new UserData(userData)));

        //same user -> returns true
        assertTrue(userData.equals(new UserData(ALICE)));

        // different user -> returns false
        UserData otherUserData = new UserData(BOB);
        assertFalse(userData.equals(otherUserData));
    }

}
