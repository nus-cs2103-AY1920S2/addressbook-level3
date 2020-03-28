package seedu.recipe.ui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
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
public class PlanningDayCard extends UiPart<Region> {

    private static final String FXML = "PlanningDayCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final List<Recipe> recipes;
    private final String styleIngredientsAndSteps = "-fx-font-size: 11pt;\n"
            + "-fx-font-family: \"Segoe UI\";\n"
            + "-fx-text-fill: #FFFFFF;\n";

    @FXML
    private VBox dayCard;

    @FXML
    private Label dayHeader;

    @FXML
    private VBox recipeVBox;

    public PlanningDayCard(List<Recipe> recipes, int dayOfMonth) { // throw IO Exception?
        super(FXML);
        this.recipes = recipes;
        dayHeader.setText("" + dayOfMonth);
    }

    public VBox getDayCard() {
        recipes.forEach(recipe -> recipeVBox.getChildren().add(new Label(recipe.getName().toString())));
        return dayCard;
    }

}
