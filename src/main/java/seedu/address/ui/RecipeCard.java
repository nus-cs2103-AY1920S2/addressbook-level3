package seedu.address.ui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    public RecipeCard(Recipe recipe, int displayedIndex) throws IOException {
        super(FXML);
        this.recipe = recipe;
        id.setText(displayedIndex + ". ");
        name.setText(recipe.getName().fullName);
        name.setWrapText(true);

        time.setText(recipe.getTime().value + " min");

        if (recipe.isFavourite()) {
            Path favouriteIconPath = Paths.get("src", "main", "resources", "images", "favourite.png");
            favourite.setImage(new Image(Files.newInputStream(favouriteIconPath)));
        }

        recipe.getGrains().forEach(grain -> grains.getChildren().add(new Label(grain.toString())));
        recipe.getVegetables().forEach(vegetable -> vegetables.getChildren().add(new Label(vegetable.toString())));
        recipe.getProteins().forEach(protein -> proteins.getChildren().add(new Label(protein.toString())));
        recipe.getFruits().forEach(fruit -> fruits.getChildren().add(new Label(fruit.toString())));
        recipe.getOthers().forEach(other -> others.getChildren().add(new Label(other.toString())));

        // Calculates step number and displays with along with the step
        AtomicInteger stepNumber = new AtomicInteger(1);
        recipe.getSteps().forEach(step -> {
            Label stepLabel = new Label("Step " + stepNumber.getAndIncrement() + ": " + step.value);
            stepLabel.setWrapText(true);
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
