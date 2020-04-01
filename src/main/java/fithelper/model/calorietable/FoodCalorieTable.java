package fithelper.model.calorietable;

import java.util.HashSet;
import java.util.Set;

/**
 * The table storing pre-defined data about calorie intake of a food
 */
public class FoodCalorieTable implements CalorieTable {
    private Set<FoodCalorieEntry> entries = new HashSet<>();

    public FoodCalorieTable() {
        String data = FoodCalorieDataUtil.DATA;
        String[] listOfEntries = data.split("\n");
        for (String entry : listOfEntries) {
            String[] attributes = entry.split("\t");
            if (attributes.length == 3) {
                entries.add(new FoodCalorieEntry(attributes[0], attributes[2], attributes[1]));
            }
        }
    }

    public Set<FoodCalorieEntry> getEntries() {
        return entries;
    }
}
