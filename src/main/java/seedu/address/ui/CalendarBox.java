package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.ReadOnlyPlanner;
import seedu.address.model.module.Lesson;
import seedu.address.model.module.LessonDataImporter;
import seedu.address.model.module.ModuleCode;

/**
 * An UI component that displays information of a {@code Timetable}.
 */
public class CalendarBox extends UiPart<Region> {
    private static final String FXML = "calendarBox.fxml";
    private final Logger logger = LogsCenter.getLogger(CalendarBox.class);
    private ReadOnlyPlanner planner;
    private LessonDataImporter lessonDataImporter = new LessonDataImporter();

    @FXML
    private Label semester;

    @FXML
    private Label dayTime;

    @FXML
    private AnchorPane am1;
    @FXML
    private AnchorPane am2;
    @FXML
    private AnchorPane am3;
    @FXML
    private AnchorPane am4;
    @FXML
    private AnchorPane am5;
    @FXML
    private AnchorPane am6;
    @FXML
    private AnchorPane am7;
    @FXML
    private AnchorPane am8;
    @FXML
    private AnchorPane am9;
    @FXML
    private AnchorPane am10;
    @FXML
    private AnchorPane am11;
    @FXML
    private AnchorPane am12;
    @FXML
    private AnchorPane at1;
    @FXML
    private AnchorPane at2;
    @FXML
    private AnchorPane at3;
    @FXML
    private AnchorPane at4;
    @FXML
    private AnchorPane at5;
    @FXML
    private AnchorPane at6;
    @FXML
    private AnchorPane at7;
    @FXML
    private AnchorPane at8;
    @FXML
    private AnchorPane at9;
    @FXML
    private AnchorPane at10;
    @FXML
    private AnchorPane at11;
    @FXML
    private AnchorPane at12;
    @FXML
    private AnchorPane aw1;
    @FXML
    private AnchorPane aw2;
    @FXML
    private AnchorPane aw3;
    @FXML
    private AnchorPane aw4;
    @FXML
    private AnchorPane aw5;
    @FXML
    private AnchorPane aw6;
    @FXML
    private AnchorPane aw7;
    @FXML
    private AnchorPane aw8;
    @FXML
    private AnchorPane aw9;
    @FXML
    private AnchorPane aw10;
    @FXML
    private AnchorPane aw11;
    @FXML
    private AnchorPane aw12;
    @FXML
    private AnchorPane ath1;
    @FXML
    private AnchorPane ath2;
    @FXML
    private AnchorPane ath3;
    @FXML
    private AnchorPane ath4;
    @FXML
    private AnchorPane ath5;
    @FXML
    private AnchorPane ath6;
    @FXML
    private AnchorPane ath7;
    @FXML
    private AnchorPane ath8;
    @FXML
    private AnchorPane ath9;
    @FXML
    private AnchorPane ath10;
    @FXML
    private AnchorPane ath11;
    @FXML
    private AnchorPane ath12;
    @FXML
    private AnchorPane af1;
    @FXML
    private AnchorPane af2;
    @FXML
    private AnchorPane af3;
    @FXML
    private AnchorPane af4;
    @FXML
    private AnchorPane af5;
    @FXML
    private AnchorPane af6;
    @FXML
    private AnchorPane af7;
    @FXML
    private AnchorPane af8;
    @FXML
    private AnchorPane af9;
    @FXML
    private AnchorPane af10;
    @FXML
    private AnchorPane af11;
    @FXML
    private AnchorPane af12;

    @FXML
    private Label t89;
    @FXML
    private Label t910;
    @FXML
    private Label t1011;
    @FXML
    private Label t1112;
    @FXML
    private Label t1213;
    @FXML
    private Label t1314;
    @FXML
    private Label t1415;
    @FXML
    private Label t1516;
    @FXML
    private Label t1617;
    @FXML
    private Label t1718;
    @FXML
    private Label t1819;
    @FXML
    private Label t1920;

