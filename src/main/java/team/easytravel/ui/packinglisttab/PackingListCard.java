package team.easytravel.ui.packinglisttab;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import team.easytravel.model.listmanagers.packinglistitem.PackingListItem;
import team.easytravel.ui.UiPart;

/**
 * An UI component that displays information of a {@code Item}.
 */
public class PackingListCard extends UiPart<Region> {

    private static final String FXML = "packinglisttab/PackingListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final PackingListItem item;

    @FXML
    private HBox cardPane;
    @FXML
    private Label itemName;
    @FXML
    private Label id;
    @FXML
    private Label quantity;
    @FXML
    private Label itemCategory;
    @FXML
    private ImageView checkBox;
    @FXML
    private ImageView category;


    public PackingListCard(PackingListItem item, int displayedIndex) {
        super(FXML);
        this.item = item;
        id.setText(displayedIndex + ". ");
        itemName.setText(item.getItemName().toString());
        quantity.setText("Quantity: " + item.getQuantity().toString());
        itemCategory.setText("Category: " + item.getItemCategory().value);

        Image image;

        if (item.getIsChecked()) {
            image = new Image("/images/tick.png");
        } else {
            image = new Image("/images/cross.png");
        }

        checkBox.setImage(image);

        Image imageCategory;

        if (item.getItemCategory().value.equals("swimming")) {
            imageCategory = new Image("/images/swim.png");
        } else if (item.getItemCategory().value.equals("clothes")) {
            imageCategory = new Image("/images/clothes.png");
        } else if (item.getItemCategory().value.equals("car")) {
            imageCategory = new Image("/images/car.png");
        } else if (item.getItemCategory().value.equals("camping")) {
            imageCategory = new Image("/images/camping.png");
        } else if (item.getItemCategory().value.equals("formal dinner female")) {
            imageCategory = new Image("/images/dress.png");
        } else if (item.getItemCategory().value.equals("formal dinner male")) {
            imageCategory = new Image("/images/suit.png");
        } else if (item.getItemCategory().value.equals("snow")) {
            imageCategory = new Image("/images/snow.png");
        } else if (item.getItemCategory().value.equals("gym")) {
            imageCategory = new Image("/images/gym.png");
        } else if (item.getItemCategory().value.equals("essentials")) {
            imageCategory = new Image("/images/exclamation.png");
        } else if (item.getItemCategory().value.equals("hiking")) {
            imageCategory = new Image("/images/adventurer.png");
        } else if (item.getItemCategory().value.equals("train")) {
            imageCategory = new Image("/images/train.png");
        } else if (item.getItemCategory().value.equals("cruise")) {
            imageCategory = new Image("/images/cruise.png");
        } else if (item.getItemCategory().value.equals("beach")) {
            imageCategory = new Image("/images/beach.png");
        } else if (item.getItemCategory().value.equals("toiletries")) {
            imageCategory = new Image("/images/soap.png");
        } else if (item.getItemCategory().value.equals("international")) {
            imageCategory = new Image("/images/global.png");
        } else if (item.getItemCategory().value.equals("work")) {
            imageCategory = new Image("/images/portfolio.png");
        } else if (item.getItemCategory().value.equals("photography")) {
            imageCategory = new Image("/images/camera.png");
        } else if (item.getItemCategory().value.equals("music festival")) {
            imageCategory = new Image("/images/music.png");
        } else {
            imageCategory = new Image("/images/default.png");
        }

        category.setImage(imageCategory);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PackingListCard)) {
            return false;
        }

        // state check
        PackingListCard card = (PackingListCard) other;
        return id.getText().equals(card.id.getText())
                && item.equals(card.item);
    }
}
