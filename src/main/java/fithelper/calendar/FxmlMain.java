package fithelper.calendar;
import java.time.YearMonth;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Sets the FXML.
 */
public class FxmlMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fullCalendar.fxml"));
        primaryStage.setTitle("Full Calendar FXML Example");
        primaryStage.setScene(new Scene(loader.load()));
        // Get the controller and add the calendar view to it
        Controller controller = loader.getController();
        controller.getCalendarPane().getChildren().add(new FullCalendarView(YearMonth.now()).getView());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
