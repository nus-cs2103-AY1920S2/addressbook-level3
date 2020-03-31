package tatracker.logic.commands;

import java.util.List;

import tatracker.logic.commands.group.AddGroupCommand;
import tatracker.logic.commands.group.DeleteGroupCommand;
import tatracker.logic.commands.group.EditGroupCommand;
import tatracker.logic.commands.module.AddModuleCommand;
import tatracker.logic.commands.module.DeleteModuleCommand;
import tatracker.logic.commands.session.AddSessionCommand;
import tatracker.logic.commands.session.DeleteSessionCommand;
import tatracker.logic.commands.session.EditSessionCommand;
import tatracker.logic.commands.student.AddStudentCommand;
import tatracker.logic.commands.student.DeleteStudentCommand;
import tatracker.logic.commands.student.EditStudentCommand;
import tatracker.logic.parser.Prefix;
import tatracker.logic.parser.PrefixDictionary;

/**
 * Stores a list of all the commands.
 */
public enum CommandEntry {
    STUDENT_ADD(AddStudentCommand.DETAILS),
    STUDENT_DELETE(DeleteStudentCommand.DETAILS),
    STUDENT_EDIT(EditStudentCommand.DETAILS),
    GROUP_ADD(AddGroupCommand.DETAILS),
    GROUP_DELETE(DeleteGroupCommand.DETAILS),
    GROUP_EDIT(EditGroupCommand.DETAILS),
    MODULE_ADD(AddModuleCommand.DETAILS),
    MODULE_DELETE(DeleteModuleCommand.DETAILS),
    SESSION_ADD(AddSessionCommand.DETAILS),
    SESSION_DELETE(DeleteSessionCommand.DETAILS),
    SESSION_EDIT(EditSessionCommand.DETAILS),
    // SORT_STUDENT(SortCommand.DETAILS),
    // SORT_GROUP(SortGroupCommand.DETAILS),
    // SORT_MODULE(SortModuleCommand.DETAILS),
    CLEAR(ClearCommand.DETAILS),
    HELP(HelpCommand.DETAILS),
    EXIT(ExitCommand.DETAILS);

    private final CommandDetails details;

    CommandEntry(CommandDetails details) {
        this.details = details;
    }

    public String getFullCommandWord() {
        return details.getFullCommandWord();
    }

    public String getCommandWord() {
        return details.getCommandWord();
    }

    public String getSubWord() {
        return details.getSubWord();
    }

    public String getInfo() {
        return details.getInfo();
    }

    public PrefixDictionary getDictionary() {
        return details.getDictionary();
    }

    public List<Prefix> getParameters() {
        return details.getParameters();
    }

    public List<Prefix> getOptionals() {
        return details.getOptionals();
    }

    public String getUsage() {
        return details.getUsage();
    }

    public String getExample() {
        return details.getExample();
    }

    // @Override
    // public String toString() {
    //
    //     final StringBuilder sb = new StringBuilder()
    //             .append(commandWord).append("\n")
    //             .append(info).append("\n")
    //             .append(parameters).append("\n")
    //             .append(optionals).append("\n")
    //             .append(usage).append("\n")
    //             .append(example).append("\n");
    //
    //     return sb.toString();
    // }
}
