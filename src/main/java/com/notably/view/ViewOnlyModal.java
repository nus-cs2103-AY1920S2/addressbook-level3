package com.notably.view;

import static com.notably.commons.util.CollectionUtil.requireAllNonNull;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import com.notably.commons.LogsCenter;
import com.notably.model.Model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;


/**
 * API for Modals that are used in the Notably application.
 */
public abstract class ViewOnlyModal extends ViewPart<Stage> {

    protected static String loggerSource;
    protected Logger logger;

    protected Stage stage;
    protected Window parentStage;
    protected Model model;
    protected BooleanProperty modelProperty;

    public ViewOnlyModal(String fxml, Stage root, Model model, BooleanProperty modelProperty,
                         String loggerSource) {
        super(fxml, root);

        requireAllNonNull(model, modelProperty, loggerSource);

        this.model = model;
        this.modelProperty = modelProperty;
        this.loggerSource = loggerSource;
        this.logger = getLoggingClass();
        this.stage = root;
        this.parentStage = Stage.getWindows().stream().filter(Window::isShowing).findFirst().get();

        setStageStyle();
        setChangeListeners();
        setInitialData();
    }

    /**
     * Sets up the modal's initial data;
     */
    protected abstract void setInitialData();

    /**
     * Helper method that sets the value of the modal's modelProperty.
     *
     * @param bool true if the property is to be set as true, false otherwise.
     */
    protected abstract void setModelProperty(Boolean bool);

    protected Logger getLoggingClass() {
        return LogsCenter.getLogger(this.getClass());
    }
    /**
     * Customizes the appearance and attributes of the modal.
     */
    protected void setStageStyle() {
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initOwner(parentStage);
        stage.initModality(Modality.WINDOW_MODAL);
    }

    /**
     * Sets listeners that update the content in the modal and toggle the visibility
     * and controls of the modal.
     */
    protected void setChangeListeners() {
        setModalVisibilityListeners();
        setKeyboardListeners();
        setInitialDimensionListeners();
        setStageDimensionListeners();
    }

    /**
     * Sets listeners that bring the modal into view when required, and in doing so apply
     * certain stylings to the main app window.
     */
    protected void setModalVisibilityListeners() {
        updateMainWindowStyle(true);
        modelProperty.addListener((observable, unused, isPropertyValueTrue) -> {
            if (isPropertyValueTrue) {
                handle();
                setModelProperty(false);
            }
            updateMainWindowStyle(isPropertyValueTrue);
        });
    }

    /**
     * Sets listeners to trigger changes in the visibility of the modal when certain Keystrokes
     * are registered. Primarily, allows for the user to exit the modal using the "ESC"
     * keyboard shortcut.
     */
    protected void setKeyboardListeners() {
        stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                handleClose();
            }
        });
    }

    /**
     * Sets listeners that correctly initialise the X and Y coordinates of the modal such
     * that it is centered to the main app window.
     */
    protected void setInitialDimensionListeners() {
        ChangeListener<Number> widthListener = (observable, oldValue, newValue) -> {
            setXDisplacement();
        };
        ChangeListener<Number> heightListener = (observable, oldValue, newValue) -> {
            setYDisplacement();
        };
        stage.widthProperty().addListener(widthListener);
        stage.heightProperty().addListener(heightListener);

        stage.setOnShown(e -> {
            stage.widthProperty().removeListener(widthListener);
            stage.heightProperty().removeListener(heightListener);
        });
    }

    /**
     * Sets listeners that change the dimensions of the modal upon being repeatedly
     * opened and closed.
     */
    protected void setStageDimensionListeners() {
        setXDisplacement();
        setYDisplacement();
        stage.setOnShowing(event -> {
            setStageDimensionListeners();
        });
    }

    /**
     * Closes the modal.
     */
    @FXML
    public void handleClose() {
        hide();
    }

    /**
     * Opens the modal or focuses on it if it's already opened.
     */
    protected void handle() {
        if (!isShowing()) {
            show();
        } else {
            focus();
        }
    }

    /**
     * Applies a darkening effect to the main app window, whenever the modal
     * is currently being displayed.
     *
     * @param isModalShowing A boolean that is true if the modal is to be shown, false otherwise.
     */
    protected void updateMainWindowStyle(Boolean isModalShowing) {
        requireNonNull(isModalShowing);
        Node mainWindow = parentStage.getScene().lookup("#mainWindow");

        ChangeListener<Boolean> focusedListener = (observable, unused, isFocused) -> {
            if (!isFocused && stage.isShowing()) {
                ColorAdjust colorAdjust = new ColorAdjust();
                colorAdjust.setBrightness(-0.4);
                mainWindow.setEffect(colorAdjust);
            } else {
                mainWindow.setEffect(null);
            }
        };

        if (isModalShowing) {
            parentStage.focusedProperty().addListener(focusedListener);
        } else {
            parentStage.focusedProperty().removeListener(focusedListener);
        }
    }

    /**
     * Shows the modal.
     *
     * @throws IllegalStateException <ul>
     *                               <li>
     *                               if this method is called on a thread other than the JavaFX
     *                               Application Thread.
     *                               </li>
     *                               <li>
     *                               if this method is called during animation or layout processing.
     *                               </li>
     *                               <li>
     *                               if this method is called on the primary stage.
     *                               </li>
     *                               <li>
     *                               if {@code dialogStage} is already showing.
     *                               </li>
     *                               </ul>
     */
    public void show() {
        logger.info("Showing " + loggerSource);
        getRoot().show();
    }

    /**
     * Returns true if the modal is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the modal.
     */
    public void hide() {
        getRoot().hide();
        logger.info("Closing " + loggerSource);
    }

    /**
     * Focuses on the modal.
     */
    public void focus() {
        getRoot().requestFocus();
    }


    /**
     * Calculates and sets the width of the modal with respect to the main app window.
     */
    protected void setXDisplacement() {
        Double newWidth = parentStage.getWidth() * 0.8;
        stage.setWidth(newWidth);
        stage.setX(parentStage.getX() + parentStage.getWidth() / 2 - newWidth / 2);
    }

    /**
     * Calculates and sets the height of the modal with respect to the main app window.
     */
    protected void setYDisplacement() {
        Double newHeight = parentStage.getHeight() * 0.7;
        stage.setHeight(newHeight);
        stage.setY(parentStage.getY() + parentStage.getHeight() / 3 - newHeight / 3);
    }
}
