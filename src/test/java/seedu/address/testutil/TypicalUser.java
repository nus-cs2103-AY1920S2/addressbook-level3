package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import seedu.address.model.UserData;
import seedu.address.model.person.User;

/**
 * A utility class containing a list of {@code User} objects to be used in tests.
 */
public class TypicalUser {

    public static final User ALICE = new UserBuilder()
            .withName("Alice Pauline")
            .withEmail("alice@example.com")
            .withPhone("94351253").build();

    // Manually added - User's details found in {@code CommandTestUtil}
    public static final User BOB = new UserBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).build();

    private TypicalUser() {
    } // prevents instantiation

    /**
     * Returns an {@code UserData} of the typical user.
     */
    public static UserData getTypicalUserData() {
        UserData userData = new UserData(ALICE);

        return userData;
    }
}
