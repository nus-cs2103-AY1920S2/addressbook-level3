package seedu.foodiebot.logic.parser;

import static seedu.foodiebot.logic.commands.ReviewCommand.MESSAGE_FAILURE;
import static seedu.foodiebot.logic.commands.ReviewCommand.MESSAGE_USAGE;

import seedu.foodiebot.commons.core.index.Index;
import seedu.foodiebot.logic.commands.ReviewCommand;
import seedu.foodiebot.logic.parser.exceptions.ParseException;
import seedu.foodiebot.model.rating.Review;

/** Set a review to a purchased food*/
public class ReviewCommandParser implements Parser<ReviewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RatingCommandParser and returns a
     * SelectItemCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReviewCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);

        if (!ParserContext.getCurrentContext().equals(ParserContext.TRANSACTIONS_CONTEXT)) {
            throw new ParseException(ParserContext.INVALID_CONTEXT_MESSAGE + ParserContext.getCurrentContext()
                    + "\n" + ParserContext.SUGGESTED_CONTEXT_MESSAGE
                    + ParserContext.TRANSACTIONS_CONTEXT);
        }

        String[] enteredText = argMultimap.getPreamble().split(" ", 2);
        try {
            Index index = Index.fromOneBased(Integer.parseInt(enteredText[0]));
            Review review = new Review(enteredText[1]);
            return new ReviewCommand(index, review);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new ParseException(MESSAGE_FAILURE + MESSAGE_USAGE);
        }
    }
}
