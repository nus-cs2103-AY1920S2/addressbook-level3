package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.nusmodule.NusModule;

/**
 * Panel containing the list of persons.
 */
public class ModulesTakenListPanel extends UiPart<Region> {
    private static final String FXML = "ModulesTakenListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ModulesTakenListPanel.class);

    @FXML
    private ListView<NusModule> modulesTaken;

    public ModulesTakenListPanel(ObservableList<NusModule> moduleList) {
        super(FXML);
        modulesTaken.setItems(moduleList);
        modulesTaken.setCellFactory(listView -> new ModuleListViewCell());

    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class ModuleListViewCell extends ListCell<NusModule> {
        @Override
        protected void updateItem(NusModule module, boolean empty) {
            super.updateItem(module, empty);

            if (empty || module == null) {
                setGraphic(null);
                setText(null);

            } else {
                setGraphic((new ModuleCard(module)).getRoot());
            }

        }
    }

}
