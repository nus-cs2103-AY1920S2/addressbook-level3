package seedu.recipe.model.recipe.ingredient;

public class MainTypeMagnitude {

    private double vegCount = 0;
    private double fruitCount = 0;
    private double proteinCount = 0;
    private  double grainCount = 0;

    public MainTypeMagnitude(double vegCount, double fruitCount, double proteinCount, double grainCount) {
        this.fruitCount = fruitCount;
        this.grainCount = grainCount;
        this.proteinCount = proteinCount;
        this.vegCount = vegCount;
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
}
