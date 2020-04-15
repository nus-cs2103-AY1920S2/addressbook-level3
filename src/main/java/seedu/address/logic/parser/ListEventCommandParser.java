package seedu.address.logic.parser;

import seedu.address.logic.commands.ListEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventDateComparator;

/**
 * Parses input arguments and creates a new ListEventCommand object.
 */
public class ListEventCommandParser implements Parser<ListEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListEventCommand
     * and returns an ListEventCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public ListEventCommand parse(String args) throws ParseException {

        EventDateComparator eventDateComparator = new EventDateComparator();
        return new ListEventCommand(eventDateComparator);
    }

}
