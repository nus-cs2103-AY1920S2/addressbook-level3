package seedu.address.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import seedu.address.model.ReadOnlyRecipeBook;
import seedu.address.model.RecipeBook;
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
import seedu.address.model.recipe.ingredient.Vegetable;

/**
 * Contains utility methods for populating {@code RecipeBook} with sample data.
 */
public class SampleDataUtil {
    public static Recipe[] getSampleRecipes() {
        return new Recipe[] {
            new Recipe(new Name("Caesar Salad"), new Time("10"),
                    getGrainSet(),
                    getVegetableSet("Tomato, 100"),
                    getProteinSet(),
                    getFruitSet(),
                    getOtherSet("Honeydew, 100"),
                    getStepsList("Cut tomatoes", "Remove honeydew skin"), getGoalSet("Herbivore"), false),
            new Recipe(new Name("Grilled Sandwich"), new Time("10"),
                    getGrainSet("Bread, 50"),
                    getVegetableSet(),
                    getProteinSet(),
                    getFruitSet(),
                    getOtherSet("Cheese, 50, other"),
                    getStepsList("Spread butter on bread", "Heat pan to medium heat"),
                    getGoalSet("Wholesome Wholemeal"), false),
            new Recipe(new Name("Boiled Chicken"), new Time("10"),
                    getGrainSet(),
                    getVegetableSet(),
                    getProteinSet("Chicken, 100"),
                    getFruitSet(),
                    getOtherSet(),
                    getStepsList("Dice chicken"), getGoalSet("Bulk like the Hulk"), false),
            new Recipe(new Name("Chocolate Cake"), new Time("120"),
                    getGrainSet(),
                    getVegetableSet(),
                    getProteinSet(),
                    getFruitSet(),
                    getOtherSet("Sugar, 100"),
                    getStepsList("Caramelize sugar"), getGoalSet(), false),
            new Recipe(new Name("Omelet"), new Time("15"),
                    getGrainSet(),
                    getVegetableSet(),
                    getProteinSet("Egg, 100"),
                    getFruitSet(),
                    getOtherSet(),
                    getStepsList("Crack and beat eggs"), getGoalSet(), false),
            new Recipe(new Name("Tuna Bread"), new Time("60"),
                    getGrainSet("Wholemeal bread, 100"),
                    getVegetableSet(),
                    getProteinSet("Tuna, 100"),
                    getFruitSet(),
                    getOtherSet(),
                    getStepsList("Slice tuna"),
                    getGoalSet("Bulk like the Hulk", "Wholesome Wholemeal"), false)
        };
    }

    public static ReadOnlyRecipeBook getSampleRecipeBook() {
        RecipeBook sampleAb = new RecipeBook();
        for (Recipe sampleRecipe : getSampleRecipes()) {
            sampleAb.addRecipe(sampleRecipe);
        }
        return sampleAb;
    }

    /**
     * Returns a goal set containing the list of strings given.
     */
    public static Set<Goal> getGoalSet(String... goals) {
        return Arrays.stream(goals)
                .map(Goal::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns an grains set containing the list of strings given in the format (name, quantity).
     */
    public static Set<Grain> getGrainSet(String... grains) {
        Set<Grain> grainSet = new TreeSet<>();
        for (String grain: grains) {
            if (!grain.isBlank()) {
                String[] splitDetails = grain.split(",");
                String name = splitDetails[1].trim();
                Quantity quantity = QuantityUtil.parseQuantity(splitDetails[0].trim());

                grainSet.add(new Grain(name, quantity));
            }
        }
        return grainSet;
    }

    /**
     * Returns an vegetables set containing the list of strings given in the format (name, quantity).
     */
    public static Set<Vegetable> getVegetableSet(String... vegetables) {
        Set<Vegetable> vegetableSet = new TreeSet<>();
        for (String vegetable: vegetables) {
            if (!vegetable.isBlank()) {
                String[] splitDetails = vegetable.split(",");
                String name = splitDetails[1].trim();
                Quantity quantity = QuantityUtil.parseQuantity(splitDetails[0].trim());

                vegetableSet.add(new Vegetable(name, quantity));
            }
        }
        return vegetableSet;
    }


    /**
     * Returns an proteins set containing the list of strings given in the format (name, quantity).
     */
    public static Set<Protein> getProteinSet(String... proteins) {
        Set<Protein> proteinSet = new TreeSet<>();
        for (String protein: proteins) {
            if (!protein.isBlank()) {
                String[] splitDetails = protein.split(",");
                String name = splitDetails[1].trim();
                Quantity quantity = QuantityUtil.parseQuantity(splitDetails[0].trim());

                proteinSet.add(new Protein(name, quantity));
            }
        }
        return proteinSet;
    }

    /**
     * Returns an fruits set containing the list of strings given in the format (name, quantity).
     */
    public static Set<Fruit> getFruitSet(String... fruits) {
        Set<Fruit> fruitSet = new TreeSet<>();
        for (String fruit: fruits) {
            if (!fruit.isBlank()) {
                String[] splitDetails = fruit.split(",");
                String name = splitDetails[1].trim();
                Quantity quantity = QuantityUtil.parseQuantity(splitDetails[0].trim());

                fruitSet.add(new Fruit(name, quantity));
            }
        }
        return fruitSet;
    }


    /**
     * Returns an others set containing the list of strings given in the format (name, quantity).
     */
    public static Set<Other> getOtherSet(String... others) {
        Set<Other> otherSet = new TreeSet<>();
        for (String other: others) {
            if (!other.isBlank()) {
                String[] splitDetails = other.split(",");
                String name = splitDetails[1].trim();
                Quantity quantity = QuantityUtil.parseQuantity(splitDetails[0].trim());

                otherSet.add(new Other(name, quantity));
            }
        }
        return otherSet;
    }


    /**
     * Returns a steps list containing the list of steps given.
     */
    public static List<Step> getStepsList(String ... steps) {
        return Arrays.stream(steps)
                .map(Step::new)
                .collect(Collectors.toList());
    }

}
