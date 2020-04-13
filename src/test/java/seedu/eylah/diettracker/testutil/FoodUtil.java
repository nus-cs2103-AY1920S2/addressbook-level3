package seedu.eylah.diettracker.testutil;

import static seedu.eylah.diettracker.logic.parser.DietCliSyntax.PREFIX_CALORIES;
import static seedu.eylah.diettracker.logic.parser.DietCliSyntax.PREFIX_NAME;
import static seedu.eylah.diettracker.logic.parser.DietCliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.eylah.diettracker.logic.commands.AddCommand;
import seedu.eylah.diettracker.logic.commands.EditCommand.EditFoodDescriptor;
import seedu.eylah.diettracker.model.food.Food;
import seedu.eylah.diettracker.model.tag.Tag;

/**
 * A utility class for Food.
 */
public class FoodUtil {

    /**
     * Returns an add command string for adding the {@code food}.
     */
    public static String getAddCommand(Food food) {
        return AddCommand.COMMAND_WORD + " " + getFoodDetails(food);
    }

    /**
     * Returns the part of command string for the given {@code food}'s details.
     */
    public static String getFoodDetails(Food food) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + food.getName().name + " ");
        sb.append(PREFIX_CALORIES + food.getCalories().toString() + " ");
        food.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditFoodDescriptor}'s details.
     */
    public static String getEditFoodDescriptorDetails(EditFoodDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.name).append(" "));
        descriptor.getCalories().ifPresent(
            calories -> sb.append(PREFIX_CALORIES).append(calories.toString()).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
