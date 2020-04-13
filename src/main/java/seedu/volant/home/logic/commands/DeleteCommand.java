package seedu.volant.home.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import seedu.volant.commons.core.Messages;
import seedu.volant.commons.core.index.Index;
import seedu.volant.commons.logic.commands.Command;
import seedu.volant.commons.logic.commands.CommandResult;
import seedu.volant.commons.logic.commands.exceptions.CommandException;
import seedu.volant.commons.model.Model;
import seedu.volant.home.model.HomeModelManager;
import seedu.volant.home.model.trip.Trip;

/**
 * Deletes a trip identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the trip identified by the index number used in the displayed trip list."
            + "\nParameters:\tINDEX (must be a positive integer within range of trip list size)"
            + "\nExample:\t" + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Trip: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        HomeModelManager homeModel = ((HomeModelManager) model);

        List<Trip> lastShownList = homeModel.getFilteredTripList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TRIP_DISPLAYED_INDEX);
        }

        Trip tripToDelete = lastShownList.get(targetIndex.getZeroBased());
        deleteFile(new File("data/" + tripToDelete.getName()));
        homeModel.deleteTrip(tripToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, tripToDelete));
    }


    /**
     * Completely removes all data from the trip.
     * @param toDelete File to be deleted.
     */
    private void deleteFile(File toDelete) {
        File[] entries = toDelete.listFiles();
        if (entries != null) {
            for (File f : entries) {
                if (!Files.isSymbolicLink(f.toPath())) {
                    deleteFile(f);
                }
            }
        }
        toDelete.delete();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
