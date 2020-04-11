package seedu.recipe.model.recipe.ingredient;

import java.util.Set;
import java.util.TreeSet;

/**
 * Consists of magnitudes of main ingredient types being tracked by goals and main ingredient type that meets
 * nutritional requirements.
 */
public class MainIngredientTypeMagnitude {
    //average nutritional count per day/3 meals
    public static final int MIN_VEGETABLE_NUTRITIONAL_COUNT = 200;
    public static final int MIN_FRUIT_NUTRITIONAL_COUNT = 160;
    public static final int MIN_PROTEIN_NUTRITIONAL_COUNT = 85;
    public static final int MIN_GRAIN_NUTRITIONAL_COUNT = 80;

    private double vegCount = 0;
    private double fruitCount = 0;
    private double proteinCount = 0;
    private double grainCount = 0;
    private Set<MainIngredientType> mainIngredientTypes;

    public MainIngredientTypeMagnitude(double vegCount, double fruitCount, double proteinCount, double grainCount) {
        this.fruitCount = fruitCount;
        this.grainCount = grainCount;
        this.proteinCount = proteinCount;
        this.vegCount = vegCount;
        this.mainIngredientTypes = getMainIngredientTypes();
    }

    public double getVegCount() {
        return this.vegCount;
    }

    public double getFruitCount() {
        return this.fruitCount;
    }

    public double getProteinCount() {
        return this.proteinCount;
    }

    public double getGrainCount() {
        return this.grainCount;
    }

    /**
     * Finding main ingredient types that meet nutrition requirement for each type according to NIH.
     * @return set of MainIngredientTypes that meets requirements for each food group.
     * @see <a href="https://www.nia.nih.gov/health/serving-and-portion-sizes-how-much-should-i-eat">nutritionReq</a>
     */
    private Set<MainIngredientType> getMainIngredientTypes() {
        Set<MainIngredientType> setOfMainTypes = new TreeSet<MainIngredientType>();
        if (this.vegCount >= MIN_VEGETABLE_NUTRITIONAL_COUNT) {
            setOfMainTypes.add(MainIngredientType.VEGETABLE);
        }

        if (this.fruitCount >= MIN_FRUIT_NUTRITIONAL_COUNT) {
            setOfMainTypes.add(MainIngredientType.FRUIT);
        }

        if (this.proteinCount >= MIN_PROTEIN_NUTRITIONAL_COUNT) {
            setOfMainTypes.add(MainIngredientType.PROTEIN);
        }

        if (this.grainCount >= MIN_GRAIN_NUTRITIONAL_COUNT) {
            setOfMainTypes.add(MainIngredientType.GRAIN);
        }
        return setOfMainTypes;
    }

    public Set<MainIngredientType> getMainTypes() {
        return this.mainIngredientTypes;
    }

    @Override
    public String toString() {
        return String.format("vegetables: %.2f, fruits: %.2f, protein: %.2f, grains: %.2f \n",
                vegCount, fruitCount, proteinCount, grainCount);
    }
}
