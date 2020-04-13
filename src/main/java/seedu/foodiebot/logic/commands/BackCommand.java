package seedu.foodiebot.logic.commands;

import seedu.foodiebot.logic.parser.ParserContext;
import seedu.foodiebot.model.Model;

/**
 * Returns a backCommand representing the user deciding to return to a previous menu
 */
public class BackCommand extends Command {

    public static final String COMMAND_WORD = "back";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT =
            "Exiting Address Book as requested ...";

    public static final String CHANGE_CONTEXT_ACKNOWLEDGEMENT =
            "Exited from menu : ";

    public static final String MESSAGE_EXIT_FAVORITES =
            "Exited from favorites : ";

    public static final String MESSAGE_EXIT_RANDOMIZE =
            "Exit Randomize. ";

    public static final String MESSAGE_EXIT_TRANSACTIONS =
            "Exit Transactions. ";

    public static final String MESSAGE_EXIT_REPORT =
            "Exit Report. ";

    public static final String INVALID_CONTEXT_MESSAGE =
            "Cannot go back any further";
    private static final String MESSAGE_EXIT_DIRECTIONS = "Exit Directions. ";

    @Override
    public CommandResult execute(Model model) {
        String context = ParserContext.getCurrentContext();
        switch (context) {
        case ParserContext.CANTEEN_CONTEXT:
            ParserContext.setCurrentContext(ParserContext.MAIN_CONTEXT);
            ParserContext.setCanteenContext(null);
            return new CommandResult(COMMAND_WORD, CHANGE_CONTEXT_ACKNOWLEDGEMENT + context,
                    false, false);

        case ParserContext.STALL_CONTEXT:
            if (ParserContext.getPreviousContext().equals(ParserContext.RANDOMIZE_CONTEXT)) {
                ParserContext.setCurrentContext(ParserContext.MAIN_CONTEXT);
            } else {
                ParserContext.setCurrentContext(ParserContext.CANTEEN_CONTEXT);
                ParserContext.setStallContext(null);
            }
            return new CommandResult(COMMAND_WORD, CHANGE_CONTEXT_ACKNOWLEDGEMENT + context,
                    false, false);

        case ParserContext.FAVORITE_CONTEXT:
            return new CommandResult(COMMAND_WORD, MESSAGE_EXIT_FAVORITES + context,
                    false, false);

        case ParserContext.RANDOMIZE_CONTEXT:
            ParserContext.setCurrentContext(ParserContext.MAIN_CONTEXT);
            return new CommandResult(COMMAND_WORD, MESSAGE_EXIT_RANDOMIZE, false, false);

        case ParserContext.DIRECTIONS_CONTEXT:
            ParserContext.setCurrentContext(ParserContext.MAIN_CONTEXT);
            return new CommandResult(COMMAND_WORD, MESSAGE_EXIT_DIRECTIONS, false, false);

        case ParserContext.TRANSACTIONS_CONTEXT:
            ParserContext.setCurrentContext(ParserContext.MAIN_CONTEXT);
            ParserContext.setCanteenContext(null);
            return new CommandResult(COMMAND_WORD, MESSAGE_EXIT_TRANSACTIONS + context,
                    false, false);
        case ParserContext.REPORT_CONTEXT:
            ParserContext.setCurrentContext(ParserContext.MAIN_CONTEXT);
            ParserContext.setCanteenContext(null);
            return new CommandResult(COMMAND_WORD, MESSAGE_EXIT_REPORT + context,
                    false, false);

        default:
            return new CommandResult(COMMAND_WORD, INVALID_CONTEXT_MESSAGE);
        }
    }

    @Override
    public boolean needToSaveCommand() {
        return false;
    }
}
