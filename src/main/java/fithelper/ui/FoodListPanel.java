package fithelper.ui;

import fithelper.model.entry.Entry;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of persons.
 */
public class FoodListPanel extends UiPart<Region> {
    private static final String FXML = "FoodListPanel.fxml";

    @FXML
    private ListView<Entry> foodListView;

    public FoodListPanel(ObservableList<Entry> foodList) {
        super(FXML);
        foodListView.setItems(foodList);
        foodListView.setCellFactory(listView -> new FoodListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Entry} using a {@code FoodCard}.
     */
    class FoodListViewCell extends ListCell<Entry> {
        @Override
        protected void updateItem(Entry food, boolean empty) {
            super.updateItem(food, empty);

            if (empty || food == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new FoodCard(food).getRoot());
            }
        }
    }

}
