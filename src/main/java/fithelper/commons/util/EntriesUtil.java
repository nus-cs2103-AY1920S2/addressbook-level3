package fithelper.commons.util;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fithelper.model.entry.Entry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Gets entries by date
 */
public class EntriesUtil {
    public static ObservableList<ObservableList<Entry>> setEntriesByDate(Map<LocalDate,
            ObservableList<Entry>> entriesByDate) {
        ObservableList<ObservableList<Entry>> entries = FXCollections.observableArrayList();
        Set<LocalDate> uniqueDateSet = new HashSet<>();
        uniqueDateSet.addAll(entriesByDate.keySet());
        List<LocalDate> sortedDates = new ArrayList<>();
        sortedDates.addAll(uniqueDateSet);
        java.util.Collections.sort(sortedDates);
        for (LocalDate dateTime : sortedDates) {
            ObservableList<Entry> temp = FXCollections.observableArrayList();
            temp.addAll(entriesByDate.get(dateTime));
            entries.add(temp);
        }
        return entries;
    }

    /**
     * Maps each entry by its date
     */
    public static Map<LocalDate, ObservableList<Entry>> getEntriesByDate (ObservableList<Entry> entries) {
        Map<LocalDate, ObservableList<Entry>> entriesByDate = new HashMap<>();
        for (Entry entry : entries) {
            LocalDate date = entry.getDate();
            requireNonNull(date);
            if (!entriesByDate.containsKey(date)) {
                entriesByDate.put(date, FXCollections.observableArrayList());
            }
            entriesByDate.get(date).add(entry);
        }
        return entriesByDate;
    }

    /**
     * Return a list of all entries of a particular date
     */
    public static ObservableList<Entry> getEntries(ObservableList<Entry>foodList,
                                                   ObservableList<Entry>sportsList, LocalDate date) {
        ObservableList<Entry> temp = FXCollections.observableArrayList();
        for (Entry entry: foodList) {
            if (entry.getDate().equals(date)) {
                temp.add(entry);
            }
        }
        for (Entry entry: sportsList) {
            if (entry.getDate().equals(date)) {
                temp.add(entry);
            }
        }
        return temp;
    }
}

