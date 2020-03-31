package seedu.recipe.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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

    public final ObservableList<Recipe> recipes;
    private final String styleIngredientsAndSteps = "-fx-font-size: 11pt;\n"
            + "-fx-font-family: \"Segoe UI\";\n"
            + "-fx-text-fill: #FFFFFF;\n";

    @FXML
    private VBox dayCard;

    @FXML
    private Label dayHeader;

    @FXML
    private ListView<Recipe> recipeListView;

    public PlanningDayCard(ObservableList<Recipe> recipes, int dayOfMonth) { // throw IO Exception?
        super(FXML);
        this.recipes = recipes;
        dayHeader.setText("" + dayOfMonth);
        if (recipes != null) {
            recipeListView.setItems(recipes);
            recipeListView.setCellFactory(listView -> new PlannedRecipeListViewCell());
        }
    }

    /**
     * todo
     */
    class PlannedRecipeListViewCell extends ListCell<Recipe> {
        @Override
        protected void updateItem(Recipe recipe, boolean empty) {
            super.updateItem(recipe, empty);
            if (empty || recipe == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new Label(recipe.getName().toString()));
            }
        }
    }

    //public VBox getDayCard() {
    //recipes.forEach(recipe -> recipeVBox.getChildren().add(new Label(recipe.getName().toString())));
    //return dayCard;
    //}

}
