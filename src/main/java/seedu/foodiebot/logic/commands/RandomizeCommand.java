package seedu.foodiebot.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import seedu.foodiebot.commons.core.Messages;
import seedu.foodiebot.commons.core.index.Index;
import seedu.foodiebot.logic.commands.exceptions.CommandException;
import seedu.foodiebot.logic.parser.CliSyntax;
import seedu.foodiebot.logic.parser.ParserContext;
import seedu.foodiebot.model.Model;
import seedu.foodiebot.model.canteen.Canteen;
import seedu.foodiebot.model.randomize.Randomize;

/**
 * Randomize a food item for the user based on index.
 */
public class RandomizeCommand extends Command {
    public static final String COMMAND_WORD = "randomize";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " [c/CANTEEN_NAME]: Generate a random food option by canteen.\n"
            + "[t/TAG]: Generate a random food option by tag.\n";

    public static final String MESSAGE_IN_CANTEEN = "\nYou call randomize on its own or use "
            + "[t/TAG] to get an option by a tag.";


    private final Randomize randomize;

    private String prefix;
    private String action = "";
    private Optional<Index> index;

    public RandomizeCommand(String prefix, String action, Randomize randomize) {
        this.prefix = prefix;
        this.action = action;
        this.randomize = Randomize.checkRandomize();
    }

    public RandomizeCommand(String prefix, Index index, Randomize randomize) {
        this.prefix = prefix;
        this.index = Optional.of(index);
        this.randomize = Randomize.checkRandomize();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException, IOException {
        requireNonNull(model);
        ParserContext.setCurrentContext(ParserContext.RANDOMIZE_CONTEXT);

        randomize.setPrefix(prefix);
        randomize.setAction(action);

        FileReader fileC = model.listOfCanteens();
        FileReader fileS = model.listOfStalls();
        randomize.resetInternalList();

        Boolean isCanteenContext = false;

        if (ParserContext.getPreviousContext().equals(ParserContext.CANTEEN_CONTEXT)) {
            Canteen canteen;
            if (ParserContext.getCurrentCanteen().isPresent()) {
                canteen = ParserContext.getCurrentCanteen().get();
                String canteenName = canteen.getName().toString();
                randomize.setSpecifiedCanteen(canteen);
                randomize.setCanteenContext();
                isCanteenContext = true;
            }
        }
        if (prefix.equals(CliSyntax.PREFIX_CANTEEN.toString()) && isCanteenContext) {
            throw new CommandException(String.format(Messages.MESSAGE_CANTEEN_ALREADY_SELECTED
                    + MESSAGE_IN_CANTEEN));
        } else if (prefix.equals(CliSyntax.PREFIX_CANTEEN.toString()) && !isCanteenContext) {
            List<Canteen> canteenList = model.getFilteredCanteenList();
            if (index != null) { //null and isPresent are different, DO NOT change
                try {
                    Canteen canteen = canteenList.get(index.get().getZeroBased());
                    randomize.setSpecifiedCanteen(canteen);
                } catch (IndexOutOfBoundsException e) {
                    throw new CommandException(Messages.MESSAGE_INVALID_CANTEEN_INDEX);
                }
            } else {
                for (int i = 0; i < canteenList.size(); i++) {
                    Canteen canteen = canteenList.get(i);
                    String canteenName = canteen.getName().toString();
                    if (canteenName.equals(action)) {
                        randomize.setSpecifiedCanteen(canteen);
                    }
                }
                if (!randomize.ifCanteenNamePresent()) {
                    throw new CommandException(Messages.MESSAGE_INVALID_CANTEEN_NAME);
                }
            }
            randomize.getOptionsByCanteen(fileS);
        } else if (prefix.equals(CliSyntax.PREFIX_TAG.toString())) {
            if (!isCanteenContext) {
                randomize.setCanteens(fileC);
            }
            randomize.getOptionsByTags(fileS);
        } else {
            randomize.getOptions(fileS);
        }

        return new CommandResult(COMMAND_WORD, randomize.output());
    }

    @Override
    public boolean needToSaveCommand() {
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RandomizeCommand)) {
            return false;
        }

        RandomizeCommand otherRandomize = (RandomizeCommand) other;
        return action.equals(otherRandomize.action) & randomize == otherRandomize.randomize;
    }
}
