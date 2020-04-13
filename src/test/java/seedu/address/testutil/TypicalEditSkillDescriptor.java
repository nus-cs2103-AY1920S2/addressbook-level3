package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_NAME_GIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_NAME_REACT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRONTEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TECH;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.edit.EditSkillDescriptor;
import seedu.address.model.item.field.Level;
import seedu.address.model.item.field.Name;
import seedu.address.model.tag.Tag;

/**
 * Returns a typical skill descriptor.
 */
public class TypicalEditSkillDescriptor {
    public static final EditSkillDescriptor GIT = new EditSkillDescriptorBuilder()
            .withName(VALID_SKILL_NAME_GIT)
            .withLevel(Level.BASIC)
            .withTags(VALID_TAG_TECH).build();

    public static final EditSkillDescriptor REACT = buildReact();

    /**
     * Builds a EditSkillDescriptor for React.
     * @return
     */
    static EditSkillDescriptor buildReact() {
        EditSkillDescriptor editSkillDescriptor = new EditSkillDescriptor();
        editSkillDescriptor.setName(new Name(VALID_SKILL_NAME_REACT));
        editSkillDescriptor.setLevel(Level.BASIC);
        Set<Tag> tagSet = new HashSet<Tag>();
        String[] tags = {VALID_TAG_TECH, VALID_TAG_FRONTEND};
        tagSet.addAll(Arrays.stream(tags).map(Tag::new).collect(Collectors.toList()));
        editSkillDescriptor.setTags(tagSet);

        return editSkillDescriptor;
    }
}


