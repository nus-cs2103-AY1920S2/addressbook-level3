//@@author aakanksha-rai

package tatracker.logic.commands.sort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_GROUP_CODE;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_MODULE_CODE;
import static tatracker.testutil.Assert.assertThrows;
import static tatracker.testutil.TypicalTaTracker.getTypicalTaTracker;
import static tatracker.testutil.student.TypicalStudents.ALICE;
import static tatracker.testutil.student.TypicalStudents.AMY;
import static tatracker.testutil.student.TypicalStudents.BOB;
import static tatracker.testutil.student.TypicalStudents.DANIEL;
import static tatracker.testutil.student.TypicalStudents.FIONA;
import static tatracker.testutil.student.TypicalStudents.GEORGE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;

import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.Model;
import tatracker.model.ModelManager;
import tatracker.model.UserPrefs;
import tatracker.model.group.Group;
import tatracker.model.module.Module;
import tatracker.model.student.Student;
import tatracker.testutil.group.GroupBuilder;
import tatracker.testutil.module.ModuleBuilder;

public class SortGroupIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTaTracker(), new UserPrefs());
    }

    @Test
    public void execute_groupEditedByModelAlphabetic_sortSuccessful() throws Exception {
        Module validModule = new ModuleBuilder().build();
        Group validGroup = new GroupBuilder().withStudent(BOB).withStudent(ALICE)
                .withStudent(AMY).withStudent(DANIEL)
                .withStudent(GEORGE).withStudent(FIONA).build();
        validModule.addGroup(validGroup);
        model.addModule(validModule);

        CommandResult commandResult = new SortGroupCommand(SortType.ALPHABETIC, validGroup.getIdentifier(),
                validModule.getIdentifier()).execute(model);

        assertEquals(String.format(SortGroupCommand.MESSAGE_SORT_GROUP_SUCCESS,
                validModule.getIdentifier(), validGroup.getIdentifier()),
                commandResult.getFeedbackToUser());


        ObservableList<Student> students =
                model.getModule(validModule.getIdentifier())
                        .getGroup(validGroup.getIdentifier()).getStudentList();

        assertEquals(ALICE, students.get(0));
        assertEquals(AMY, students.get(1));
        assertEquals(BOB, students.get(2));
        assertEquals(DANIEL, students.get(3));
        assertEquals(FIONA, students.get(4));
        assertEquals(GEORGE, students.get(5));
    }

    @Test
    public void execute_groupEditedByModelRatingAsc_sortSuccessful() throws Exception {
        Module validModule = new ModuleBuilder().build();
        Group validGroup = new GroupBuilder().withStudent(BOB).withStudent(ALICE)
                .withStudent(AMY).withStudent(DANIEL)
                .withStudent(GEORGE).withStudent(FIONA).build();
        validModule.addGroup(validGroup);
        model.addModule(validModule);

        CommandResult commandResult = new SortGroupCommand(SortType.RATING_ASC, validGroup.getIdentifier(),
                validModule.getIdentifier()).execute(model);

        assertEquals(String.format(SortGroupCommand.MESSAGE_SORT_GROUP_SUCCESS,
                validModule.getIdentifier(), validGroup.getIdentifier()),
                commandResult.getFeedbackToUser());

        ObservableList<Student> students =
                model.getModule(validModule.getIdentifier())
                        .getGroup(validGroup.getIdentifier()).getStudentList();

        assertEquals(ALICE, students.get(0));
        assertEquals(FIONA, students.get(1));
        assertEquals(BOB, students.get(2));
        assertEquals(GEORGE, students.get(3));
        assertEquals(DANIEL, students.get(4));
        assertEquals(AMY, students.get(5));
    }

    @Test
    public void execute_groupEditedByModelRatingDesc_sortSuccessful() throws Exception {
        Module validModule = new ModuleBuilder().build();
        Group validGroup = new GroupBuilder().withStudent(BOB).withStudent(ALICE)
                .withStudent(AMY).withStudent(DANIEL)
                .withStudent(GEORGE).withStudent(FIONA).build();
        validModule.addGroup(validGroup);
        model.addModule(validModule);

        CommandResult commandResult = new SortGroupCommand(SortType.RATING_DESC, validGroup.getIdentifier(),
                validModule.getIdentifier()).execute(model);

        assertEquals(String.format(SortGroupCommand.MESSAGE_SORT_GROUP_SUCCESS,
                validModule.getIdentifier(), validGroup.getIdentifier()),
                commandResult.getFeedbackToUser());


        ObservableList<Student> students =
                model.getModule(validModule.getIdentifier())
                        .getGroup(validGroup.getIdentifier()).getStudentList();

        assertEquals(AMY, students.get(0));
        assertEquals(DANIEL, students.get(1));
        assertEquals(BOB, students.get(2));
        assertEquals(GEORGE, students.get(3));
        assertEquals(ALICE, students.get(4));
        assertEquals(FIONA, students.get(5));
    }

    @Test
    public void execute_groupEditedByModelMatric_sortSuccessful() throws Exception {
        Module validModule = new ModuleBuilder().build();
        Group validGroup = new GroupBuilder().withStudent(BOB).withStudent(ALICE)
                .withStudent(AMY).withStudent(DANIEL)
                .withStudent(GEORGE).withStudent(FIONA).build();
        validModule.addGroup(validGroup);
        model.addModule(validModule);

        CommandResult commandResult = new SortGroupCommand(SortType.RATING_DESC, validGroup.getIdentifier(),
                validModule.getIdentifier()).execute(model);

        assertEquals(String.format(SortGroupCommand.MESSAGE_SORT_GROUP_SUCCESS,
                validModule.getIdentifier(), validGroup.getIdentifier()),
                commandResult.getFeedbackToUser());


        ObservableList<Student> students =
                model.getModule(validModule.getIdentifier())
                        .getGroup(validGroup.getIdentifier()).getStudentList();

        assertEquals(AMY, students.get(0));
        assertEquals(DANIEL, students.get(1));
        assertEquals(BOB, students.get(2));
        assertEquals(GEORGE, students.get(3));
        assertEquals(ALICE, students.get(4));
        assertEquals(FIONA, students.get(5));
    }

    @Test
    public void execute_invalidGroup_throwsCommandException() throws CommandException {
        Module validModule = new ModuleBuilder().build();
        model.addModule(validModule);
        SortGroupCommand sortGroupCommand =
                new SortGroupCommand(SortType.ALPHABETIC, "G03",
                        validModule.getIdentifier());

        assertThrows(CommandException.class, MESSAGE_INVALID_GROUP_CODE, () ->
                sortGroupCommand.execute(model));
    }

    @Test
    public void execute_invalidModule_throwsCommandException() throws CommandException {
        SortGroupCommand sortGroupCommand =
                new SortGroupCommand(SortType.ALPHABETIC, "G03",
                        "CS2030");

        assertThrows(CommandException.class, MESSAGE_INVALID_MODULE_CODE, () ->
                sortGroupCommand.execute(model));
    }
}
