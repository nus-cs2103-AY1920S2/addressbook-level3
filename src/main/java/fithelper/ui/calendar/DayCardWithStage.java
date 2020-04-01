package fithelper.ui.calendar;

import java.time.LocalDate;

import fithelper.model.calculator.CalorieCalculator;
import fithelper.model.entry.Entry;
import fithelper.ui.UiPart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * A section which displays entries of a day.
 */
public class DayCardWithStage extends UiPart<Stage> {
    private static final String FXML = "DayCardWithStage.fxml";
    private ObservableList<Entry> entries;
    private CalorieCalculator stats;

    @FXML
    private Label dayTitle;

    @FXML
    private Label statsNo;

    @FXML
    private ListView<Entry> listView;

    public DayCardWithStage(Stage root) {
        super(FXML, root);
    }
    /**
     * Creates a new DayPage.
     */
    public DayCardWithStage() {
        this(new Stage());
    }

    public DayCardWithStage(ObservableList<Entry> entriesToSet, LocalDate dateToSet) {
        super(FXML);
        LocalDate time = dateToSet;
        dayTitle.setText(time.toString());
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
