package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.hirelah.Question;

import java.util.logging.Logger;

/**
 * Panel containing the list of questions.
 */
public class QuestionListPanel extends UiPart<Region> {
    private static final String FXML = "GeneralListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(QuestionListPanel.class);

    @FXML
    private ListView<Question> questionListView;

    public QuestionListPanel(ObservableList<Question> questionList) {
        super(FXML);
        questionListView.setItems(questionList);
    }
}
