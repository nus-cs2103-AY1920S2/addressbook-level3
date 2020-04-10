package seedu.zerotoone.ui;

import javafx.application.Preloader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SplashScreen extends Preloader {
    private final String FXML = "/view/SplashScreen.fxml";
    private FXMLLoader loader = new FXMLLoader();
    private Stage stage;

    @FXML
    private ImageView imageView;

    @FXML
    private ProgressBar progressBar;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        // Remove Windows Border
        stage.initStyle(StageStyle.UNDECORATED);

        // Load fxml
        loader.setLocation(getClass().getResource(FXML));
        loader.setController(this);
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        // Set Width and Height based on Screen Size
        Rectangle2D screenBoundary = Screen.getPrimary().getVisualBounds();
        stage.setWidth(screenBoundary.getWidth() / 3);
        stage.setHeight(screenBoundary.getWidth() / 3 + 20);

        // Set Width and Height of inner elements
        imageView.preserveRatioProperty();
        imageView.fitWidthProperty().bind(stage.widthProperty());
        imageView.fitHeightProperty().bind(stage.heightProperty());
        progressBar.prefWidthProperty().bind(stage.widthProperty());

        // Center Stage
        stage.setX((screenBoundary.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBoundary.getHeight() - stage.getHeight()) / 2);
        stage.show();
    }
 
    @Override
    public void handleApplicationNotification(PreloaderNotification info) {
        if (info instanceof ProgressNotification) {
           double v = ((ProgressNotification) info).getProgress();
           progressBar.setProgress(v);
        } else if (info instanceof StateChangeNotification) {
            stage.hide();
        }
    }  
}