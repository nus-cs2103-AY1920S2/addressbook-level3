package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_GRAIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_OTHER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_PROTEIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_VEGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STEP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.recipe.Recipe;

/**
 * Adds a recipe to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a recipe to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_TIME + "TIME "
            + "[" + PREFIX_INGREDIENT_GRAIN + "GRAIN]..."
            + "[" + PREFIX_INGREDIENT_VEGE + "VEGETABLE]..."
            + "[" + PREFIX_INGREDIENT_PROTEIN + "PROTEIN]..."
            + "[" + PREFIX_INGREDIENT_OTHER + "OTHER]..."
            + "[" + PREFIX_STEP + "STEP]... "
            + "[" + PREFIX_GOAL + "GOAL]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Caesar Salad "
            + PREFIX_TIME + "10 "
            + PREFIX_INGREDIENT_VEGE + "100, Tomato"
            + PREFIX_INGREDIENT_VEGE + "100, Lettuce"
            + PREFIX_INGREDIENT_OTHER + "50, Honeydew"
            + PREFIX_STEP + "Cut tomatoes "
            + PREFIX_STEP + "Remove honeydew skin "
            + PREFIX_GOAL + "Herbivore ";

    public static final String MESSAGE_SUCCESS = "New recipe added: %1$s";
    public static final String MESSAGE_DUPLICATE_RECIPE = "This recipe already exists in the address book";

    private final Recipe toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Recipe}
     */
    public AddCommand(Recipe recipe) {
        requireNonNull(recipe);
        toAdd = recipe;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasRecipe(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_RECIPE);
        }

        model.addRecipe(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
