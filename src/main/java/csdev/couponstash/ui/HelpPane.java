package csdev.couponstash.ui;

import java.util.Arrays;
import java.util.logging.Logger;

import csdev.couponstash.commons.core.LogsCenter;
import csdev.couponstash.logic.commands.AddCommand;
import csdev.couponstash.logic.commands.ArchiveCommand;
import csdev.couponstash.logic.commands.ClearCommand;
import csdev.couponstash.logic.commands.CopyCommand;
import csdev.couponstash.logic.commands.DeleteCommand;
import csdev.couponstash.logic.commands.EditCommand;
import csdev.couponstash.logic.commands.ExitCommand;
import csdev.couponstash.logic.commands.ExpandCommand;
import csdev.couponstash.logic.commands.ExpiringCommand;
import csdev.couponstash.logic.commands.FindCommand;
import csdev.couponstash.logic.commands.GoToCommand;
import csdev.couponstash.logic.commands.HelpCommand;
import csdev.couponstash.logic.commands.ListCommand;
import csdev.couponstash.logic.commands.RedoCommand;
import csdev.couponstash.logic.commands.SavedCommand;
import csdev.couponstash.logic.commands.SetCurrencyCommand;
import csdev.couponstash.logic.commands.ShareCommand;
import csdev.couponstash.logic.commands.SortCommand;
import csdev.couponstash.logic.commands.UnarchiveCommand;
import csdev.couponstash.logic.commands.UndoCommand;
import csdev.couponstash.logic.commands.UsedCommand;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of commands.
 */
public class HelpPane extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(HelpPane.class);
    private static final String FXML = "HelpPane.fxml";

    private static final ObservableList<CommandCard> content =
            FXCollections.observableList(Arrays.asList(
                    new CommandCard(AddCommand.COMMAND_WORD, AddCommand.MESSAGE_USAGE),
                    new CommandCard(ArchiveCommand.COMMAND_WORD, ArchiveCommand.MESSAGE_USAGE),
                    new CommandCard(ClearCommand.COMMAND_WORD, ClearCommand.MESSAGE_USAGE),
                    new CommandCard(CopyCommand.COMMAND_WORD, CopyCommand.MESSAGE_USAGE),
                    new CommandCard(DeleteCommand.COMMAND_WORD, DeleteCommand.MESSAGE_USAGE),
                    new CommandCard(EditCommand.COMMAND_WORD, EditCommand.MESSAGE_USAGE),
                    new CommandCard(ExitCommand.COMMAND_WORD, ExitCommand.MESSAGE_USAGE),
                    new CommandCard(ExpandCommand.COMMAND_WORD, ExpandCommand.MESSAGE_USAGE),
                    new CommandCard(ExpiringCommand.COMMAND_WORD, ExpiringCommand.MESSAGE_USAGE),
                    new CommandCard(FindCommand.COMMAND_WORD, FindCommand.MESSAGE_USAGE),
                    new CommandCard(GoToCommand.COMMAND_WORD, GoToCommand.MESSAGE_USAGE),
                    new CommandCard(HelpCommand.COMMAND_WORD, HelpCommand.MESSAGE_USAGE),
                    new CommandCard(ListCommand.COMMAND_WORD, ListCommand.MESSAGE_USAGE),
                    new CommandCard(RedoCommand.COMMAND_WORD, RedoCommand.MESSAGE_USAGE),
                    new CommandCard(SavedCommand.COMMAND_WORD, SavedCommand.MESSAGE_USAGE),
                    new CommandCard(SetCurrencyCommand.COMMAND_WORD, SetCurrencyCommand.MESSAGE_USAGE),
                    new CommandCard(ShareCommand.COMMAND_WORD, ShareCommand.MESSAGE_USAGE),
                    new CommandCard(SortCommand.COMMAND_WORD, SortCommand.MESSAGE_USAGE),
                    new CommandCard(UnarchiveCommand.COMMAND_WORD, UnarchiveCommand.MESSAGE_USAGE),
                    new CommandCard(UndoCommand.COMMAND_WORD, UndoCommand.MESSAGE_USAGE),
                    new CommandCard(UsedCommand.COMMAND_WORD, UsedCommand.MESSAGE_USAGE)
            ));

    @FXML
    private ListView<CommandCard> commandListView;

    /**
     * Constructor for a HelpPane.
     *
     */
    public HelpPane() {
        super(FXML);
        logger.info("Loading help pane...");
        commandListView.setItems(content);
        commandListView.setCellFactory(listView -> new CommandListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Coupon} using a {@code CouponCard}.
     */
    class CommandListViewCell extends ListCell<CommandCard> {
        @Override
        protected void updateItem(CommandCard commandCard, boolean empty) {
            super.updateItem(commandCard, empty);

            if (empty || commandCard == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(commandCard.getRoot());
            }
        }
    }
}
