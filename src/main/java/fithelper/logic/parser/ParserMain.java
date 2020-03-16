package fithelper.logic.parser;

import fithelper.logic.commands.EditCommand;
import fithelper.logic.parser.exceptions.ParseException;

public class ParserMain {
    public static void main(String[] args) throws ParseException {
        EditCommandParser tryECP =  new EditCommandParser();
        tryECP.parsePrint("edit x/food i/1 n/waffles");
    }
}
