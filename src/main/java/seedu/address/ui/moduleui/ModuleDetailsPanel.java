package seedu.address.ui.moduleui;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import seedu.address.model.module.Module;
import seedu.address.ui.UiPart;

/**
 * A ui for the module details bar that is displayed at the top of the module tab.
 */
public class ModuleDetailsPanel extends UiPart<Region> {
    private static final String FXML = "ModuleDetailsPanel.fxml";

    @FXML
    private Label moduleDetails;

    public ModuleDetailsPanel() {
        super(FXML);
        moduleDetails.setText("");
        GridPane.setHalignment(moduleDetails, HPos.CENTER);
    }

    /**
     * Sets the module code and description of the module to be displayed.
     */
    public void changeDisplayModule(Optional<Module> module) {
        if (module.isEmpty()) {
            moduleDetails.setText("");
        } else {
            if (module.get().getDescription().value == null) {
                moduleDetails.setText(module.get().getModuleCode().value);
            } else {
                moduleDetails.setText(module.get().getModuleCode().value + " " + module.get().getDescription().value);
            }
        }
    }
}
