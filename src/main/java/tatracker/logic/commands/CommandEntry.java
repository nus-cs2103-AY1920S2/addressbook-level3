package tatracker.logic.commands;

import java.util.List;

import tatracker.logic.commands.commons.ClearCommand;
import tatracker.logic.commands.commons.ExitCommand;
import tatracker.logic.commands.commons.GotoCommand;
import tatracker.logic.commands.commons.HelpCommand;
import tatracker.logic.commands.commons.ListCommand;
import tatracker.logic.commands.group.AddGroupCommand;
import tatracker.logic.commands.group.DeleteGroupCommand;
import tatracker.logic.commands.group.EditGroupCommand;
import tatracker.logic.commands.module.AddModuleCommand;
import tatracker.logic.commands.module.DeleteModuleCommand;
import tatracker.logic.commands.module.EditModuleCommand;
import tatracker.logic.commands.session.AddSessionCommand;
import tatracker.logic.commands.session.DeleteSessionCommand;
import tatracker.logic.commands.session.DoneSessionCommand;
import tatracker.logic.commands.session.EditSessionCommand;
import tatracker.logic.commands.session.FilterClaimCommand;
import tatracker.logic.commands.session.FilterSessionCommand;
import tatracker.logic.commands.sort.SortCommand;
import tatracker.logic.commands.sort.SortGroupCommand;
import tatracker.logic.commands.sort.SortModuleCommand;
import tatracker.logic.commands.statistic.ShowStatisticCommand;
import tatracker.logic.commands.student.AddStudentCommand;
import tatracker.logic.commands.student.DeleteStudentCommand;
import tatracker.logic.commands.student.EditStudentCommand;
import tatracker.logic.commands.student.FilterStudentViewCommand;
import tatracker.logic.parser.Prefix;
import tatracker.logic.parser.PrefixDictionary;

/**
 * Stores a list of all the commands.
 */
public enum CommandEntry {

    /* Student View */
    MODULE_ADD(AddModuleCommand.DETAILS),
    MODULE_DELETE(DeleteModuleCommand.DETAILS),
    MODULE_EDIT(EditModuleCommand.DETAILS),

    GROUP_ADD(AddGroupCommand.DETAILS),
    GROUP_DELETE(DeleteGroupCommand.DETAILS),
    GROUP_EDIT(EditGroupCommand.DETAILS),

    STUDENT_ADD(AddStudentCommand.DETAILS),
    STUDENT_DELETE(DeleteStudentCommand.DETAILS),
    STUDENT_EDIT(EditStudentCommand.DETAILS),
    STUDENT_FILTER(FilterStudentViewCommand.DETAILS),

    SORT_ALL(SortCommand.DETAILS),
    SORT_GROUP(SortGroupCommand.DETAILS),
    SORT_MODULE(SortModuleCommand.DETAILS),

    /* Session View */
    SESSION_ADD(AddSessionCommand.DETAILS),
    SESSION_DELETE(DeleteSessionCommand.DETAILS),
    SESSION_EDIT(EditSessionCommand.DETAILS),
    SESSION_FILTER(FilterSessionCommand.DETAILS),
    SESSION_DONE(DoneSessionCommand.DETAILS),

    LIST(ListCommand.DETAILS),

    /* TSS View */
    CLAIM_FILTER(FilterClaimCommand.DETAILS),

    /* Storage Operations */
    CLEAR(ClearCommand.DETAILS),

    /* Navigation */
    GOTO(GotoCommand.DETAILS),
    REPORT(ShowStatisticCommand.DETAILS),
    HELP(HelpCommand.DETAILS),
    EXIT(ExitCommand.DETAILS);

    private final CommandDetails details;

    CommandEntry(CommandDetails details) {
        this.details = details;
    }

    public CommandDetails getCommandDetails() {
        return details;
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
}
