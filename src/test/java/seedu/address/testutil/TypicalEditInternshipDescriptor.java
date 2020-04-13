package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_FROM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FROM_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERNSHIP_LEARN_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERNSHIP_NAME_NINJAVAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERNSHIP_NAME_PAYPAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERNSHIP_NINJA_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERNSHIP_ROLE_BACKEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERNSHIP_ROLE_NINJA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BACKEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TECH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_UX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TO_2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.edit.EditInternshipDescriptor;
import seedu.address.model.item.field.Description;
import seedu.address.model.item.field.Name;
import seedu.address.model.item.field.Role;
import seedu.address.model.item.field.Time;
import seedu.address.model.tag.Tag;

/**
 * Returns a typical edit internship descriptor.
 */
public class TypicalEditInternshipDescriptor {
    public static final EditInternshipDescriptor PAY_PAL = new EditInternshipDescriptorBuilder()
            .withName(VALID_INTERNSHIP_NAME_PAYPAL)
            .withRole(VALID_INTERNSHIP_ROLE_BACKEND)
            .withFrom(VALID_FROM)
            .withTo(VALID_TO)
            .withDescription(VALID_INTERNSHIP_LEARN_DESCRIPTION)
            .withTags(VALID_TAG_BACKEND, VALID_TAG_SE, VALID_TAG_TECH).build();

    public static final EditInternshipDescriptor NINJA_VAN = buildNinjaVan();

    /**
     * Build an internship descriptor for ninja van.
     * @return
     */
    static EditInternshipDescriptor buildNinjaVan() {
        EditInternshipDescriptor editInternshipDescriptor = new EditInternshipDescriptor();
        editInternshipDescriptor.setName(new Name(VALID_INTERNSHIP_NAME_NINJAVAN));
        editInternshipDescriptor.setRole(new Role(VALID_INTERNSHIP_ROLE_NINJA));
        editInternshipDescriptor.setFrom(new Time(VALID_FROM_2));
        editInternshipDescriptor.setTo(new Time(VALID_TO_2));
        editInternshipDescriptor.setDescription(new Description(VALID_INTERNSHIP_NINJA_DESCRIPTION));
        Set<Tag> tagSet = new HashSet<Tag>();
        String[] tags = {VALID_TAG_UX, VALID_TAG_TECH};
        tagSet.addAll(Arrays.stream(tags).map(Tag::new).collect(Collectors.toList()));
        editInternshipDescriptor.setTags(tagSet);

        return editInternshipDescriptor;
    }
}

