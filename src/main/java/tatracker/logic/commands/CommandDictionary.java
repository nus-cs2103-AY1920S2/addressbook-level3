// @@author potatocombat

package tatracker.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import tatracker.logic.commands.commons.ClearCommand;
import tatracker.logic.commands.commons.ExitCommand;
import tatracker.logic.commands.commons.GotoCommand;
import tatracker.logic.commands.commons.HelpCommand;
import tatracker.logic.commands.commons.ListCommand;
import tatracker.logic.commands.commons.SetRateCommand;
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
import tatracker.logic.commands.student.FilterStudentCommand;

/**
 * Stores a list of all the commands.
 */
public class CommandDictionary {
    private static final List<CommandDetails> COMMAND_DETAILS = List.of(
        /* Student View */
        AddModuleCommand.DETAILS,
        DeleteModuleCommand.DETAILS,
        EditModuleCommand.DETAILS,

        AddGroupCommand.DETAILS,
        DeleteGroupCommand.DETAILS,
        EditGroupCommand.DETAILS,

        AddStudentCommand.DETAILS,
        DeleteStudentCommand.DETAILS,
        EditStudentCommand.DETAILS,
        FilterStudentCommand.DETAILS,

        SortCommand.DETAILS,
        SortGroupCommand.DETAILS,
        SortModuleCommand.DETAILS,

        /* Session View */
        AddSessionCommand.DETAILS,
        DeleteSessionCommand.DETAILS,
        EditSessionCommand.DETAILS,
        DoneSessionCommand.DETAILS,

        /* Session - Claims Filtering */
        FilterSessionCommand.DETAILS,
        FilterClaimCommand.DETAILS,
        ListCommand.DETAILS,

        /* Claims View */
        SetRateCommand.DETAILS,

        /* Storage Operations */
        ClearCommand.DETAILS,

        /* Navigation */
        GotoCommand.DETAILS,
        HelpCommand.DETAILS,
        ShowStatisticCommand.DETAILS,
        ExitCommand.DETAILS
    );

    private static final Map<String, CommandDetails> FULL_COMMAND_WORDS = COMMAND_DETAILS
            .stream()
            .collect(Collectors.toUnmodifiableMap(CommandDetails::getFullCommandWord,
                entry -> entry, (key1, key2) -> {
                    throw new IllegalArgumentException("CommandDictionary: cannot have two commands with the same id");
                }));

    /**
     * Returns true if the {@code fullCommandWord} is valid.
     */
    public static boolean hasFullCommandWord(String fullCommandWord) {
        requireNonNull(fullCommandWord);
        return FULL_COMMAND_WORDS.containsKey(fullCommandWord);
    }

    /**
     * Returns the matching CommandDetails based on the {@code fullCommandWord}.
     */
    public static CommandDetails getDetailsWithFullCommandWord(String fullCommandWord) {
        requireNonNull(fullCommandWord);
        return FULL_COMMAND_WORDS.get(fullCommandWord);
    }

    /**
     * Returns the message information of all the commands.
     */
    public static List<CommandDetails> getDetails() {
        return COMMAND_DETAILS;
    }
}
