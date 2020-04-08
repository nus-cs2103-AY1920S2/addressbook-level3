package fithelper.model.calorietable;

import java.util.HashSet;
import java.util.Set;

/**
 * The table storing pre-defined data about calorie intake of a food
 */
public class FoodCalorieTable implements CalorieTable {
    private Set<FoodCalorieDatum> table = new HashSet<>();

    public FoodCalorieTable() {
        String data = FoodCalorieDataUtil.DATA;
        String[] listOfData = data.split("\n");
        for (String datum : listOfData) {
            String[] attributes = datum.split("\t");
            if (attributes.length == 3) {
                table.add(new FoodCalorieDatum(attributes[0], attributes[2], attributes[1]));
            }
        }
    }

    public Set<FoodCalorieDatum> getData() {
        return table;
    }
}
