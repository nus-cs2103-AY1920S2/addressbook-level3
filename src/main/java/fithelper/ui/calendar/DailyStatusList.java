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
 * A section which displays upcoming tasks.
 */
public class DailyStatusList extends UiPart<AnchorPane> {
    private static final String FXML = "DailyStatusList.fxml";
    private ObservableList<Entry> combined;
    private LocalDateTime time;
    private ObservableList<ObservableList<Entry>> entries;

    @FXML
    private Label dailyStatusTitle;

    @FXML
    private ListView<ObservableList<Entry>> dailyStatusList;


    public DailyStatusList(ObservableList<Entry> foodList, ObservableList<Entry> sportList, LocalDateTime dateToSet) {
        super(FXML);
        time = dateToSet;
        dailyStatusTitle.setText("Daily Status in " + time.getMonth());
        combined = FXCollections.observableArrayList();
        initializeListView(foodList, sportList);
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
                setGraphic(new DailyStatus(entries, entries.get(0).getDateTime()).getRoot());
            }
        }
    }

    /**
     * Initializes the list view.
     *
     * @param foodList  an observable list of food entries
     * @param sportList an observable list of sport entries
     */
    private void initializeListView(ObservableList<Entry> foodList, ObservableList<Entry> sportList) {
        addFilteredEntries(sportList);
        addFilteredEntries(foodList);
        Map<LocalDate, ObservableList<Entry>> entriesByDate = EntriesUtil.getEntriesByDate(combined);
        entries = EntriesUtil.setEntriesByDate(entriesByDate);
        dailyStatusList.setItems(entries);
        //dailyStatusList.setCellFactory(listView -> new DailyStatus(entries, LocalDateTime.now()));
    }

    /**
     * Add filtered entries to combined list
     * @param list the list to be filterd, can be either food or sports type
     */
    public void addFilteredEntries(ObservableList<Entry> list) {
        for (Entry entry : list) {
            if (time.getMonth().equals(entry.getDateTime().getMonth())) {
                combined.add(entry);
            }
        }
    }
}

