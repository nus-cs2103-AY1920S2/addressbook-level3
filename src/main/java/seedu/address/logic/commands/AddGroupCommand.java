package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;

/**
 * Represents the command to add a new group to CoderLifeInsights.
 */
public class AddGroupCommand extends Command {

    public static final String COMMAND_WORD = "add_group";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD
                    + ": Adds a new social group with given name and "
                    + "given (if any) person IDs as members"
                    + PREFIX_NAME
                    + "Name: "
                    + "["
                    + PREFIX_MEMBER
                    + " Members]...\n"
                    + "Example: "
                    + COMMAND_WORD
                    + " "
                    + PREFIX_NAME
                    + "SoC Friends "
                    + PREFIX_MEMBER
                    + "1 "
                    + PREFIX_MEMBER
                    + "3 "
                    + PREFIX_MEMBER
                    + "5";

    public static final String MESSAGE_SUCCESS = "New group added: %1$s";
    public static final String MESSAGE_DUPLICATE_GROUP =
            "Group with given name already exists. " + "Please try again with another name";
    public static final String MESSAGE_PERSON_DOES_NOT_EXIST = "Person(s) with given index does not exist";
    public static final String MESSAGE_DUPLICATE_MEMBERS = "Group contains duplicate member indexes. Please try again "
            + "with unique member indexes.";
    private final Group toAdd;

    public AddGroupCommand(Group group) {
        requireNonNull(group);
        this.toAdd = group;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasGroup(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_GROUP);
        }

        List<Person> lastShownList = model.getFilteredPersonList();
        ArrayList<Integer> members = toAdd.getMembers();
        Set<Integer> set = new HashSet<>(members);

        if (set.size() < members.size()) {
            throw new CommandException(MESSAGE_DUPLICATE_MEMBERS);
        }

        for (int i = 0; i < members.size(); i++) {
            int currIndex = members.get(i);
            if (currIndex > lastShownList.size() || currIndex <= 0) {
                throw new CommandException(MESSAGE_PERSON_DOES_NOT_EXIST);
            }
        }

        model.addGroup(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), ViewType.GROUPS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddGroupCommand)) {
            return false;
        }

        AddGroupCommand otherCommand = (AddGroupCommand) other;
        return this.toAdd.equals(otherCommand.toAdd);
    }
}
