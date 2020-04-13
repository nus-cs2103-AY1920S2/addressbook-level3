package seedu.recipe.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.model.Model;
import seedu.recipe.model.achievement.Content;
import seedu.recipe.model.achievement.Quote;

/**
 * Represents a quote command.
 */
public class QuoteCommand extends Command {

    public static final String COMMAND_WORD = "quote";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Indicate that the quote has been added to database\n"
            + "Parameters: [quote]... \n"
            + "Example: " + COMMAND_WORD + " Today is a good day to start!!";

    public static final String MESSAGE_DUPLICATE_RECORD = "This quote has already been added!";
    public static final String MESSAGE_SUCCESS = "Added your new quote!";

    private String quote;

    public QuoteCommand(String quote) {
        this.quote = quote;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!Content.isValidContent(this.quote)) {
            throw new CommandException(Content.MESSAGE_CONSTRAINTS);
        }
        Quote newQuote = new Quote(new Content(quote));
        if (model.hasQuote(newQuote)) {
            throw new CommandException(MESSAGE_DUPLICATE_RECORD);
        }
        //add record to internal list and update goals tally for each record added
        model.addQuote(newQuote);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
