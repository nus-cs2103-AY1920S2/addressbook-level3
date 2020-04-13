package seedu.address.testutil;

import java.util.ArrayList;

import seedu.address.logic.commands.EditGroupCommand.EditGroupDescriptor;
import seedu.address.model.group.Group;
import seedu.address.model.person.Name;

/**
 * A utility class to build {@code EditGroupDescriptor} objects.
 */
public class EditGroupDescriptorBuilder {

    private EditGroupDescriptor descriptor;

    public EditGroupDescriptorBuilder(EditGroupDescriptor descriptor) {
        this.descriptor = new EditGroupDescriptor(descriptor);
    }

    public EditGroupDescriptorBuilder() {
        this.descriptor = new EditGroupDescriptor();
    }
    /**
     * Creates a {@code EditGroupDescriptor} based on {@code Group} instance provided.
     * @param group
     */
    public EditGroupDescriptorBuilder(Group group) {
        this.descriptor = new EditGroupDescriptor();
        this.descriptor.setName(group.getName());
        this.descriptor.setMemberIds(group.getMembers());
    }

    /**
     * Sets the {@code Name} of the {@code EditGroupDescriptor}
     */
    public EditGroupDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the member ids of the {@code EditGroupDescriptor}
     */
    public EditGroupDescriptorBuilder withMemberIds(ArrayList<Integer> ids) {
        descriptor.setMemberIds(ids);
        return this;
    }

    /**
     * Builds an edit group descriptor based on the descriptor in
     * the current {@code EditGroupDescriptorBuilder } instance.
      * @return
     */
    public EditGroupDescriptor build() {
        return descriptor;
    }

}
