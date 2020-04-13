package seedu.address.logic.commands.cart;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_QUANTITY;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ingredient.Ingredient;

/**
 * Adds an ingredient to the cart
 */

public class CartAddIngredientCommand extends CartAddCommand {

    public static final String COMMAND_WORD = "ingredient";
    public static final String MESSAGE_SUCCESS = "New ingredient added: %1$s";
    public static final String MESSAGE_USAGE = "\n" + COMMAND_CATEGORY + " " + COMMAND_WORD
            + ": adds ingredients to your cart.\n"
            + "Parameters: "
            + PREFIX_INGREDIENT_NAME + "INGREDIENT "
            + PREFIX_INGREDIENT_QUANTITY + "QUANTITY\n"
            + "Example: " + COMMAND_CATEGORY + " "
            + COMMAND_WORD + " "
            + PREFIX_INGREDIENT_NAME + "Eggs "
            + PREFIX_INGREDIENT_QUANTITY + "10\n";

    private final Ingredient toAdd;

    /**
     * Creates a CartAddIngredientCommand to add the specified {@code Ingredient} to the cart
     */
    public CartAddIngredientCommand(Ingredient toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.addCartIngredient(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CartAddIngredientCommand // instanceof handles nulls
                && toAdd.equals(((CartAddIngredientCommand) other).toAdd));
    }
}
