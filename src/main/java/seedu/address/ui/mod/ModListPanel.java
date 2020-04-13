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
 * Panel containing the list of Mods.
 */
public class ModListPanel extends UiPart<Region> {

    private static final String FXML = "ModListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ModListPanel.class);

    @FXML
    private ListView<Mod> modListView;

    public ModListPanel(ObservableList<Mod> modList) {
        super(FXML);
        modListView.setItems(modList);
        modListView.setCellFactory(listView -> new ModListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Mod} using a {@code ModCard}.
     */
    class ModListViewCell extends ListCell<Mod> {
        @Override
        protected void updateItem(Mod mod, boolean empty) {
            super.updateItem(mod, empty);

            if (empty || mod == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ModCard(mod, getIndex() + 1).getRoot());
            }
        }
    }
}
