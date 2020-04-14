package fithelper.ui.home;

import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.model.entry.Entry;
import fithelper.ui.FoodCard;
import fithelper.ui.ReminderCard;
import fithelper.ui.SportCard;
import fithelper.ui.UiPart;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

/**
 * Controller class for dash board.
 * An order page contains order cards and a statistics bar.
 */
public class DashBoard extends UiPart<AnchorPane> {
    private static final String FXML = "DashBoard.fxml";
    private final Logger logger = LogsCenter.getLogger(DashBoard.class);

    @FXML
    private ListView<Entry> foodListView;

    @FXML
    private ListView<Entry> sportListView;

    @FXML
    private ListView<Entry> reminderListView;



    /**
     * Creates an order page displaying entries from {@code entryList}.
     */
    public DashBoard(ObservableList<Entry> foodList, ObservableList<Entry> sportList,
                     ObservableList<Entry> reminderList) {
        super(FXML);

        logger.info("Initializing Dash Board");

        initializeFoodListView(foodList);
        initializeSportListView(sportList);
        initializeReminderListView(reminderList);

    }

    /**
     * Initializes the list view.
     * @param foodList an observable list of food entries
     */
    private void initializeFoodListView(ObservableList<Entry> foodList) {
        foodListView.setItems(foodList);
        foodListView.setCellFactory(listView -> new FoodListViewCell());
    }

    /**
     * Initializes the list view.
     * @param sportList an observable list of sports entries
     */
    private void initializeSportListView(ObservableList<Entry> sportList) {
        sportListView.setItems(sportList);
        sportListView.setCellFactory(listView -> new SportListViewCell());
    }

    /**
     * Initializes the list view.
     * @param reminderList an observable list of sports entries
     */
    private void initializeReminderListView(ObservableList<Entry> reminderList) {
        reminderListView.setItems(reminderList);
        reminderListView.setCellFactory(listView -> new ReminderListViewCell());
    }

    /**
     * Constructs foodListView Cell class.
     */
    static class FoodListViewCell extends ListCell<Entry> {
        @Override
        protected void updateItem(Entry entry, boolean empty) {
            super.updateItem(entry, empty);
            updateSelected(false);
            if (empty || entry == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new FoodCard(entry, getIndex() + 1).getRoot());
            }
        }
    }

    /**
     * Constructs sportListView Cell class.
     */
    static class SportListViewCell extends ListCell<Entry> {
        @Override
        protected void updateItem(Entry sport, boolean empty) {
            super.updateItem(sport, empty);
            updateSelected(false);
            if (empty || sport == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new SportCard(sport, getIndex() + 1).getRoot());
            }
        }
    }

    /**
     * Constructs sportListView Cell class.
     */
    static class ReminderListViewCell extends ListCell<Entry> {
        @Override
        protected void updateItem(Entry reminder, boolean empty) {
            super.updateItem(reminder, empty);
            updateSelected(false);
            if (empty || reminder == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ReminderCard(reminder, getIndex() + 1).getRoot());
            }
        }
    }
}
