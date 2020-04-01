package nasa.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import nasa.model.module.Module;
import nasa.ui.activity.ActivityListPanel;

/**
 * An UI component that displays information of a {@code Module}.
 */
public class ModuleCard extends UiPart<Region> {

    private static final String FXML = "ModuleListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Module module;
    public final int id;
    private ActivityListPanel activityListPanel;

    @FXML
    private Pane cardPane;
    @FXML
    private Label code;
    @FXML
    private StackPane activityListPanelPlaceholder;

    public ModuleCard(Module module, int displayedIndex) {
        super(FXML);
        this.module = module;
        this.id = displayedIndex;
        code.setText(module.getModuleCode().toString());
        activityListPanel = new ActivityListPanel(module.getFilteredActivityList());
        activityListPanelPlaceholder.getChildren().add(activityListPanel.getRoot());
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
        return id == card.id
                && module.equals(card.module);
    }
}
