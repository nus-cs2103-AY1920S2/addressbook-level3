package seedu.address.logic.parser.cookbook;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEARCH_KEYWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.cookbook.CookbookSearchByInventoryCommand;
import seedu.address.logic.commands.cookbook.CookbookSearchByKeywordCommand;
import seedu.address.logic.commands.cookbook.CookbookSearchByTagCommand;
import seedu.address.logic.commands.cookbook.CookbookSearchCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.recipe.RecipeContainsTagsPredicate;
import seedu.address.model.recipe.RecipeNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new CookbookSearchCommand object
 */
public class CookbookSearchCommandParser implements Parser<CookbookSearchCommand> {

    private static final Pattern COOKBOOK_SEARCH_COMMAND_ARGUMENT_FORMAT = Pattern
            .compile(" *(?<category>recipe|tag|inventory)(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the CookbookSearchCommand
     * and returns a CookbookSearchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CookbookSearchCommand parse(String args) throws ParseException {
        requireNonNull(args);

        final Matcher matcher = COOKBOOK_SEARCH_COMMAND_ARGUMENT_FORMAT.matcher(args.trim());

        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    CookbookSearchCommand.MESSAGE_USAGE));
        }

        final String category = matcher.group("category");
        final String arguments = matcher.group("arguments");

        switch(category) {
        case CookbookSearchCommand.SEARCH_INVENTORY_COMMAND:
            return parseSearchByInventory(arguments);
        case CookbookSearchCommand.RECIPE_KEYWORD:
            return parseSearchByKeyword(arguments);
        case CookbookSearchCommand.SEARCH_TAG_COMMAND:
            return parseSearchByTag(arguments);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    CookbookSearchCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the CookbookSearchByKeywordCommand
     * and returns a CookbookSearchByKeywordCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CookbookSearchByKeywordCommand parseSearchByKeyword(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SEARCH_KEYWORD);

        if (!argMultimap.arePrefixesPresent(PREFIX_SEARCH_KEYWORD) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CookbookSearchByKeywordCommand.MESSAGE_USAGE));
        }

        assert argMultimap.arePrefixesPresent(PREFIX_SEARCH_KEYWORD);
        assert argMultimap.getPreamble().isEmpty();
        List<String> trimmedArgs = argMultimap.getAllValues(PREFIX_SEARCH_KEYWORD);
        return new CookbookSearchByKeywordCommand(new RecipeNameContainsKeywordsPredicate(trimmedArgs));
    }

    /**
     * Parses the given {@code String} of arguments in the context of the CookbookSearchByTagCommand
     * and returns a CookbookSearchByTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CookbookSearchByTagCommand parseSearchByTag(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        if (!argMultimap.arePrefixesPresent(PREFIX_TAG) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CookbookSearchByTagCommand.MESSAGE_USAGE));
        }

        assert argMultimap.arePrefixesPresent(PREFIX_TAG);
        assert argMultimap.getPreamble().isEmpty();
        List<String> trimmedArgs = argMultimap.getAllValues(PREFIX_TAG);

        return new CookbookSearchByTagCommand(new RecipeContainsTagsPredicate(trimmedArgs));
    }

    /**
     * Parses the given {@code String} of arguments in the context of the CookbookSearchByInventoryCommand
     * and returns a CookbookSearchByInventoryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CookbookSearchByInventoryCommand parseSearchByInventory(String args) {
        requireNonNull(args);

        return new CookbookSearchByInventoryCommand();
    }
}
