package seedu.recipe.logic.parser;

import static java.util.Objects.requireNonNull;

import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_FRUIT;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_GRAIN;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_OTHER;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_PROTEIN;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_VEGE;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.recipe.commons.core.index.Index;

import seedu.recipe.logic.commands.DeleteIngredientCommand;
import seedu.recipe.logic.commands.EditCommand;
import seedu.recipe.logic.commands.EditCommand.EditRecipeDescriptor;
import seedu.recipe.logic.parser.exceptions.ParseException;

import seedu.recipe.model.recipe.ingredient.Fruit;
import seedu.recipe.model.recipe.ingredient.Grain;
import seedu.recipe.model.recipe.ingredient.Other;
import seedu.recipe.model.recipe.ingredient.Protein;
import seedu.recipe.model.recipe.ingredient.Vegetable;

/**
 * Parses input arguments and creates a new DeleteIngredientCommand object.
 */
public class DeleteIngredientCommandParser implements Parser<DeleteIngredientCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteIngredientCommand
     * and returns an DeleteIngredientCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteIngredientCommand parse(String args) throws ParseException {
        requireNonNull(args);
        if (args.isBlank()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteIngredientCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INGREDIENT_GRAIN, PREFIX_INGREDIENT_VEGE,
                        PREFIX_INGREDIENT_PROTEIN, PREFIX_INGREDIENT_FRUIT, PREFIX_INGREDIENT_OTHER);

        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());

        EditRecipeDescriptor editRecipeDescriptor = new EditRecipeDescriptor();

        parseGrainsForDeleteIngredient(argMultimap.getAllValues(PREFIX_INGREDIENT_GRAIN))
                .ifPresent(editRecipeDescriptor::setGrains);
        parseVegetablesForDeleteIngredient(argMultimap.getAllValues(PREFIX_INGREDIENT_VEGE))
                .ifPresent(editRecipeDescriptor::setVegetables);
        parseProteinsForDeleteIngredient(argMultimap.getAllValues(PREFIX_INGREDIENT_PROTEIN))
                .ifPresent(editRecipeDescriptor::setProteins);
        parseFruitsForDeleteIngredient(argMultimap.getAllValues(PREFIX_INGREDIENT_FRUIT))
                .ifPresent(editRecipeDescriptor::setFruits);
        parseOthersForDeleteIngredient(argMultimap.getAllValues(PREFIX_INGREDIENT_OTHER))
                .ifPresent(editRecipeDescriptor::setOthers);

        if (!editRecipeDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new DeleteIngredientCommand(index, editRecipeDescriptor);
    }


    /**
     * Parses and adds the {@code Collection<String> grains} into the {@code Set<Grain>}
     * If {@code grains} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Grain>} containing zero Grain ingredients.
     */
    private Optional<Set<Grain>> parseGrainsForDeleteIngredient(Collection<String> grains) throws ParseException {
        assert grains != null;
        if (grains.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> grainSet = grains.size() == 1 && grains.contains("") ? Collections.emptySet() : grains;
        return Optional.of(ParserUtil.parseGrainsNameOnly(grainSet));
    }

    /**
     * Parses and adds the {@code Collection<String> vegetables} into the {@code Set<Vegetable>}
     * If {@code vegetables} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Vegetable>} containing zero Vegetable ingredients.
     */
    private Optional<Set<Vegetable>> parseVegetablesForDeleteIngredient(Collection<String> vegetables)
            throws ParseException {
        assert vegetables != null;

        if (vegetables.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> vegetableSet = vegetables.size() == 1 && vegetables.contains("")
                ? Collections.emptySet()
                : vegetables;
        return Optional.of(ParserUtil.parseVegetablesNameOnly(vegetableSet));
    }

    /**
     * Parses and adds the {@code Collection<String> proteins} into the {@code Set<Protein>}
     * If {@code proteins} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Protein>} containing zero Protein ingredients.
     */
    private Optional<Set<Protein>> parseProteinsForDeleteIngredient(Collection<String> proteins) throws ParseException {
        assert proteins != null;

        if (proteins.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> proteinSet = proteins.size() == 1 && proteins.contains("")
                ? Collections.emptySet()
                : proteins;

        return Optional.of(ParserUtil.parseProteinsNameOnly(proteinSet));
    }

    /**
     * Parses and adds the {@code Collection<String> fruits} into the {@code Set<Fruit>}
     * If {@code fruits} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Fruit>} containing zero Protein ingredients.
     */
    private Optional<Set<Fruit>> parseFruitsForDeleteIngredient(Collection<String> fruits) throws ParseException {
        assert fruits != null;

        if (fruits.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> fruitSet = fruits.size() == 1 && fruits.contains("")
                ? Collections.emptySet()
                : fruits;

        return Optional.of(ParserUtil.parseFruitsNameOnly(fruitSet));
    }

    /**
     * Parses and adds the {@code Collection<String> others} into the {@code Set<Other>}
     * If {@code others} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Other>} containing zero Other ingredients.
     */
    private Optional<Set<Other>> parseOthersForDeleteIngredient(Collection<String> others) throws ParseException {
        assert others != null;

        if (others.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> otherSet = others.size() == 1 && others.contains("") ? Collections.emptySet() : others;
        return Optional.of(ParserUtil.parseOthersNameOnly(otherSet));
    }

}
