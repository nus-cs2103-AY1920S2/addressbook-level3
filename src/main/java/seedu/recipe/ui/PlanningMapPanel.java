package seedu.recipe.ui;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import seedu.recipe.commons.core.LogsCenter;
import seedu.recipe.commons.util.StringUtil;
import seedu.recipe.model.plan.PlannedDate;
import seedu.recipe.model.plan.UniquePlannedList;
import seedu.recipe.model.recipe.Recipe;

/**
 * Panel containing the list of recipes.
 */
public class PlanningMapPanel extends UiPart<Region> {
    private static final String FXML = "PlanningMapPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RecipeListPanel.class);

    private static UniquePlannedList uniqueScheduleMap;

    //@FXML
    //private ListView<Recipe> planningMapView;

    @FXML
    private BorderPane borderPane;

    @FXML
    private GridPane calendarPane;

    @FXML
    private Label monthHeader;

    public PlanningMapPanel(ObservableMap subMap, List<Recipe> recipes) {
        super(FXML);

        //this.uniqueScheduleMap ;

        LocalDate timeNow = LocalDate.now(ZoneId.of("Singapore")).withDayOfMonth(1);
        System.out.println("First day of this month is: " + timeNow);
        Locale singaporeLocale = new Locale("en", "SGP");
        int numDaysThisMonth = timeNow.lengthOfMonth();

        //ObservableMap subMap = uniqueScheduleMap.getSubMapOfMonth(new PlannedDate(timeNow));

        String thisMonth = timeNow.getMonth().getDisplayName(TextStyle.FULL, singaporeLocale);
        monthHeader.setText(thisMonth);

        calendarPane.setGridLinesVisible(true);
        calendarPane.addRow(0,
                new Label("Monday"),
                new Label("Tuesday"),
                new Label("Wednesday"),
                new Label("Thursday"),
                new Label("Friday"),
                new Label("Saturday"),
                new Label("Sunday"));

        DayOfWeek getFirstDay = timeNow.getDayOfWeek();
        int dayOfWeek = getFirstDay.getValue() - 1;
        int weekOfMonth = 1;

        System.out.println(numDaysThisMonth);

        List<Recipe> stubRecipes = new ArrayList<>();
        stubRecipes.add(recipes.get(0));

        for (int i = 1; i <= numDaysThisMonth; i++) {
            PlannedDate curDate = new PlannedDate(timeNow);
            PlanningDayCard card = new PlanningDayCard((ObservableList<Recipe>) subMap.getOrDefault(curDate, null), i);

            //PlanningDayCard card = new PlanningDayCard(stubRecipes, i);
            calendarPane.add(card.getRoot(), dayOfWeek, weekOfMonth);

            dayOfWeek++;
            if (dayOfWeek > 6) {
                dayOfWeek = 0;
                weekOfMonth++;
                timeNow.plusDays(1);
            }
        }



    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Recipe} using a {@code RecipeCard}.
     */
    class PlanningListViewCell extends ListCell<Recipe> {
        @Override
        protected void updateItem(Recipe recipe, boolean empty) {
            super.updateItem(recipe, empty);
            if (empty || recipe == null) {
                setGraphic(null);
                setText(null);
            } else {
                try {
                    setGraphic(new PlanningMapCard(recipe, getIndex() + 1).getRoot());
                } catch (IOException e) {
                    logger.warning("Failed to favourites icon : " + StringUtil.getDetails(e));
                }
            }
        }
    }

}
