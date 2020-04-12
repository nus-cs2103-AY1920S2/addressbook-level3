package nasa.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import nasa.commons.core.index.Index;
import nasa.model.HistoryBook;
import nasa.model.Model;
import nasa.model.ModelManager;
import nasa.model.UserPrefs;
import nasa.model.activity.Date;
import nasa.model.activity.Deadline;
import nasa.model.activity.Name;
import nasa.model.module.ModuleCode;
import nasa.testutil.NasaBookBuilder;
import nasa.testutil.TypicalModules;

public class UndoCommandTest {

    private Model model = new ModelManager(new NasaBookBuilder().build(), new HistoryBook<>(),
            new HistoryBook<>(), new UserPrefs());

    @Test
    void multipleUndoCheck() {
        //add -> add -> add -> undo -> undo -> undo
        model.addModule(TypicalModules.CS2102);
        model.addModule(TypicalModules.CS2105);
        model.addModule(TypicalModules.CS2107);

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

        model.addModule(TypicalModules.CS2105);
        undoCommand.execute(model);

        model.addModule(TypicalModules.CS2107);
        undoCommand.execute(model);

        assertFalse(model.hasModule(new ModuleCode("CS2105")));
        assertFalse(model.hasModule(new ModuleCode("CS2107")));
    }

    @Test
    void complexCheck() {
        UndoCommand undoCommand = new UndoCommand();
        RedoCommand redoCommand = new RedoCommand();

        Deadline caseOne = new Deadline(new Name("Hello World"),
                new Date("20-04-2020 00:00"));

        Deadline caseTwo = new Deadline(new Name("Check"),
                new Date("20-06-2020 00:00"));

        Deadline caseThree = new Deadline(new Name("SEA"),
                new Date("20-06-2020 00:00"));

        Deadline caseFourth = new Deadline(new Name("TEST"),
                new Date("20-06-2020 00:00"));

        //add m/CS3233 n/Competitive Programming
        model.addModule(TypicalModules.CS2105);

        //add m/CS3033 n/Competitive Programming
        model.addModule(TypicalModules.CS2102);

        //deadline m/CS3233 a/Hello World d/20-04-2020 00:00
        model.addDeadline(new ModuleCode("CS2105"), caseOne);

        //repeat m/CS3233 a/Hello World r/1
        model.setDeadlineSchedule(new ModuleCode("CS2105"), Index.fromOneBased(1), Index.fromZeroBased(1));

        //add m/CS2105 n/Software
        model.addModule(TypicalModules.CS2107);

        //deadline m/CS2105 a/Check d/20-06-2020 00:00
        model.addDeadline(new ModuleCode("CS2105"), caseTwo);

        //deadline m/CS3233 d/20-05-2020 02:30 a/SEA
        model.addDeadline(new ModuleCode("CS2105"), caseThree);

        //-> undo -> undo -> redo -> redo
        //Ensure redo is working properly.
        undoCommand.execute(model);
        assertFalse(model.hasActivity(new ModuleCode("CS2105"), caseThree));

        undoCommand.execute(model);
        assertFalse(model.hasActivity(new ModuleCode("CS2105"), caseTwo));

        redoCommand.execute(model);

        assertTrue(model.hasActivity(new ModuleCode("CS2105"), caseTwo));

        redoCommand.execute(model);

        assertTrue(model.hasActivity(new ModuleCode("CS2105"), caseThree));

        //undo -> add deadline -> undo
        undoCommand.execute(model);

        model.addDeadline(new ModuleCode("CS2105"), caseFourth);

        undoCommand.execute(model);

        assertFalse(model.hasActivity(new ModuleCode("CS2105"), caseFourth));

        assertFalse(model.hasActivity(new ModuleCode("CS2105"), caseThree));

        assertTrue(model.hasActivity(new ModuleCode("CS2105"), caseTwo));
    }
}