    @FXML
    private Label m1;
    @FXML
    private Label m2;
    @FXML
    private Label m3;
    @FXML
    private Label m4;
    @FXML
    private Label m5;
    @FXML
    private Label m6;
    @FXML
    private Label m7;
    @FXML
    private Label m8;
    @FXML
    private Label m9;
    @FXML
    private Label m10;
    @FXML
    private Label m11;
    @FXML
    private Label m12;

    @FXML
    private Label t1;
    @FXML
    private Label t2;
    @FXML
    private Label t3;
    @FXML
    private Label t4;
    @FXML
    private Label t5;
    @FXML
    private Label t6;
    @FXML
    private Label t7;
    @FXML
    private Label t8;
    @FXML
    private Label t9;
    @FXML
    private Label t10;
    @FXML
    private Label t11;
    @FXML
    private Label t12;

    @FXML
    private Label w1;
    @FXML
    private Label w2;
    @FXML
    private Label w3;
    @FXML
    private Label w4;
    @FXML
    private Label w5;
    @FXML
    private Label w6;
    @FXML
    private Label w7;
    @FXML
    private Label w8;
    @FXML
    private Label w9;
    @FXML
    private Label w10;
    @FXML
    private Label w11;
    @FXML
    private Label w12;

    @FXML
    private Label th1;
    @FXML
    private Label th2;
    @FXML
    private Label th3;
    @FXML
    private Label th4;
    @FXML
    private Label th5;
    @FXML
    private Label th6;
    @FXML
    private Label th7;
    @FXML
    private Label th8;
    @FXML
    private Label th9;
    @FXML
    private Label th10;
    @FXML
    private Label th11;
    @FXML
    private Label th12;

    @FXML
    private Label f1;
    @FXML
    private Label f2;
    @FXML
    private Label f3;
    @FXML
    private Label f4;
    @FXML
    private Label f5;
    @FXML
    private Label f6;
    @FXML
    private Label f7;
    @FXML
    private Label f8;
    @FXML
    private Label f9;
    @FXML
    private Label f10;
    @FXML
    private Label f11;
    @FXML
    private Label f12;


    /**
     * Constructor for CalendarBox class
     */
    public CalendarBox(ReadOnlyPlanner planner) {
        super(FXML);
        this.planner = planner;
        dayTime.setText("Day / Time");
        m1.setText("");
        m2.setText("");
        m3.setText("");
        m4.setText("");
        m5.setText("");
        m6.setText("");
        m7.setText("");
        m8.setText("");
        m9.setText("");
        m10.setText("");
        m11.setText("");
        m12.setText("");
        t1.setText("");
        t2.setText("");
        t3.setText("");
        t4.setText("");
        t5.setText("");
        t6.setText("");
        t7.setText("");
        t8.setText("");
        t9.setText("");
        t10.setText("");
        t11.setText("");
        t12.setText("");
        w1.setText("");
        w2.setText("");
        w3.setText("");
        w4.setText("");
        w5.setText("");
        w6.setText("");
        w7.setText("");
        w8.setText("");
        w9.setText("");
        w10.setText("");
        w11.setText("");
        w12.setText("");
        th1.setText("");
        th2.setText("");
        th3.setText("");
        th4.setText("");
        th5.setText("");
        th6.setText("");
        th7.setText("");
        th8.setText("");
        th9.setText("");
        th10.setText("");
        th11.setText("");
        th12.setText("");
        f1.setText("");
        f2.setText("");
        f3.setText("");
        f4.setText("");
        f5.setText("");
        f6.setText("");
        f7.setText("");
        f8.setText("");
        f9.setText("");
        f10.setText("");
        f11.setText("");
        f12.setText("");
    }

    /**
     * Todo once timetable can be seen
     */
    public void setCalendar(ReadOnlyPlanner planner) {
        ObservableList<ModuleCode> mod = planner.getEnrolledModulesList();
        ArrayList<ModuleCode> codes = new ArrayList<>();
        List<Lesson> lessonsMod = new ArrayList<>();
        for (int i = 0; i < mod.size(); i++) {
            lessonsMod = lessonDataImporter.run(mod.get(i).toString());
            System.out.println(lessonsMod.get(1).getStartTime());
        }

    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CalendarBox)) {
            return false;
        }
        return false;
    }

}
