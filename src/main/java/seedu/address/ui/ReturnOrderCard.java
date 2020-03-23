package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.order.returnorder.ReturnOrder;

/**
 * An UI component that displays information of a {@code Order}.
 */
public class ReturnOrderCard extends UiPart<Region> {

    private static final String FXML = "ReturnOrderListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     */

    public final ReturnOrder returnOrder;

    @FXML
    private HBox cardPane;
    @FXML
    private Label tid;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label warehouse;
    @FXML
    private Label cashOnDelivery;
    @FXML
    private Label comment;
    @FXML
    private Label timeStamp;
    @FXML
    private Label deliveryStatus;
    @FXML
    private FlowPane isReturn;
    @FXML
    private FlowPane itemType;

    public ReturnOrderCard(ReturnOrder returnOrder, int displayedIndex) {
        super(FXML);
        this.returnOrder = returnOrder;
        id.setText(displayedIndex + ". ");
        tid.setText(returnOrder.getTid().tid);
        name.setText(returnOrder.getName().fullName);
        phone.setText(returnOrder.getPhone().value);
        address.setText(returnOrder.getAddress().value);
        timeStamp.setText(returnOrder.getTimestamp().value);
        warehouse.setText(returnOrder.getWarehouse().address);
        comment.setText(returnOrder.getComment().commentMade);

        if (!(returnOrder.getItemType().itemType).equals("NIL")) {
            itemType.getChildren().add(new Label(returnOrder.getItemType().itemType));
        }

        if ((returnOrder.isDelivered())) {
            deliveryStatus.setText("Delivered");
        } else {
            deliveryStatus.setText("Not Delivered");
        }
        if (!(returnOrder.isReturn())) {
            isReturn.getChildren().add(new Label("Delivery Order"));
        } else {
            isReturn.getChildren().add(new Label("Return Order"));
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ReturnOrderCard)) {
            return false;
        }

        // state check
        ReturnOrderCard card = (ReturnOrderCard) other;
        return id.getText().equals(card.id.getText())
                && returnOrder.equals(card.returnOrder);
    }
}
