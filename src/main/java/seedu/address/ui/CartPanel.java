package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.ingredient.Ingredient;

/**
 * Panel containing the list of persons.
 */
public class CartPanel extends UiPart<Region> {
    private static final String FXML = "CartPanel.fxml";

    @FXML
    private ListView<Ingredient> cartListView;

    public CartPanel(ObservableList<Ingredient> ingredientList) {
        super(FXML);
        cartListView.setItems(ingredientList);
        cartListView.setCellFactory(listView -> new CartViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of an {@code Ingredient} using a {@code CartCard}.
     */
    class CartViewCell extends ListCell<Ingredient> {
        @Override
        protected void updateItem(Ingredient ingredient, boolean empty) {
            super.updateItem(ingredient, empty);

            if (empty || ingredient == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new IngredientCard(ingredient, getIndex() + 1).getRoot());
            }
        }
    }

}
