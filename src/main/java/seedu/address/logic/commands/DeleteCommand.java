package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete_person";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD
                    + ": Deletes the person identified by the index number used in the displayed person list.\n"
                    + "Parameters: INDEX (must be a positive integer)\n"
                    + "Example: "
                    + COMMAND_WORD
                    + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private static final Logger logger = LogsCenter.getLogger(DeleteCommand.class);

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());

        List<Group> lastGroupList = model.getFilteredGroupList();

        for (Group group: lastGroupList) {
            List<Integer> currMembers = group.getMembers();
            logger.info("Index to remove is " + targetIndex.getZeroBased());
            boolean groupChanged = currMembers.remove(Integer.valueOf(targetIndex.getOneBased()));
            if (groupChanged) {
                Group editedGroup = new Group(group.getName(), group.getPlaceList(),
                        group.getActivityList(), group.getTimeList());
                editedGroup.setTimeSpent(group.getTimeSpent());
                ArrayList<Integer> newMembers = new ArrayList<>(currMembers);
                editedGroup.setMemberIDs(newMembers);
                editedGroup.setEventIDs(group.getEvents());
                model.setGroup(group, editedGroup);
            }
        }

        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
