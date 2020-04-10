
package nasa.logic.commands;

import nasa.commons.util.StringUtil;
import nasa.model.Model;
import nasa.model.activity.Activity;
import javafx.collections.ObservableList;
import nasa.model.module.Module;

/**
 * Format contents of NasaBook to Qr code for export.
 */
public class ExportQrCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports the deadlines and events to QR code.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "QR code generated.";

    @Override
    public CommandResult execute(Model model) {
        ObservableList<Module> list = model.getFilteredModuleList();
        return new CommandResult(MESSAGE_SUCCESS, false, false, false, true,
                StringUtil.toQr(list.toString(), 500));
    }
}