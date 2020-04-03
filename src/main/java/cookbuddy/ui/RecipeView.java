package cookbuddy.ui;

import cookbuddy.model.recipe.Recipe;
import cookbuddy.model.recipe.attribute.Image;
import cookbuddy.model.recipe.attribute.Ingredient;
import cookbuddy.model.recipe.attribute.Instruction;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

/**
 * RecipeView
 */
public class RecipeView extends UiPart<Region> {

    private static final String FXML = "RecipeView.fxml";
    private final Recipe recipe;

    @FXML
    private Label name;
    @FXML
    private ImageView recipeImage;
    @FXML
    private ListView<Ingredient> ingredients;
    @FXML
    private ListView<Instruction> instructions;


    public RecipeView(Recipe recipe) {
        super(FXML);
        this.recipe = recipe;

        this.name.setText(recipe.getName().name);
        this.ingredients.setItems(FXCollections.observableList(this.recipe.getIngredients().asList()));
        this.instructions.setItems(FXCollections.observableList(this.recipe.getInstructions().asList()));
        this.recipeImage.setImage(Image.getWritableImage(this.recipe));
        this.ingredients.setStyle(".list-cell:empty {-fx-background-color: transparent;}");
    }

    public RecipeView() {
        super(FXML);
        this.recipe = null;
        this.name.setText("Welcome to CookBuddy!");
    }
}
