package seedu.address.logic.commands.commandDelete;

import static java.util.Objects.requireNonNull;

import java.util.List;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelGeneric.ModelObject;
import seedu.address.model.person.Person;

/**
 * Abstract class for the deletion of objects. All delete commands will extend off this one.
 */

public abstract class DeleteCommand extends UndoableCommand {
  // TODO: Consider deleting by ID instead of Index
  public static final String COMMAND_WORD = "delete";
}
