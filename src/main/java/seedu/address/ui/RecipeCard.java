package seedu.address.ui;

import java.util.Comparator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.recipe.Recipe;

/**
 * An UI component that displays information of a {@code Recipe}.
 */
public class RecipeCard extends UiPart<Region> {

    private static final String FXML = "RecipeCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    private Recipe recipe;
    private boolean isFullyDisplayed;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private Button button;
    @FXML
    private FlowPane tags;

    public RecipeCard(Recipe recipe, int displayedIndex) {
        super(FXML);
        init(recipe, displayedIndex);
    }

    public RecipeCard(Recipe recipe, int displayedIndex, boolean isViewRecipe) {
        super(FXML);
        init(recipe, displayedIndex);
        if (isViewRecipe) {
            displayRecipeComplete();
        }
    }

    /**
     * Initializes the creation of a {@code RecipeCard}.
     */
    public void init(Recipe recipe, int displayedIndex) {
        this.recipe = recipe;
        id.setText(displayedIndex + ". ");
        recipe.getTags().stream()
            .sorted(Comparator.comparing(tag -> tag.tagName))
            .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        displayRecipeOverview();

        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/images/button_icon.png")));
        imageView.setFitHeight(25);
        imageView.setFitWidth(25);
        button.setGraphic(imageView);
    }

    /**
     * Sets the {@code RecipeCard} to display a brief overview of its {@code Recipe}.
     */
    public void displayRecipeOverview() {
        isFullyDisplayed = false;
        name.setText(recipe.getName().fullRecipeName);
        description.setText(recipe.getDescription().fullRecipeDescription);
    }

    /**
     * Sets the {@code RecipeCard} to display a full overview of its {@code Recipe} when the view button is pressed.
     */
    @FXML
    private void handleViewButtonAction(ActionEvent event) {
        if (isFullyDisplayed) {
            displayRecipeOverview();
        } else {
            displayRecipeComplete();
        }
    }

    private void displayRecipeComplete() {
        isFullyDisplayed = true;
        description.setText(recipe.getDescription().fullRecipeDescription
            + "\n\n" + recipe.getIngredients().toString() + "\n" + recipe.getSteps());
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
