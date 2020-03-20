package fithelper.model.statistics;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import fithelper.model.entry.Entry;

import javafx.collections.ObservableList;

/**
 * GenerateS relevant stats based on foodList and sportsList
 */
public class StatsGenerator {
    private ObservableList<Entry> foodList;
    private ObservableList<Entry> sportsList;
    private LocalDate startDate;
    private LocalDate endDate;

    public StatsGenerator(ObservableList< Entry > foodList, ObservableList<Entry> sportsList,
                          LocalDate startDate, LocalDate endDate) {
        this.foodList = foodList;
        this.sportsList = sportsList;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public HashMap<LocalDate, Double> getDailyCalorie() {
        ArrayList<Entry> filteredList = getFilteredList();
        HashMap<LocalDate, Double> calories = new HashMap<>();
        for (Entry entry: filteredList) {
            if (calories.containsKey(entry.getDate())) {
                if (entry.getType().toString().equals("food")) {
                    calories.put(entry.getDate(), calories.get(entry.getDate()) + entry.getCalorieValue());
                } else {
                    calories.put(entry.getDate(), calories.get(entry.getDate()) - entry.getCalorieValue());
                }
            } else {
                if (entry.getType().toString().equals("food")) {
                    calories.put(entry.getDate(), entry.getCalorieValue());
                } else {
                    calories.put(entry.getDate(), -entry.getCalorieValue());
                }
            }
        }
        return calories;
    }

    /**
     * Get all entries between the start date and the end date.
     */
    private ArrayList<Entry> getFilteredList() {
        ArrayList<Entry> filteredList = new ArrayList<>();
        for (Entry entry: sportsList) {
            LocalDate date = entry.getDate();
            if (!date.isBefore(startDate) && !date.isAfter(endDate) && entry.getStatus().toString().equals("Done")) {
                filteredList.add(entry);
            }
        }
        for (Entry entry: foodList) {
            LocalDate date = entry.getDate();
            if (!date.isBefore(startDate) && !date.isAfter(endDate) && entry.getStatus().toString().equals("Done")) {
                filteredList.add(entry);
            }
        }
        return filteredList;
    }
}
