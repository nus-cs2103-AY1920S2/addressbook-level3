package fithelper.logic.parser;

import fithelper.logic.parser.exceptions.ParseException;

/**
 * This class is for testing and debugging only.
 */
public class ParserMain {
    /**
     * For debugging.
     * @param args input
     * @throws ParseException exception
     */
    public static void main(String[] args) throws ParseException {
        EditCommandParser tryE =  new EditCommandParser();
        tryE.parsePrint("edit x/food i/1 n/waffles");
    }
}
