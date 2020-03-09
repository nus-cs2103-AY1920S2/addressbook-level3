package seedu.foodiebot.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.foodiebot.commons.core.LogsCenter;
import seedu.foodiebot.commons.core.index.Index;
import seedu.foodiebot.logic.parser.ParserContext;
import seedu.foodiebot.model.Model;
import seedu.foodiebot.model.canteen.Canteen;

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
        //model.updateFilteredStallList(Model.PREDICATE_SHOW_ALL_STALLS);
        //List<Stall> lastShownList = model.getFilteredStallList();
        if (index.isPresent()) {
            Canteen canteen = model.getFilteredCanteenList().get(index.get().getZeroBased());
            logger.info("Enter " + canteen.getName());
            model.updateFilteredStallList(s -> s.getCanteenName().equalsIgnoreCase(canteen.getName().toString()));

        } else if (canteenName.isPresent()) {
            List<Canteen> canteens = model.getFilteredCanteenList();
            for (Canteen c : canteens) {
                if (c.getName().toString().equalsIgnoreCase(canteenName.get())) {
                    ParserContext.setCanteenContext(c);
                    model.updateFilteredStallList(s -> s.getCanteenName().equalsIgnoreCase(c.getName().toString()));
                    break;
                }
            }
        }
        return new CommandResult(COMMAND_WORD, MESSAGE_SUCCESS);
    }
}
