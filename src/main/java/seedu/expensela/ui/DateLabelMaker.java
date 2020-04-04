package seedu.expensela.ui;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.Label;

/**
 * Factory class for making coloured labels.
 */
public class DateLabelMaker {

    private static final String BACKGROUND_COLOR_GREEN = "-fx-background-color: #00897B; -fx-text-fill: white;";
    private static final String BACKGROUND_COLOR_RED = "-fx-background-color: #e53935; -fx-text-fill: white;";
    private static final String BACKGROUND_COLOR_BLUE = "-fx-background-color: #3949AB; -fx-text-fill: white;";
    private static final String BACKGROUND_COLOR_ORANGE = "-fx-background-color: #e65100; -fx-text-fill: white;";
    private static final String BACKGROUND_COLOR_PURPLE = "-fx-background-color: #755990; -fx-text-fill: white;";
    private static final String BACKGROUND_COLOR_LIGHT_GREEN = "-fx-background-color: #558B2F; -fx-text-fill: white;";
    private static final String BACKGROUND_COLOR_NAVY_BLUE = "-fx-background-color: #3e7b91; -fx-text-fill: white;";
    private static final String BACKGROUND_COLOR_YELLOW = "-fx-background-color: #FFA000; -fx-text-fill: white;";
    private static final String BACKGROUND_COLOR_PINK = "-fx-background-color: #ec407a; -fx-text-fill: white;";
    private static final String BACKGROUND_COLOR_GREY = "-fx-background-color: #959595; -fx-text-fill: white;";
    private static final String BACKGROUND_COLOR_DARK_GREY = "-fx-background-color: #616161; -fx-text-fill: white;";
    private static final String BACKGROUND_COLOR_BLACK = "-fx-background-color: #555555; -fx-text-fill: white;";
    private static final String BACKGROUND_COLOR_WHITE = "-fx-background-color: #FFFFFF; -fx-text-fill: black;";


    private static Map<String, String> mapDateColour = new HashMap<String, String>(0);


    /**
     * Takes in a date name and returns a JavaFX Label coloured based on it.
     *
     * @param dateName of the date
     * @return a JavaFX Label coloured based on date name
     */
    public static Label getColouredDateLabel(String dateName) {
        initMapDateColour();
        String trueDateName = dateName.toLowerCase();

        Label colouredLabel = new Label(dateName);

        colouredLabel.setStyle(BACKGROUND_COLOR_WHITE);

        return colouredLabel;
    }

    /**
     * Initialises the Date-Colour map if it hasn't been initialised yet
     */
    public static void initMapDateColour() {
        if (mapDateColour.size() > 0) {
            return;
        }

        mapDateColour.put("2020-01", BACKGROUND_COLOR_RED);
        mapDateColour.put("2020-02", BACKGROUND_COLOR_PINK);
        mapDateColour.put("2020-03", BACKGROUND_COLOR_GREEN);
        mapDateColour.put("2020-04", BACKGROUND_COLOR_BLUE);
        mapDateColour.put("2020-05", BACKGROUND_COLOR_LIGHT_GREEN);
        mapDateColour.put("2020-06", BACKGROUND_COLOR_YELLOW);
        mapDateColour.put("2020-07", BACKGROUND_COLOR_GREY);
        mapDateColour.put("2020-08", BACKGROUND_COLOR_NAVY_BLUE);
        mapDateColour.put("2020-09", BACKGROUND_COLOR_ORANGE);
        mapDateColour.put("2020-10", BACKGROUND_COLOR_PURPLE);
        mapDateColour.put("2020-11", BACKGROUND_COLOR_DARK_GREY);
        mapDateColour.put("2020-12", BACKGROUND_COLOR_BLACK);
    }
}
