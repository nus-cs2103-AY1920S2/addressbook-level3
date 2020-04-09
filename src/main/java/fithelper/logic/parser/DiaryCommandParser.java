package fithelper.logic.parser;

import fithelper.logic.diary.DiaryCommand;

/**
 * Parses input arguments and creates a new TpdayCommand object
 */
public class DiaryCommandParser implements Parser<DiaryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TpdayCommand
     * and returns an TpdayCommand object for execution.
     */
    public DiaryCommand parse(String args) {
        return new DiaryCommand();
    }

}
