package cookbuddy.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cookbuddy.logic.commands.ModifyCommand;
import cookbuddy.logic.commands.ModifyCommand.EditRecipeDescriptor;
import cookbuddy.model.recipe.Recipe;
import cookbuddy.model.recipe.attribute.IngredientList;
import cookbuddy.model.recipe.attribute.InstructionList;
import cookbuddy.model.recipe.attribute.Name;
import cookbuddy.model.recipe.attribute.Tag;

/**
 * A utility class to help with building EditRecipeDescriptor objects.
 */
public class EditRecipeDescriptorBuilder {

    private ModifyCommand.EditRecipeDescriptor descriptor;

    public EditRecipeDescriptorBuilder() {
        descriptor = new ModifyCommand.EditRecipeDescriptor();
    }

    public EditRecipeDescriptorBuilder(ModifyCommand.EditRecipeDescriptor descriptor) {
        this.descriptor = new EditRecipeDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditRecipeDescriptor} with fields containing {@code recipe}'s details
     */
    public EditRecipeDescriptorBuilder(Recipe recipe) {
        descriptor = new EditRecipeDescriptor();
        descriptor.setName(recipe.getName());
        descriptor.setIngredients(recipe.getIngredients());
        descriptor.setInstructions(recipe.getInstructions());
        descriptor.setTags(recipe.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditRecipeDescriptor} that we are building.
     */
    public EditRecipeDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code IngredientList} of the {@code EditRecipeDescriptor} that we are building.
     */
    public EditRecipeDescriptorBuilder withIngredients(String ingredients) {
        descriptor.setIngredients(new IngredientList(ingredients));
        return this;
    }

    /**
     * Sets the {@code InstructionList} of the {@code EditRecipeDescriptor} that we are building.
     */
    public EditRecipeDescriptorBuilder withInstructions(String instructions) {
        descriptor.setInstructions(new InstructionList(instructions));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditRecipeDescriptor}
     * that we are building.
     */
    public EditRecipeDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public ModifyCommand.EditRecipeDescriptor build() {
        return descriptor;
    }
}
