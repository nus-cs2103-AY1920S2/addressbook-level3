package seedu.eylah.diettracker.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.eylah.diettracker.logic.commands.EditCommand.EditFoodDescriptor;
import seedu.eylah.diettracker.model.food.Name;
import seedu.eylah.diettracker.model.food.Food;
import seedu.eylah.diettracker.model.tag.Tag;

/**
 * A utility class to help with building EditFoodDescriptor objects.
 */
public class EditFoodDescriptorBuilder {

    private EditFoodDescriptor descriptor;

    public EditFoodDescriptorBuilder() {
        descriptor = new EditFoodDescriptor();
    }

    public EditFoodDescriptorBuilder(EditFoodDescriptor descriptor) {
        this.descriptor = new EditFoodDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditFoodDescriptor} with fields containing {@code food}'s details
     */
    public EditFoodDescriptorBuilder(Food food) {
        descriptor = new EditFoodDescriptor();
        descriptor.setName(food.getName());
        descriptor.setTags(food.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditFoodDescriptor} that we are building.
     */
    public EditFoodDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditFoodDescriptor}
     * that we are building.
     */
    public EditFoodDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditFoodDescriptor build() {
        return descriptor;
    }
}
