package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_RESUME_NAME_CE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RESUME_NAME_FILLED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RESUME_NAME_ME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RESUME_NAME_SE;
import static seedu.address.testutil.TypicalSkill.GIT;

import seedu.address.model.item.Resume;

/**
 * A utility class containing a list of {@code Resume} objects to be used in tests.
 */
public class TypicalResume {
    public static final Resume SE_RESUME = new ResumeBuilder().withName(VALID_RESUME_NAME_SE).build();
    public static final Resume ME_RESUME = new ResumeBuilder().withName(VALID_RESUME_NAME_ME).build();
    public static final Resume CE_RESUME = new ResumeBuilder().withName(VALID_RESUME_NAME_CE).build();
    public static final Resume FILLED_RESUME = new ResumeBuilder()
            .withName(VALID_RESUME_NAME_FILLED)
            .withInternship(TypicalInternship.GOOGLE)
            .withProject(TypicalProject.ORBITAL)
            .withSkill(GIT)
            .build();
}
