package com.notably.view;

import static com.notably.commons.util.CollectionUtil.requireAllNonNull;

import java.util.logging.Logger;

import com.notably.MainApp;
import com.notably.commons.LogsCenter;
import com.notably.commons.util.StringUtil;
import com.notably.logic.Logic;
import com.notably.model.Model;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * The manager of the VIEW component.
 */
public class ViewManager implements View {

    public static final String ALERT_DIALOG_PANE_FIELD_ID = "alertDialogPane";

    private static final Logger logger = LogsCenter.getLogger(ViewManager.class);
    private static final String ICON_APPLICATION = "/images/notably_logo_48.png";

    private Logic logic;
    private Model model;
    private MainWindow mainWindow;

    public ViewManager(Logic logic, Model model) {
        super();

        requireAllNonNull(logic, model);

        this.logic = logic;
        this.model = model;
    }

    @Override
    public void start(Stage primaryStage) {
        requireAllNonNull(primaryStage);

        logger.info("Starting VIEW...");

        primaryStage.getIcons().add(getImage(ICON_APPLICATION));

        try {
            mainWindow = new MainWindow(primaryStage, logic, model);
            mainWindow.show();
            mainWindow.fillInnerParts();

        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    private Image getImage(String imagePath) {
        requireAllNonNull(imagePath);

        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
        requireAllNonNull(type, title, headerText, contentText);

        showAlertDialogAndWait(mainWindow.getPrimaryStage(), type, title, headerText, contentText);
    }

    /**
     * Shows an alert dialog on {@code owner} with the given parameters.
     * This method only returns after the user has closed the alert dialog.
     */
    private static void showAlertDialogAndWait(Stage owner, AlertType type, String title, String headerText,
                                               String contentText) {
        requireAllNonNull(owner, type, title, headerText, contentText);

        final Alert alert = new Alert(type);
        alert.getDialogPane().getStylesheets().add("view/NotablyTheme.css");
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.getDialogPane().setId(ALERT_DIALOG_PANE_FIELD_ID);
        alert.showAndWait();
    }

    /**
     * Shows an error alert dialog with {@code title} and error message, {@code e},
     * and exits the application after the user has closed the alert dialog.
     */
    private void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        logger.severe(title + " " + e.getMessage() + StringUtil.getDetails(e));
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }

}
