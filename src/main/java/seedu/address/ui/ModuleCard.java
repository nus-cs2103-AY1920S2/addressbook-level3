package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.model.module.Module;

/**
 * An UI component that displays information of a {@code Module}.
 */
public class ModuleCard extends UiPart<Region> {

    private static final String FXML = "ModuleListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     */

    public final Module module;

    @FXML
    private HBox cardPane;
    @FXML
    private Label code;
    @FXML
    private Label id;
    @FXML
    private Label title;
    @FXML
    private Label semester;
    @FXML
    private FlowPane tags;

    /**
     * Constructor for ModuleCard Class
     * @param module
     * @param displayedIndex
     */
    public ModuleCard(Module module, int displayedIndex) {
        super(FXML);
        this.module = module;
        id.setText(displayedIndex + ". ");
        code.setText(module.getModuleCode().value);
        title.setText(module.getModuleTitle());
        String builder = "Semesters: ";
        for (int i = 0; i < module.getSemesterName().size(); i++) {
            builder += module.getSemesterName().get(i).getSemester() + " ";
        }
        semester.setText(builder);
    }

    /**
     * Overrides Equal function to compare objects
     * @param other
     * @return
     */
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

    @FXML
    public void showDetails() {

        String moduleBuilder = "Module: " + module.getTitle() + "  " + module.getModuleTitle()
                + "\n\n" + "Department: " + module.getDepartment() + "\n\n" + "Description: " + module.getDescription();
        Label moduleDetails = new Label(moduleBuilder);
        moduleDetails.setWrapText(true);
        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(moduleDetails);

        Scene secondScene = new Scene(secondaryLayout, 500, 500);
        secondScene.getStylesheets().add(getClass().getResource("/view/DarkTheme.css").toExternalForm());
        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle(code.getText());
        newWindow.setScene(secondScene);

        newWindow.show();
    }
}

