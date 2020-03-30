package cookbuddy.ui;

import java.util.Comparator;

import cookbuddy.model.recipe.Recipe;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Recipe}.
 */
public class RecipePage extends UiPart<Region> {

    private static final String FXML = "RecipeListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on RecipeBook level 4</a>
     */

    public final Recipe recipe;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label ingredients;
    @FXML
    private Label instructions;
    @FXML
    private Label calorie;
    @FXML
    private Label serving;
    @FXML
    private Label rating;
    @FXML
    private Label diff;
    @FXML
    private FlowPane tags;
    public RecipePage(Recipe recipe, int displayedIndex) {
        super(FXML);
        this.recipe = recipe;

        this.cardPane.setBackground(Background.EMPTY);

        id.setText(displayedIndex + ". ");
        name.setText(recipe.getName().name);
        calorie.setText(recipe.getCalorie().toString() + " calories");
        serving.setText("Serving size: " + recipe.getServing().toString() + " pax");
        rating.setText("Rating: " + recipe.getRating().toString());
        diff.setText("Difficulty: " + recipe.getDifficulty().toString());
        recipe.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RecipePage)) {
            return false;
        }

        // state check
        RecipePage card = (RecipePage) other;
        return id.getText().equals(card.id.getText())
                && recipe.equals(card.recipe);
    }
}
