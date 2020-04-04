package seedu.address.logic.commands.commandDone;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandAssign.AssignDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;

import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;

public class DoneOneAssignmentOneStudent extends DoneCommandBase {
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks an assignment as done for a student. "
            + "Parameters: "
            + PREFIX_ASSIGNMENTID + "ASSIGNMENTID "
            + PREFIX_STUDENTID + "STUDENTID"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ASSIGNMENTID + "100"
            + PREFIX_STUDENTID + "329";

    public static final String MESSAGE_INVALID_STUDENT_ID = "There is no such Student that with ID";
    public static final String MESSAGE_INVALID_ASSIGNMENT_ID = "There is no such Assignment that with ID";
    public static final String MESSAGE_INVALID_STUDENT_ASSIGNMENT = "Student does not have this assignment currently!";
    public static final String MESSAGE_SUCCESS = "Successfully marked Assignment %s (%s) as done by Student %s (%s)";

    /*
        AssignDescriptor used as it takes in two prefixes just like assign commands. Essentially performs the same functionality
     **/
    private final AssignDescriptor assignDescriptor;

    public DoneOneAssignmentOneStudent(AssignDescriptor assignDescriptor) {
        requireNonNull(assignDescriptor);

        this.assignDescriptor = assignDescriptor;
    }

    public static boolean isValidDescriptor(AssignDescriptor assignDescriptor) {
        Set<Prefix> allAssignEntities = assignDescriptor.getAllAssignKeys();
        int numOfKeys = allAssignEntities.size();
        return allAssignEntities.contains(PREFIX_STUDENTID) && allAssignEntities.contains(PREFIX_ASSIGNMENTID) && numOfKeys == 2;
    }


    @Override
    protected CommandResult executeUndoableCommand(Model model) throws CommandException {
        return null;
    }

    @Override
    protected void generateOppositeCommand() throws CommandException {

    }
}
