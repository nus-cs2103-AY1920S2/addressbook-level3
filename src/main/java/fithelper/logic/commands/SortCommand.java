package fithelper.logic.commands;

import static fithelper.logic.commands.CommandResult.DisplayedPage.HOME;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_SORTBY;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_TYPE;
import static java.util.Objects.requireNonNull;

import fithelper.commons.core.Messages;
import fithelper.commons.exceptions.IllegalValueException;
import fithelper.model.Model;
import fithelper.model.entry.SortBy;
import fithelper.model.entry.Type;

public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts food/sports entry list or both lists "
            + "by either calorie or time.\n"
            + "Parameters: ["
            + PREFIX_TYPE + "TYPE] "
            + PREFIX_SORTBY + "SORTBY\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TYPE + "sports "
            + PREFIX_SORTBY + "cal ";

    private final Type sortType;
    private final SortBy sortBy;

    public SortCommand(Type type, SortBy order) {
        sortType = type;
        sortBy = order;
    }

    @Override
    public CommandResult execute(Model model) throws IllegalValueException {
        requireNonNull(model);
        String feedback = "";
        if (this.sortType == null) {
            model.sortFilteredEntryList(sortBy);
            feedback = Messages.MESSAGE_BOTH_ENTRY_LIST_SORTED + " by " + sortBy.getValue();
        } else {
            if ("food".equalsIgnoreCase(sortType.getValue())) {
                model.sortFilteredFoodEntryList(sortBy);
                feedback = Messages.MESSAGE_FOOD_ENTRY_LIST_SORTED + " by " + sortBy.getValue();
            } else {
                model.sortFilteredSportEntryList(sortBy);
                feedback = Messages.MESSAGE_SPORTS_ENTRY_LIST_SORTED + " by " + sortBy.getValue();
            }
        }
        return new CommandResult(feedback, HOME, false);
    }
}
