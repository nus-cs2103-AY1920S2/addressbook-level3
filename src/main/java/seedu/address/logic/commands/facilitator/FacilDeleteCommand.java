package seedu.address.logic.commands.facilitator;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_FACILITATORS;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.facilitator.Facilitator;
import seedu.address.model.facilitator.Name;
import seedu.address.model.util.action.DoableActionType;
import seedu.address.model.util.action.FacilAction;

/**
 * Deletes a facilitator identified using it's displayed index from Mod Manager.
 */
public class FacilDeleteCommand extends FacilCommand {

    public static final String MESSAGE_USAGE = COMMAND_GROUP_FACIL + " " + COMMAND_WORD_DELETE
            + ": Deletes the facilitator identified by the index number used in the displayed facilitator list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_GROUP_FACIL + " " + COMMAND_WORD_DELETE + " 1\n"
            + "Parameters: FACILITATOR_NAME\n"
            + "Example: " + COMMAND_GROUP_FACIL + " " + COMMAND_WORD_DELETE + " Akshay Narayan";

    public static final String MESSAGE_DELETE_FACILITATOR_SUCCESS = "Deleted Facilitator: %1$s";

    private final Index targetIndex;
    private final Name fname;

    /**
     * Creates a FacilDeleteCommand to delete the facilitator the specified {@code index}.
     */
    public FacilDeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.fname = null;
    }

    public FacilDeleteCommand(Name fname) {
        this.targetIndex = null;
        this.fname = fname;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Facilitator> lastShownList = model.getFilteredFacilitatorList();
        Facilitator facilitatorToDelete;

        if (targetIndex != null) {
            assert fname == null;
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_FACILITATOR_DISPLAYED_INDEX);
            }
            facilitatorToDelete = lastShownList.get(targetIndex.getZeroBased());
        } else {
            assert fname != null;
            model.updateFilteredFacilitatorList(PREDICATE_SHOW_ALL_FACILITATORS);
            lastShownList = model.getFilteredFacilitatorList();
            final List<Facilitator> fetch = new ArrayList<>();
            lastShownList.stream().filter(x -> x.getName().equals(fname)).forEach(fetch::add);

            if (fetch.isEmpty()) {
                // No facilitators with such name, so ask the user to confirm
                final List<String> words = List.of(fname.fullName.split("\\s+"));
                Predicate<Facilitator> predicate =
                    f -> words.stream().anyMatch(x -> StringUtil.containsWordIgnoreCase(f.getName().fullName, x));

                lastShownList.stream().filter(predicate).forEach(fetch::add);

                if (fetch.isEmpty()) {
                    throw new CommandException(String.format(Messages.MESSAGE_FACILITATOR_NOT_FOUND, fname));
                }

                return promptUserToConfirm(fetch);
            }

            assert fetch.size() == 1;
            facilitatorToDelete = fetch.get(0);
        }

        model.deleteFacilitator(facilitatorToDelete);

        FacilAction deleteFacilAction = new FacilAction(facilitatorToDelete, DoableActionType.DELETE);
        model.addAction(deleteFacilAction);

        return new CommandResult(String.format(MESSAGE_DELETE_FACILITATOR_SUCCESS, facilitatorToDelete),
                CommandType.FACILITATOR);
    }

    /**
     * Returns a CommandResult with type PROMPTING, asking the user to input the more precise information.
     */
    private CommandResult promptUserToConfirm(List<Facilitator> fetch) {
        StringBuilder builder = new StringBuilder(
                String.format(Messages.MESSAGE_PARTIAL_FACILITATOR_NAME_MATCHING_FOUND, fname));
        fetch.forEach(x -> builder.append("  ").append(x.getName().toString()).append('\n'));
        builder.append(Messages.MESSAGE_ASK_TO_CONFIRM_FACILITATOR);
        return new CommandResult(builder.toString(), CommandType.PROMPTING);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof FacilDeleteCommand)) {
            return false;
        }

        FacilDeleteCommand fd = (FacilDeleteCommand) other;

        if ((targetIndex == null && fd.targetIndex != null) || (targetIndex != null && fd.targetIndex == null)) {
            return false;
        }

        if ((fname == null && fd.fname != null) || (fname != null && fd.fname == null)) {
            return false;
        }

        return (targetIndex == null || targetIndex.equals(fd.targetIndex))
                && (fname == null || fname.equals(fd.fname)); // state check
    }
}
