package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_FRUIT;
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

import seedu.address.model.recipe.ingredient.Fruit;
import seedu.address.model.recipe.ingredient.Grain;
import seedu.address.model.recipe.ingredient.Other;
import seedu.address.model.recipe.ingredient.Protein;
import seedu.address.model.recipe.ingredient.Vegetable;

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
                        PREFIX_INGREDIENT_VEGE, PREFIX_INGREDIENT_PROTEIN, PREFIX_INGREDIENT_FRUIT,
                        PREFIX_INGREDIENT_OTHER, PREFIX_STEP, PREFIX_GOAL);

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

        parseGrainsForEdit(argMultimap.getAllValues(PREFIX_INGREDIENT_GRAIN))
                .ifPresent(editRecipeDescriptor::setGrains);
        parseVegetablesForEdit(argMultimap.getAllValues(PREFIX_INGREDIENT_VEGE))
                .ifPresent(editRecipeDescriptor::setVegetables);
        parseProteinsForEdit(argMultimap.getAllValues(PREFIX_INGREDIENT_PROTEIN))
                .ifPresent(editRecipeDescriptor::setProteins);
        parseFruitsForEdit(argMultimap.getAllValues(PREFIX_INGREDIENT_FRUIT))
                .ifPresent(editRecipeDescriptor::setFruits);
        parseOthersForEdit(argMultimap.getAllValues(PREFIX_INGREDIENT_OTHER))
                .ifPresent(editRecipeDescriptor::setOthers);

        parseStepsForEdit(argMultimap.getAllValues(PREFIX_STEP)).ifPresent(editRecipeDescriptor::setSteps);
        parseGoalsForEdit(argMultimap.getAllValues(PREFIX_GOAL)).ifPresent(editRecipeDescriptor::setGoals);

        if (!editRecipeDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editRecipeDescriptor);
    }


    /**
     * Parses and adds the {@code Collection<String> grains} into the {@code Set<Grain>}
     * If {@code grains} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Grain>} containing zero Grain ingredients.
     */
    private Optional<Set<Grain>> parseGrainsForEdit(Collection<String> grains) throws ParseException {
        assert grains != null;
        if (grains.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> grainSet = grains.size() == 1 && grains.contains("") ? Collections.emptySet() : grains;
        return Optional.of(ParserUtil.parseGrains(grainSet));
    }

    /**
     * Parses and adds the {@code Collection<String> vegetables} into the {@code Set<Vegetable>}
     * If {@code vegetables} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Vegetable>} containing zero Vegetable ingredients.
     */
    private Optional<Set<Vegetable>> parseVegetablesForEdit(Collection<String> vegetables) throws ParseException {
        assert vegetables != null;

        if (vegetables.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> vegetableSet = vegetables.size() == 1 && vegetables.contains("")
                ? Collections.emptySet()
                : vegetables;
        return Optional.of(ParserUtil.parseVegetables(vegetableSet));
    }

    /**
     * Parses and adds the {@code Collection<String> proteins} into the {@code Set<Protein>}
     * If {@code proteins} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Protein>} containing zero Protein ingredients.
     */
    private Optional<Set<Protein>> parseProteinsForEdit(Collection<String> proteins) throws ParseException {
        assert proteins != null;

        if (proteins.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> proteinSet = proteins.size() == 1 && proteins.contains("")
                ? Collections.emptySet()
                : proteins;

        return Optional.of(ParserUtil.parseProteins(proteinSet));
    }

    /**
     * Parses and adds the {@code Collection<String> fruits} into the {@code Set<Fruit>}
     * If {@code fruits} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Fruit>} containing zero Protein ingredients.
     */
    private Optional<Set<Fruit>> parseFruitsForEdit(Collection<String> fruits) throws ParseException {
        assert fruits != null;

        if (fruits.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> fruitSet = fruits.size() == 1 && fruits.contains("")
                ? Collections.emptySet()
                : fruits;

        return Optional.of(ParserUtil.parseFruits(fruitSet));
    }

    /**
     * Parses and adds the {@code Collection<String> others} into the {@code Set<Other>}
     * If {@code others} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Other>} containing zero Other ingredients.
     */
    private Optional<Set<Other>> parseOthersForEdit(Collection<String> others) throws ParseException {
        assert others != null;

        if (others.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> otherSet = others.size() == 1 && others.contains("") ? Collections.emptySet() : others;
        return Optional.of(ParserUtil.parseOthers(otherSet));
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
