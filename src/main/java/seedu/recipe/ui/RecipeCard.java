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
import seedu.recipe.model.recipe.ingredient.Ingredient;

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

    private static final double INGREDIENT_WIDTH = 300;
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
    private VBox details;
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
    private Label grainsHeader;
    @FXML
    private Label vegetablesHeader;
    @FXML
    private Label proteinsHeader;
    @FXML
    private Label fruitsHeader;
    @FXML
    private Label othersHeader;
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

        setHeader(ingredientsHeader, "Ingredients");
        ingredientsHeader.setPadding(new Insets(10, 0, 0, 0));

        if (!recipe.getGrains().isEmpty()) {
            setHeader(grainsHeader, "Grains");
            recipe.getGrains().forEach(grain -> addIngredientToVerticalBox(grain, grains));
            grains.setPrefWidth(INGREDIENT_WIDTH);
        }

        if (!recipe.getVegetables().isEmpty()) {
            setHeader(vegetablesHeader, "Vegetables");
            recipe.getVegetables().forEach(vegetable -> addIngredientToVerticalBox(vegetable, vegetables));
            vegetables.setPrefWidth(INGREDIENT_WIDTH);
        }

        if (!recipe.getProteins().isEmpty()) {
            setHeader(proteinsHeader, "Proteins");
            recipe.getProteins().forEach(protein -> addIngredientToVerticalBox(protein, proteins));
            proteins.setPrefWidth(INGREDIENT_WIDTH);
        }

        if (!recipe.getFruits().isEmpty()) {
            setHeader(fruitsHeader, "Fruits");
            recipe.getFruits().forEach(fruit -> addIngredientToVerticalBox(fruit, fruits));
            fruits.setPrefWidth(INGREDIENT_WIDTH);
        }

        if (!recipe.getOthers().isEmpty()) {
            setHeader(othersHeader, "Others");
            recipe.getOthers().forEach(other -> addIngredientToVerticalBox(other, others));
            others.setPrefWidth(INGREDIENT_WIDTH);
        }


        if (!recipe.getSteps().isEmpty()) {
            setHeader(stepsHeader, "Steps");
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

    private void setHeader(Label label, String text) {
        label.setText(text);
        label.setUnderline(true);
        label.setWrapText(true);
    }

    /**
     * Creates a new label for the ingredient and adds it into the vertical box.
     */
    private void addIngredientToVerticalBox(Ingredient ingredient, VBox box) {
        Label label = new Label(ingredient.toDisplayedString());
        label.setWrapText(true);
        label.setStyle(styleIngredientsAndSteps);
        box.getChildren().add(label);
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
