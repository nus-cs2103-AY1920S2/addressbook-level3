package seedu.delino.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seedu.delino.commons.core.LogsCenter;

//@@author cherweijie
/**
 * Controller for Delino's help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://bit.ly/38Y296W";
    public static final String HELP_MESSAGE = "Delino supports insert, clear, delete, done, edit, exit, search, help,"
            + " import, list, return, show and nearby commands.\n"
            + "Each feature will require a different set of input prefixes.\n"
            + "The examples below will better"
            + " illustrate the usage of the aforementioned commands in Delino.\n"

            + "\n 1. Insert a new order to the list of orders based on their order attributes\n"
            + "      Format: insert TRANSACTION_ID NAME ADDRESS PHONE_NUMBER DELIVERY_TIMESTAMP \n"
            + "                          EMAIL WAREHOUSE_ADDRESS CASH_ON_DELIVERY\n"
            + "      Eg. insert tid/9876543210 n/John Doe a/Blk 572 Hougang st 51 #10-33 S530572"
            + " p/98766789 \n              dts/2020-05-20 1300 e/johndoe@example.com w/Yishun cod/$4 \n"

            + "\n 2. Clear all orders/returns while orders are listed \n"
            + "      Format: clear FLAG INDEX \n"
            + "      Eg. clear -o -f (force clear order list without prompt) OR\n"
            + "      Eg. clear -r -f (force clear return list without prompt) \n"

            + "\n 3. Delete a specified order/return order from the list of orders/return orders \n"
            + "      Format: delete FLAG INDEX \n"
            + "      Eg. delete -o 2 (Removes the second order in the list) \n"

            + "\n 4. Marks a specified order as delivered \n"
            + "      Format: delivered FLAG INDEX \n"
            + "      Eg. delivered -o 3 (The third order in the order list will be marked as completed) \n"

            + "\n 5. Edit a parcel's specified attribute\n"
            + "      Format: edit FLAG INDEX PARCEL_ATTRIBUTE \n"
            + "      Eg. edit -o 2 n/James Charles (Edit the customer name in the second order of \n"
            + "                    the order list to 'James Charles') \n"

            + "\n 6. Exit the Delino application\n"
            + "      Format: exit \n"
            + "      Eg. exit OR you may choose to press 'F1' to close the application \n"

            + "\n 7. Search for a parcel based on either its attributes \n"
            + "      Format: search [FLAG] KEYWORD [MORE_KEYWORDS]... \n"
            + "      OR      search [FLAG] [ORDER_ATTRIBUTE_PREFIX/KEYWORD [MORE_KEYWORDS]... \n"
            + "                     [ORDER_ATTRIBUTE_PREFIX/KEYWORD] [MORE_KEYWORDS]... \n"
            + "      Eg. search -o amos (only searches order list) \n"
            + "      OR  search amos (searches both lists) \n"

            + "\n 8. Provide more information about Delino and its commands\n"
            + "      Format: help \n"
            + "      Eg. help OR you may choose to press 'F2' to open a help window \n"

            + "\n 9. Import a list of orders from a given .csv file with the order data format\n"
            + "      Format: import FILE_NAME \n"
            + "      Eg. import customers_20_02_2020.csv \n"
            + "      Please refer to the User Guide for more information regarding the order data format. \n"

            + "\n 10. Display the default list of orders or a list based on specified status\n"
            + "       Format: list OR list done OR list undone \n"
            + "       Eg. list OR list done OR list undone \n"

            + "\n 11. Create a return order and adds it into the list of returns\n"
            + "       Format: return TRANSACTION_ID NAME ADDRESS PHONE_NUMBER RETURN_TIMESTAMP WAREHOUSE_LOCATION \n"
            + "                      [COMMENTS] [ITEM_TYPE] \n"
            + "       OR      return TRANSACTION_ID RETURN_TIME_STAMP \n"
            + "       Eg. return tid/ac17s2a n/Bobby Tan a/123 Clementi Rd S120363\n"
            + "                  p/91230456 rts/2020-12-12 1300 w/Jurong Warehouse c/NIL type/glass \n"
            + "       OR  return tid/ac123d rts/2020-12-12 1300 \n"

            + "\n 12. View orders at a specified Singapore postal sector "
            + "(i.e. the first two digits of the postal code)\n"
            + "       Format: nearby POSTAL_SECTOR \n"
            + "       Eg. nearby 14 (View all orders located in the general location of Queenstown & Tiong Bahru)\n"
            + "       Please refer to the User Guide for the respective postal sectors in Singapore. \n"

            + "\n 13. View statistics of Delino's parcels\n"
            + "       Format: show \n"
            + "       OR      show START_DATE [END_DATE] \n"
            + "       OR      show all \n"
            + "       OR      show today \n"
            + "       OR      show DATE \n"
            + "       Eg. show 2020-01-01 2020-12-31 \n"
            + "       Eg. show all \n"
            + "       Eg. show today \n"
            + "       Eg. show 2020-04-10 \n"
            + "       Please refer to the User Guide for the respective usages of the show command. \n"

            + "\n For more information, please refer to Delino's User Guide.";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Scene scene;

    @FXML
    private Label helpMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        Text text = new Text(10, 40, HELP_MESSAGE);
        helpMessage.setText(HELP_MESSAGE);
        scene = new Scene(new Group(text));
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
