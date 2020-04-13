package seedu.address.logic.commands.cart;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_QUANTITY;

import java.util.Optional;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientQuantity;
import seedu.address.model.ingredient.exceptions.IngredientNotFoundException;
import seedu.address.model.ingredient.exceptions.NonPositiveIngredientQuantityException;

/**
 * Removes an ingredient from the cart
 */
public class CartRemoveIngredientCommand extends CartCommand {

    public static final String COMMAND_WORD = "remove";
    public static final String INGREDIENT_KEYWORD = "ingredient";
    public static final String ALL_KEYWORD = "All";
    public static final String MESSAGE_SUCCESS = "%1$s removed from cart";
    public static final String MESSAGE_INGREDIENT_QUANTITY_TOO_HIGH = "The quantity specified is too large";
    public static final String MESSAGE_INGREDIENT_NOT_FOUND = "The cart does not contain %1$s";
    public static final String MESSAGE_USAGE = COMMAND_CATEGORY + " " + COMMAND_WORD
            + ": removes ingredients from your cart.\n"
            + "Parameters: \n"
            + PREFIX_INGREDIENT_NAME + "INGREDIENT "
            + "[" + PREFIX_INGREDIENT_QUANTITY + "QUANTITY]\n"
            + "Examples:\n"
            + COMMAND_CATEGORY + " " + COMMAND_WORD + " "
            + PREFIX_INGREDIENT_NAME + "Eggs\n"
            + COMMAND_CATEGORY + " " + COMMAND_WORD + " "
            + PREFIX_INGREDIENT_NAME + "Eggs " + PREFIX_INGREDIENT_QUANTITY + "10";

    private final IngredientName ingredientName;
    private final Optional<IngredientQuantity> ingredientQuantity;

    /**
     * Creates a CartRemoveIngredientCommand to remove an ingredient with the specified {@code IngredientName} and
     * {@code IngredientQuantity} (if any) to the cart
     */
    public CartRemoveIngredientCommand(IngredientName ingredientName,
                                       Optional<IngredientQuantity> ingredientQuantity) {
        requireAllNonNull(ingredientName, ingredientQuantity);

        this.ingredientName = ingredientName;
        this.ingredientQuantity = ingredientQuantity;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            ingredientQuantity.map(x -> new Ingredient(ingredientName, x))
                    .ifPresentOrElse(model::removeCartIngredient, () ->
                        model.removeCartIngredient(ingredientName));

            String ingredientRemoved = ingredientQuantity.map(x -> new Ingredient(ingredientName, x).toString())
                    .orElseGet(() -> ALL_KEYWORD + " " + ingredientName);

            return new CommandResult(String.format(MESSAGE_SUCCESS, ingredientRemoved));
        } catch (IngredientNotFoundException e) {
            throw new CommandException(String.format(MESSAGE_INGREDIENT_NOT_FOUND, ingredientName));
        } catch (NonPositiveIngredientQuantityException e) {
            throw new CommandException(String.format(MESSAGE_INGREDIENT_QUANTITY_TOO_HIGH));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CartRemoveIngredientCommand // instanceof handles nulls
                && ingredientName.equals(((CartRemoveIngredientCommand) other).ingredientName)
                && ingredientQuantity.equals(((CartRemoveIngredientCommand) other).ingredientQuantity));
    }
}
