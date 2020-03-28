package seedu.recipe.ui;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.recipe.commons.core.LogsCenter;
import seedu.recipe.commons.util.StringUtil;
import seedu.recipe.model.recipe.Recipe;

/**
 * Panel containing the list of recipes.
 */
public class PlanningMapPanel extends UiPart<Region> {
    private static final String FXML = "PlanningMapPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RecipeListPanel.class);

    @FXML
    private ListView<Recipe> planningMapView;

    public PlanningMapPanel(ObservableMap<Date, List<Recipe>> plannedMap) {
        super(FXML);
        planningMapView.setItems(recipeList);
        planningMapView.setCellFactory(listView -> new PlanningListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Recipe} using a {@code RecipeCard}.
     */
    class PlanningListViewCell extends ListCell<Recipe> {
        @Override
        protected void updateItem(Recipe recipe, boolean empty) {
            super.updateItem(recipe, empty);
            if (empty || recipe == null) {
                setGraphic(null);
                setText(null);
            } else {
                try {
                    setGraphic(new PlanningMapCard(recipe, getIndex() + 1).getRoot());
                } catch (IOException e) {
                    logger.warning("Failed to favourites icon : " + StringUtil.getDetails(e));
                }
            }
        }
    }

}
