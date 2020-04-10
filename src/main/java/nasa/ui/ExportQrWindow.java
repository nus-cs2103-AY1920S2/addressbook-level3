package nasa.ui;

import java.io.ByteArrayInputStream;
import java.util.logging.Logger;

import nasa.commons.core.LogsCenter;
import nasa.commons.util.StringUtil;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ExportQrWindow extends UiPart<Stage> {

    public static final Logger logger = LogsCenter.getLogger(ExportQrWindow.class);
    public static final String FXML = "ExportQrWindow.fxml";

    @FXML
    private ImageView qrCode;

    /**
     * Creates a new ExportWindow.
     *
     * @param root Stage to use as the root of the ExportWindow.
     *
     */
    public ExportQrWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new HelpWindow.
     */
    public ExportQrWindow() {
        this(new Stage());
    }

    public void update(byte[] pngData) {
        Image qrCode = new Image(new ByteArrayInputStream(pngData));
        this.qrCode.setImage(qrCode);
    }

    public void show() {
        this.logger.fine("Showing QR Code to user");
        this.getRoot().show();
        this.getRoot().centerOnScreen();
    }

    public boolean isShowing() {
        return this.getRoot().isShowing();
    }

    public void hide() {
        this.getRoot().hide();
    }

    public void focus() {
        this.getRoot().requestFocus();
    }
}