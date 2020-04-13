package seedu.eylah.diettracker.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eylah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eylah.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.eylah.testutil.Assert.assertThrows;
import static seedu.eylah.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.eylah.commons.core.index.Index;
import seedu.eylah.commons.logic.parser.exception.ParseException;
import seedu.eylah.diettracker.logic.commands.AddCommand;
import seedu.eylah.diettracker.logic.commands.BmiCommand;
import seedu.eylah.diettracker.logic.commands.DeleteCommand;
import seedu.eylah.diettracker.logic.commands.EditCommand;
import seedu.eylah.diettracker.logic.commands.EditCommand.EditFoodDescriptor;
import seedu.eylah.diettracker.logic.commands.HeightCommand;
import seedu.eylah.diettracker.logic.commands.HelpCommand;
import seedu.eylah.diettracker.logic.commands.ListCommand;
import seedu.eylah.diettracker.logic.commands.MetricsCommand;
import seedu.eylah.diettracker.logic.commands.ModeCommand;
import seedu.eylah.diettracker.logic.commands.WeightCommand;
import seedu.eylah.diettracker.model.DietModelManager;
import seedu.eylah.diettracker.model.Mode;
import seedu.eylah.diettracker.model.food.Food;
import seedu.eylah.diettracker.model.self.Height;
import seedu.eylah.diettracker.model.self.Weight;
import seedu.eylah.diettracker.testutil.EditFoodDescriptorBuilder;
import seedu.eylah.diettracker.testutil.FoodBuilder;
import seedu.eylah.diettracker.testutil.FoodUtil;

public class FoodBookParserTest {

    private final FoodBookParser parser = new FoodBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Food food = new FoodBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(FoodUtil.getAddCommand(food));
        assertEquals(new AddCommand(food).execute(new DietModelManager()), command.execute(new DietModelManager()));
    }

    @Test
    public void parseCommand_bmi() throws Exception {
        BmiCommand command = (BmiCommand) parser.parseCommand(
                BmiCommand.COMMAND_WORD + " " + DietCliSyntax.PREFIX_HEIGHT + " 170.2 "
                        + DietCliSyntax.PREFIX_WEIGHT + " 67" + ".5");
        assertEquals(new BmiCommand(new Height("170.2"), new Weight("67.5")), command);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    // TO MAKE THIS PASS
    // EditCommand.java equal for getDates has been commented out.
    @Test
    public void parseCommand_edit() throws Exception {
        Food food = new FoodBuilder().build();
        EditFoodDescriptor descriptor = new EditFoodDescriptorBuilder(food).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + "-i 1 " + FoodUtil.getEditFoodDescriptorDetails(descriptor));
        assertEquals((new EditCommand(Index.fromOneBased(1), descriptor)), command);
    }

    @Test
    public void parseCommand_height() throws Exception {
        HeightCommand command = (HeightCommand) parser.parseCommand(
                HeightCommand.COMMAND_WORD + " 170.2");
        assertEquals(new HeightCommand(new Height("170.2")), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " -a") instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " -d 3") instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " -t healthy") instanceof ListCommand);
    }

    @Test
    public void parseCommand_mode() throws Exception {
        ModeCommand command = (ModeCommand) parser.parseCommand(
                ModeCommand.COMMAND_WORD + " -g");
        assertEquals(new ModeCommand(Mode.GAIN), command);
    }

    @Test
    public void parseCommand_weight() throws Exception {
        WeightCommand command = (WeightCommand) parser.parseCommand(
                WeightCommand.COMMAND_WORD + " 60.2");
        assertEquals(new WeightCommand(new Weight("60.2")), command);
    }

    @Test
    public void parseCommand_metrics() throws Exception {
        MetricsCommand command = (MetricsCommand) parser.parseCommand(
                MetricsCommand.COMMAND_WORD);
        assertEquals(new MetricsCommand(), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
