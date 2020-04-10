package csdev.couponstash.ui;

import java.util.List;

import csdev.couponstash.model.coupon.Coupon;
import javafx.stage.Stage;

/**
 * API of UI component
 */
public interface Ui {

    /** Starts the UI (and the App).  */
    void start(Stage primaryStage, List<Coupon> coupons);
}
