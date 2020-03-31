package fithelper.model.calorietable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The table storing pre-defined data about calorie consumption of a food
 */
public class SportsCalorieTable implements CalorieTable {
    private Set<SportsCalorieEntry> entries = new HashSet<>();

    public SportsCalorieTable() {
        String data = SportsCalorieDataUtil.DATA;
        String[] listOfEntries = data.split("//");
        for (String entry : listOfEntries) {
            String[] attributes = entry.split("/");
            if (attributes.length == 2) {
                entries.add(new SportsCalorieEntry(attributes[0], attributes[1]));
            }
        }
    }

    public Set<SportsCalorieEntry> getEntries() {
        return entries;
    }
}
