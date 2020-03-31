package cookbuddy.logic.parser;

import static cookbuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static cookbuddy.logic.parser.CliSyntax.PREFIX_INGREDIENTS;
import static cookbuddy.logic.parser.CliSyntax.PREFIX_INSTRUCTIONS;
import static cookbuddy.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Arrays;

import cookbuddy.logic.commands.FindCommand;
import cookbuddy.logic.parser.exceptions.ParseException;
import cookbuddy.model.recipe.IngredientContainsKeywordsPredicate;
import cookbuddy.model.recipe.InstructionContainsKeywordsPredicate;
import cookbuddy.model.recipe.NameContainsKeywordsPredicate;
import cookbuddy.model.recipe.attribute.Name;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Returns true if only one of the prefixes contains a value in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean isSinglePrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        long number = Arrays.stream(prefixes).filter(prefix -> argumentMultimap.getValue(prefix).isPresent()).count();

        return number == 1 ? true : false;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_INGREDIENTS,
                PREFIX_INSTRUCTIONS);

        // TODO: allow finding via multiple prefixes
        if (!isSinglePrefixPresent(argumentMultimap, PREFIX_NAME, PREFIX_INGREDIENTS, PREFIX_INSTRUCTIONS)) {
            throw new ParseException("Please search for 1 attribute at a time!");
        }

        String[] keywords;

        if (argumentMultimap.getValue(PREFIX_NAME).isPresent()) {
            if (argumentMultimap.getValue(PREFIX_NAME).get().isEmpty()) {
                throw new ParseException(Name.MESSAGE_CONSTRAINTS);
            }

            keywords = argumentMultimap.getValue(PREFIX_NAME).get().split("\\s+");
            return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));

        } else if (argumentMultimap.getValue(PREFIX_INGREDIENTS).isPresent()) {
            if (argumentMultimap.getValue(PREFIX_INGREDIENTS).get().isEmpty()) {
                throw new ParseException("Enter ingredients to search for.");
            }

            keywords = argumentMultimap.getValue(PREFIX_INGREDIENTS).get().split("\\s+");
            return new FindCommand(new IngredientContainsKeywordsPredicate(Arrays.asList(keywords)));
        } else if (argumentMultimap.getValue(PREFIX_INSTRUCTIONS).isPresent()) {
            if (argumentMultimap.getValue(PREFIX_INSTRUCTIONS).get().isEmpty()) {
                throw new ParseException("Enter instruction words to search for.");
            }

            keywords = argumentMultimap.getValue(PREFIX_INSTRUCTIONS).get().split("\\s+");
            return new FindCommand(new InstructionContainsKeywordsPredicate(Arrays.asList(keywords)));
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

}
