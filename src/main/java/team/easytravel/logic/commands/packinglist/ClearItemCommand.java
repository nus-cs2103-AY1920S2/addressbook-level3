package team.easytravel.logic.commands.packinglist;

import static java.util.Objects.requireNonNull;
import static team.easytravel.logic.commands.CommandResult.Action.SWITCH_TAB_PACKING_LIST;

import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.model.Model;
import team.easytravel.model.listmanagers.PackingListManager;

/**
 * The type Clear item command.
 */
public class ClearItemCommand extends Command {
    /**
     * The constant COMMAND_WORD.
     */
    public static final String COMMAND_WORD = "clearitem";
    /**
     * The constant MESSAGE_SUCCESS.
     */
    public static final String MESSAGE_SUCCESS = "Packing List has been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setPackingListManager(new PackingListManager());
        return new CommandResult(MESSAGE_SUCCESS, SWITCH_TAB_PACKING_LIST);
    }
}
