package seedu.address.testutil;

import seedu.address.logic.EditAssignmentDescriptor;
import seedu.address.logic.EditPersonDescriptor;
import seedu.address.model.assignment.*;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EditAssignmentDescriptorBuilder {
    private EditAssignmentDescriptor descriptor;

    public EditAssignmentDescriptorBuilder() {
        descriptor = new EditAssignmentDescriptor();
    }

    public EditAssignmentDescriptorBuilder(EditAssignmentDescriptor descriptor) {
        this.descriptor = new EditAssignmentDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditAssignmentDescriptor} with fields containing {@code assignment}'s details
     */
    public EditAssignmentDescriptorBuilder(Assignment assignment) {
        descriptor = new EditAssignmentDescriptor();
        descriptor.setTitle(assignment.getTitle());
        descriptor.setWorkload(assignment.getWorkload());
        descriptor.setDeadline(assignment.getDeadline());
        descriptor.setStatus(assignment.getStatus());
    }

    /**
     * Sets the {@code Title} of the {@code EditAssignmentDescriptor} that we are building.
     */
    public EditAssignmentDescriptorBuilder withTitle(String title) {
        descriptor.setTitle(new Title(title));
        return this;
    }

    /**
     * Sets the {@code Workload} of the {@code EditAssignmentDescriptor} that we are building.
     */
    public EditAssignmentDescriptorBuilder withWorkload(String workload) {
        descriptor.setWorkload(new Workload(workload));
        return this;
    }

    /**
     * Sets the {@code Deadline} of the {@code EditAssignmentDescriptor} that we are building.
     */
    public EditAssignmentDescriptorBuilder withDeadline(String deadline) {
        descriptor.setDeadline(new Deadline(deadline));
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code EditAssignmentDescriptor} that we are building.
     */
    public EditAssignmentDescriptorBuilder withStatus(String status) {
        descriptor.setStatus(new Status(status));
        return this;
    }

    public EditAssignmentDescriptor build() {
        return descriptor;
    }
}
