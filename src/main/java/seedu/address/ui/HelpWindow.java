package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://bit.ly/2U9MBrf";
    public static final String HELP_MESSAGE = "Delino supports add, clear, delete, done, edit, exit, find, help,"
            + " import, list, return, sort and undo commands.\n"
            + "Each feature will require a different set of input prefixes. The examples below will better"
            + " illustrate the usage of the aforementioned commands in Delino.\n"

            + "\n 1. Add an order to the list of orders."
            + "\n     Eg. add tid/9876543210 n/John Doe a/Blk 572 Hougang st 51 #10-33 S530572"
            + " p/98766789 ts/2020-02-20 1300 w/Yishun pm/Cash \n"

            + "\n 2. Clear all orders while orders are listed."
            + "\n     Eg. clear (prompts user for confirmation) OR clear -f (force delete) \n"

            + "\n 3. Delete an order with respect to the displayed list of orders"
            + "\n     Eg. delete 2 (Removes the second order in the list) \n"

            + "\n 4. Marks a specified order as completed"
            + "\n     Eg. done 3 (The third order in the list will be marked as completed) \n"

            + "\n 5. Edit an order's specified attribute."
            + "\n     Eg. edit 2 n/James Charles (Edit the customer name in the second order to 'James Charles' \n"

            + "\n 6. Exit the Delino application"
            + "\n     Eg. exit OR you may choose to press 'F1' to close the application \n"

            + "\n 7. Find an order based on either the person’s Name or by the Transaction ID."
            + "\n     Eg. find -n Amos or find -t A195BCD2S \n"

            + "\n 8. Provide more information about Delino and its commands."
            + "\n     Eg. help OR you may choose to press 'F2' \n"

            + "\n 9. Import a list of orders from a given .csv file."
            + "\n     Eg. import customers_20_02_2020.csv \n"

            + "\n 10. Display the default list of orders or a list based on specified status."
            + "\n      Eg. list OR list done OR list undone \n"

            + "\n 11. Create a return order and adds it into the list of returns."
            + "\n      Eg. return `tid/ac17s2a n/Bobby Tan a/123 Delta Road #03-333, Singapore 123456"
            + " p/91230456 ts/12-12-2020 w/Jurong Warehouse \n"

            + "\n 12. Sort the list of orders by the specified sequence."
            + "\n      Eg. sort ts/ (Sorts the orders in ascending order based on timestamp of the orders \n"

            + "\n 13. Undo the previous action."
            + "\n      Eg. After calling 'list, delete 1, delete 2', calling 'undo' will reverse the deletion of"
            + " the second item of the list \n"

            + "\n For more information, please refer to Delino's User Guide.";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}
