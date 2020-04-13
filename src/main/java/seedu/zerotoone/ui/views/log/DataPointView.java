package seedu.zerotoone.ui.views.log;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.zerotoone.logic.statistics.DataPoint;
import seedu.zerotoone.ui.util.UiPart;

/**
 * The type Data point view.
 */
public class DataPointView extends UiPart<Region> {

    private static final String FXML = "log/DataPoint.fxml";

    @FXML
    private Label labelName;

    @FXML
    private Label value;

    /**
     * Instantiates a new Data point view.
     *
     * @param dataPoint the data point
     */
    public DataPointView(DataPoint dataPoint) {
        super(FXML);

        this.labelName.setText(dataPoint.getLabelName() + ":");
        this.value.setText(dataPoint.getResult());
    }
}
