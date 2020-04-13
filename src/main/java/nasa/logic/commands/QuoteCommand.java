package nasa.logic.commands;

import nasa.model.Model;

/**
 * Command class to activate quote.
 */
public class QuoteCommand extends Command {

    public static final String COMMAND_WORD = "quote";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": quote.\n"
            + "Parameters: none\nExample: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model) {
        String quoteMessage = model.quote();
        CommandResult result = new CommandResult(quoteMessage);
        result.setQuote();
        return result;
    }
}
