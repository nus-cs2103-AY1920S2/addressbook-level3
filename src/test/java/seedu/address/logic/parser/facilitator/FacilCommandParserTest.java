package seedu.address.logic.parser.facilitator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_FACILITATOR_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalFacilitators.AMY;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.facilitator.FacilAddCommand;
import seedu.address.logic.commands.facilitator.FacilDeleteCommand;
import seedu.address.logic.commands.facilitator.FacilEditCommand;
import seedu.address.logic.commands.facilitator.FacilFindCommand;
import seedu.address.logic.commands.facilitator.FacilListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.facilitator.Facilitator;
import seedu.address.model.facilitator.NameContainsKeywordsPredicate;
import seedu.address.testutil.EditFacilitatorDescriptorBuilder;
import seedu.address.testutil.FacilitatorBuilder;
import seedu.address.testutil.FacilitatorUtil;

public class FacilCommandParserTest {
    private FacilCommandParser parser = new FacilCommandParser();

    @Test
    public void parse_add() throws Exception {
        Facilitator facilitator = new FacilitatorBuilder(AMY).build();
        FacilAddCommand command = (FacilAddCommand) parser.parse(FacilitatorUtil.getFacilAddCommand(facilitator));
        assertEquals(new FacilAddCommand(facilitator), command);
    }

    @Test
    public void parse_delete() throws Exception {
        FacilDeleteCommand command = (FacilDeleteCommand) parser.parse(Command.COMMAND_WORD_DELETE + " "
                + INDEX_FIRST.getOneBased());
        assertEquals(new FacilDeleteCommand(INDEX_FIRST), command);
    }

    @Test
    public void parse_edit() throws Exception {
        Facilitator facilitator = new FacilitatorBuilder(AMY).build();
        FacilEditCommand.EditFacilitatorDescriptor descriptor = new EditFacilitatorDescriptorBuilder(facilitator)
                .build();
        FacilEditCommand command = (FacilEditCommand) parser.parse(Command.COMMAND_WORD_EDIT + " "
                + INDEX_FIRST.getOneBased() + " "
                + FacilitatorUtil.getEditFacilitatorDescriptorDetails(descriptor));
        assertEquals(new FacilEditCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parse_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FacilFindCommand command = (FacilFindCommand) parser.parse(Command.COMMAND_WORD_FIND + " "
                + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FacilFindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parse_list() throws Exception {
        assertTrue(parser.parse(Command.COMMAND_WORD_LIST)
                instanceof FacilListCommand);
        assertTrue(parser.parse(Command.COMMAND_WORD_LIST + " 3")
                instanceof FacilListCommand);
    }

    @Test
    public void parse_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_UNKNOWN_FACILITATOR_COMMAND,
                HelpCommand.MESSAGE_USAGE), () -> parser.parse(""));
    }

    @Test
    public void parse_unknownFacilitatorCommand_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_UNKNOWN_FACILITATOR_COMMAND,
                HelpCommand.MESSAGE_USAGE), () -> parser
                .parse("facil unknownCommand"));
    }
}
