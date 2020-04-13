package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_NAME_CODE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_NAME_GIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_NAME_REACT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRONTEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TECH;

import seedu.address.model.item.Skill;
import seedu.address.model.item.field.Level;

/**
 * A utility class containing a list of {@code Skill} objects to be used in tests.
 */
public class TypicalSkill {
    public static final Skill GIT = new SkillBuilder()
            .withName(VALID_SKILL_NAME_GIT)
            .withLevel(Level.BASIC)
            .withTags(VALID_TAG_TECH)
            .build();
    public static final Skill REACT = new SkillBuilder()
            .withName(VALID_SKILL_NAME_REACT)
            .withLevel(Level.BASIC)
            .withTags(VALID_TAG_TECH, VALID_TAG_FRONTEND)
            .build();
    public static final Skill CODE = new SkillBuilder()
            .withName(VALID_SKILL_NAME_CODE)
            .withLevel(Level.BASIC)
            .withTags(VALID_TAG_TECH)
            .build();
}
