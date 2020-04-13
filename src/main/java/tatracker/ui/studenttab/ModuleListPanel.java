//@@author fatin99

package tatracker.ui.studenttab;

import static tatracker.model.TaTracker.getCurrentlyShownModule;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import tatracker.commons.core.LogsCenter;
import tatracker.model.module.Module;
import tatracker.ui.Focusable;
import tatracker.ui.UiPart;

/**
 * Panel containing the list of modules.
 */
public class ModuleListPanel extends UiPart<Region> implements Focusable {
    private static final String FXML = "ModuleListPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(ModuleListPanel.class);

    @FXML
    private ListView<Module> moduleListView;

    public ModuleListPanel(ObservableList<Module> moduleList) {
        super(FXML);
        moduleListView.setItems(moduleList);
        moduleListView.setCellFactory(listView -> new ModuleListViewCell());
        moduleListView.focusedProperty().addListener((arg, oldVal, focused) -> {
            if (focused) {
                moduleListView.setStyle("-fx-border-color: #264780; -fx-border-width: 1;");
            } else {
                moduleListView.setStyle("");
            }
        });
    }

    @Override
    public void requestFocus() {
        moduleListView.requestFocus();
    }

    @Override
    public boolean isFocused() {
        return moduleListView.isFocused();
    }

    /**
     * Update ListCells in order to facilitate highlighting when a filter command is entered
     * @param moduleList the updated moduleList
     */
    public void updateCells(ObservableList<Module> moduleList) {
        logger.fine("reached updateCells");
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
            getStyleClass().removeAll("filtered", "list-cell");

            if (empty || module == null) {
                setGraphic(null);
                setText(null);
                getStyleClass().add("list-cell");
                setStyle("");
            } else {
                setGraphic(new ModuleCard(module, getIndex() + 1).getRoot());
                if (module.equals(getCurrentlyShownModule())) {
                    getStyleClass().add("filtered");
                } else {
                    getStyleClass().add("list-cell");
                    setStyle("");
                }
            }
        }
    }
}
