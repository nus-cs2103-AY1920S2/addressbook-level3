package fithelper.ui.calendar;

import java.time.LocalDateTime;

import fithelper.model.entry.Entry;
import fithelper.ui.UiPart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;

/**
 * A section which displays upcoming tasks.
 */
public class UpcomingList extends UiPart<StackPane> {
    private static final String FXML = "UpcomingList.fxml";
    private ObservableList<Entry> combined;
    private LocalDateTime time;

    @FXML
    private Label upcomingTitle;

    @FXML
    private ListView<Entry> listView;


    public UpcomingList(ObservableList<Entry> foodList, ObservableList<Entry> sportList, LocalDateTime dateToSet) {
        super(FXML);
        time = dateToSet;
        upcomingTitle.setText("Upcoming in " + time.getMonth());
        combined = FXCollections.observableArrayList();
        initializeListView(foodList, sportList);
    }

    /**
     * Constructs ListView Cell class.
     */
    static class ListViewCell extends ListCell<Entry> {
        @Override
        protected void updateItem(Entry entry, boolean empty) {
            super.updateItem(entry, empty);
            updateSelected(false);
            if (empty || entry == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EntryCardSmallerWidth(entry, getIndex() + 1).getRoot());
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
        addFilteredEntries(foodList);
        addFilteredEntries(sportList);
        listView.setItems(combined);
        listView.setCellFactory(listView -> new UpcomingList.ListViewCell());
    }

    /**
     * Add filtered entries to combined list
     * @param list the list to be filterd, can be either food or sports type
     */
    public void addFilteredEntries(ObservableList<Entry> list) {
        for (Entry entry : list) {
            if (LocalDateTime.now().isBefore(entry.getDateTime())
                    && time.getMonth().equals(entry.getDateTime().getMonth())
                    && !entry.isDone()) {
                combined.add(entry);
            }
        }
    }
}
