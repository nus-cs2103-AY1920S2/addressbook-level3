package seedu.recipe.model.recipe.ingredient;

import java.util.Set;
import java.util.TreeSet;

public class MainTypeMagnitude {

    private double vegCount = 0;
    private double fruitCount = 0;
    private double proteinCount = 0;
    private  double grainCount = 0;
    private int index;
    Set<MainIngredientType> mainTypes;

    public MainTypeMagnitude( int index, double vegCount, double fruitCount, double proteinCount, double grainCount) {
        this.fruitCount = fruitCount;
        this.grainCount = grainCount;
        this.proteinCount = proteinCount;
        this.vegCount = vegCount;
        this.index = index;
        mainTypes = getMainIngredientTypes();
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

    public int getIndex() {
        return this.index;
    }

    public void updateIndex(int index) {
        this.index = index;
    }

    /**
     * Finding main ingredient types that meet nutrition requirement for each type according to
     * https://www.nia.nih.gov/health/serving-and-portion-sizes-how-much-should-i-eat.
     * @return set of MainIngredientTypes
     */
    private Set<MainIngredientType> getMainIngredientTypes() {
        Set<MainIngredientType> setOfMainTypes = new TreeSet<MainIngredientType>();
        if(this.vegCount > 300) {
            setOfMainTypes.add(MainIngredientType.VEGETABLE);
        }

        if(this.fruitCount > 300) {
            setOfMainTypes.add(MainIngredientType.FRUIT);
        }

        if(this.proteinCount > 200) {
            setOfMainTypes.add(MainIngredientType.PROTEIN);
        }

        if(this.grainCount > 145) {
            setOfMainTypes.add(MainIngredientType.GRAIN);
        }

        return setOfMainTypes;
    }

    public Set<MainIngredientType> getMainTypes() {
        return this.mainTypes;
    }
    @Override
    public String toString() {
        return String.format("vegetables: %.2f, fruits: %.2f, protein: %.2f, grains: %.2f \n",
                vegCount, fruitCount, proteinCount, grainCount);
    }
}
