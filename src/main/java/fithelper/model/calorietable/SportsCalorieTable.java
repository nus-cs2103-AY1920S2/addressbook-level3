package fithelper.model.calorietable;

import java.util.ArrayList;
import java.util.List;

/**
 * The table storing pre-defined data about calorie consumption of a food
 */
public class SportsCalorieTable implements CalorieTable {
    private List<SportsCalorieEntry> entries = new ArrayList<>();

    public SportsCalorieTable() {
        String data = SportsCalorieDataUtil.DATA;
        String[] listOfEntries = data.split("//");
        for (String entry : listOfEntries) {
            String[] attributes = entry.split("/");
            entries.add(new SportsCalorieEntry(attributes[0], attributes[1]));
        }
    }

    public List<SportsCalorieEntry> getEntries() {
        return entries;
    }
}
