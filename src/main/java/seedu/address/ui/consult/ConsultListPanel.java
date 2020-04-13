package seedu.address.ui.consult;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.event.consult.Consult;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of consults.
 */
public class ConsultListPanel extends UiPart<Region> {
    private static final String FXML = "ConsultListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ConsultListPanel.class);

    @javafx.fxml.FXML
    private ListView<Consult> consultListView;

    public ConsultListPanel(ObservableList<Consult> consultList) {
        super(FXML);
        consultListView.setItems(consultList);
        consultListView.setCellFactory(listView -> new ConsultListPanel.ConsultListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Consult} using a {@code ConsultCard}.
     */
    class ConsultListViewCell extends ListCell<Consult> {
        @Override
        protected void updateItem(Consult consult, boolean empty) {
            super.updateItem(consult, empty);

            if (empty || consult == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ConsultCard(consult, getIndex() + 1).getRoot());
            }
        }
    }
}
