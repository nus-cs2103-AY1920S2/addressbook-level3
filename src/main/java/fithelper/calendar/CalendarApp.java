package fithelper.calendar;

import javafx.application.Application;
import jfxtras.icalendarfx.VCalendar;
import javafx.stage.Stage;
import jfxtras.scene.control.agenda.icalendar.ICalendarAgenda;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;

public class CalendarApp extends Application
{
    @Override
    public void start(Stage primaryStage) {
        VCalendar vCalendar = new VCalendar();
        ICalendarAgenda agenda = new ICalendarAgenda(vCalendar);

        BorderPane root = new BorderPane();
        root.setCenter(agenda);
        Scene scene = new Scene(root, 1366, 768);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
