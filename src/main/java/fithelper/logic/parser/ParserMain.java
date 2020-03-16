package fithelper.logic.parser;

import fithelper.logic.parser.exceptions.ParseException;

public class ParserMain {
    public static void main(String[] args) throws ParseException {
        DeleteCommandParser tryDCP =  new DeleteCommandParser();
        tryDCP.parsePrint("delete x/f i/1");

    }
}
