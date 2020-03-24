package fithelper.ui.calendar;

import fithelper.model.calculator.CalorieCalculatorByDateRange;
import fithelper.model.entry.Entry;
import fithelper.ui.calendar.EntryCard;
import fithelper.ui.UiPart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

import static java.util.Objects.requireNonNull;

public class EntryList extends UiPart<AnchorPane> {
    @FXML
    private Label listTitle;

    @FXML
    private ListView<ListView<Entry>> entryListView;

    private static final String FXML = "entryList.fxml";
    private LocalDateTime time;
    private ObservableList<Entry> combined;

    public EntryList(ObservableList<Entry> foodList, ObservableList<Entry> sportList, LocalDateTime dateToSet, CalorieCalculatorByDateRange stats) {
        super(FXML);
        time = dateToSet;
        listTitle.setText(time.getMonth().toString());
        combined = FXCollections.observableArrayList();
        initialiseEntries(foodList, sportList);
    }

    private void initialiseEntries(ObservableList<Entry> foodList, ObservableList<Entry> sportList) {
        addFilteredEntries(sportList);
        addFilteredEntries(foodList);
        Map<LocalDateTime, ArrayList<Entry>> entriesByDate = getEntriesByDate(combined);
        Set<LocalDateTime> uniqueDateSet = new HashSet<>();
        uniqueDateSet.addAll(entriesByDate.keySet());
        List<LocalDateTime> sortedDates = new ArrayList<>();
        sortedDates.addAll(uniqueDateSet);
        java.util.Collections.sort(sortedDates);

        for (LocalDateTime dateTime : sortedDates) {
            List<Entry> entries = entriesByDate.get(dateTime);
        }
    }

    private Map<LocalDateTime, ArrayList<Entry>> getEntriesByDate(List<Entry> entries) {
        Map<LocalDateTime, ArrayList<Entry>> entriesByDate = new HashMap<>();

        for (Entry entry: entries) {
            LocalDateTime date = entry.getDateTime();
            requireNonNull(date);
            if (!entriesByDate.containsKey(date)) {
                entriesByDate.put(date, new ArrayList<Entry>());
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
     * Custom {@code ListCell} that displays the graphics of a {@code Entry} using a {@code SportsCard}.
     */
    class EntryListViewCell extends ListCell<Entry> {
        @Override
        protected void updateItem(Entry sports, boolean empty) {
            super.updateItem(sports, empty);

            if (empty || sports == null) {
                setGraphic(null);
                setText(null);
            } else {
                //setGraphic(new EntryCard().getRoot());
            }
        }
    }

}
