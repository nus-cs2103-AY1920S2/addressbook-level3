package seedu.address.logic.parser.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.recipe.RecipeAddCommand;
import seedu.address.logic.commands.recipe.RecipeCommand;
import seedu.address.logic.commands.recipe.RecipeRemoveCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and into a {@code RecipeCommand}.
 */
public class RecipeCommandParser implements Parser<RecipeCommand> {

    private static final Pattern RECIPE_COMMAND_ARGUMENT_FORMAT = Pattern
            .compile("(?<index>\\d+) +(?<commandWord>\\S+) +(?<category>\\S+)(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of a RecipeCommand
     * and returns a RecipeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RecipeCommand parse(String args) throws ParseException {
        requireNonNull(args);

        final Matcher matcher = RECIPE_COMMAND_ARGUMENT_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecipeCommand.MESSAGE_USAGE));
        }

        final String index = matcher.group("index");
        final String commandWord = matcher.group("commandWord");
        final String category = matcher.group("category");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {
        case RecipeAddCommand.COMMAND_WORD:
            return new RecipeAddCommandParser().parse(index + " " + category + " " + arguments);
        case RecipeRemoveCommand.COMMAND_WORD:
            return new RecipeRemoveCommandParser().parse(index + " " + category + " " + arguments);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
    }
}
