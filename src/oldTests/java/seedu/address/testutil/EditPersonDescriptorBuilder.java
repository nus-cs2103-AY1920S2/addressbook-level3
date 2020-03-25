package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.ModifyCommand;
import seedu.address.logic.commands.ModifyCommand.EditRecipeDescriptor;
import seedu.address.model.recipe.Email;
import seedu.address.model.recipe.IngredientList;
import seedu.address.model.recipe.InstructionList;
import seedu.address.model.recipe.Name;
import seedu.address.model.recipe.Phone;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditRecipeDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private ModifyCommand.EditRecipeDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new ModifyCommand.EditRecipeDescriptor();
    }

    public EditPersonDescriptorBuilder(ModifyCommand.EditRecipeDescriptor descriptor) {
        this.descriptor = new EditRecipeDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditRecipeDescriptor} with fields containing {@code recipe}'s details
     */
    public EditPersonDescriptorBuilder(Recipe recipe) {
        descriptor = new EditRecipeDescriptor();
        descriptor.setName(recipe.getName());
        descriptor.setIngredients(recipe.getIngredients());
        descriptor.setInstructions(recipe.getInstructions());
        descriptor.setTags(recipe.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditRecipeDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code IngredientList} of the {@code EditRecipeDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withIngredients(String ingredients) {
        descriptor.setIngredients(new IngredientList(ingredients));
        return this;
    }

    /**
     * Sets the {@code InstructionList} of the {@code EditRecipeDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withInstructions(String instructions) {
        descriptor.setInstructions(new InstructionList(instructions));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditRecipeDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public ModifyCommand.EditRecipeDescriptor build() {
        return descriptor;
    }
}
