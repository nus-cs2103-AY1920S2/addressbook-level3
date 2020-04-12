package nasa.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import nasa.model.module.Module;
import nasa.ui.activity.DeadlineListPanel;
import nasa.ui.activity.EventListPanel;

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
    private DeadlineListPanel deadlineListPanel;
    private EventListPanel eventListPanel;

    @FXML
    private Pane cardPane;
    @FXML
    private Label code;
    @FXML
    private Label events;
    @FXML
    private Label deadlines;
    @FXML
    private VBox activityListPanelPlaceholder;

    public ModuleCard(Module module, int displayedIndex) {
        super(FXML);
        this.module = module;
        this.id = displayedIndex;
        code.setText(module.getModuleCode().toString() + " " + module.getModuleName().toString());
        deadlineListPanel = new DeadlineListPanel(module.getFilteredDeadlineList());
        eventListPanel = new EventListPanel(module.getFilteredEventList());
        activityListPanelPlaceholder.getChildren().add(eventListPanel.getRoot());
        activityListPanelPlaceholder.getChildren().add(deadlineListPanel.getRoot());
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
