package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_DUKE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_ORBITAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_NAME_DUKE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_NAME_ORBITAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_JAVA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TECH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEBSITE_DUKE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEBSITE_ORBITAL;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.edit.EditProjectDescriptor;
import seedu.address.model.item.field.Description;
import seedu.address.model.item.field.Name;
import seedu.address.model.item.field.Time;
import seedu.address.model.item.field.Website;
import seedu.address.model.tag.Tag;

/**
 * Returns a typical project descriptor.
 */
public class TypicalEditProjectDescriptor {
    public static final EditProjectDescriptor ORBITAL = new EditProjectDescriptorBuilder()
            .withName(VALID_PROJECT_NAME_ORBITAL)
            .withTime(VALID_TIME_1)
            .withWebsite(VALID_WEBSITE_ORBITAL)
            .withDescription(VALID_DESCRIPTION_ORBITAL)
            .withTags(VALID_TAG_TECH).build();

    public static final EditProjectDescriptor DUKE = buildDuke();

    /**
     * Builds a EditProjectDescriptor for Duke.
     * @return
     */
    static EditProjectDescriptor buildDuke() {
        EditProjectDescriptor editProjectDescriptor = new EditProjectDescriptor();
        editProjectDescriptor.setName(new Name(VALID_PROJECT_NAME_DUKE));
        editProjectDescriptor.setTime(new Time(VALID_TIME_2));
        editProjectDescriptor.setWebsite(new Website(VALID_WEBSITE_DUKE));
        editProjectDescriptor.setDescription(new Description(VALID_DESCRIPTION_DUKE));
        Set<Tag> tagSet = new HashSet<Tag>();
        String[] tags = {VALID_TAG_TECH, VALID_TAG_JAVA};
        tagSet.addAll(Arrays.stream(tags).map(Tag::new).collect(Collectors.toList()));
        editProjectDescriptor.setTags(tagSet);

        return editProjectDescriptor;
    }
}


