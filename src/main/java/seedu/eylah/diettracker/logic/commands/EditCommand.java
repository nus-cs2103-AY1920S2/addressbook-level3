package seedu.eylah.diettracker.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.eylah.diettracker.logic.parser.DietCliSyntax.PREFIX_CALORIES;
import static seedu.eylah.diettracker.logic.parser.DietCliSyntax.PREFIX_INDEX;
import static seedu.eylah.diettracker.logic.parser.DietCliSyntax.PREFIX_NAME;
import static seedu.eylah.diettracker.logic.parser.DietCliSyntax.PREFIX_TAG;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.eylah.commons.core.Messages;
import seedu.eylah.commons.core.index.Index;
import seedu.eylah.commons.logic.command.Command;
import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.commons.logic.command.exception.CommandException;
import seedu.eylah.commons.util.CollectionUtil;
import seedu.eylah.diettracker.model.DietModel;
import seedu.eylah.diettracker.model.food.Calories;
import seedu.eylah.diettracker.model.food.Date;
import seedu.eylah.diettracker.model.food.Food;
import seedu.eylah.diettracker.model.food.Name;
import seedu.eylah.diettracker.model.tag.Tag;

/**
 * Adds a food to the food book.
 */
public class EditCommand extends Command<DietModel> {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a food in the food book. "
            + "by the index number used in the displayed food list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: " + PREFIX_INDEX + " INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + " NAME] "
            + "[" + PREFIX_CALORIES + " CALORIES] "
            + "[" + PREFIX_TAG + " TAG] ";

    public static final String MESSAGE_SUCCESS = "Food Edited: %1$s";
    public static final String MESSAGE_DUPLICATE_FOOD = "This food already exists in the food book.";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final EditFoodDescriptor editFoodDescriptor;
    private final Index index;

    /**
     * Creates an EditCommand to edit the specified {@code Food}
     */
    public EditCommand(Index index, EditFoodDescriptor editFoodDescriptor) {
        requireNonNull(index);
        requireNonNull(editFoodDescriptor);

        this.editFoodDescriptor = editFoodDescriptor;
        this.index = index;
    }

    @Override
    public CommandResult execute(DietModel model) throws CommandException {
        requireNonNull(model);
        List<Food> lastShownList = model.getFilteredFoodList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FOOD_DISPLAYED_INDEX);
        }

        Food foodToEdit = lastShownList.get(index.getZeroBased());
        Food editedFood = createEditedFood(foodToEdit, editFoodDescriptor);

        if (!foodToEdit.isSameFood(editedFood) && model.hasFood(editedFood)) {
            throw new CommandException(MESSAGE_DUPLICATE_FOOD);
        }

        model.setFood(foodToEdit, editedFood);
        model.updateFilteredFoodList(DietModel.PREDICATE_SHOW_ALL_FOODS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, editedFood));
    }

    /**
     * Creates and returns a {@code Food} with the details of {@code foodToEdit}
     * edited with {@code editFoodDescriptor}.
     */
    private static Food createEditedFood(Food foodToEdit, EditFoodDescriptor editFoodDescriptor) {
        assert foodToEdit != null;

        Name updatedName = editFoodDescriptor.getName().orElse(foodToEdit.getName());
        Calories updatedCalories = editFoodDescriptor.getCalories().orElse(foodToEdit.getCalories());
        Set<Tag> updatedTags = editFoodDescriptor.getTags().orElse(foodToEdit.getTags());

        return new Food(updatedName, updatedCalories, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editFoodDescriptor.equals(e.editFoodDescriptor);
    }

    /**
     * Stores the details to edit the food with. Each non-empty field value will replace the
     * corresponding field value of the food.
     */
    public static class EditFoodDescriptor {
        private Name name;
        private Calories calories;
        private Date date;
        private Set<Tag> tags;

        public EditFoodDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditFoodDescriptor(EditFoodDescriptor toCopy) {
            setName(toCopy.name);
            setCalories(toCopy.calories);
            setDate(toCopy.date);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, calories, date, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setCalories(Calories calories) {
            this.calories = calories;
        }

        public Optional<Calories> getCalories() {
            return Optional.ofNullable(calories);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditFoodDescriptor)) {
                return false;
            }

            // state check
            EditFoodDescriptor e = (EditFoodDescriptor) other;

            return getName().equals(e.getName())
                    && getCalories().equals(e.getCalories())
                    //&& getDate().toString().equals(e.getDate().toString())
                    && getTags().equals(e.getTags());
        }
    }
}

