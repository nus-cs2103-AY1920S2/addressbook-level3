package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GROUPS;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Time;

/**
 * Represents the command to edit a group in Coder Life Insights.
 */
public class EditGroupCommand extends Command {

    public static final String COMMAND_WORD = "edit_group";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD
                    + ": Edits the details of the group identified "
                    + "by the index number used in the displayed group list. "
                    + "Existing values will be overwritten by the input values.\n"
                    + "Parameters: INDEX (must be a positive integer) "
                    + "["
                    + PREFIX_NAME
                    + "NAME] "
                    + "["
                    + PREFIX_MEMBER
                    + "MEMBERS]...\n"
                    + "Example: "
                    + COMMAND_WORD
                    + " 1 "
                    + PREFIX_NAME
                    + "SoC Friends "
                    + PREFIX_MEMBER
                    + "1 "
                    + PREFIX_MEMBER
                    + "7";

    public static final String MESSAGE_EDIT_GROUP_SUCCESS = "Edited Group: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one edit field must be provided to edit a group";
    public static final String MESSAGE_DUPLICATE_GROUP = "This group already exists in Coder Life Insights";
    public static final String MESSAGE_DUPLICATE_MEMBERS = "Group contains duplicate member indexes. Please try again "
            + "with unique member indexes.";
    public static final String MESSAGE_PERSON_DOES_NOT_EXIST = "Person(s) with given index does not exist";
    private final Index index;
    private final EditGroupDescriptor editGroupDescriptor;

    public EditGroupCommand(Index index, EditGroupDescriptor editGroupDescriptor) {
        requireNonNull(index);
        requireNonNull(editGroupDescriptor);

        this.index = index;
        this.editGroupDescriptor = editGroupDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Group> lastShownList = model.getFilteredGroupList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
        }

        Group groupToEdit = lastShownList.get(index.getZeroBased());

        //check if member indexes are valid
        List<Person> lastPersonList = model.getFilteredPersonList();

        if (editGroupDescriptor.getMemberIds().isPresent()) {
            ArrayList<Integer> members = editGroupDescriptor.getMemberIds().get();
            Set<Integer> set = new HashSet<>(members);

            if (set.size() < members.size()) {
                throw new CommandException(MESSAGE_DUPLICATE_MEMBERS);
            }

            for (int i = 0; i < members.size(); i++) {
                int currIndex = members.get(i);
                if (currIndex > lastPersonList.size() || currIndex <= 0) {
                    throw new CommandException(MESSAGE_PERSON_DOES_NOT_EXIST);
                }
            }
        }

        Group editedGroup = createEditedGroup(groupToEdit, editGroupDescriptor);

        if (!groupToEdit.equals(editedGroup) && model.hasGroup(editedGroup)) {
            throw new CommandException(MESSAGE_DUPLICATE_GROUP);
        }

        model.setGroup(groupToEdit, editedGroup);
        model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
        return new CommandResult(String.format(MESSAGE_EDIT_GROUP_SUCCESS, editedGroup));
    }

    /**
     * Creates and returns a {@code Group} with the details of {@code groupToEdit}.
     * @param groupToEdit
     * @param editGroupDescriptor
     * @return
     */
    private static Group createEditedGroup(Group groupToEdit, EditGroupDescriptor editGroupDescriptor) {
        assert groupToEdit != null;

        Name updatedName = editGroupDescriptor.getName().orElse(groupToEdit.getName());
        Time oldTime = groupToEdit.getTimeSpent();
        ArrayList<Integer> memberIds;
        if (editGroupDescriptor.getMemberIds().isPresent()) {
            memberIds = editGroupDescriptor.getMemberIds().get();
        } else {
            memberIds = groupToEdit.getMembers();
        }

        ArrayList<Integer> eventIds = groupToEdit.getEvents();

        Group modifiedGroup = new Group(updatedName, groupToEdit.getPlaceList(),
                groupToEdit.getActivityList(), groupToEdit.getTimeList());
        modifiedGroup.setTimeSpent(oldTime);
        modifiedGroup.setMemberIDs(memberIds);
        modifiedGroup.setEventIDs(eventIds);
        return modifiedGroup;
    }


    /**
     * Stores the details to edit the group with. Each non-empty field value will replace the corresponding field value
     * of the specified group.
     */
    public static class EditGroupDescriptor {
        private Name name;
        private ArrayList<Integer> memberIds;

        public EditGroupDescriptor() {

        }

        public EditGroupDescriptor(EditGroupDescriptor toCopy) {
            setName(toCopy.name);
            setMemberIds(toCopy.memberIds);
        }

        /**
         * Checks if at least one field is edited by user.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, memberIds);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(this.name);
        }

        public void setMemberIds(ArrayList<Integer> memberIds) {
            this.memberIds = (memberIds != null) ? new ArrayList<>(memberIds) : null;
        }

        public Optional<ArrayList<Integer>> getMemberIds() {
            return (memberIds != null) ? Optional.of(memberIds) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditGroupDescriptor)) {
                return false;
            }

            EditGroupDescriptor e = (EditGroupDescriptor) other;

            return getName().equals(e.getName()) && getMemberIds().equals(e.getMemberIds());
        }

    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof EditGroupCommand)) {
            return false;
        }

        EditGroupCommand e = (EditGroupCommand) other;
        return index.equals(e.index) && editGroupDescriptor.equals(e.editGroupDescriptor);
    }

}
