package csdev.couponstash.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import csdev.couponstash.commons.core.LogsCenter;
import csdev.couponstash.logic.commands.SortCommand;
import csdev.couponstash.model.coupon.Coupon;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Controller for a help page
 */
public class RemindWindow extends UiPart<Stage> {

    public static final String REMIND_MESSAGE = "Remember to use these coupon(s) before they expire!\n";
    public static final String EXIT_MESSAGE = "[Press Ctrl + Q to exit]";
    public static final int MAX_DISPLAY_REMIND_COUPONS = 6;

    private static final Logger logger = LogsCenter.getLogger(RemindWindow.class);
    private static final String FXML = "RemindWindow.fxml";

    private final Stage root;

    @FXML
    private Label remindMessage;

    @FXML
    private Label remindCoupons;

    @FXML
    private Label exitMessage;

    @FXML
    private Scene scene;

    /**
     * Creates a new RemindWindow.
     *
     * @param message Message for remind window.
     */
    public RemindWindow(String message) {
        this(new Stage(), message);
    }

    public RemindWindow(Stage root, String message) {
        super(FXML, root);
        remindMessage.setText(REMIND_MESSAGE);
        remindCoupons.setText(message);
        exitMessage.setText(EXIT_MESSAGE);
        this.root = root;
        UiUtil.setExitAccelerator(root, scene, logger, "Remind Window");

    }

    /**
     * This method is to check all coupon's remind date against today's and
     * formulate the entire coupons that has to be reminded today as a string
     *
     * @param coupons - the current coupon's list
     */
    public static void showRemind(List<Coupon> coupons) {
        String remindMessage = "";
        int index = 1;
        LocalDate today = LocalDate.now();

        List<Coupon> remindCoupons = coupons.stream()
                .filter(coupon -> coupon.getRemindDate().getDate().equals(today))
                .sorted(SortCommand.REMINDER_COMPARATOR)
                .collect(Collectors.toList());

        for (Coupon coupon : remindCoupons) {
            remindMessage += index + ". "
                    + coupon.getName().toString()
                    + " (Starts on " + coupon.getStartDate().toString() + ")"
                    + " (Expires on " + coupon.getExpiryDate().toString() + ")"
                    + "\n";
            index++;

            if (index > MAX_DISPLAY_REMIND_COUPONS) {
                remindMessage += "...and more! (Tip: Sort the list by remind dates!)\n";
                break;
            }
        }

        if (index > 1) {
            logger.info("Remind Window is opening");
            Stage remindWindow = new RemindWindow(remindMessage).getRoot();
            remindWindow.show();
        }
    }
}
