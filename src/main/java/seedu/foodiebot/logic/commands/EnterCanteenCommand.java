package seedu.foodiebot.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.foodiebot.commons.core.LogsCenter;
import seedu.foodiebot.commons.core.index.Index;
import seedu.foodiebot.model.Model;
import seedu.foodiebot.model.canteen.Stall;

/**
 * Selects a canteen to view the food stalls.
 */
public class EnterCanteenCommand extends Command {
    public static final String COMMAND_WORD = "enter";

    public static final String MESSAGE_USAGE =
        COMMAND_WORD
            + "Parameters: "
            + "CANTEEN_NAME \n"
            + "Example: "
            + COMMAND_WORD
            + " "
            + "deck ";

    public static final String MESSAGE_SUCCESS = "";
    private static final Logger logger = LogsCenter.getLogger(EnterCanteenCommand.class);

    private final Optional<String> canteenName;
    private final Optional<Index> index;

    /**
     * @param index of the canteen in the filtered stall list to edit
     */
    public EnterCanteenCommand(Index index) {
        requireNonNull(index);
        this.index = Optional.of(index);
        this.canteenName = Optional.empty();
    }

    /**
     * @param canteenName from the given canteen name
     */
    public EnterCanteenCommand(String canteenName) {
        requireNonNull(canteenName);
        this.index = Optional.empty();
        this.canteenName = Optional.of(canteenName);
    }


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredStallList(Model.PREDICATE_SHOW_ALL_STALLS);
        List<Stall> lastShownList = model.getFilteredStallList();
        if (index.isPresent()) {
            Stall stall = lastShownList.get(index.get().getZeroBased());
            logger.info("Enter " + stall.getCanteenName());
            model.updateFilteredStallList(s -> s.getCanteenName().equalsIgnoreCase(stall.getCanteenName()));

        } else if (canteenName.isPresent()) {
            model.updateFilteredStallList(stall -> stall.getCanteenName().equalsIgnoreCase(canteenName.get()));
        }
        return new CommandResult(COMMAND_WORD, MESSAGE_SUCCESS);
    }
}
