package modulo.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import modulo.model.module.Module;

/**
 * An UI component that displays information of a {@code Module}.
 */
public class ModuleCard extends UiPart<Region> {

    private static final String FXML = "ModuleListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX. As a consequence, UI
     * elements' variable names cannot be set to such keywords or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Module module;

    @FXML
    private Label id;
    @FXML
    private HBox moduleCardPane;
    @FXML
    private Label moduleName;
    @FXML
    private Label moduleAcadYear;
    @FXML
    private Label moduleCode;

    public ModuleCard(Module module, int displayedIndex, String tagColorClass) {
        super(FXML);
        this.module = module;
        id.setText(displayedIndex + ". ");
        moduleName.setText(module.getName().toString());
        moduleAcadYear.setText(module.getAcademicYear().toModuleCardFormat());
        moduleCode.setText(module.getModuleCode().toString());
        moduleCode.getStyleClass().clear();
        moduleCode.getStyleClass().addAll(tagColorClass, "moduleCode");
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

        // state check
        ModuleCard card = (ModuleCard) other;
        return id.getText().equals(card.id.getText())
                && module.equals(card.module);
    }
}
