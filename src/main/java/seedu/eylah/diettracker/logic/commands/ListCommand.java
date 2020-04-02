package seedu.eylah.diettracker.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.eylah.diettracker.model.DietModel.PREDICATE_SHOW_ALL_FOODS;

import java.time.LocalDateTime;
import java.util.function.Predicate;

import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.commons.logic.command.exception.CommandException;
import seedu.eylah.diettracker.model.DietModel;
import seedu.eylah.diettracker.model.food.Date;
import seedu.eylah.diettracker.model.food.Food;
import seedu.eylah.diettracker.model.tag.Tag;

/**
 * Lists all food and their calories.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": lists all food in the book. Use tags to list "
            + "different things."
            + "Parameters: [-f] [-d] [-t [numDays]] [-e [tag]]";
    public static final String MESSAGE_SUCCESS = "All foods over period based on input tag has been listed.\n";

    private String mode = "-d";
    private int numDays;
    private Tag tag;

    /**
     * Creates a ListCommand to list all food consumed by default.
     */
    public ListCommand() {}

    /**
     * Creates a ListCommand to list the foods consumed for a certain duration of time based on the input mode.
     */
    public ListCommand(String mode) {
        this.mode = mode;
    }

    /**
     * Creates a ListCommand to list the foods over the last numDays number of days.
     */
    public ListCommand(String mode, int numDays) {
        this.mode = mode;
        this.numDays = numDays;
    }

    /**
     * Creates a ListCommand to list all food with the given tag.
     */
    public ListCommand(String mode, Tag tag) {
        this.mode = mode;
        this.tag = tag;
    }

    @Override
    public CommandResult execute(DietModel model) throws CommandException {
        requireNonNull(model);
        String listString;

        if (mode.equals("-t")) {
            // date till which to obtain food logged
            Date limit = new Date(LocalDateTime.now().minusDays((long) numDays));
            Predicate<Food> datePredicate = food -> food.getDate().isAfter(limit);
            model.updateFilteredFoodList(datePredicate);
            model.listFoods(mode);
            return new CommandResult(MESSAGE_SUCCESS);
        } else if (mode.equals("-e")) {
            Predicate<Food> tagPredicate = food -> food.getTags().contains(this.tag);
            model.updateFilteredFoodList(tagPredicate);
            model.listFoods(mode);
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            model.updateFilteredFoodList(PREDICATE_SHOW_ALL_FOODS);
            model.listFoods(mode);
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCommand // instanceof handles nulls
                );
    }
}
