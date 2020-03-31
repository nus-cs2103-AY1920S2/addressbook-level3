package cookbuddy.ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import cookbuddy.model.recipe.Recipe;
import cookbuddy.model.recipe.attribute.Ingredient;
import cookbuddy.model.recipe.attribute.Instruction;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
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

    public RecipeView(Recipe recipe, int displayedIndex) throws FileNotFoundException {
        super(FXML);
        this.recipe = recipe;

        this.name.setText(recipe.getName().name);
        this.ingredients.setItems(FXCollections.observableList(this.recipe.getIngredients().asList()));
        this.instructions.setItems(FXCollections.observableList(this.recipe.getInstructions().asList()));

        BufferedImage bf = null;
        try
        {
            bf = ImageIO.read(new File(String.valueOf(this.recipe.getFilePath().filePath)));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        WritableImage wr = null;
        if (bf != null) {
            wr = new WritableImage(bf.getWidth(), bf.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < bf.getWidth(); x++) {
                for (int y = 0; y < bf.getHeight(); y++) {
                    pw.setArgb(x, y, bf.getRGB(x, y));
                }
            }
        }

        recipeImage = new ImageView(wr);

        this.recipeView = new VBox();

        // this.recipeImage.setImage(new Image());

    }
}
