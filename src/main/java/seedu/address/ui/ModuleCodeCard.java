package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.profile.course.module.ModuleCode;

/**
 * An UI component that displays information of a {@code ModuleCode}.
 */
public class ModuleCodeCard extends UiPart<Region> {

    private static final String FXML = "ModuleCodeCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label moduleCode;


    public ModuleCodeCard(ModuleCode mc) {
        super(FXML);

        moduleCode.setText(mc.toString());
    }

}
