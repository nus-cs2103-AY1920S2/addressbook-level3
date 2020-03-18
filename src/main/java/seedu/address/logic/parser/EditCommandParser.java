package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_GRAIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_OTHER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_PROTEIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_VEGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STEP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditRecipeDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.goal.Goal;
import seedu.address.model.recipe.Step;

import seedu.address.model.recipe.ingredient.Ingredient;
/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TIME, PREFIX_INGREDIENT_GRAIN,
                        PREFIX_INGREDIENT_VEGE, PREFIX_INGREDIENT_PROTEIN, PREFIX_INGREDIENT_OTHER,
                        PREFIX_STEP, PREFIX_GOAL);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditRecipeDescriptor editRecipeDescriptor = new EditRecipeDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editRecipeDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_TIME).isPresent()) {
            editRecipeDescriptor.setTime(ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get()));
        }

        Optional<Set<Ingredient>> optionalIngredients = Optional.empty();
        optionalIngredients = parseGrainsForEdit(argMultimap.getAllValues(PREFIX_INGREDIENT_GRAIN),
                optionalIngredients);
        optionalIngredients = parseVegetablesForEdit(argMultimap.getAllValues(PREFIX_INGREDIENT_VEGE),
                optionalIngredients);
        optionalIngredients = parseProteinsForEdit(argMultimap.getAllValues(PREFIX_INGREDIENT_PROTEIN),
                optionalIngredients);
        optionalIngredients = parseOthersForEdit(argMultimap.getAllValues(PREFIX_INGREDIENT_OTHER),
                optionalIngredients);

        optionalIngredients.ifPresent(editRecipeDescriptor::setIngredients);

        parseStepsForEdit(argMultimap.getAllValues(PREFIX_STEP)).ifPresent(editRecipeDescriptor::setSteps);
        parseGoalsForEdit(argMultimap.getAllValues(PREFIX_GOAL)).ifPresent(editRecipeDescriptor::setGoals);

        if (!editRecipeDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editRecipeDescriptor);
    }


    /**
     * Parses and adds the {@code Collection<String> grains} into the {@code Set<Ingredient>}
     * If {@code grains} contain only one element which is an empty string, no additional ingredient will be
     * added into the {@code Set<Ingredient>} .
     */
    private Optional<Set<Ingredient>> parseGrainsForEdit(Collection<String> grains,
                                                         Optional<Set<Ingredient>> ingredientSet)
            throws ParseException {
        assert grains != null;

        if (grains.isEmpty()) {
            return ingredientSet;
        }

        Collection<String> grainSet = grains.size() == 1 && grains.contains("") ? Collections.emptySet() : grains;

        if (ingredientSet.isPresent()) {
            return Optional.of(ParserUtil.parseGrains(grainSet, ingredientSet.get()));
        } else {
            return Optional.of(ParserUtil.parseGrains(grainSet, null));
        }
    }

    /**
     * Parses and adds the {@code Collection<String> vegetables} into the {@code Set<Ingredient>}
     * If {@code vegetables} contain only one element which is an empty string, no additional ingredient will be
     * added into the {@code Set<Ingredient>}.
     */
    private Optional<Set<Ingredient>> parseVegetablesForEdit(Collection<String> vegetables,
                                                             Optional<Set<Ingredient>> ingredientSet)
            throws ParseException {
        assert vegetables != null;

        if (vegetables.isEmpty()) {
            return ingredientSet;
        }
        Collection<String> vegetableSet = vegetables.size() == 1 && vegetables.contains("")
                ? Collections.emptySet()
                : vegetables;

        if (ingredientSet.isPresent()) {
            return Optional.of(ParserUtil.parseGrains(vegetableSet, ingredientSet.get()));
        } else {
            return Optional.of(ParserUtil.parseVegetables(vegetableSet, null));
        }
    }

    /**
     * Parses and adds the {@code Collection<String> proteins} into the {@code Set<Ingredient>}
     * If {@code proteins} contain only one element which is an empty string, no additional ingredient will be
     * added into the {@code Set<Ingredient>} .
     */
    private Optional<Set<Ingredient>> parseProteinsForEdit(Collection<String> proteins,
                                                           Optional<Set<Ingredient>> ingredientSet)
            throws ParseException {
        assert proteins != null;

        if (proteins.isEmpty()) {
            return ingredientSet;
        }

        Collection<String> proteinSet = proteins.size() == 1 && proteins.contains("")
                ? Collections.emptySet()
                : proteins;

        if (ingredientSet.isPresent()) {
            return Optional.of(ParserUtil.parseGrains(proteinSet, ingredientSet.get()));
        } else {
            return Optional.of(ParserUtil.parseGrains(proteinSet, null));
        }
    }

    /**
     * Parses and adds the {@code Collection<String> others} into the {@code Set<Ingredient>}
     * If {@code others} contain only one element which is an empty string, no additional ingredient will be
     * added into the {@code Set<Ingredient>} .
     */
    private Optional<Set<Ingredient>> parseOthersForEdit(Collection<String> others,
                                                         Optional<Set<Ingredient>> ingredientSet)
            throws ParseException {
        assert others != null;

        if (others.isEmpty()) {
            return ingredientSet;
        }
        Collection<String> otherSet = others.size() == 1 && others.contains("") ? Collections.emptySet() : others;

        if (ingredientSet.isPresent()) {
            return Optional.of(ParserUtil.parseGrains(otherSet, ingredientSet.get()));
        } else {
            return Optional.of(ParserUtil.parseGrains(otherSet, null));
        }
    }

    /**
     * Parses {@code Collection<String> goals} into a {@code Set<Goal>} if {@code goals} is non-empty.
     * If {@code goals} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Goal>} containing zero goals.
     */
    private Optional<Set<Goal>> parseGoalsForEdit(Collection<String> goals) throws ParseException {
        assert goals != null;

        if (goals.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> goalSet = goals.size() == 1 && goals.contains("") ? Collections.emptySet() : goals;
        return Optional.of(ParserUtil.parseGoals(goalSet));
    }

    /**
     * Parses {@code Collection<String> steps} into a {@code List<Step>} if {@code steps} is non-empty.
     */
    private Optional<List<Step>> parseStepsForEdit(Collection<String> steps) throws ParseException {
        assert steps != null;

        if (steps.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(ParserUtil.parseSteps(steps));
    }

}
