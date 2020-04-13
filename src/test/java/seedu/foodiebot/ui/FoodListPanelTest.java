package seedu.foodiebot.ui;

import static seedu.foodiebot.model.util.SampleDataUtil.getTagSet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.ListViewMatchers;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import seedu.foodiebot.model.food.Food;
import seedu.foodiebot.model.util.SampleDataUtil;

@ExtendWith(ApplicationExtension.class)
class FoodListPanelTest {
    private static final Food COMBO_SET = new Food("Combo Set", 6, "1 Meat, 1 Pasta, 2 Sides", "Combo Set.png",
            1, "Nus Flavors", "Western", getTagSet("western", "expensive"));
    private FoodListPanel panel = new FoodListPanel(
            FXCollections.observableArrayList(SampleDataUtil.getSampleFoods()));
    private ListView<Food> listView;
    /** Create the ui card. */
    @BeforeAll
    public static void setupSpec() throws Exception {
        FxToolkit.registerPrimaryStage();

    }

    /** Create the ui card. */
    @Start
    private void start(Stage stage) {
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(panel.getRoot());
        stage.setScene(new Scene(stackPane));
        stage.show();
        listView = panel.getSimpleListView();
    }

    @Test
    void init(FxRobot robot) {
        FxAssert.verifyThat(listView, ListViewMatchers.hasItems(
                25));
        FxAssert.verifyThat(listView, ListViewMatchers.hasItems(
                SampleDataUtil.getSampleFoods().length));
    }
}
