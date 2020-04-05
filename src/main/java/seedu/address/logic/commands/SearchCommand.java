package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.searcher.Module;
import seedu.address.searcher.Search;

/**
 * <h1>Search Command Class</h1>
 * Handler for Search Command
 */
public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE =
            "Use search to perform a module search. Syntax is search <module code>\n"
                    + "Example: search CS1101S";

    public static final String SEARCH_FAILURE = "There is no such Module";
    public static final String SYNTAX_FAILURE = "Your command seems to be wrong";

    private String modToSearch;

    public SearchCommand(String modToSearch) {
        this.modToSearch = modToSearch;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        Module myMod;

        try {
            myMod = Search.findModule(modToSearch);

            return new CommandResult(myMod.toString());
        } catch (StringIndexOutOfBoundsException e) {
            return new CommandResult(SYNTAX_FAILURE);
        }
    }
}
