package tatracker.ui;

import static tatracker.model.TaTracker.getCurrentlyShownModule;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import tatracker.commons.core.LogsCenter;
import tatracker.model.module.Module;

/**
 * Panel containing the list of modules.
 */
public class ModuleListPanel extends UiPart<Region> {
    private static final String FXML = "ModuleListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ModuleListPanel.class);
    private Module currentlyShownModule = getCurrentlyShownModule();

    @FXML
    private ListView<Module> moduleListView;

    private static final String HIGHLIGHTED_CONTROL_INNER_BACKGROUND = "#424d5f";

    public ModuleListPanel(ObservableList<Module> moduleList) {
        super(FXML);
        moduleListView.setItems(moduleList);
        moduleListView.setCellFactory(listView -> new ModuleListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Module} using a {@code ModuleCard}.
     */
    class ModuleListViewCell extends ListCell<Module> {
        @Override
        protected void updateItem(Module module, boolean empty) {
            super.updateItem(module, empty);

            if (empty || module == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ModuleCard(module, getIndex() + 1).getRoot());
                if (module.equals(currentlyShownModule)) {
                    setStyle("-fx-background-color: #424d5f; -fx-border-color: #3e7b91; -fx-border-width: 1;");
                }
            }
        }
    }

}
