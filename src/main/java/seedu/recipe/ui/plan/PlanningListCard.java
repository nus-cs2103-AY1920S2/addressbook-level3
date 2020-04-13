package seedu.recipe.ui.plan;

import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.recipe.model.Date;
import seedu.recipe.model.plan.Plan;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.ui.RecipeCard;
import seedu.recipe.ui.UiPart;

/**
 * An UI component that displays information of a {@code Plan}.
 */
public class PlanningListCard extends UiPart<Region> {

    private static final String FXML = "plan/PlanningListCard.fxml";

    public final Date plannedDate;
    public final Recipe recipe;

    @FXML
    private Label date;
    @FXML
    private VBox plannedBox;

    public PlanningListCard(Plan plan, int displayedIndex) {
        super(FXML);
        this.plannedDate = plan.getDate();
        this.recipe = plan.getRecipe();

        date.setText(this.plannedDate.toString());
        plannedBox.getChildren().add(new RecipeCard(recipe, displayedIndex).getRoot());
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
                && recipe.equals(card.recipe);
    }
}
