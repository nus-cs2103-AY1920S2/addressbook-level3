package seedu.address.logic.commands;

import seedu.address.logic.conditions.Conditions;
import seedu.address.logic.conditions.LiterallyNoConditions;
import seedu.address.logic.conditions.PersonIDConditions;
import seedu.address.logic.conditions.UserIDConditions;
import seedu.address.logic.messages.AppMessage;
import seedu.address.logic.messages.BluetoothPingsMessage;
import seedu.address.logic.messages.BluetoothSummaryMessage;
import seedu.address.logic.messages.UserSummaryMessage;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.report.PersonReportGenerator;
import seedu.address.storage.AppStorage;
import seedu.address.storage.UserStorageAccess;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersonReportGenerationCommand implements  AppCommand, UserStorageAccess {
    public static final String COMMAND_WORD = "report_person";
    public static String ConType = "None";
    public static int personID;

    @Override
    public PersonReportGenerationCommand validate(String arguments) throws ParseException {

        if (arguments.trim().equals("")) {
            this.ConType = "person";
        } else {
            Pattern person = Pattern.compile("(?<personId>\\d+)");
            Matcher m = person.matcher(arguments.trim());

            if (m.matches()) {
                this.ConType = "person_by";
                this.personID = Integer.parseInt(m.group("personId"));
            } else {
                String error = String.format("report_person Command %s invalid", arguments);
                throw new ParseException(error);
            }
        }
        return this;
    }


    @Override
    public AppMessage execute(AppStorage dao) {
        ArrayList resp = new ArrayList();
        switch (this.ConType) {
            case "person":
                System.out.println("Search all people");
                Conditions cond = new LiterallyNoConditions();
                resp = dao.search(cond);
                break;

            case "person_by":
                System.out.println("Search person by ID");
                cond = new PersonIDConditions(this.personID);
                resp = dao.search(cond);
                break;

            default:
                System.out.println("Wrong Command");
                BluetoothPingsMessage result = new BluetoothPingsMessage("Wrong Command.", false);
                return result;

        }

        if (resp.size() > 0)
        {
            PersonReportGenerator generator = new PersonReportGenerator();
            try {
                generator.GenerateReport(resp);
            } catch (Exception e) {
                System.out.println("Write failed");
                UserSummaryMessage result = new UserSummaryMessage("Write failed.", false);
                result.setToDisplayList(resp);
                return result;
            }
            UserSummaryMessage result = new UserSummaryMessage("Saved report file to result folder.", false);
            result.setToDisplayList(resp);
            return result;

        }
        else
        {
            UserSummaryMessage result = new UserSummaryMessage("No instance in database.", false);
            result.setToDisplayList(resp);
            return result;
        }
    }
    }
