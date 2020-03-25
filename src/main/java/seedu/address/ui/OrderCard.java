package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.order.Order;

/**
 * An UI component that displays information of a {@code Order}.
 */
public class OrderCard extends UiPart<Region> {

    private static final String FXML = "OrderListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     */

    public final Order order;

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
    private Label email;
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

    public OrderCard(Order order, int displayedIndex) {
        super(FXML);
        this.order = order;
        id.setText(displayedIndex + ". ");
        tid.setText(order.getTid().tid);
        name.setText(order.getName().fullName);
        phone.setText(order.getPhone().value);
        email.setText(order.getEmail().value);
        address.setText(order.getAddress().value);
        timeStamp.setText(order.getTimestamp().value);
        warehouse.setText(order.getWarehouse().address);
        comment.setText(order.getComment().commentMade);
        cashOnDelivery.setText(order.getCash().cashOnDelivery);

        if (!(order.getItemType().itemType).equals("NIL")) {
            itemType.getChildren().add(new Label(order.getItemType().itemType));
        }

        if ((order.isDelivered())) {
            deliveryStatus.setText("Delivered");
        } else {
            deliveryStatus.setText("Not Delivered");
        }
        if (!(order.isReturn())) {
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
        if (!(other instanceof OrderCard)) {
            return false;
        }

        // state check
        OrderCard card = (OrderCard) other;
        return id.getText().equals(card.id.getText())
                && order.equals(card.order);
    }
}
