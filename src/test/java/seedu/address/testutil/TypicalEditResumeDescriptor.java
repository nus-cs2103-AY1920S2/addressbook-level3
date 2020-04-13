package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_RESUME_NAME_ME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RESUME_NAME_SE;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.edit.EditResumeDescriptor;
import seedu.address.model.item.field.Name;
import seedu.address.model.tag.Tag;

/**
 * Returns a typical resume descriptor.
 */
public class TypicalEditResumeDescriptor {
    public static final EditResumeDescriptor ME_RESUME = new EditResumeDescriptorBuilder()
            .withName(VALID_RESUME_NAME_ME)
            .withTags().build();

    public static final EditResumeDescriptor SE_RESUME = buildSe();

    /**
     * Builds an EditResumeDescriptor for SE_RESUME.
     * @return
     */
    static EditResumeDescriptor buildSe() {
        EditResumeDescriptor editResumeDescriptor = new EditResumeDescriptor();
        editResumeDescriptor.setName(new Name(VALID_RESUME_NAME_SE));
        Set<Tag> tagSet = new HashSet<Tag>();
        String[] tags = {};
        tagSet.addAll(Arrays.stream(tags).map(Tag::new).collect(Collectors.toList()));
        editResumeDescriptor.setTags(tagSet);

        return editResumeDescriptor;
    }
}


