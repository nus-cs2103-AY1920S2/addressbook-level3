package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;

/**
 * Shows the user the places visited, activities done or last 5 events
 * with a chosen person from the Address Book.
 */
public class ViewGroupCommand extends Command {

    public static final String COMMAND_WORD = "view_group";

    public static final String KEYWORD_PLACE = "places";
    public static final String KEYWORD_ACTIVITY = "activities";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays insights about the places visited, "
            + "activities done, or time spent with the group identified "
            + "by the index number used in the displayed group list.\n"
            + "Parameters: INDEX (must be a positive integer) [INSIGHT_PARAMETER]\n"
            + "[INSIGHT_PARAMETER] can be [" + KEYWORD_PLACE + "], [" + KEYWORD_ACTIVITY
            + "].\n"
            + "Example: " + COMMAND_WORD + " " + KEYWORD_PLACE + " " + PREFIX_GROUP + "1 ";

    public static final String MESSAGE_PLACE = "Displaying places visited with ";
    public static final String MESSAGE_ACTIVITY = "Displaying activities done with ";
    public static final String MESSAGE_INVALID_PARAMETER = "The entered parameter is invalid.";

    public static final int TYPE_PERSON = 1;

    private final Index index;
    private final String parameter;
    private final int type;

    public ViewGroupCommand(Index index, String parameter, int type) {
        requireNonNull(index);
        requireNonNull(parameter);

        this.index = index;
        this.parameter = parameter;
        this.type = type;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Group> lastShownList = model.getFilteredGroupList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
        }

        Group groupToView = lastShownList.get(index.getZeroBased());

        if (parameter.equals(KEYWORD_PLACE)) {
            model.showGroupPlaceList(groupToView);
            return new CommandResult(MESSAGE_PLACE + groupToView.getName() + ".", ViewType.PLACES);
        } else if (parameter.equals(KEYWORD_ACTIVITY)) {
            model.showGroupActivityList(groupToView);
            return new CommandResult(MESSAGE_ACTIVITY + groupToView.getName() + ".", ViewType.ACTIVITIES);
        } else {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }
    }

    public Index getIndex() {
        return index;
    }

    public String getParam() {
        return parameter;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ViewGroupCommand)) {
            return false;
        }
        ViewGroupCommand e = (ViewGroupCommand) other;
        return this.index.equals(e.index)
                && this.parameter.equals(e.parameter)
                && this.type == e.type;
    }
}
