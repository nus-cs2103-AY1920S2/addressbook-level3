package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_DUKE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_ORBITAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_RESUME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_NAME_DUKE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_NAME_ORBITAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_NAME_RESUME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_INTELLIJ;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_JAVA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TECH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_3;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEBSITE_DUKE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEBSITE_ORBITAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEBSITE_RESUME;

import seedu.address.model.item.Project;

/**
 * A utility class containing a list of {@code Project} objects to be used in tests.
 */
public class TypicalProject {
    // Manually added - project's details found in {@code CommandTestUtil}
    public static final Project ORBITAL = new ProjectBuilder().withName(VALID_PROJECT_NAME_ORBITAL)
            .withTime(VALID_TIME_1).withWebsite(VALID_WEBSITE_ORBITAL)
            .withDescription(VALID_DESCRIPTION_ORBITAL).withTags(VALID_TAG_TECH).build();
    public static final Project DUKE = new ProjectBuilder().withName(VALID_PROJECT_NAME_DUKE)
            .withTime(VALID_TIME_2).withWebsite(VALID_WEBSITE_DUKE).withDescription(VALID_DESCRIPTION_DUKE)
            .withTags(VALID_TAG_TECH, VALID_TAG_JAVA).build();
    public static final Project RESUME = new ProjectBuilder().withName(VALID_PROJECT_NAME_RESUME)
            .withTime(VALID_TIME_3).withWebsite(VALID_WEBSITE_RESUME)
            .withDescription(VALID_DESCRIPTION_RESUME).withTags(VALID_TAG_INTELLIJ).build();
}
