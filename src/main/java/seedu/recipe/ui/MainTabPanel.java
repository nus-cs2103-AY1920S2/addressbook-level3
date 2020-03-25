package seedu.recipe.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import seedu.recipe.commons.core.LogsCenter;

/**
 * Panel containing the different tabs of the application.
 */
public class MainTabPanel extends UiPart<Region> {
    private static final String FXML = "MainTabPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MainTabPanel.class);

    @FXML
    private StackPane recipeListPanelPlaceholder;

    @FXML
    private StackPane planningListPanelPlaceholder;


    @FXML
    private TabPane mainTabPanel;

    public MainTabPanel(RecipeListPanel recipeListPanel, RecipeListPanel planningListPanel) {
        super(FXML);

        recipeListPanelPlaceholder.getChildren().add(recipeListPanel.getRoot());

        planningListPanelPlaceholder.getChildren().add(planningListPanel.getRoot());

    }

    protected void switchToRecipesTab() {
        this.mainTabPanel.getSelectionModel().select(0);
    }

    protected void switchToPlanningTab() {
        this.mainTabPanel.getSelectionModel().select(1);
    }
}
