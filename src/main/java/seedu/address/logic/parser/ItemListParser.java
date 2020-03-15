package seedu.address.logic.parser;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.recipe.Ingredient;
import seedu.address.model.recipe.Instruction;

/**
 * Parses and tokenises arguments to the Instruction and Ingredients command.
 */
public class ItemListParser {

    /**
     * Parses and returns a {@link Stream} of {@link Ingredient}s from the provided {@code itemString}.
     * @param itemString the {@link String} to be parsed
     * @return
     * @throws ParseException if {@code itemString} is blank, as specified by {@link String#isBlank()}
     */
    public static Stream<Ingredient> parseIngredientsToStream(String itemString) throws ParseException {
        if (itemString.isBlank()) {
            throw new ParseException("Recipes need to have instructions; please enter some instructions.");
        }

        String[] ingredientTokens = itemString.split(";");

        return Stream.of(ingredientTokens).map(x -> x.split(",")).map(x -> new Ingredient(x[0], x[1]));
    }

    /**
     * Parses and returns a {@link List} of {@link Ingredient}s from the provided {@code itemString}.
     * @param itemString the {@link String} to be parsed
     * @return a
     * @throws ParseException if {@code itemString} is blank, as specified by {@link String#isBlank()}
     */
    public static List<Ingredient> parseIngredientsToList(String itemString) throws ParseException {
        return parseIngredientsToStream(itemString).collect(Collectors.toList());
    }

    /**
     * Parses and returns a {@link Stream} of {@link Instruction}s from the provided {@code itemString}
     * @param itemString the {@link String} to be parsed
     * @return
     * @throws ParseException if {@code itemString} is blank, as specified by {@link String#isBlank()}
     */
    public static Stream<Instruction> parseInstructionsToStream(String itemString) throws ParseException {
        if (itemString.isBlank()) {
            throw new ParseException("Recipes need to have ingredients; please enter some ingredients.");
        }

        String[] instructionTokens = itemString.split(";");

        return Stream.of(instructionTokens).map(Instruction::new);
    }

    /**
     * Parses and returns a {@link List} of {@link Instruction}s from the provided {@code itemString}
     * @param itemString
     * @return
     * @throws ParseException if {@code itemString} is blank, as specified by {@link String#isBlank()}
     */
    public static List<Instruction> parseInstructionsToList(String itemString) throws ParseException {
        return parseInstructionsToStream(itemString).collect(Collectors.toList());
    }
}
