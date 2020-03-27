package fithelper.ui.calendar;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fithelper.model.entry.Entry;
import fithelper.ui.UiPart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

/**
 * Display list of entries by dates
 */
public class DaysCard extends UiPart<AnchorPane> {
    private static final String FXML = "DaysListCard.fxml";

    @FXML
    private Label listTitle;

    @FXML
    private ListView<ObservableList<Entry>> daysListView;

    private LocalDateTime time;
    private ObservableList<Entry> combined;
    private ObservableList<ObservableList<Entry>> entries;

    public DaysCard(ObservableList<Entry> foodList, ObservableList<Entry> sportList, LocalDateTime
            dateToSet) {
        super(FXML);
        time = dateToSet;
        listTitle.setText(time.getMonth().toString());
        combined = FXCollections.observableArrayList();
        entries = FXCollections.observableArrayList();
        initialiseEntries(foodList, sportList);
    }

    /**
     * Initialises the entries for display
     */
    private void initialiseEntries(ObservableList<Entry> foodList, ObservableList<Entry> sportList) {
        addFilteredEntries(sportList);
        addFilteredEntries(foodList);
        Map<LocalDate, ObservableList<Entry>> entriesByDate = getEntriesByDate(combined);
        Set<LocalDate> uniqueDateSet = new HashSet<>();
        uniqueDateSet.addAll(entriesByDate.keySet());
        List<LocalDate> sortedDates = new ArrayList<>();
        sortedDates.addAll(uniqueDateSet);
        java.util.Collections.sort(sortedDates);
        for (LocalDate dateTime: sortedDates) {
            ObservableList<Entry> temp = FXCollections.observableArrayList();
            temp.addAll(entriesByDate.get(dateTime));
            entries.add(temp);
        }
        daysListView.setItems(entries);
        daysListView.setCellFactory(listView -> new DaysCard.ListViewCell());
    }

    /**
     * Maps each entry by its date
     */
    private Map<LocalDate, ObservableList<Entry>> getEntriesByDate(ObservableList<Entry> entries) {
        Map<LocalDate, ObservableList<Entry>> entriesByDate = new HashMap<>();

        for (Entry entry: entries) {
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
     * Add filtered entries to combined list
     * @param list the list to be filterd, can be either food or sports type
     */
    public void addFilteredEntries(ObservableList<Entry> list) {
        for (Entry entry : list) {
            if (time.getMonth().equals((entry.getDateTime().getMonth()))) {
                combined.add(entry);
            }
        }
    }

    /**
     * Constructs ListView Cell class.
     */
    static class ListViewCell extends ListCell<ObservableList<Entry>> {
        @Override
        protected void updateItem(ObservableList<Entry> entries, boolean empty) {
            super.updateItem(entries, empty);
            updateSelected(false);
            if (empty || entries == null) {
                setGraphic(null);
                setText(null);
            } else {
                LocalDateTime temp = entries.get(0).getDateTime();
                setGraphic(new DayCard(entries, temp).getRoot());
            }
        }
    }
}
