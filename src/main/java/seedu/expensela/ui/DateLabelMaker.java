package seedu.expensela.ui;

import javafx.scene.control.Label;

/**
 * Factory class for making coloured labels.
 */
public class DateLabelMaker {

    private static final String BACKGROUND_COLOR_WHITE = "-fx-background-color: #FFFFFF; -fx-text-fill: black;";

    /**
     * Takes in a date name and returns a JavaFX Label coloured based on it.
     *
     * @param dateName of the date
     * @return a JavaFX Label coloured based on date name
     */
    public static Label getColouredDateLabel(String dateName) {

        Label colouredLabel = new Label(dateName);

        colouredLabel.setStyle(BACKGROUND_COLOR_WHITE);

        return colouredLabel;
    }
}
