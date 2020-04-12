package seedu.address.testutil;

import seedu.address.logic.commands.commandAdd.AddAssignmentCommand;
import seedu.address.model.modelAssignment.Assignment;

import static seedu.address.logic.parser.CliSyntax.*;

public class AssignmentUtil {
    /**
     * Returns an add command string for adding the {@code Assignment}.
     */
    public static String getAddCommand(Assignment assignment) {
        return AddAssignmentCommand.COMMAND_WORD + " " + getAssignmentDetails(assignment);
    }

    /**
     * Returns the part of command string for the given {@code Assignment}'s details.
     */
    public static String getAssignmentDetails(Assignment assignment) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + assignment.getName().fullName + " ");
        sb.append(PREFIX_ASSIGNMENTID + assignment.getId().value + " ");
        sb.append(PREFIX_DEADLINE + assignment.getDeadline().value + " ");
        assignment.getTags().stream().forEach(
                s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }
    /*
    TODO: Need to update after edit command implemented
    public static String getEditAssignmentDescriptorDetails(EditCommand.EditAssignmentDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
    */
}
