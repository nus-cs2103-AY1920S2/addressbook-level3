package seedu.recipe.ui;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.recipe.model.recipe.Recipe;

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
    private final String styleIngredientsAndSteps = "-fx-font-size: 11pt;\n"
            + "-fx-font-family: \"Segoe UI\";\n"
            + "-fx-text-fill: #FFFFFF;\n";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private ImageView favourite;
    @FXML
    private Label time;
    @FXML
    private VBox grains;
    @FXML
    private VBox vegetables;
    @FXML
    private VBox proteins;
    @FXML
    private VBox fruits;
    @FXML
    private VBox others;
    @FXML
    private VBox steps;
    @FXML
    private FlowPane goals;
    @FXML
    private Label ingredientsHeader;
    @FXML
    private Label stepsHeader;

    public RecipeCard(Recipe recipe, int displayedIndex) {
        super(FXML);
        this.recipe = recipe;
        id.setText(displayedIndex + ". ");
        name.setText(recipe.getName().fullName);
        name.setWrapText(true);

        if (recipe.isFavourite()) {
            favourite.setImage(new Image("/images/favourite.png"));
        }

        recipe.getGoals().stream()
                .sorted(Comparator.comparing(goal -> goal.goalName))
                .forEach(goal -> goals.getChildren().add(new Label(goal.goalName)));

        time.setText(recipe.getTime().value + " min");

        ingredientsHeader.setText("Ingredients");
        ingredientsHeader.setUnderline(true);
        ingredientsHeader.setPadding(new Insets(10, 0, 0, 0));

        recipe.getGrains().forEach(grain -> grains.getChildren().add(new Label(grain.toString())));
        grains.getChildren().forEach(grain -> grain.setStyle(styleIngredientsAndSteps));

        recipe.getVegetables().forEach(vegetable -> vegetables.getChildren().add(new Label(vegetable.toString())));
        vegetables.getChildren().forEach(vegetable -> vegetable.setStyle(styleIngredientsAndSteps));

        recipe.getProteins().forEach(protein -> proteins.getChildren().add(new Label(protein.toString())));
        proteins.getChildren().forEach(protein -> protein.setStyle(styleIngredientsAndSteps));

        recipe.getFruits().forEach(fruit -> fruits.getChildren().add(new Label(fruit.toString())));
        fruits.getChildren().forEach(fruit -> fruit.setStyle(styleIngredientsAndSteps));

        recipe.getOthers().forEach(other -> others.getChildren().add(new Label(other.toString())));
        others.getChildren().forEach(other -> other.setStyle(styleIngredientsAndSteps));

        if (!recipe.getSteps().isEmpty()) {
            stepsHeader.setText("Steps");
            stepsHeader.setUnderline(true);
            stepsHeader.setPadding(new Insets(10, 0, 0, 0));

            // Calculates step number and displays with along with the step
            AtomicInteger stepNumber = new AtomicInteger(1);
            recipe.getSteps().forEach(step -> {
                Label stepLabel = new Label("Step " + stepNumber.getAndIncrement() + ": " + step.value);
                stepLabel.setWrapText(true);
                stepLabel.setStyle(styleIngredientsAndSteps);
                steps.getChildren().add(stepLabel);
            });
            steps.setSpacing(5);
        }

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
