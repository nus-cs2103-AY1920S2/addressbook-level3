
package nasa.logic.commands;

import javafx.collections.ObservableList;

import nasa.commons.util.StringUtil;
import nasa.model.Model;
import nasa.model.activity.Activity;
import nasa.model.activity.Deadline;
import nasa.model.activity.Event;
import nasa.model.module.Module;
import nasa.model.module.ModuleCode;

/**
 * Format contents of NasaBook to Qr code for export.
 */
public class ExportQrCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports the deadlines and events to QR code.\n"
            + "Parameters: none\nExample: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "QR code generated.";

    public static final String STRING_DELIMITER = "****************\n";

    public static final String DEFAULT_INDENTATION = "    ";

    @Override
    public CommandResult execute(Model model) {
        StringBuilder sb = new StringBuilder();
        ObservableList<Module> moduleList = model.getFilteredModuleList();
        for (Module module : moduleList) {
            ModuleCode moduleCode = module.getModuleCode();

            sb.append(STRING_DELIMITER);
            sb.append(module.toString()).append('\n');

            ObservableList<Deadline> deadlineObservableList = model.getFilteredDeadlineList(moduleCode);
            ObservableList<Event> eventObservableList = model.getFilteredEventList(moduleCode);

            sb.append("Deadlines\n");

            if (deadlineObservableList.isEmpty()) {
                sb.append(DEFAULT_INDENTATION).append("No deadlines for this module.\n");
            }

            for (Deadline d : deadlineObservableList) {
                int index = deadlineObservableList.indexOf(d) + 1;
                sb.append(DEFAULT_INDENTATION).append(index).append(". ").append(d.getName().toString()).append('\n');
                sb.append(DEFAULT_INDENTATION).append(d.getDueDate().toString()).append('\n');
                sb.append(DEFAULT_INDENTATION).append("Priority: ").append(d.getPriority().toString()).append('\n');

                if (!isEmptyNoteString(d.getNote().toString())) {
                    sb.append(DEFAULT_INDENTATION).append(d.getNote().toString()).append('\n');
                }

                if (d.isDone()) {
                    sb.append(DEFAULT_INDENTATION).append("DONE").append('\n');
                }
            }

            sb.append("Events\n");

            if (eventObservableList.isEmpty()) {
                sb.append(DEFAULT_INDENTATION).append("No events for this module.\n");
            }

            for (Event e : eventObservableList) {
                int index = eventObservableList.indexOf(e) + 1;
                sb.append(DEFAULT_INDENTATION).append(index).append(". ").append(e.getName().toString()).append('\n');
                sb.append(DEFAULT_INDENTATION).append(e.getStartDate().toString()).append(" to ")
                        .append(e.getEndDate().toString()).append('\n');

                if (!isEmptyNoteString(e.getNote().toString())) {
                    sb.append(DEFAULT_INDENTATION).append(e.getNote().toString()).append('\n');
                }
            }
            sb.append('\n');
        }
        return new CommandResult(MESSAGE_SUCCESS, false, false, false, true,
                StringUtil.toQr(sb.toString(), 500));
    }

    /**
     * Returns true if note is empty, given by {@code Activity.EMPTY_NOTE_STRING}.
     * @param str note string to be tested.
     * @return true if note is empty (ie. "-"), else false.
     */
    private boolean isEmptyNoteString(String str) {
        if (str.equals(Activity.EMPTY_NOTE_STRING)) {
            return true;
        }
        return false;
    }

}
