package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.model.Model;
import seedu.address.model.modelObjectTags.ID;
import seedu.address.viewmodel.ViewModel;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Finds and lists all courses in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Selects an item.\n"
                    + "Parameters: [PREFIX] + \"ID\"\n"
                    + "Example: " + COMMAND_WORD + " cid/ 1";

    public ViewModel viewModel = ViewModel.getInstance();

    List<ArgumentTokenizer.PrefixPosition> positions;

    List<ID> selectMetaDataIDs;

    public SelectCommand(List<ArgumentTokenizer.PrefixPosition> positions, List<ID> selectMetaDataIDs) {
        this.positions = positions;
        this.selectMetaDataIDs = selectMetaDataIDs;
        this.viewModel = ViewModel.getInstance();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        int no_of_commands = 2;
        for (int i = 0; i < no_of_commands; i++) {
            viewModel.updateDetails(positions, selectMetaDataIDs);
        }
        return new CommandResult("Selected item.");
    }

}
