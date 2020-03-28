package seedu.address.ui;

import java.awt.*;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.course.module.Module;

/**
 * Panel containing the list of persons.
 */
public class OverviewPanel extends UiPart<Region> {
    private static final String FXML = "OverviewPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ModuleListPanel.class);

    @FXML
    private TextFlow profileDetails;
    @FXML
    private ScrollPane modulesPanel;
    @FXML
    private ListView<Module> moduleSemesterPanel;


    public OverviewPanel(Profile profile) throws ParseException {
        super(FXML);

        //Profile details
        Text name = new Text("Name: " + profile.getName().fullName + "\n");
        name.setFont(Font.font("Segoe UI", 16));
        name.setFill(Color.WHITE);

        Text course = new Text("Course: " + profile.getCourseName().courseName + "\n");
        course.setFont(Font.font("Segoe UI", 14));
        course.setFill(Color.WHITE);

        Text specialisation;
        if (profile.getSpecialisation() != null) {
            specialisation = new Text("Specialisation: " + profile.getSpecialisation() + "\n");
        } else {
            specialisation = new Text("Specialisation: - \n");
        }
        specialisation.setFont(Font.font("Segoe UI", 14));
        specialisation.setFill(Color.WHITE);

        Text currentSem = new Text("Current Semester: " + Profile.getCurrentSemester());
        currentSem.setFont(Font.font("Segoe UI", 14));
        currentSem.setFill(Color.WHITE);

        profileDetails.getChildren().addAll(name, course, specialisation, currentSem);

        //Modules panel
        HBox modPane = new HBox();
        int totalNoOfSem = Profile.getAllModules().keySet().size();
        for (int i = 1; i <= totalNoOfSem; i++) {
            HBox temp = new HBox();
            temp.setAlignment(Pos.CENTER);
            temp.setMaxWidth(40);

            String yearSem;
            if (i == 1) {
                yearSem = "Year 1 Semester 1";
            } else if (i == 2) {
                yearSem = "Year 1 Semester 2";
            } else if (i == 3) {
                yearSem = "Year 2 Semester 1";
            } else if (i == 4) {
                yearSem = "Year 2 Semester 2";
            } else if (i == 5) {
                yearSem = "Year 3 Semester 1";
            } else if (i == 6) {
                yearSem = "Year 3 Semester 2";
            } else if (i == 7) {
                yearSem = "Year 4 Semester 1";
            } else {
                yearSem = "Year 4 Semester 2";
            }
            Label listTitle = new Label(yearSem.toUpperCase());


            listTitle.setRotate(-90);
            listTitle.setWrapText(true);
            listTitle.setStyle("-fx-font-family: \"Segoe Pro Display\"; -fx-font-weight: bold; " +
                    "-fx-font-size: 15; -fx-text-fill: #f7c4bb;");

            temp.getChildren().addAll(new Group(listTitle));

            ListView<Module> moduleSemPanel = new ListView<>();
            moduleSemPanel.setItems(Profile.getModules(i).getModuleList());
            moduleSemPanel.setCellFactory(listView -> new ModuleListViewCell());

            modPane.setBackground(new Background(
                    new BackgroundFill(Color.valueOf("#383838"), CornerRadii.EMPTY, Insets.EMPTY)));
            modPane.setSpacing(5);
            modPane.getChildren().addAll(temp, moduleSemPanel);
        }
        modulesPanel.setContent(modPane);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Profile} using a {@code DeadlineCard}.
     */
    class ModuleListViewCell extends ListCell<Module> {
        @Override
        protected void updateItem(Module module, boolean empty) {
            super.updateItem(module, empty);

            if (empty || module == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new OverviewModuleCard(module).getRoot());
            }
        }
    }

}
