package thrift.ui;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.Label;

/**
 * Factory class for making coloured labels.
 */
public class CategoryLabelMaker {
    
    private static final String BACKGROUND_COLOR_GREEN = "-fx-background-color: #00897B";
    private static final String BACKGROUND_COLOR_RED = "-fx-background-color: #e53935";
    private static final String BACKGROUND_COLOR_BLUE = "-fx-background-color: #3949AB";
    private static final String BACKGROUND_COLOR_ORANGE = "-fx-background-color: #e65100";
    private static final String BACKGROUND_COLOR_PURPLE = "-fx-background-color: #755990";
    private static final String BACKGROUND_COLOR_LIGHT_GREEN = "-fx-background-color: #558B2F";
    private static final String BACKGROUND_COLOR_NAVY_BLUE = "-fx-background-color: #3e7b91";
    private static final String BACKGROUND_COLOR_YELLOW = "-fx-background-color: #FFA000";
    private static final String BACKGROUND_COLOR_PINK = "-fx-background-color: #ec407a";
    private static final String BACKGROUND_COLOR_GREY = "-fx-background-color: #959595";
    private static final String BACKGROUND_COLOR_DARK_GREY = "-fx-background-color: #616161";
    private static final String BACKGROUND_COLOR_BLACK = "-fx-background-color: #555555";
    private static final String BACKGROUND_COLOR_WHITE = "-fx-background-color: #FFFFFF";


    private static Map<String, String> mapCategoryColour = new HashMap<String, String>(0);


    /**
     * Takes in a category name and returns a JavaFX Label coloured based on it.
     *
     * @param categoryName of the category
     * @return a JavaFX Label coloured based on category name
     */
    public static Label getColouredCategoryLabel(String categoryName) {
        initMapCategoryColour();
        String trueCategoryName = categoryName.toLowerCase();

        Label colouredLabel = new Label(categoryName);

        if (mapCategoryColour.containsKey(trueCategoryName)) {
            colouredLabel.setStyle(mapCategoryColour.get(trueCategoryName));
        } else {
            colouredLabel.setStyle(BACKGROUND_COLOR_PURPLE);
        }

        return colouredLabel;
    }

    /**
     * Initialises the Category-Colour map if it hasn't been initialised yet
     */
    public static void initMapCategoryColour() {
        if (mapCategoryColour.size() > 0) {
            return;
        }

        mapCategoryColour.put("shopping", BACKGROUND_COLOR_RED);
        mapCategoryColour.put("health", BACKGROUND_COLOR_PINK);
        mapCategoryColour.put("food", BACKGROUND_COLOR_GREEN);
        mapCategoryColour.put("transport", BACKGROUND_COLOR_BLUE);
        mapCategoryColour.put("groceries", BACKGROUND_COLOR_LIGHT_GREEN);
        mapCategoryColour.put("recreation", BACKGROUND_COLOR_YELLOW);
        mapCategoryColour.put("utilities", BACKGROUND_COLOR_GREY);
        mapCategoryColour.put("income", BACKGROUND_COLOR_NAVY_BLUE);

        // Miscellaneous
        mapCategoryColour.put("misc", BACKGROUND_COLOR_ORANGE);

    }
}
