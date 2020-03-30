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

/**
 * Stores a list of all the commands.
 */
public enum CommandEntry {
    STUDENT_ADD(
            AddStudentCommand.COMMAND_WORD,
            AddStudentCommand.INFO,
            AddStudentCommand.PARAMETERS,
            AddStudentCommand.OPTIONALS,
            AddStudentCommand.USAGE,
            AddStudentCommand.EXAMPLE
    ),
    STUDENT_DELETE(
            DeleteStudentCommand.COMMAND_WORD,
            DeleteStudentCommand.INFO,
            DeleteStudentCommand.PARAMETERS,
            DeleteStudentCommand.USAGE,
            DeleteStudentCommand.EXAMPLE
    ),
    STUDENT_EDIT(
            EditStudentCommand.COMMAND_WORD,
            EditStudentCommand.INFO,
            EditStudentCommand.PARAMETERS,
            EditStudentCommand.OPTIONALS,
            EditStudentCommand.USAGE,
            EditStudentCommand.EXAMPLE
    ),
    GROUP_ADD(
            AddGroupCommand.COMMAND_WORD,
            AddGroupCommand.INFO,
            AddGroupCommand.PARAMETERS,
            AddGroupCommand.USAGE,
            AddGroupCommand.EXAMPLE
    ),
    GROUP_DELETE(
            DeleteGroupCommand.COMMAND_WORD,
            DeleteGroupCommand.INFO,
            DeleteGroupCommand.PARAMETERS,
            DeleteGroupCommand.USAGE,
            DeleteGroupCommand.EXAMPLE
    ),
    GROUP_EDIT(
            EditGroupCommand.COMMAND_WORD,
            EditGroupCommand.INFO,
            EditGroupCommand.PARAMETERS,
            EditGroupCommand.OPTIONALS,
            EditGroupCommand.USAGE,
            EditGroupCommand.EXAMPLE
    ),
    MODULE_ADD(
            AddModuleCommand.COMMAND_WORD,
            AddModuleCommand.INFO,
            AddModuleCommand.PARAMETERS,
            AddModuleCommand.USAGE,
            AddModuleCommand.EXAMPLE
    ),
    MODULE_DELETE(
            DeleteModuleCommand.COMMAND_WORD,
            DeleteModuleCommand.INFO,
            DeleteModuleCommand.PARAMETERS,
            DeleteModuleCommand.USAGE,
            DeleteModuleCommand.EXAMPLE
    ),
    SESSION_ADD(
            AddSessionCommand.COMMAND_WORD,
            AddSessionCommand.INFO,
            AddSessionCommand.PARAMETERS,
            AddSessionCommand.OPTIONALS,
            AddSessionCommand.USAGE,
            AddSessionCommand.EXAMPLE
    ),
    SESSION_DELETE(
            DeleteSessionCommand.COMMAND_WORD,
            DeleteSessionCommand.INFO,
            DeleteSessionCommand.PARAMETERS,
            DeleteSessionCommand.USAGE,
            DeleteSessionCommand.EXAMPLE
    ),
    SESSION_EDIT(
            EditSessionCommand.COMMAND_WORD,
            EditSessionCommand.INFO,
            EditSessionCommand.PARAMETERS,
            EditSessionCommand.OPTIONALS,
            EditSessionCommand.USAGE,
            EditSessionCommand.EXAMPLE
    ),
    // SORT_STUDENT(
    //         SortCommand.COMMAND_WORD,
    //         SortCommand.INFO,
    //         SortCommand.PARAMETERS,
    //         SortCommand.USAGE,
    //         SortCommand.EXAMPLE
    // ),
    // SORT_GROUP(
    //         SortGroupCommand.COMMAND_WORD,
    //         SortGroupCommand.INFO,
    //         SortGroupCommand.PARAMETERS,
    //         SortGroupCommand.USAGE,
    //         SortGroupCommand.EXAMPLE
    // ),
    // SORT_MODULE(
    //         SortModuleCommand.COMMAND_WORD,
    //         SortModuleCommand.INFO,
    //         SortModuleCommand.PARAMETERS,
    //         SortModuleCommand.USAGE,
    //         SortModuleCommand.EXAMPLE
    // ),
    CLEAR(
            ClearCommand.COMMAND_WORD,
            ClearCommand.INFO
    ),
    HELP(
            HelpCommand.COMMAND_WORD,
            HelpCommand.INFO
    ),
    EXIT(
            ExitCommand.COMMAND_WORD,
            ExitCommand.INFO
    );

    private final String commandWord;
    private final String info;

    private final List<Prefix> parameters;
    private final List<Prefix> optionals;

    private final String usage;
    private final String example;

    CommandEntry(String commandWord,
                 String info,
                 List<Prefix> parameters,
                 List<Prefix> optionals,
                 String usage,
                 String example) {

        this.commandWord = commandWord;
        this.info = info;
        this.parameters = parameters;
        this.optionals = optionals;
        this.usage = usage;
        this.example = example;
    }

    CommandEntry(String commandWord,
                 String info,
                 List<Prefix> parameters,
                 String usage,
                 String example) {
        this(commandWord, info, parameters, List.of(), usage, example);
    }

    CommandEntry(String commandWord,
                 String info) {
        this(commandWord, info, List.of(), List.of(), "", "");
    }

    public String getCommandWord() {
        return commandWord;
    }

    public String getInfo() {
        return info;
    }

    public List<Prefix> getParameters() {
        return parameters;
    }

    public List<Prefix> getOptionals() {
        return optionals;
    }

    public String getUsage() {
        return usage;
    }

    public String getExample() {
        return example;
    }
}
