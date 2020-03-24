package fithelper.model.calorietable;

import java.util.ArrayList;
import java.util.List;

/**
 * The table storing pre-defined data about calorie intake of a food
 */
public class FoodCalorieTable implements CalorieTable {
    private List<FoodCalorieEntry> entries = new ArrayList<>();

    public FoodCalorieTable() {
        String data = FoodCalorieDataUtil.DATA;
        String[] listOfEntries = data.split("//");
        for (String entry : listOfEntries) {
            String[] attributes = entry.split("/");
            if (attributes.length == 3) {
                entries.add(new FoodCalorieEntry(attributes[0], attributes[1], attributes[2]));
            }
        }
    }

    public List<FoodCalorieEntry> getEntries() {
        return entries;
    }
}
