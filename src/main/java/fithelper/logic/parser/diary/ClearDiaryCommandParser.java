package fithelper.logic.parser.diary;

import fithelper.logic.diary.ClearDiaryCommand;
import fithelper.logic.parser.Parser;

/**
 * Parses input arguments and creates a new ClearDiaryCommand object
 */
public class ClearDiaryCommandParser implements Parser<ClearDiaryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ClearDiaryCommand
     * and returns an ClearDiaryCommand object for execution.
     */
    public ClearDiaryCommand parse(String args) {
        return new ClearDiaryCommand();
    }

}
