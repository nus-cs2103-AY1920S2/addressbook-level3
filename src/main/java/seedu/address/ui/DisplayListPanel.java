package seedu.address.ui;

import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import javafx.scene.layout.VBox;
import seedu.address.model.pet.FoodAmountAndPet;
import seedu.address.model.pet.FoodCollection;
import seedu.address.model.pet.Pet;
import seedu.address.model.slot.Slot;

/**
 * Panel containing the list of display items.
 */
public class DisplayListPanel extends UiPart<Region> {
    private static final String FXML = "DisplayListPanel.fxml";
    private static final ObservableList<DisplayItem> EMPTY_DISPLAY_ITEM_LIST = FXCollections.observableArrayList();

    @FXML
    private ListView<DisplayItem> displayListView;
    @FXML
    private ListView<DisplayItem> displayInformationView;
    @FXML
    private VBox displayInformationViewContainer;

    public DisplayListPanel(ObservableList<DisplayItem> displayList) {
        super(FXML);
        displayListView.setItems(displayList);
        displayListView.setCellFactory(listView -> new DisplayListViewCell());
        displayInformationView.setCellFactory(listView -> new DisplayListViewCell());
    }

    /**
     * Changes the backing list of display items to {@code newDisplayList}.
     * Also update the settings of displayInformationView to match the corresponding system being displayed.
     */
    public final void updateWith(ObservableList<DisplayItem> newDisplayList, DisplaySystemType type) {
        displayListView.setItems(newDisplayList);
        displayInformationView.setItems(EMPTY_DISPLAY_ITEM_LIST);
        if (type.equals(DisplaySystemType.INVENTORY)) {
            adjustInformationViewToInventory();
        } else {
            adjustInformationViewToRest();
        }
    }

    /**
     * Collapses the displayInformationView so that the displayListView occupies the entire screen.
     */
    private void adjustInformationViewToRest() {
        displayInformationViewContainer.setPrefWidth(0);
        displayInformationViewContainer.setMinWidth(0);
        HBox.setHgrow(displayInformationViewContainer, Priority.NEVER);
        displayInformationView.setPrefWidth(0);
        displayInformationView.setMinWidth(0);
        HBox.setHgrow(displayInformationView, Priority.NEVER);
        displayInformationView.getStyleClass().clear();
    }

    /**
     * Expands the displayInformationView for the display of inventory system.
     */
    private void adjustInformationViewToInventory() {
        displayInformationViewContainer.setPrefWidth(displayListView.getPrefWidth());
        displayInformationViewContainer.setMinWidth(displayListView.getPrefWidth());
        HBox.setHgrow(displayInformationViewContainer, Priority.ALWAYS);
        displayInformationView.setPrefWidth(displayListView.getPrefWidth());
        displayInformationView.setMinWidth(displayListView.getMinWidth());
        HBox.setHgrow(displayInformationView, Priority.ALWAYS);
        displayInformationView.getStyleClass().add("pane-with-border");
    }

    /**
     * Displays the amount breakdown of the food collection being clicked on through the list provided.
     */
    public final void handleClickOnList(ObservableList<DisplayItem> foodAmountAndPets) {
        displayInformationView.setItems(foodAmountAndPets);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code DisplayItem}.
     */
    class DisplayListViewCell extends ListCell<DisplayItem> {
        @Override
        protected void updateItem(DisplayItem item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
                setText(null);
            } else {
                switch (item.getDisplaySystemType()) {
                case PETS:
                    setGraphic(new PetCard((Pet) item, getIndex() + 1).getRoot());
                    break;
                case SCHEDULE:
                    List<Slot> allSlots = displayListView.getItems()
                            .stream()
                            .map(slot -> (Slot) slot)
                            .collect(Collectors.toList());
                    setGraphic(new SlotCard((Slot) item, getIndex() + 1, allSlots).getRoot());
                    break;
                case INVENTORY:
                    setGraphic(new FoodCollectionCard((FoodCollection) item,
                            getIndex() + 1, DisplayListPanel.this::handleClickOnList).getRoot());
                    break;
                case FOOD_AMOUNT_AND_PET:
                    FoodAmountAndPet foodAmountAndPet = (FoodAmountAndPet) item;
                    setGraphic(new FoodAmountAndPetCard(foodAmountAndPet).getRoot());
                    break;
                default:
                    setGraphic(null);
                    setText(null);
                }
            }
        }
    }
}
