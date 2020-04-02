package seedu.recipe.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import seedu.recipe.model.Date;
import seedu.recipe.model.ReadOnlyCookedRecordBook;
import seedu.recipe.model.ReadOnlyRecipeBook;
import seedu.recipe.model.RecipeBook;
import seedu.recipe.model.cooked.CookedRecordBook;
import seedu.recipe.model.cooked.Record;
import seedu.recipe.model.goal.Goal;
import seedu.recipe.model.recipe.Name;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.Step;
import seedu.recipe.model.recipe.Time;

import seedu.recipe.model.recipe.ingredient.Fruit;
import seedu.recipe.model.recipe.ingredient.Grain;
import seedu.recipe.model.recipe.ingredient.Other;
import seedu.recipe.model.recipe.ingredient.Protein;
import seedu.recipe.model.recipe.ingredient.Quantity;
import seedu.recipe.model.recipe.ingredient.Vegetable;

/**
 * Contains utility methods for populating {@code RecipeBook} with sample data. todo: populate with real recipes
 */
public class SampleDataUtil {
    public static Recipe[] getSampleRecipes() {
        return new Recipe[] {
            new Recipe(new Name("Caesar Salad"), new Time("10"),
                    getGrainSet(),
                    getVegetableSet("100g, Tomato"),
                    getProteinSet(),
                    getFruitSet(),
                    getOtherSet("100g, Honeydew"),
                    getStepsList("Cut tomatoes", "Remove honeydew skin"), getGoalSet("Herbivore"), false),
            new Recipe(new Name("Grilled Sandwich"), new Time("10"),
                    getGrainSet("50g, Bread"),
                    getVegetableSet(),
                    getProteinSet(),
                    getFruitSet(),
                    getOtherSet("50g, Cheese"),
                    getStepsList("Spread butter on bread", "Heat pan to medium heat"),
                    getGoalSet("Wholesome Wholemeal"), false),
            new Recipe(new Name("Boiled Chicken"), new Time("10"),
                    getGrainSet(),
                    getVegetableSet(),
                    getProteinSet("50g, Chicken"),
                    getFruitSet(),
                    getOtherSet(),
                    getStepsList("Dice chicken"), getGoalSet("Bulk like the Hulk"), false),
            new Recipe(new Name("Chocolate Cake"), new Time("120"),
                    getGrainSet(),
                    getVegetableSet(),
                    getProteinSet(),
                    getFruitSet(),
                    getOtherSet("50g, Sugar"),
                    getStepsList("Caramelize sugar"), getGoalSet(), false),
            new Recipe(new Name("Omelet"), new Time("15"),
                    getGrainSet(),
                    getVegetableSet(),
                    getProteinSet("50g, Egg"),
                    getFruitSet(),
                    getOtherSet(),
                    getStepsList("Crack and beat eggs"), getGoalSet(), false),
            new Recipe(new Name("Tuna Bread"), new Time("60"),
                    getGrainSet("100g, Wholemeal bread"),
                    getVegetableSet(),
                    getProteinSet("50g, Tuna"),
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
                String[] splitDetails = grain.split(",", 2);
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
                String[] splitDetails = vegetable.split(",", 2);
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
                String[] splitDetails = protein.split(",", 2);
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
                String[] splitDetails = fruit.split(",", 2);
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
                String[] splitDetails = other.split(",", 2);
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

    /**
     * Returns an empty grain set, to be used to replace the default grain ingredient.
     */
    public static Set<Grain> emptyGrainSet() {
        return new TreeSet<Grain>();
    }

    public static Record[] getSampleRecords() {
        return new Record[] {
            new Record(new Name("Caesar Salad"), new Date(), getGoalSet("Herbivores")),
            new Record(new Name("Grilled Sandwich"), new Date(), getGoalSet("Wholesome Wholemeal")),
            new Record(new Name("Boiled Chicken"), new Date(), getGoalSet("Bulk like the Hulk")),
            new Record(new Name("Chocolate Cake"), new Date(), getGoalSet("Bulk like the Hulk", "Wholesome Wholemeal")),
            new Record(new Name("Omelet"), new Date(), getGoalSet()),
            new Record(new Name("Tuna Bread"), new Date(), getGoalSet("Wholesome Wholemeal"))
        };
    }

    public static ReadOnlyCookedRecordBook getSampleRecordBook() {
        CookedRecordBook sampleAb = new CookedRecordBook();
        for (Record sampleRecord : getSampleRecords()) {
            sampleAb.addRecord(sampleRecord);
        }
        return sampleAb;
    }

}
