package fithelper.model.calorietable;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The table storing pre-defined data about calorie consumption of a food
 */
public class SportsCalorieTable implements CalorieTable {
    private Set<SportsCalorieDatum> table = new LinkedHashSet<>();

    public SportsCalorieTable() {
        String data = SportsCalorieDataUtil.DATA;
        String[] listOfData = data.split("\n");
        for (String datum : listOfData) {
            String[] attributes = datum.split("\t");
            if (attributes.length == 2) {
                table.add(new SportsCalorieDatum(attributes[0], attributes[1]));
            }
        }
    }

    public Set<SportsCalorieDatum> getData() {
        return table;
    }
}
