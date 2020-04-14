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
public class SportsListPanel extends UiPart<Region> {
    private static final String FXML = "SportsListPanel.fxml";

    @FXML
    private ListView<Entry> sportsListView;

    public SportsListPanel(ObservableList<Entry> sportsList) {
        super(FXML);
        sportsListView.setItems(sportsList);
        sportsListView.setCellFactory(listView -> new SportsListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Entry} using a {@code SportsCard}.
     */
    class SportsListViewCell extends ListCell<Entry> {
        @Override
        protected void updateItem(Entry sports, boolean empty) {
            super.updateItem(sports, empty);

            if (empty || sports == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new SportsCard(sports).getRoot());
            }
        }
    }

}
