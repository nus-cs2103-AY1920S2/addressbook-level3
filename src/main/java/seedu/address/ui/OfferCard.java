package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import seedu.address.model.offer.Offer;

import java.io.IOException;

public class OfferCard extends VBox {

    private static final String FXML = "/view/OfferCard.fxml";

    public final Offer offer;

    @FXML
    private Label good;
    @FXML
    private Label price;

    public OfferCard(Offer offer) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource(FXML));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.offer = offer;
        good.setText("Good: " + offer.getGood().toString());
        price.setText("Price: " + offer.getPrice().toString());
    }
}
