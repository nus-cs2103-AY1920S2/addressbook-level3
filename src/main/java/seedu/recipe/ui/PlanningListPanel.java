package seedu.recipe.ui;

import java.io.IOException;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

import seedu.recipe.commons.core.LogsCenter;
import seedu.recipe.commons.util.StringUtil;
import seedu.recipe.model.Date;
import seedu.recipe.model.plan.PlannedRecipe;
import seedu.recipe.model.plan.UniquePlannedList;

/**
 * Panel containing the list of recipes.
 */
public class PlanningListPanel extends UiPart<Region> {
    private static final String FXML = "PlanningListPanel.fxml";
    private static UniquePlannedList uniqueScheduleMap;

    private final Logger logger = LogsCenter.getLogger(RecipeListPanel.class);

    private final String styleHeader = "-fx-font-family: \"Segoe UI\";\n"
            + "-fx-text-fill: #FFFFFF;\n"
            + "-fx-font-weight: bold;";
    private final String monthStyleHeader = styleHeader + "-fx-font-size: 30pt;\n";
    private final String weekStyleHeader = styleHeader + "-fx-font-size: 15pt;\n";



    @FXML
    private ListView<PlannedRecipe> planningListView;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Label monthHeader;

    @FXML
    private Label dayHeader;

    public PlanningListPanel(ObservableList<PlannedRecipe> plannedRecipes) {
        super(FXML);
        Date today = Date.today();
        monthHeader.setText(today.getMonthName());
        monthHeader.setStyle(monthStyleHeader);

        dayHeader.setText(today.getDayOfWeek() + ": " + today.toString());
        dayHeader.setStyle(weekStyleHeader);

        planningListView.setItems(plannedRecipes);
        planningListView.setCellFactory(planningListView -> new PlanningListViewCell());

    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PlannedRecipe} using a {@code PlanningListCard}.
     */
    class PlanningListViewCell extends ListCell<PlannedRecipe> {
        @Override
        protected void updateItem(PlannedRecipe plannedRecipe, boolean empty) {
            super.updateItem(plannedRecipe, empty);
            if (empty || plannedRecipe == null) {
                setGraphic(null);
                setText(null);
            } else {
                try {
                    setGraphic(new PlanningListCard(plannedRecipe, getIndex() + 1).getRoot());
                } catch (IOException e) {
                    logger.warning("Failed to favourites icon : " + StringUtil.getDetails(e));
                }
            }
        }
    }

}
