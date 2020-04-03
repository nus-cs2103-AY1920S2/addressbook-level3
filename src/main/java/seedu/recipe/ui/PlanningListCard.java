package seedu.recipe.ui;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.recipe.model.Date;
import seedu.recipe.model.plan.PlannedDate;
import seedu.recipe.model.recipe.Recipe;

/**
 * An UI component that displays information of a {@code Recipe}.
 */
public class PlanningListCard extends UiPart<Region> {

    private static final String FXML = "PlanningListCard.fxml";

    public final PlannedDate plannedDateObject;
    public final Date plannedDate;
    public final List<Recipe> recipes;

    private final String styleIngredientsAndSteps = "-fx-font-size: 11pt;\n"
            + "-fx-font-family: \"Segoe UI\";\n"
            + "-fx-text-fill: #FFFFFF;\n";

    @FXML
    private HBox cardPane;
    @FXML
    private Label date;
    @FXML
    private VBox recipesBox;

    public PlanningListCard(PlannedDate plannedDate, int displayedIndex) {
        super(FXML);
        this.plannedDateObject = plannedDate;
        this.plannedDate = plannedDate.getDate();
        date.setText(this.plannedDate.toString());

        this.recipes = plannedDate.getRecipes();

        for (int i = 0; i < recipes.size(); i++) {
            Recipe recipe = recipes.get(i);
            recipesBox.getChildren().add(new RecipeCard(recipe, i + 1).getRoot());
        }

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PlanningListCard)) {
            return false;
        }

        // state check
        PlanningListCard card = (PlanningListCard) other;
        return plannedDate.equals(card.plannedDate)
                && recipes.equals(card.recipes);
    }
}
