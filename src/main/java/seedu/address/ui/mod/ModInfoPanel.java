package seedu.address.ui.mod;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.mod.Mod;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of consults.
 */
public class ModInfoPanel extends UiPart<Region> {
    private static final String FXML = "ModInfoPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ModInfoPanel.class);

    @FXML
    private ListView<Mod> modInfoListView;

    public ModInfoPanel(ObservableList<Mod> viewedModSingletonList) {
        super(FXML);
        modInfoListView.setItems(viewedModSingletonList);
        modInfoListView.setCellFactory(listView -> new ModInfoViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the info of a {@code Pair<String, ModLink>} using a {@code ModLinkCard}.
     */
    class ModInfoViewCell extends ListCell<Mod> {
        @Override
        protected void updateItem(Mod viewedMod, boolean empty) {
            super.updateItem(viewedMod, empty);

            if (empty || viewedMod == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ModInfoCard(viewedMod).getRoot());
            }
        }
    }
}

