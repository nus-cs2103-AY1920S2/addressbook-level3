package seedu.address.logic.commands.inventory;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_QUANTITY;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ingredient.Ingredient;

/**
 * Adds an ingredient to the inventory
 */

public class InventoryAddIngredientCommand extends InventoryCommand {

    public static final String COMMAND_WORD = "add";
    public static final String INGREDIENT_KEYWORD = "ingredient";
    public static final String MESSAGE_SUCCESS = "New ingredient added: %1$s";
    public static final String MESSAGE_USAGE = COMMAND_CATEGORY + " " + COMMAND_WORD + " " + INGREDIENT_KEYWORD
            + ": adds ingredients to your inventory.\n"
            + "\nParameters: "
            + PREFIX_INGREDIENT_NAME + "INGREDIENT "
            + PREFIX_INGREDIENT_QUANTITY + "QUANTITY\n"
            + "Example: " + COMMAND_CATEGORY + " "
            + COMMAND_WORD + " " + INGREDIENT_KEYWORD + " "
            + PREFIX_INGREDIENT_NAME + "Eggs "
            + PREFIX_INGREDIENT_QUANTITY + "10\n";

    private final Ingredient toAdd;

    /**
     * Creates a InventoryAddIngredientCommand to add the specified {@code Ingredient} to the inventory
     */
    public InventoryAddIngredientCommand(Ingredient toAdd) {
        requireNonNull(toAdd);

        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.addInventoryIngredient(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InventoryAddIngredientCommand // instanceof handles nulls
                && toAdd.equals(((InventoryAddIngredientCommand) other).toAdd));
    }


}
