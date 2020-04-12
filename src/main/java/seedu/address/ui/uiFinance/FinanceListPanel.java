package seedu.address.ui.uiFinance;

import javafx.collections.ObservableList;
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
public class FinanceListPanel extends UiPart<Region> {

    private static final String FXML = "FinanceListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(FinanceListPanel.class);
    private CommandBox commandBox;

    @FXML
    private ListView<Finance> financeListView;

    public FinanceListPanel(ObservableList<Finance> financeList, CommandBox commandBox) {
        super(FXML);
        this.commandBox = commandBox;
        financeListView.setItems(financeList);
        financeListView.setCellFactory(listView -> new FinanceListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Finance} using a {@code
     * FinanceCard}.
     */
    class FinanceListViewCell extends ListCell<Finance> {

        @Override
        protected void updateItem(Finance finance, boolean empty) {
            super.updateItem(finance, empty);

            if (empty || finance == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new FinanceCard(finance, commandBox, getIndex() + 1).getRoot());
            }
        }
    }

}
