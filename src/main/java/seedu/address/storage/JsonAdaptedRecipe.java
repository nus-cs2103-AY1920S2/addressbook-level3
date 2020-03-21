package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.goal.Goal;
import seedu.address.model.recipe.Name;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.recipe.Step;
import seedu.address.model.recipe.Time;

import seedu.address.model.recipe.ingredient.Fruit;
import seedu.address.model.recipe.ingredient.Grain;
import seedu.address.model.recipe.ingredient.Other;
import seedu.address.model.recipe.ingredient.Protein;
import seedu.address.model.recipe.ingredient.Vegetable;

/**
 * Jackson-friendly version of {@link Recipe}.
 */
class JsonAdaptedRecipe {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Recipe's %s field is missing!";

    private final String name;
    private final String time;
    private final boolean isFavourite;
    private final List<JsonAdaptedStep> steps = new ArrayList<>();
    private final List<JsonAdaptedGoal> goals = new ArrayList<>();
    private final List<JsonAdaptedGrain> grains = new ArrayList<>();
    private final List<JsonAdaptedVegetable> vegetables = new ArrayList<>();
    private final List<JsonAdaptedProtein> proteins = new ArrayList<>();
    private final List<JsonAdaptedFruit> fruits = new ArrayList<>();
    private final List<JsonAdaptedOther> others = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedRecipe} with the given recipe details.
     */
    @JsonCreator
    public JsonAdaptedRecipe(@JsonProperty("name") String name, @JsonProperty("time") String time,
            @JsonProperty("isFavourite") boolean isFavourite,
            @JsonProperty("grains") List<JsonAdaptedGrain> grains,
            @JsonProperty("vegetables") List<JsonAdaptedVegetable> vegetables,
            @JsonProperty("proteins") List<JsonAdaptedProtein> proteins,
            @JsonProperty("fruits") List<JsonAdaptedFruit> fruits,
            @JsonProperty("others") List<JsonAdaptedOther> others,
            @JsonProperty("steps") List<JsonAdaptedStep> steps,
            @JsonProperty("goals") List<JsonAdaptedGoal> goals) {
        this.name = name;
        this.time = time;
        this.isFavourite = isFavourite;
        if (grains != null) {
            this.grains.addAll(grains);
        }
        if (vegetables != null) {
            this.vegetables.addAll(vegetables);
        }
        if (proteins != null) {
            this.proteins.addAll(proteins);
        }
        if (fruits != null) {
            this.fruits.addAll(fruits);
        }
        if (others != null) {
            this.others.addAll(others);
        }
        if (steps != null) {
            this.steps.addAll(steps);
        }
        if (goals != null) {
            this.goals.addAll(goals);
        }
    }

    /**
     * Converts a given {@code Recipe} into this class for Jackson use.
     */
    public JsonAdaptedRecipe(Recipe source) {
        name = source.getName().fullName;
        time = source.getTime().value;
        grains.addAll(source.getGrains().stream()
                .map(JsonAdaptedGrain::new)
                .collect(Collectors.toList()));
        vegetables.addAll(source.getVegetables().stream()
                .map(JsonAdaptedVegetable::new)
                .collect(Collectors.toList()));
        proteins.addAll(source.getProteins().stream()
                .map(JsonAdaptedProtein::new)
                .collect(Collectors.toList()));
        fruits.addAll(source.getFruits().stream()
                .map(JsonAdaptedFruit::new)
                .collect(Collectors.toList()));
        others.addAll(source.getOthers().stream()
                .map(JsonAdaptedOther::new)
                .collect(Collectors.toList()));
        isFavourite = source.isFavourite();
        steps.addAll(source.getSteps().stream()
                .map(JsonAdaptedStep::new).collect(Collectors.toList()));
        goals.addAll(source.getGoals().stream()
                .map(JsonAdaptedGoal::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted recipe object into the model's {@code Recipe} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted recipe.
     */
    public Recipe toModelType() throws IllegalValueException {
        final List<Goal> recipeGoals = new ArrayList<>();
        for (JsonAdaptedGoal goal : goals) {
            recipeGoals.add(goal.toModelType());
        }

        final List<Grain> recipeGrains = new ArrayList<>();
        for (JsonAdaptedGrain grain : grains) {
            recipeGrains.add(grain.toModelType());
        }

        final List<Vegetable> recipeVegetables = new ArrayList<>();
        for (JsonAdaptedVegetable vegetable : vegetables) {
            recipeVegetables.add(vegetable.toModelType());
        }

        final List<Protein> recipeProteins = new ArrayList<>();
        for (JsonAdaptedProtein protein : proteins) {
            recipeProteins.add(protein.toModelType());
        }

        final List<Fruit> recipeFruits = new ArrayList<>();
        for (JsonAdaptedFruit fruit : fruits) {
            recipeFruits.add(fruit.toModelType());
        }

        final List<Other> recipeOthers = new ArrayList<>();
        for (JsonAdaptedOther other : others) {
            recipeOthers.add(other.toModelType());
        }
        // todo: throw illegalvalue if ingredients list is empty

        final List<Step> recipeSteps = new ArrayList<>();
        if (steps.isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Step.class.getSimpleName()));
        }
        for (JsonAdaptedStep step : steps) {
            recipeSteps.add(step.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName()));
        }
        if (!Time.isValidTime(time)) {
            throw new IllegalValueException(Time.MESSAGE_CONSTRAINTS);
        }
        final Time modelTime = new Time(time);

        final Set<Goal> modelGoals = new HashSet<>(recipeGoals);
        final Set<Grain> modelGrains = new TreeSet<>(recipeGrains);
        final Set<Vegetable> modelVegetables = new TreeSet<>(recipeVegetables);
        final Set<Protein> modelProteins = new TreeSet<>(recipeProteins);
        final Set<Fruit> modelFruits = new TreeSet<>(recipeFruits);
        final Set<Other> modelOthers = new TreeSet<>(recipeOthers);
        final ArrayList<Step> modelSteps = new ArrayList<>(recipeSteps);
        return new Recipe(modelName, modelTime,
                modelGrains, modelVegetables, modelProteins, modelFruits, modelOthers,
                modelSteps, modelGoals, isFavourite);
    }

}
