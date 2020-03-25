package fithelper.logic.parser;

import fithelper.logic.commands.TodayCommand;

/**
 * Parses input arguments and creates a new TpdayCommand object
 */
public class TodayCommandParser implements Parser<TodayCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TpdayCommand
     * and returns an TpdayCommand object for execution.
     */
    public TodayCommand parse(String args) {
        return new TodayCommand();
    }

}
