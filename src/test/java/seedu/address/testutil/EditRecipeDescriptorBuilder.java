package seedu.address.testutil;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditRecipeDescriptor;
import seedu.address.model.goal.Goal;
import seedu.address.model.recipe.Name;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.recipe.Step;
import seedu.address.model.recipe.Time;
import seedu.address.model.recipe.ingredient.Fruit;
import seedu.address.model.recipe.ingredient.Grain;
import seedu.address.model.recipe.ingredient.Other;
import seedu.address.model.recipe.ingredient.Protein;
import seedu.address.model.recipe.ingredient.Quantity;
import seedu.address.model.recipe.ingredient.Unit;
import seedu.address.model.recipe.ingredient.Vegetable;

/**
 * A utility class to help with building EditRecipeDescriptor objects.
 */
public class EditRecipeDescriptorBuilder {

    private EditRecipeDescriptor descriptor;

    public EditRecipeDescriptorBuilder() {
        descriptor = new EditRecipeDescriptor();
    }

    public EditRecipeDescriptorBuilder(EditRecipeDescriptor descriptor) {
        this.descriptor = new EditRecipeDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditRecipeDescriptor} with fields containing {@code recipe}'s details
     */
    public EditRecipeDescriptorBuilder(Recipe recipe) {
        descriptor = new EditRecipeDescriptor();
        descriptor.setName(recipe.getName());
        descriptor.setTime(recipe.getTime());

        descriptor.setGrains(recipe.getGrains());
        descriptor.setVegetables(recipe.getVegetables());
        descriptor.setProteins(recipe.getProteins());
        descriptor.setFruits(recipe.getFruits());
        descriptor.setOthers(recipe.getOthers());

        descriptor.setSteps(recipe.getSteps());
        descriptor.setGoals(recipe.getGoals());
    }

    /**
     * Sets the {@code Name} of the {@code EditRecipeDescriptor} that we are building.
     */
    public EditRecipeDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code EditRecipeDescriptor} that we are building.
     */
    public EditRecipeDescriptorBuilder withTime(String time) {
        descriptor.setTime(new Time(time));
        return this;
    }

    /**
     * Sets the {@code Step} of the {@code EditRecipeDescriptor} that we are building.
     */
    public EditRecipeDescriptorBuilder withSteps(String ... steps) {
        List<Step> stepsList = Stream.of(steps).map(Step::new).collect(Collectors.toList());
        descriptor.setSteps(stepsList);
        return this;
    }

    /**
     * Parses the {@code goals} into a {@code Set<Goal>} and set it to the {@code EditRecipeDescriptor}
     * that we are building.
     */
    public EditRecipeDescriptorBuilder withGoals(String... goals) {
        Set<Goal> goalSet = Stream.of(goals).map(Goal::new).collect(Collectors.toSet());
        descriptor.setGoals(goalSet);
        return this;
    }

    /**
     * Helper function for ingredients. Parses the {@code quantity} into a {@code Quantity}.
     */
    private Quantity getQuantity(String quantity) {
        int index = 0;
        while (index < quantity.length() && !Character.isLetter(quantity.charAt(index))) {
            index++;
        }

        double magnitude = Double.parseDouble(quantity.substring(0, index));
        String unitString = quantity.substring(index);
        Unit unit = null;
        switch (unitString) {
        case "ml":
            unit = Unit.MILLILITER;
            break;
        case "g":
            unit = Unit.GRAM;
            break;
        case "tbsp":
            unit = Unit.TABLESPOON;
            break;
        case "tsp":
            unit = Unit.TEASPOON;
            break;
        case "cup":
            unit = Unit.CUP;
            break;
        default:
            System.out.println("Unit not supported");
        }
        return new Quantity(magnitude, unit);
    }

    /**
     * Parses the {@code grains} into a {@code Set<Grain>} and set it to the {@code EditRecipeDescriptor}
     * that we are building.
     */
    public EditRecipeDescriptorBuilder withGrains(String... grains) {
        Set<Grain> grainSet = Stream.of(grains).map(grain -> {
            String[] details = grain.split(",");
            return new Grain(details[1], getQuantity(details[0]));
        }).collect(Collectors.toSet());
        descriptor.setGrains(grainSet);
        return this;
    }

    /**
     * Parses the {@code vegetables} into a {@code Set<Vegetable>} and set it to the {@code EditRecipeDescriptor}
     * that we are building.
     */
    public EditRecipeDescriptorBuilder withVegetables(String... vegetables) {
        Set<Vegetable> vegetableSet = Stream.of(vegetables).map(vegetable -> {
            String[] details = vegetable.split(",");
            return new Vegetable(details[1], getQuantity(details[0]));
        }).collect(Collectors.toSet());
        descriptor.setVegetables(vegetableSet);
        return this;
    }

    /**
     * Parses the {@code proteins} into a {@code Set<Protein>} and set it to the {@code EditRecipeDescriptor}
     * that we are building.
     */
    public EditRecipeDescriptorBuilder withProteins(String... proteins) {
        Set<Protein> proteinSet = Stream.of(proteins).map(protein -> {
            String[] details = protein.split(",");
            return new Protein(details[1], getQuantity(details[0]));
        }).collect(Collectors.toSet());
        descriptor.setProteins(proteinSet);
        return this;
    }

    /**
     * Parses the {@code fruits} into a {@code Set<Fruit>} and set it to the {@code EditRecipeDescriptor}
     * that we are building.
     */
    public EditRecipeDescriptorBuilder withFruits(String... fruits) {
        Set<Fruit> fruitSet = Stream.of(fruits).map(fruit -> {
            String[] details = fruit.split(",");
            return new Fruit(details[1], getQuantity(details[0]));
        }).collect(Collectors.toSet());
        descriptor.setFruits(fruitSet);
        return this;
    }

    /**
     * Parses the {@code others} into a {@code Set<Other>} and set it to the {@code EditRecipeDescriptor}
     * that we are building.
     */
    public EditRecipeDescriptorBuilder withOthers(String... others) {
        Set<Other> otherSet = Stream.of(others).map(other -> {
            String[] details = other.split(",");
            return new Other(details[1], getQuantity(details[0]));
        }).collect(Collectors.toSet());
        descriptor.setOthers(otherSet);
        return this;
    }

    public EditRecipeDescriptor build() {
        return descriptor;
    }
}
