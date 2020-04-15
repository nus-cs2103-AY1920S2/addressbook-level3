package hirelah.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.util.logging.Logger;

import hirelah.commons.core.LogsCenter;
import hirelah.model.hirelah.Attribute;
import hirelah.model.hirelah.Interviewee;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Pair;


/**
 * An UI component that displays information of a {@code Interviewee}.
 */
public class DetailedIntervieweeCard extends UiPart<Region> {

    private static final String FXML = "DetailedIntervieweeCard.fxml";

    public final Interviewee interviewee;
    private final Logger logger = LogsCenter.getLogger(DetailedIntervieweeCard.class);

    @FXML
    private VBox detailedIntervieweePane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label alias;
    @FXML
    private Button viewResume;
    @FXML
    private TableView<Pair<Attribute, Double>> attributeToScoreTable;


    public DetailedIntervieweeCard(Interviewee interviewee) {
        super(FXML);
        this.interviewee = interviewee;
        ObservableList<Pair<Attribute, Double>> attributeToScoreData = interviewee.getTranscript()
                .get()
                .getAttributeToScoreData();

        name.setText(interviewee.getFullName());
        id.setText("ID:         " + interviewee.getId());
        alias.setText("Alias:     " + interviewee.getAlias().orElse("No alias has been set."));
        viewResume.setText(interviewee.getResume().isPresent() ? "View Resume" : "No Resume");
        viewResume.setOnAction(en -> {
            if (Desktop.isDesktopSupported() && interviewee.getResume().isPresent()) {
                new Thread(() -> {
                    try {
                        Desktop.getDesktop().open(interviewee.getResume().get());
                    } catch (IOException e) {
                        logger.info(e.getMessage());
                    }
                }).start();
            }
        });
        loadAttributeToScoreTable(attributeToScoreData);
    }

    /**
     * Loads the table showing the score the user assigned to each Attribute for an Interviewee.
     *
     * @param attributeToScoreData An ObservableList of Attribute to score pairs.
     */
    private void loadAttributeToScoreTable(ObservableList<Pair<Attribute, Double>> attributeToScoreData) {
        attributeToScoreTable.setItems(attributeToScoreData);

        TableColumn<Pair<Attribute, Double>, String> attributeColumn = new TableColumn<>("Attribute:");
        attributeColumn.setCellValueFactory(p ->
                new SimpleObjectProperty<>(truncateAttributeName(p.getValue().getKey().toString())));
        attributeToScoreTable.getColumns().set(0, attributeColumn);

        TableColumn<Pair<Attribute, Double>, String> scoreColumn = new TableColumn<>("Score:");
        scoreColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(setValueLabels(p.getValue().getValue())));
        attributeToScoreTable.getColumns().set(1, scoreColumn);

        attributeColumn.prefWidthProperty().bind(attributeToScoreTable.widthProperty().multiply(0.58));
        scoreColumn.prefWidthProperty().bind(attributeToScoreTable.widthProperty().multiply(0.31));
        attributeColumn.setResizable(false);
        scoreColumn.setResizable(false);
        attributeToScoreTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    /**
     * Truncates the input Attribute Name if it is longer than 18 characters.
     *
     * @param attributeName String representing a tick on the X axis.
     *
     * @return String of the attributeName after truncation.
     */
    private String truncateAttributeName(String attributeName) {
        if (attributeName.length() > 18) {
            return attributeName.substring(0, 15) + "...";
        } else {
            return attributeName;
        }
    }

    /**
     * Formats a score to 3 decimal places.
     * If score has not been assigned yet (it is equal to NaN), it will be replaced by "-".
     *
     * @param value Double value of the score for an attribute.
     *
     * @return String representation of the score.
     */
    private String setValueLabels(Double value) {
        return !Double.isNaN(value) ? String.format("%.3f", value) : "-";
    }
}
