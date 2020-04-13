package com.flashspeed.ui;

import java.util.logging.Logger;

import com.flashspeed.commons.core.LogsCenter;
import com.flashspeed.model.Statistics;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Controller for a statistics page
 */
public class StatisticsPopUp extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(StatisticsPopUp.class);
    private static final String FXML = "StatisticsPopUp.fxml";



    @FXML
    private Label stats;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public StatisticsPopUp(Stage root, Statistics statistics) {
        super(FXML, root);
        if (statistics != null) {
            stats.setText(statistics.toString());
        }

    }

    /**
     * Creates a new HelpWindow.
     */
    public StatisticsPopUp(Statistics statistics) {
        this(new Stage(), statistics);
    }


    /**
     * Shows the statistics window.
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
        logger.fine("Showing statistics of the previous play session");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the statistics window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the statistics window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the statistics window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
