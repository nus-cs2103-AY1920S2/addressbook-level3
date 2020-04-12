package seedu.address.logic.commands.commandDone;

import seedu.address.logic.commands.commandAssign.AssignDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;

public class DoneCommandFactory {
    public static DoneCommandBase getCommand(AssignDescriptor assignDescriptor) throws ParseException {
        final String DONE_FAILURE_MESSAGE =
                "done command has two use cases!" +
                        "\n1. For a particular assignment, marking a specific student who has submitted it as 'Done'" +
                        "\nProvide the following prefixes if so:" +
                        PREFIX_ASSIGNMENTID + " " + PREFIX_STUDENTID +
                        "\n2. For a particular assignment, marking all students who have submitted as 'Done " +
                        "\nProvide the following prefixes if so:" +
                        PREFIX_ASSIGNMENTID;

        DoneCommandBase outputCommand = null;

        if (DoneOneAssignmentOneStudent.isValidDescriptor(assignDescriptor)) {
            outputCommand = new DoneOneAssignmentOneStudent(assignDescriptor);
        } else {
            throw new ParseException(DONE_FAILURE_MESSAGE);
        }

        return outputCommand;
    }
}
