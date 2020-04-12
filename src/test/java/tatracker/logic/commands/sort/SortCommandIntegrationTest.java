//@@author aakanksha-rai

package tatracker.logic.commands.sort;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import tatracker.model.Model;
import tatracker.model.ModelManager;
import tatracker.model.UserPrefs;
import tatracker.model.group.Group;
import tatracker.model.module.Module;
import tatracker.model.student.Student;
import tatracker.testutil.group.GroupBuilder;
import tatracker.testutil.module.ModuleBuilder;

public class SortCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        Module validModule = new ModuleBuilder().build();
        model = new ModelManager(getTypicalTaTracker(), new UserPrefs());
        model.addModule(validModule);
    }

    @Test
    public void execute_groupEditedByModelAlphabetic_sortSuccessful() throws Exception {
        Module validModuleOne = new ModuleBuilder().withIdentifier("CS2103").build();
        Module validModuleTwo = new ModuleBuilder().withIdentifier("CS2030").build();
        Group validGroupOne = new GroupBuilder().withIdentifier("G03").withStudent(BOB).withStudent(ALICE)
                .withStudent(AMY).withStudent(DANIEL)
                .withStudent(GEORGE).withStudent(FIONA).build();
        Group validGroupTwo = new GroupBuilder().withIdentifier("G06").withStudent(BOB).withStudent(ALICE)
                .withStudent(AMY).withStudent(DANIEL)
                .withStudent(GEORGE).withStudent(FIONA).build();
        validModuleOne.addGroup(validGroupOne);
        validModuleTwo.addGroup(validGroupTwo);
        model.addModule(validModuleOne);
        model.addModule(validModuleTwo);

        CommandResult commandResult = new SortCommand(SortType.ALPHABETIC).execute(model);

        assertEquals(String.format(SortCommand.MESSAGE_SORT_ALL_SUCCESS),
                commandResult.getFeedbackToUser());


        ObservableList<Student> students =
                model.getModule(validModuleOne.getIdentifier())
                        .getGroup(validGroupOne.getIdentifier()).getStudentList();

        assertEquals(ALICE, students.get(0));
        assertEquals(AMY, students.get(1));
        assertEquals(BOB, students.get(2));
        assertEquals(DANIEL, students.get(3));
        assertEquals(FIONA, students.get(4));
        assertEquals(GEORGE, students.get(5));

        students = model.getModule(validModuleTwo.getIdentifier())
                .getGroup(validGroupTwo.getIdentifier()).getStudentList();

        assertEquals(ALICE, students.get(0));
        assertEquals(AMY, students.get(1));
        assertEquals(BOB, students.get(2));
        assertEquals(DANIEL, students.get(3));
        assertEquals(FIONA, students.get(4));
        assertEquals(GEORGE, students.get(5));
    }

    @Test
    public void execute_groupEditedByModelRatingAsc_sortSuccessful() throws Exception {
        Module validModuleOne = new ModuleBuilder().withIdentifier("CS2103").build();
        Module validModuleTwo = new ModuleBuilder().withIdentifier("CS2030").build();
        Group validGroupOne = new GroupBuilder().withIdentifier("G03").withStudent(BOB).withStudent(ALICE)
                .withStudent(AMY).withStudent(DANIEL)
                .withStudent(GEORGE).withStudent(FIONA).build();
        Group validGroupTwo = new GroupBuilder().withIdentifier("G06").withStudent(BOB).withStudent(ALICE)
                .withStudent(AMY).withStudent(DANIEL)
                .withStudent(GEORGE).withStudent(FIONA).build();
        validModuleOne.addGroup(validGroupOne);
        validModuleTwo.addGroup(validGroupTwo);
        model.addModule(validModuleOne);
        model.addModule(validModuleTwo);

        CommandResult commandResult = new SortCommand(SortType.RATING_ASC).execute(model);

        assertEquals(String.format(SortCommand.MESSAGE_SORT_ALL_SUCCESS),
                commandResult.getFeedbackToUser());


        ObservableList<Student> students =
                model.getModule(validModuleOne.getIdentifier())
                        .getGroup(validGroupOne.getIdentifier()).getStudentList();

        assertEquals(ALICE, students.get(0));
        assertEquals(FIONA, students.get(1));
        assertEquals(BOB, students.get(2));
        assertEquals(GEORGE, students.get(3));
        assertEquals(DANIEL, students.get(4));
        assertEquals(AMY, students.get(5));

        students = model.getModule(validModuleTwo.getIdentifier())
                .getGroup(validGroupTwo.getIdentifier()).getStudentList();

        assertEquals(ALICE, students.get(0));
        assertEquals(FIONA, students.get(1));
        assertEquals(BOB, students.get(2));
        assertEquals(GEORGE, students.get(3));
        assertEquals(DANIEL, students.get(4));
        assertEquals(AMY, students.get(5));
    }

    @Test
    public void execute_groupEditedByModelRatingDesc_sortSuccessful() throws Exception {
        Module validModuleOne = new ModuleBuilder().withIdentifier("CS2103").build();
        Module validModuleTwo = new ModuleBuilder().withIdentifier("CS2030").build();
        Group validGroupOne = new GroupBuilder().withIdentifier("G03").withStudent(BOB).withStudent(ALICE)
                .withStudent(AMY).withStudent(DANIEL)
                .withStudent(GEORGE).withStudent(FIONA).build();
        Group validGroupTwo = new GroupBuilder().withIdentifier("G06").withStudent(BOB).withStudent(ALICE)
                .withStudent(AMY).withStudent(DANIEL)
                .withStudent(GEORGE).withStudent(FIONA).build();
        validModuleOne.addGroup(validGroupOne);
        validModuleTwo.addGroup(validGroupTwo);
        model.addModule(validModuleOne);
        model.addModule(validModuleTwo);

        CommandResult commandResult = new SortCommand(SortType.RATING_DESC).execute(model);

        assertEquals(String.format(SortCommand.MESSAGE_SORT_ALL_SUCCESS),
                commandResult.getFeedbackToUser());


        ObservableList<Student> students =
                model.getModule(validModuleOne.getIdentifier())
                        .getGroup(validGroupOne.getIdentifier()).getStudentList();

        assertEquals(AMY, students.get(0));
        assertEquals(DANIEL, students.get(1));
        assertEquals(BOB, students.get(2));
        assertEquals(GEORGE, students.get(3));
        assertEquals(ALICE, students.get(4));
        assertEquals(FIONA, students.get(5));

        students = model.getModule(validModuleTwo.getIdentifier())
                .getGroup(validGroupTwo.getIdentifier()).getStudentList();

        assertEquals(AMY, students.get(0));
        assertEquals(DANIEL, students.get(1));
        assertEquals(BOB, students.get(2));
        assertEquals(GEORGE, students.get(3));
        assertEquals(ALICE, students.get(4));
        assertEquals(FIONA, students.get(5));
    }

}
