package seedu.recipe.logic.commands.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_FRUIT;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_GRAIN;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_OTHER;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_PROTEIN;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_VEGE;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_STEP;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.recipe.logic.commands.Command;
import seedu.recipe.logic.commands.CommandResult;
import seedu.recipe.logic.commands.CommandType;
import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.model.Model;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.ui.tab.Tab;

/**
 * Adds a recipe to the recipe book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a recipe to the recipe book.\n"
            + "Parameters: "
            + "[" + PREFIX_NAME + "name] "
            + "[" + PREFIX_TIME + "time] "
            + "[<" + PREFIX_INGREDIENT_GRAIN + "grain>... "
            + "<" + PREFIX_INGREDIENT_VEGE + "vegetable>... "
            + "<" + PREFIX_INGREDIENT_PROTEIN + "protein>... "
            + "<" + PREFIX_INGREDIENT_FRUIT + "fruit>... "
            + "<" + PREFIX_INGREDIENT_OTHER + "other>...] "
            + "<" + PREFIX_STEP + "step>... "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Caesar Salad "
            + PREFIX_TIME + "10 "
            + PREFIX_INGREDIENT_VEGE + "100g, Tomato "
            + PREFIX_INGREDIENT_OTHER + "100g, 100% Parmesan cheese (grandma's favourite)"
            + PREFIX_STEP + "Cut tomatoes "
            + PREFIX_STEP + "Remove honeydew skin ";

    public static final String MESSAGE_SUCCESS = "New recipe added: %1$s";
    public static final String MESSAGE_DUPLICATE_RECIPE = "This recipe already exists in the address book";

    private final Tab recipesTab = Tab.RECIPES;
    private final Recipe toAdd;
    private final CommandType commandType;

    /**
     * Creates an AddCommand to add the specified {@code Recipe}
     */
    public AddCommand(Recipe recipe) {
        requireNonNull(recipe);
        this.toAdd = recipe;
        this.commandType = CommandType.MAIN_LONE;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasRecipe(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_RECIPE);
        }

        toAdd.calculateGoals();
        model.addRecipe(toAdd);
        model.commitBook(commandType, recipesTab);
        String message = String.format(MESSAGE_SUCCESS, toAdd);
        return new CommandResult(message, false, false, recipesTab, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
