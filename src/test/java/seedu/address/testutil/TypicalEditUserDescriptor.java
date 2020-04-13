package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_CAP_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CAP_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DP_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DP_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FROM_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FROM_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GITHUB_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GITHUB_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAJOR_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAJOR_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TO_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TO_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNIVERSITY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNIVERSITY_BOB;

import seedu.address.logic.commands.edit.EditUserDescriptor;

/**
 * A utility class containing a list of {@code EditUserDescriptor} objects to be used in tests.
 */
public class TypicalEditUserDescriptor {
    public static final EditUserDescriptor AMY_DESC = new EditUserDescriptorBuilder()
            .withDisplayPicture(VALID_DP_AMY)
            .withName(VALID_NAME_AMY)
            .withDescription(VALID_DESCRIPTION_AMY)
            .withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withGithub(VALID_GITHUB_AMY)
            .withUniversity(VALID_UNIVERSITY_AMY)
            .withMajor(VALID_MAJOR_AMY)
            .withFrom(VALID_FROM_AMY)
            .withTo(VALID_TO_AMY)
            .withCap(VALID_CAP_AMY)
            .build();
    public static final EditUserDescriptor BOB_DESC = new EditUserDescriptorBuilder()
            .withDisplayPicture(VALID_DP_BOB)
            .withName(VALID_NAME_BOB)
            .withDescription(VALID_DESCRIPTION_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB)
            .withGithub(VALID_GITHUB_BOB)
            .withUniversity(VALID_UNIVERSITY_BOB)
            .withMajor(VALID_MAJOR_BOB)
            .withFrom(VALID_FROM_BOB)
            .withTo(VALID_TO_BOB)
            .withCap(VALID_CAP_BOB)
            .build();
}
