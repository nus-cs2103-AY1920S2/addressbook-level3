package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.commands.edit.EditSkillDescriptor;
import seedu.address.model.item.Skill;
import seedu.address.model.item.field.Level;
import seedu.address.model.item.field.Name;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building EditSkillDescriptor objects.
 */
public class EditSkillDescriptorBuilder {
    private EditSkillDescriptor descriptor;

    public EditSkillDescriptorBuilder() {
        descriptor = new EditSkillDescriptor();
    }

    public EditSkillDescriptorBuilder(EditSkillDescriptor descriptor) {
        this.descriptor = new EditSkillDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditSkillDescriptor} with fields containing {@code Skill}'s details
     */
    public EditSkillDescriptorBuilder(Skill skill) {
        descriptor = new EditSkillDescriptor();
        descriptor.setName(skill.getName());
        descriptor.setLevel(skill.getLevel());
        descriptor.setTags(skill.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditSkillDescriptor} that we are building.
     */
    public EditSkillDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Level} of the {@code EditSkillDescriptor} that we are building.
     */
    public EditSkillDescriptorBuilder withLevel(Level level) {
        descriptor.setLevel(level);
        return this;
    }

    /**
     * Sets the {@code Tag} of the {@code EditSkillDescriptor} that we are building.
     */
    public EditSkillDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = new HashSet<>();
        tagSet = SampleDataUtil.getTagSet(tags);
        descriptor.setTags(tagSet);
        return this;
    }

    public EditSkillDescriptor build() {
        return descriptor;
    }
}
