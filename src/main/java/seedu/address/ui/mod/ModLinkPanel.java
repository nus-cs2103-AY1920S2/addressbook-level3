package seedu.address.ui.mod;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.mod.ModLinkPair;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of consults.
 */
public class ModLinkPanel extends UiPart<Region> {
    private static final String FXML = "ModLinkPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ModLinkPanel.class);

    @FXML
    private ListView<ModLinkPair> modLinkListView;

    public ModLinkPanel(ObservableList<ModLinkPair> modLinkList) {
        super(FXML);
        requireNonNull(modLinkList);
        modLinkListView.setItems(modLinkList);
        modLinkListView.setCellFactory(modLinkListView -> new ModLinkViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the info of a {@code Pair<String, ModLink>} using a {@code ModLinkCard}.
     */
    class ModLinkViewCell extends ListCell<ModLinkPair> {
        @Override
        protected void updateItem(ModLinkPair modLinkPair, boolean empty) {
            super.updateItem(modLinkPair, empty);

            if (empty || modLinkPair == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ModLinkCard(modLinkPair, getIndex() + 1).getRoot());
            }
        }
    }
}
