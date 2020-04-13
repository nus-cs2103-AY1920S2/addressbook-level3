package seedu.address.testutil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.edit.EditNoteDescriptor;
import seedu.address.model.item.field.Name;
import seedu.address.model.item.field.Time;
import seedu.address.model.tag.Tag;

/**
 * Returns a typical note descriptor.
 */
public class TypicalEditNoteDescriptor {
    public static final EditNoteDescriptor FINISH_HOMEWORK = new EditNoteDescriptorBuilder()
            .withName("Finish Homework")
            .withTime("03-2020")
            .withTags().build();

    public static final EditNoteDescriptor FINISH_CS_2103 = buildFinishCs();

    /**
     * Builds a EditNoteDescriptor for FINISH_CS_2103
     * @return
     */
    static EditNoteDescriptor buildFinishCs() {
        EditNoteDescriptor editNoteDescriptor = new EditNoteDescriptor();
        editNoteDescriptor.setName(new Name("Finish CS2103"));
        editNoteDescriptor.setTime(new Time("12-2020"));
        Set<Tag> tagSet = new HashSet<Tag>();
        String[] tags = {};
        tagSet.addAll(Arrays.stream(tags).map(Tag::new).collect(Collectors.toList()));
        editNoteDescriptor.setTags(tagSet);

        return editNoteDescriptor;
    }
}

