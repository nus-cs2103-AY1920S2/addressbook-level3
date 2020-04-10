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
    private final List<Coupon> coupons;

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
     */
    public RemindWindow(List<Coupon> coupons) {
        this(new Stage(), coupons);
    }

    /**
     * Creates a new RemindWindow with the {@code coupons} that are to be reminded.
     */
    public RemindWindow(Stage root, List<Coupon> coupons) {
        super(FXML, root);
        this.coupons = coupons;
        this.root = root;

        remindMessage.setText(REMIND_MESSAGE);
        String remindCouponsString = constructRemindCoupons();
        remindCoupons.setText(remindCouponsString);
        exitMessage.setText(EXIT_MESSAGE);
        UiUtil.setExitAccelerator(root, scene, logger, "Remind Window");
    }

    /**
     * Constructs the string that includes coupons that are to be reminded today,
     * limited to the {@code MAX_DISPLAY_REMIND_COUPONS} number of coupons.
     * This method will check all coupon's remind date against today's.
     */
    public String constructRemindCoupons() {
        assert coupons.size() > 0 : "Coupons to be reminded today should exist";

        int index = 1;
        String remindMessage = "";
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

        return remindMessage;
    }

    /** Opens and displays the RemindWindow */
    public void show() {
        logger.info("Remind Window is opening");
        root.show();
    }
}
