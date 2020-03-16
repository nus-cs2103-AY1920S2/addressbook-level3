package fithelper.logic.parser;

import fithelper.logic.parser.exceptions.ParseException;

/**
 * This class is for testing and debugging only.
 */
public class ParserMain {
    public static void main(String[] args) throws ParseException {
        EditCommandParser tryECP =  new EditCommandParser();
        tryECP.parsePrint("edit x/food i/1 n/waffles");
    }
}
