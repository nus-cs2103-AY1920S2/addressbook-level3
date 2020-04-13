package nasa.ui;

import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import nasa.commons.core.LogsCenter;
import nasa.model.module.Module;

/**
 * Panel containing the list of modules.
 */
public class ModuleListPanel extends UiPart<Region> {
    private static final String FXML = "ModuleListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ModuleListPanel.class);

    private ObservableList<Module> moduleObservableList;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private HBox moduleListView;
    @FXML
    private Label noModules;


    public ModuleListPanel(ObservableList<Module> moduleObservableList) {
        super(FXML);
        this.moduleObservableList = moduleObservableList;
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        if (!moduleObservableList.isEmpty()) {
            noModules.setManaged(false);
        }

        moduleObservableList.addListener(new ListChangeListener<Module>() {
            @Override
            public void onChanged(Change<? extends Module> c) {
                setModuleListView();
            }
        });
        setModuleListView();
    }


    public void setModuleListView() {
        moduleListView.getChildren().clear();
        if (moduleObservableList.isEmpty()) {
            return;
        }
        int width = Math.max((int) scrollPane.getWidth() / moduleObservableList.size(), 275);
        for (Module module :moduleObservableList) {
            moduleListView.getChildren().add(new ModuleCard(module, width).getRoot());
        }
    }
}
