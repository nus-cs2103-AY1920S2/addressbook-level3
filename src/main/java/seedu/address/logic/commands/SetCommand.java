package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.time.LocalDate;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.recipe.Recipe;


public class SetCommand extends Command {

    public static final String COMMAND_WORD = "set";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Plans a recipe to be cooked on a certain date. "
            + "Parameters: "
            + "RECIPE_INDEX "
            + PREFIX_DATE + "YYYY-MM-DD \n"
            + "Example: " + COMMAND_WORD + " "
            + "3 "
            + PREFIX_DATE + "2020-03-16";

    public static final String MESSAGE_SUCCESS = "Recipe planned: %1$s"; // at date todo

    private final Recipe toSet;
    private final LocalDate atDate;

    /**
     * Creates an SetCommand to set the specified {@code Recipe} on a certain date
     */
    public SetCommand(Recipe recipe, LocalDate date) {
        requireNonNull(recipe);
        toSet = recipe;
        atDate = date;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.planRecipe(toSet, atDate);
        //model.addRecipe(toAdd); add the PlannedRecipeTree class to the Model and
        // add this command to the model interface and to the modelmanager
        //return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

}
