package nasa.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import nasa.model.HistoryBook;
import nasa.model.Model;
import nasa.model.ModelManager;
import nasa.model.UserPrefs;
import nasa.model.module.ModuleCode;
import nasa.model.module.ModuleName;
import nasa.testutil.NasaBookBuilder;

public class UndoCommandTest {

    private Model model = new ModelManager(new NasaBookBuilder().build(), new HistoryBook<>(), new UserPrefs());

    /*
    @Test
    void multipleUndoCheck() {
        //add -> add -> add -> undo -> undo -> undo
        model.addModule(new ModuleCode("CS2105"), new ModuleName("Computer Networks"));
        model.addModule(new ModuleCode("CS2107"), new ModuleName("Computer Security"));
        model.addModule(new ModuleCode("CS2102"), new ModuleName("Database"));

        UndoCommand undoCommand = new UndoCommand();

        undoCommand.execute(model);
        undoCommand.execute(model);
        undoCommand.execute(model);

        assertFalse(model.hasModule(new ModuleCode("CS2105")));
        assertFalse(model.hasModule(new ModuleCode("CS2107")));
        assertFalse(model.hasModule(new ModuleCode("CS2102")));
    }

    @Test
    void inBetweenUndoCheck() {
        //add -> undo -> add -> undo
        UndoCommand undoCommand = new UndoCommand();

        model.addModule(new ModuleCode("CS2105"), new ModuleName("Computer Networks"));
        undoCommand.execute(model);

        model.addModule(new ModuleCode("CS2107"), new ModuleName("Computer Security"));
        undoCommand.execute(model);

        assertFalse(model.hasModule(new ModuleCode("CS2105")));
        assertFalse(model.hasModule(new ModuleCode("CS2107")));
    }

    @Test
    void complexCheck() {
        UndoCommand undoCommand = new UndoCommand();
        RedoCommand redoCommand = new RedoCommand();

        //add m/CS3233 n/Competitive Programming
        model.addModule(new ModuleCode("CS3233"), new ModuleName("Competitive programming"));

        //add m/CS3033 n/Competitive Programming
        model.addModule(new ModuleCode("CS3033"), new ModuleName("Competitive programming"));

        //deadline m/CS3233 a/Hello World d/20-04-2020 00:00
        model.addActivity(new ModuleCode("CS3233"), new Deadline(new Name("Hello World"),
                new Date("20-04-2020 00:00")));

        //repeat m/CS3233 a/Hello World r/1
        model.setSchedule(new ModuleCode("CS3233"), new Name("Hello World"), Index.fromZeroBased(1));

        //add m/CS2105 n/Software
        model.addModule(new ModuleCode("CS2105"), new ModuleName("Computer Network"));

        //deadline m/CS2105 a/Check d/20-06-2020 00:00
        model.addActivity(new ModuleCode("CS2105"), new Deadline(new Name("Check"),
                new Date("20-06-2020 00:00")));

        //deadline m/CS3233 d/20-05-2020 02:30 a/SEA
        model.addActivity(new ModuleCode("CS3233"), new Deadline(new Name("SEA"),
                new Date("20-06-2020 00:00")));

        //-> undo -> undo -> redo -> redo
        //Ensure redo is working properly.
        undoCommand.execute(model);

        assertFalse(model.hasActivity(new ModuleCode("CS3233"), new Name("SEA")));

        undoCommand.execute(model);

        assertFalse(model.hasActivity(new ModuleCode("CS2105"), new Name("Check")));

        redoCommand.execute(model);

        assertTrue(model.hasActivity(new ModuleCode("CS2105"), new Name("Check")));

        redoCommand.execute(model);

        assertTrue(model.hasActivity(new ModuleCode("CS3233"), new Name("SEA")));

        //undo -> add deadline -> undo
        undoCommand.execute(model);

        model.addActivity(new ModuleCode("CS3233"), new Deadline(new Name("TEST"),
                new Date("20-06-2020 00:00")));

        undoCommand.execute(model);

        assertFalse(model.hasActivity(new ModuleCode("CS3233"), new Name("TEST")));

        assertFalse(model.hasActivity(new ModuleCode("CS3233"), new Name("SEA")));

        assertTrue(model.hasActivity(new ModuleCode("CS2105"), new Name("Check")));
    }*/
}
