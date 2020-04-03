package cookbuddy.logic.parser;

<<<<<<< HEAD:src/main/java/seedu/address/logic/parser/NewCommandParser.java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALORIE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSTRUCTIONS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERVING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
=======
import static cookbuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static cookbuddy.logic.parser.CliSyntax.PREFIX_CALORIE;
import static cookbuddy.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static cookbuddy.logic.parser.CliSyntax.PREFIX_IMAGEFILEPATH;
import static cookbuddy.logic.parser.CliSyntax.PREFIX_INGREDIENTS;
import static cookbuddy.logic.parser.CliSyntax.PREFIX_INSTRUCTIONS;
import static cookbuddy.logic.parser.CliSyntax.PREFIX_NAME;
import static cookbuddy.logic.parser.CliSyntax.PREFIX_RATING;
import static cookbuddy.logic.parser.CliSyntax.PREFIX_SERVING;
import static cookbuddy.logic.parser.CliSyntax.PREFIX_TAG;
>>>>>>> c5cbb6e2b9ee00ada3e6ee689676c50da75a94c5:src/main/java/cookbuddy/logic/parser/NewCommandParser.java

import java.util.Set;
import java.util.stream.Stream;

<<<<<<< HEAD:src/main/java/seedu/address/logic/parser/NewCommandParser.java
import seedu.address.logic.commands.NewCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.recipe.Recipe;
import seedu.address.model.recipe.attribute.Calorie;
import seedu.address.model.recipe.attribute.Difficulty;
import seedu.address.model.recipe.attribute.IngredientList;
import seedu.address.model.recipe.attribute.InstructionList;
import seedu.address.model.recipe.attribute.Name;
import seedu.address.model.recipe.attribute.Rating;
import seedu.address.model.recipe.attribute.Serving;
import seedu.address.model.recipe.attribute.Tag;
=======
import cookbuddy.logic.commands.NewCommand;
import cookbuddy.logic.parser.exceptions.ParseException;
import cookbuddy.model.recipe.Recipe;
import cookbuddy.model.recipe.attribute.Calorie;
import cookbuddy.model.recipe.attribute.Difficulty;
import cookbuddy.model.recipe.attribute.Image;
import cookbuddy.model.recipe.attribute.IngredientList;
import cookbuddy.model.recipe.attribute.InstructionList;
import cookbuddy.model.recipe.attribute.Name;
import cookbuddy.model.recipe.attribute.Rating;
import cookbuddy.model.recipe.attribute.Serving;
import cookbuddy.model.recipe.attribute.Tag;
>>>>>>> c5cbb6e2b9ee00ada3e6ee689676c50da75a94c5:src/main/java/cookbuddy/logic/parser/NewCommandParser.java

/**
 * Parses input arguments and creates a new NewCommand object
 */
public class NewCommandParser implements Parser<NewCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the NewCommand
     * and returns an NewCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public NewCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_INGREDIENTS,
<<<<<<< HEAD
                PREFIX_INSTRUCTIONS, PREFIX_CALORIE, PREFIX_SERVING, PREFIX_RATING, PREFIX_DIFFICULTY, PREFIX_TAG);
=======
            PREFIX_INSTRUCTIONS, PREFIX_IMAGEFILEPATH, PREFIX_CALORIE, PREFIX_SERVING, PREFIX_RATING, PREFIX_DIFFICULTY,
            PREFIX_TAG);
>>>>>>> 6e999eb3ab90da48f83c9efe740a12b9839a34cf

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_INGREDIENTS, PREFIX_INSTRUCTIONS)
            || !argMultimap
            .getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NewCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        IngredientList ingredients = ParserUtil.parseIngredients(argMultimap.getValue(PREFIX_INGREDIENTS).get());
        InstructionList instructions = ParserUtil.parseInstructions(argMultimap.getValue(PREFIX_INSTRUCTIONS).get());
        Image url =
            ParserUtil.parseFilePath(argMultimap.getValue(PREFIX_IMAGEFILEPATH)
                .orElse(Image.toAbsolutePath("src\\main\\resources"
                    + "\\images\\recipe placeholder.jpg")));
        Calorie calorie = ParserUtil.parseCalorie(argMultimap.getValue(PREFIX_CALORIE).orElse("0"));
        Serving serving = ParserUtil.parseServing(argMultimap.getValue(PREFIX_SERVING).orElse("1"));
        Rating rating = ParserUtil.parseRating(argMultimap.getValue(PREFIX_RATING).orElse("0"));
<<<<<<< HEAD
        Difficulty difficulty = ParserUtil.parseDifficulty(argMultimap.getValue(PREFIX_Difficulty).orElse("0"));
=======
        Difficulty difficulty = ParserUtil.parseDifficulty(argMultimap.getValue(PREFIX_DIFFICULTY).orElse("0"));
>>>>>>> 6e999eb3ab90da48f83c9efe740a12b9839a34cf
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Recipe recipe = new Recipe(name, ingredients, instructions, url, calorie, serving, rating, difficulty, tagList);

        return new NewCommand(recipe);
    }
}
