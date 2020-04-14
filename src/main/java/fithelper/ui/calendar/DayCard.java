package fithelper.ui.calendar;

import java.time.LocalDateTime;

import fithelper.model.calculator.CalorieCalculator;
import fithelper.model.entry.Entry;
import fithelper.ui.UiPart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/**
 * A section which displays entries of a day.
 */
public class DayCard extends UiPart<AnchorPane> {
    private static final String FXML = "DayCard.fxml";
    private ObservableList<Entry> entries;
    private CalorieCalculator stats;

    @FXML
    private Label dayTitle;

    @FXML
    private Label statsNo;

    @FXML
    private ListView<Entry> listView;

    public DayCard(ObservableList<Entry> entriesToSet, LocalDateTime dateToSet) {
        super(FXML);
        LocalDateTime time = dateToSet;
        dayTitle.setText(time.toLocalDate().toString());
        entries = FXCollections.observableArrayList();
        entries = entriesToSet;
        getGenerator();
        statsNo.setText("Calorie: " + String.valueOf(stats.getTotalCalorie()));
        if (stats.getTotalCalorie() > 0) {
            statsNo.setTextFill(Color.RED);
        } else if (stats.getTotalCalorie() < 0) {
            statsNo.setTextFill(Color.GREEN);
        }
        initializeListView(entries);
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
                setGraphic(new EntryCard(entry, getIndex() + 1).getRoot());
            }
        }
    }

    /**
     * Initializes the list view.
     *
     * @param entries an observable list of food and sports entries of a day
     */
    private void initializeListView(ObservableList<Entry> entries) {
        listView.setItems(entries);
        listView.setCellFactory(listView -> new DayCard.ListViewCell());
    }

    public void getGenerator() {
        stats = new CalorieCalculator(entries);
    }
}
