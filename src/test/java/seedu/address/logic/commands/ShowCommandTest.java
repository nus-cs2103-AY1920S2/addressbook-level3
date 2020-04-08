package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ShowCommand.MESSAGE_ALL;
import static seedu.address.logic.commands.ShowCommand.MESSAGE_INCLUSIVE;
import static seedu.address.logic.commands.ShowCommand.MESSAGE_ONE_DATE;
import static seedu.address.logic.commands.ShowCommand.MESSAGE_TODAY;
import static seedu.address.logic.commands.ShowCommand.SHOW_MESSAGE;
import static seedu.address.logic.commands.ShowCommand.TO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

/**
 * Show Command Test Class
 *
 * @author Amoscheong97
 *
 */

public class ShowCommandTest {
    private static String showMessage;
    private static String todayMessage;
    private static String thisDayMessage;
    private static String allMessage;
    private static String inclusiveMessage;
    private static DateTimeFormatter dateFormatter;

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @BeforeEach
    public void init() {
        showMessage = SHOW_MESSAGE;
        todayMessage = showMessage + MESSAGE_TODAY;
        thisDayMessage = showMessage + MESSAGE_ONE_DATE;
        allMessage = showMessage + MESSAGE_ALL;
        inclusiveMessage = showMessage + MESSAGE_INCLUSIVE;
        dateFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
    }

    /**
     * Format String to MMMM dd yyyy
     * @param str Parse in a String
     * @return String of MMMM dd yyyy
     */
    public String actualDate(String str) {
        LocalDate ld = LocalDate.parse(str);
        return ld.format(dateFormatter);
    }
    //-------------------------------------- One Argument -----------------------------------------------------------
    @Test
    public void execute_showAll_success() {
        CommandResult expectedCommandResult = new CommandResult(allMessage, false, false, false, true);
        assertCommandSuccess(new ShowCommand("all"), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_showToday_success() {
        CommandResult expectedCommandResult = new CommandResult(todayMessage, false, false, false, true);
        assertCommandSuccess(new ShowCommand("today"), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_showTodayManySpaces_success() {
        CommandResult expectedCommandResult = new CommandResult(todayMessage,
                false, false, false, true);
        assertCommandSuccess(new ShowCommand("                          today                                  "),
                model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_showOneDate_success() {
        CommandResult expectedCommandResult = new CommandResult(thisDayMessage + actualDate("2020-01-01"),
                false, false, false, true);
        assertCommandSuccess(new ShowCommand("2020-01-01"), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_showOneDateManySpaces_success() {
        CommandResult expectedCommandResult = new CommandResult(thisDayMessage + actualDate("2020-01-01"),
                false, false, false, true);
        assertCommandSuccess(new ShowCommand("             2020-01-01                                  "),
                model, expectedCommandResult, expectedModel);
    }
    //-------------------------------------- Two Arguments -----------------------------------------------------------

    @Test
    public void execute_showInclusive_success() {
        CommandResult expectedCommandResult = new CommandResult(inclusiveMessage + actualDate("2020-02-03")
                + TO + actualDate("2020-04-05") , false, false, false, true);
        assertCommandSuccess(new ShowCommand("2020-02-03 2020-04-05"), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_showMoreInclusiveDates_success() {
        CommandResult expectedCommandResult = new CommandResult(inclusiveMessage + actualDate("2020-01-01")
                + TO + actualDate("2020-04-20"), false, false, false, true);
        assertCommandSuccess(new ShowCommand("2020-01-01 2020-04-20"), model, expectedCommandResult, expectedModel);

        expectedCommandResult = new CommandResult(inclusiveMessage + actualDate("1997-07-01")
                + TO + actualDate("2100-01-09"), false, false, false, true);
        assertCommandSuccess(new ShowCommand("1997-07-01 2100-01-09"), model, expectedCommandResult, expectedModel);

        expectedCommandResult = new CommandResult(inclusiveMessage + actualDate("2020-02-03")
                + TO + actualDate("2020-11-03"), false, false, false, true);
        assertCommandSuccess(new ShowCommand("2020-02-03 2020-11-03"), model, expectedCommandResult, expectedModel);

        // Some ancient dates to some future dates
        expectedCommandResult = new CommandResult(inclusiveMessage + actualDate("1409-02-03")
                + TO + actualDate("2100-11-03"), false, false, false, true);
        assertCommandSuccess(new ShowCommand("1409-02-03 2100-11-03"), model, expectedCommandResult, expectedModel);

        expectedCommandResult = new CommandResult(inclusiveMessage + actualDate("1000-02-03")
                + TO + actualDate("3100-11-03"), false, false, false, true);
        assertCommandSuccess(new ShowCommand("1000-02-03 3100-11-03"), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_showInclusiveManySpaces_success() {
        CommandResult expectedCommandResult = new CommandResult(inclusiveMessage + actualDate("2020-01-01")
                + TO + actualDate("2100-01-09") , false, false, false, true);
        assertCommandSuccess(new ShowCommand("            2020-01-01                 2100-01-09                   "),
                model, expectedCommandResult, expectedModel);

        expectedCommandResult = new CommandResult(inclusiveMessage + actualDate("2020-02-03")
                + TO + actualDate("2020-11-03") , false, false, false, true);
        assertCommandSuccess(new ShowCommand("       2020-02-03                       2020-11-03             "),
                model, expectedCommandResult, expectedModel);
    }

}
