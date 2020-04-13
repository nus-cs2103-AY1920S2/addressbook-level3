package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.index.Index;
import seedu.address.model.recipe.Recipe;

/**
 * Panel containing the list of recipes.
 */
public class CookbookPanel extends UiPart<Region> {
    private static final String FXML = "CookbookPanel.fxml";

    @FXML
    private ListView<Recipe> recipeListView;

    public CookbookPanel(ObservableList<Recipe> recipesList) {
        super(FXML);
        recipeListView.setItems(recipesList);
        recipeListView.setCellFactory(listView -> new RecipeListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Recipe} using a {@code RecipeCard}.
     */
    static class RecipeListViewCell extends ListCell<Recipe> {
        private int recipeIndex;

        public RecipeListViewCell() {
            recipeIndex = -1;
        }

        public RecipeListViewCell(int recipeIndex) {
            RecipeListViewCell.this.recipeIndex = recipeIndex;
        }

        @Override
        protected void updateItem(Recipe recipe, boolean isEmpty) {
            super.updateItem(recipe, isEmpty);

            if (isEmpty || recipe == null) {
                setGraphic(null);
                setText(null);
            } else {
                int newIndex = getIndex() + 1;
                if (newIndex == recipeIndex) {
                    setGraphic(new RecipeCard(recipe, newIndex, true).getRoot());
                } else {
                    setGraphic(new RecipeCard(recipe, newIndex).getRoot());
                }
            }
        }
    }

    public void handleViewRecipe(Index recipeIndex) {
        recipeListView.setCellFactory(listView -> new RecipeListViewCell(recipeIndex.getOneBased()));
    }
}
