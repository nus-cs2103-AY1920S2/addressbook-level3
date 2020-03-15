package NASA.ui;

import NASA.model.activity.Activity;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import NASA.model.module.Module;
import javafx.scene.layout.VBox;

import java.util.Comparator;

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
    @FXML
    private VBox cardPane;
    @FXML
    private Label code;
    @FXML
    private FlowPane activities;

    public ModuleCard(Module module, int displayedIndex) {
        super(FXML);
        this.module = module;
        code.setText(module.getModuleCode().moduleCode);
        module.getActivities().asUnmodifiableObservableList().stream()
                .sorted(Comparator.comparing(activity -> activity.getName().toString()))
                .forEach(activity -> activities.getChildren().add(new Label(((Activity) activity).getName().toString())));
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
        return code.getText().equals(card.code.getText())
                && module.equals(card.module);
    }
}
