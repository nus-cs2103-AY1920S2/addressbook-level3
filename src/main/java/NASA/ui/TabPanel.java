package nasa.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import nasa.commons.core.LogsCenter;
import nasa.logic.Logic;
import nasa.model.module.Module;

/**
 * Tab for modules.
 */
public class TabPanel extends UiPart<Region> {
    private static final String FXML = "TabPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TabPanel.class);

    private ModuleListPanel moduleListPanel;

    @FXML
    private StackPane moduleListPanelPlaceholder;
    @FXML
    private HBox moduleList;
    @FXML
    private TabPane tabPane;


    public TabPanel(Logic logic) {
        super(FXML);
        moduleListPanel = new ModuleListPanel(logic.getFilteredModuleList());
        moduleListPanelPlaceholder.getChildren().add(moduleListPanel.getRoot());
    }

    public void next() {
        if(tabPane.getSelectionModel().isSelected(tabPane.getTabs().size() - 1)) {
            tabPane.getSelectionModel().selectFirst();
        } else {
            tabPane.getSelectionModel().selectNext();
        }
    }

    public ModuleListPanel getModuleListPanel() {
        return moduleListPanel;
    }

}
