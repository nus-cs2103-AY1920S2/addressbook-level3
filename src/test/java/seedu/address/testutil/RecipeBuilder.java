package seedu.address.testutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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

import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Recipe objects.
 */
public class RecipeBuilder {

    public static final String DEFAULT_NAME = "Egg Tomato Stir Fry";
    public static final String DEFAULT_TIME = "10";
    public static final boolean DEFAULT_FAVOURITE = false;
    public static final Step DEFAULT_STEP = new Step("Cut tomatoes into crescent shaped slices");
    public static final Quantity DEFAULT_QUANTITY = new Quantity(100, Unit.GRAM);
    public static final Grain DEFAULT_GRAIN = new Grain("Bread", DEFAULT_QUANTITY);
    public static final Vegetable DEFAULT_VEGETABLE = new Vegetable("Celery", DEFAULT_QUANTITY);
    public static final Protein DEFAULT_PROTEIN = new Protein("Minced Meat", DEFAULT_QUANTITY);
    public static final Other DEFAULT_OTHER = new Other("Oil", DEFAULT_QUANTITY);

    private Name name;
    private Time time;
    private boolean isFavourite;
    private Set<Grain> grains;
    private Set<Vegetable> vegetables;
    private Set<Protein> proteins;
    private Set<Fruit> fruits;
    private Set<Other> others;
    private List<Step> steps;
    private Set<Goal> goals;

    public RecipeBuilder() {
        name = new Name(DEFAULT_NAME);
        time = new Time(DEFAULT_TIME);
        isFavourite = DEFAULT_FAVOURITE;

        grains = new TreeSet<>();
        vegetables = new TreeSet<>();
        proteins = new TreeSet<>();
        fruits = new TreeSet<>();
        others = new TreeSet<>();

        steps = new ArrayList<>();
        steps.add(DEFAULT_STEP);
        goals = new HashSet<>();
    }

    /**
     * Initializes the RecipeBuilder with the data of {@code recipeToCopy}.
     */
    public RecipeBuilder(Recipe recipeToCopy) {
        name = recipeToCopy.getName();
        time = recipeToCopy.getTime();
        grains = new TreeSet<>(recipeToCopy.getGrains());
        vegetables = new TreeSet<>(recipeToCopy.getVegetables());
        proteins = new TreeSet<>(recipeToCopy.getProteins());
        fruits = new TreeSet<>(recipeToCopy.getFruits());
        others = new TreeSet<>(recipeToCopy.getOthers());
        isFavourite = recipeToCopy.isFavourite();
        steps = recipeToCopy.getSteps();
        goals = new HashSet<>(recipeToCopy.getGoals());
    }

    /**
     * Sets the {@code Name} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code goals} into a {@code Set<Goal>} and set it to the {@code Recipe} that we are building.
     */
    public RecipeBuilder withGoals(String ... goals) {
        this.goals = SampleDataUtil.getGoalSet(goals);
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withTime(String time) {
        this.time = new Time(time);
        return this;
    }

    /**
     * Sets the {@code Step} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withSteps(String ... steps) {
        this.steps = SampleDataUtil.getStepsList(steps);
        return this;
    }

    /**
     * Parses the {@code grains} into a {@code Set<Grain>} and set it to the {@code Recipe} that we are building.
     */
    public RecipeBuilder withGrains(String ... grains) {
        this.grains = SampleDataUtil.getGrainSet(grains);
        return this;
    }

    /**
     * Parses the {@code vegetables} into a {@code Set<Vegetable>} and set it to
     * the {@code Recipe} that we are building.
     */
    public RecipeBuilder withVegetables(String ... vegetables) {
        this.vegetables = SampleDataUtil.getVegetableSet(vegetables);
        return this;
    }


    /**
     * Parses the {@code proteins} into a {@code Set<Protein>} and set it to the {@code Recipe} that we are building.
     */
    public RecipeBuilder withProteins(String ... proteins) {
        this.proteins = SampleDataUtil.getProteinSet(proteins);
        return this;
    }

    /**
     * Parses the {@code fruits} into a {@code Set<Fruit>} and set it to the {@code Recipe} that we are building.
     */
    public RecipeBuilder withFruits(String ... fruits) {
        this.fruits = SampleDataUtil.getFruitSet(fruits);
        return this;
    }

    /**
     * Parses the {@code others} into a {@code Set<Other>} and set it to the {@code Recipe} that we are building.
     */
    public RecipeBuilder withOthers(String ... others) {
        this.others = SampleDataUtil.getOtherSet(others);
        return this;
    }



    public Recipe build() {
        return new Recipe(name, time, grains, vegetables, proteins, fruits, others, steps, goals, isFavourite);
    }

}
