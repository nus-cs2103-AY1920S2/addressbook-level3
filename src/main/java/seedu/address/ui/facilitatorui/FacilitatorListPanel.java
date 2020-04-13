package seedu.address.ui.facilitatorui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.facilitator.Facilitator;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of facilitators.
 */
public class FacilitatorListPanel extends UiPart<Region> {
    private static final String FXML = "FacilitatorListPanel.fxml";

    @FXML
    private ListView<Facilitator> facilitatorListView;

    public FacilitatorListPanel(ObservableList<Facilitator> facilitatorList) {
        super(FXML);
        facilitatorListView.setItems(facilitatorList);
        facilitatorListView.setCellFactory(listView -> new FacilitatorListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Facilitator} using a {@code FacilitatorCard}.
     */
    class FacilitatorListViewCell extends ListCell<Facilitator> {
        @Override
        protected void updateItem(Facilitator facilitator, boolean empty) {
            super.updateItem(facilitator, empty);

            if (empty || facilitator == null) {
                setGraphic(null);
                setText(null);
                setStyle("-fx-background-color: transparent;");
            } else {
                setGraphic(new FacilitatorCard(facilitator, getIndex() + 1).getRoot());
                setStyle("-fx-background-color: #865E6E; -fx-background-radius: 15px; "
                        + "-fx-background-insets: 3px, 0px; -fx-padding: 2px");

            }
        }
    }

}
