package seedu.recipe.ui.plan;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import seedu.recipe.commons.core.LogsCenter;
import seedu.recipe.model.plan.Plan;
import seedu.recipe.ui.RecipeListPanel;
import seedu.recipe.ui.UiPart;

/**
 * Panel containing the list of recipes.
 */
public class PlanningListPanel extends UiPart<Region> {
    private static final String FXML = "plan/PlanningListPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(RecipeListPanel.class);

    private final String titleStyle = "-fx-font-family: \"Segoe UI\";\n"
            + "-fx-text-fill: #FFFFFF;\n"
            + "-fx-font-weight: bold;\n"
            + "-fx-font-size: 20pt;";

    private final String viewType = "All plans";


    @FXML
    private ListView<Plan> planningListView;

    @FXML
    private Label title;

    public PlanningListPanel(ObservableList<Plan> plans) {
        super(FXML);
        title.setText("Viewing: " + viewType);
        title.setStyle(titleStyle);

        planningListView.setItems(plans);
        planningListView.setCellFactory(planningListView -> new PlanningListViewCell());

    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Plan} using a {@code PlanningListCard}.
     */
    class PlanningListViewCell extends ListCell<Plan> {
        @Override
        protected void updateItem(Plan plan, boolean empty) {
            super.updateItem(plan, empty);
            if (empty || plan == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PlanningListCard(plan, getIndex() + 1).getRoot());
            }
        }
    }

}
