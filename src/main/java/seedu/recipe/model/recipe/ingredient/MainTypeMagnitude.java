package seedu.recipe.model.recipe.ingredient;

public class MainTypeMagnitude {

    private double vegCount = 0;
    private double fruitCount = 0;
    private double proteinCount = 0;
    private  double grainCount = 0;
    private int index;

    public MainTypeMagnitude( int index, double vegCount, double fruitCount, double proteinCount, double grainCount) {
        this.fruitCount = fruitCount;
        this.grainCount = grainCount;
        this.proteinCount = proteinCount;
        this.vegCount = vegCount;
        this.index = index;
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

    @Override
    public String toString() {
        return String.format("vegetables: %.2f, fruits: %.2f, protein: %.2f, grains: %.2f \n",
                vegCount, fruitCount, proteinCount, grainCount);
    }
}
