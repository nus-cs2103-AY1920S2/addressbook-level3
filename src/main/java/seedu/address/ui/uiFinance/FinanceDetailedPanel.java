package seedu.address.ui.uiFinance;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.modelFinance.Finance;
import seedu.address.ui.CommandBox;
import seedu.address.ui.UiPart;

import java.util.logging.Logger;

/**
 * Panel containing the list of finances.
 */
public class FinanceDetailedPanel extends UiPart<Region> {

    private static final String FXML = "FinanceDetailedPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(FinanceDetailedPanel.class);
    private CommandBox commandBox;

    @FXML
    private ListView<Finance> financeDetailedView;

//  Map: key -> value
//  {
//    "details": Finance
//    "course": [CourseDetail]
//  }

    public FinanceDetailedPanel(ObservableMap<String, Object> financeMap, CommandBox commandBox) {
        super(FXML);
        this.commandBox = commandBox;
        financeMap.addListener(new MapChangeListener<String, Object>() {
            @Override
            public void onChanged(MapChangeListener.Change<? extends String, ? extends Object> change) {
                ObservableMap<String, Object> newFinanceMap = (ObservableMap<String, Object>) change.getMap();
                updateDetailView(newFinanceMap);
            }
        });
    }

    private void updateDetailView(ObservableMap<String, Object> newFinanceMap) {
        if (newFinanceMap.containsKey("details")) {
            Finance finance = (Finance) newFinanceMap.get("details");
            ObservableList<Finance> filteredFinances = FXCollections.observableArrayList();
            filteredFinances.add(finance);
            financeDetailedView.setItems(filteredFinances);
            financeDetailedView.setCellFactory(listView -> new FinanceListViewCell());
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Finance} using a {@code
     * CourseCard}.
     */
    class FinanceListViewCell extends ListCell<Finance> {

        @Override
        protected void updateItem(Finance finance, boolean empty) {
            super.updateItem(finance, empty);

            if (empty || finance == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new FinanceDetailedCard(finance, commandBox, getIndex() + 1).getRoot());
            }
        }
    }

}
