package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.nusmodule.NusModule;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class ModuleCard extends UiPart<Region> {

    private static final String FXML = "ModuleTaken.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final NusModule module;

    @FXML
    private HBox cardPane;
    @FXML
    private Label moduleName;
    @FXML
    private Label grade;
    @FXML
    private GridPane grid;

    public ModuleCard(NusModule module) {
        super(FXML);
        this.module = module;
        moduleName.setText(module.getModuleCode().toString());

        if (!module.getGrade().isEmpty()) {
            grade.setText(module.getGrade().get().getText());
        } else {
            grade.setText("");
        }

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModuleCard)) {
            return false;
        }
        return false;
    }
}
