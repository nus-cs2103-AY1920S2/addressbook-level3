package seedu.address.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import seedu.address.model.parcel.returnorder.ReturnOrder;

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
     * Images adopted from:
     * https://icons8.com/
     *
     */

    public final ReturnOrder order;

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
    private Label comment;
    @FXML
    private FlowPane tagFlowPane;
    @FXML
    private ImageView phoneImage;
    @FXML
    private ImageView nameImage;
    @FXML
    private ImageView warehouseImage;
    @FXML
    private ImageView commentImage;
    @FXML
    private ImageView emailImage;
    @FXML
    private ImageView addressImage;

    public ReturnOrderCard(ReturnOrder order, int displayedIndex) {
        super(FXML);
        this.order = order;

        id.setWrapText(true);
        tid.setWrapText(true);
        name.setWrapText(true);
        phone.setWrapText(true);
        email.setWrapText(true);
        address.setWrapText(true);
        warehouse.setWrapText(true);
        comment.setWrapText(true);

        phoneImage.setImage(getImage("/images/phone.png"));
        nameImage.setImage(getImage("/images/name.png"));
        warehouseImage.setImage(getImage("/images/warehouse.png"));
        commentImage.setImage(getImage("/images/comments_icon.png"));
        emailImage.setImage(getImage("/images/email.png"));
        addressImage.setImage(getImage("/images/address.png"));

        id.setText(displayedIndex + ". ");
        tid.setText("Transaction ID: " + order.getTid().tid);
        name.setText(order.getName().fullName);
        phone.setText(order.getPhone().value);
        email.setText(order.getEmail().value);
        address.setText(order.getAddress().value);
        warehouse.setText(order.getWarehouse().address);
        comment.setText(order.getComment().commentMade);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm");
        LocalDateTime ldt = order.getTimestamp().timeStamp;

        Label dts = new Label(ldt.format(dtf));
        dts.setId("dateTimeId");
        tagFlowPane.getChildren().add(dts);

        if (!(order.getItemType().itemType).equals("NIL")) {
            Label item = new Label(order.getItemType().itemType);
            tagFlowPane.getChildren().add(item);
        }

        if (order.isDelivered()) {
            ImageView deliveredImage = new ImageView(getImage("/images/delivered.png"));
            deliveredImage.setFitHeight(40);
            deliveredImage.setFitWidth(40);
            tagFlowPane.getChildren().add(deliveredImage);
        } else {
            ImageView undeliveredImage = new ImageView(getImage("/images/not_delivered.png"));
            undeliveredImage.setFitHeight(40);
            undeliveredImage.setFitWidth(40);
            tagFlowPane.getChildren().add(undeliveredImage);
        }

    }

    private Image getImage(String imagePath) {
        return new Image(OrderCard.class.getResourceAsStream(imagePath));
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
        ReturnOrderCard card = (ReturnOrderCard) other;
        return id.getText().equals(card.id.getText())
                && order.equals(card.order);
    }
}
