package seedu.address.ui;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.recipe.Recipe;

/**
 * An UI component that displays information of a {@code Recipe}.
 */
public class RecipeCard extends UiPart<Region> {

    private static final String FXML = "RecipeListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Recipe recipe;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label time;
    @FXML
    private FlowPane ingredients;
    @FXML
    private VBox steps;
    @FXML
    private FlowPane goals;

    public RecipeCard(Recipe recipe, int displayedIndex) {
        super(FXML);
        this.recipe = recipe;
        id.setText(displayedIndex + ". ");
        name.setText(recipe.getName().fullName);
        time.setText(recipe.getTime().value + " min");

        ingredients.setOrientation(Orientation.VERTICAL);
        recipe.getIngredients().stream()
                .forEach(ingredient -> ingredients.getChildren().add(new Label(ingredient.toString())));

        AtomicInteger stepNumber = new AtomicInteger(1);
        recipe.getSteps().forEach(step -> {
            Label stepLabel = new Label("Step " + stepNumber.getAndIncrement() + ": " + step.value);
            stepLabel.setWrapText(true);
            stepLabel.prefWidthProperty().bind(getRoot().widthProperty());
            steps.getChildren().add(stepLabel);
        });

        recipe.getGoals().stream()
                .sorted(Comparator.comparing(goal -> goal.goalName))
                .forEach(goal -> goals.getChildren().add(new Label(goal.goalName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RecipeCard)) {
            return false;
        }

        // state check
        RecipeCard card = (RecipeCard) other;
        return id.getText().equals(card.id.getText())
                && recipe.equals(card.recipe);
    }
}
