package fithelper.ui.calendar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import fithelper.commons.util.EntriesUtil;
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
public class DailyStatusList extends UiPart<AnchorPane> {
    private static final String FXML = "DailyStatusList.fxml";

    @FXML
    private Label listTitle;

    @FXML
    private ListView<ObservableList<Entry>> daysListView;

    private LocalDate time;
    private ObservableList<Entry> combined;
    private ObservableList<ObservableList<Entry>> entries;

    public DailyStatusList(ObservableList<Entry> foodList, ObservableList<Entry> sportList, LocalDate
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
        Map<LocalDate, ObservableList<Entry>> entriesByDate = EntriesUtil.getEntriesByDate(combined);
        entries = EntriesUtil.setEntriesByDate(entriesByDate);
        daysListView.setItems(entries);
        daysListView.setCellFactory(listView -> new DailyStatusList.ListViewCell());
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
                setGraphic(new DailyStatus(entries, temp).getRoot());
            }
        }
    }
}
