package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
// import seedu.address.commons.core.LogsCenter;
import seedu.address.model.ingredient.Ingredient;

/**
 * Panel containing the list of ingredients.
 */
public class InventoryPanel extends UiPart<Region> {
    private static final String FXML = "InventoryPanel.fxml";

    @FXML
    private ListView<Ingredient> inventoryListView;

    public InventoryPanel(ObservableList<Ingredient> ingredientList) {
        super(FXML);
        inventoryListView.setItems(ingredientList);
        inventoryListView.setCellFactory(listView -> new IngredientListViewCell());
    }

    /**
     * Custom {@code ListCell} displays the graphics of a {@code UniqueIngredientList} using {@code IngredientCard}.
     */
    static class IngredientListViewCell extends ListCell<Ingredient> {
        @Override
        protected void updateItem(Ingredient ingredient, boolean isEmpty) {
            super.updateItem(ingredient, isEmpty);

            if (isEmpty || ingredient == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new IngredientCard(ingredient, getIndex() + 1).getRoot());
            }
        }
    }

}
