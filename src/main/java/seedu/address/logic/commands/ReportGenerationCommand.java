package seedu.address.logic.commands;

import seedu.address.logic.conditions.Conditions;
import seedu.address.logic.conditions.TimestampConditions;
import seedu.address.logic.conditions.UserIDConditions;
import seedu.address.logic.messages.BluetoothPingsMessage;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.bluetooth.BluetoothPings;
import seedu.address.report_generator.report_generator;
import seedu.address.storage.AppStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReportGenerationCommand implements AppCommand {
    public static final String COMMAND_WORD = "report";
    public static String con_type = "None";
    public static int TARGET_ID;
    public static long START_TIME;
    public static long END_TIME;
    public static final String CON_TYPE_ERROR = "Condition type not well defined";


    @Override
    public ReportGenerationCommand validate(String arguments) throws ParseException {
        if (arguments.trim() == "")
        {
            con_type = "None";
        }
        else
        {
            Pattern ID = Pattern.compile("id is (?<userid>\\d+)");
            Matcher m = ID.matcher(arguments.trim());
            if (m.matches())
            {
                this.TARGET_ID = Integer.parseInt(m.group("userid"));
                this.con_type = "ID";
            }
            else
            {
                Pattern Time = Pattern.compile("time from (?<start>\\d+) to (?<end>\\d+)");
                m = Time.matcher(arguments.trim());
                if (m.matches())
                {
                    this.START_TIME = Long.parseLong(m.group("start"));
                    this.END_TIME = Long.parseLong(m.group("end"));
                    this.con_type = "Time";
                }
                else
                {
                    String error = String.format("Command %s invalid", arguments);
                    throw new ParseException(error);
                }
            }
        }

        return this;
    }

    @Override
    public BluetoothPingsMessage execute(AppStorage dao) {
        ArrayList resp = new ArrayList<BluetoothPings>();
        switch (this.con_type)
        {
            case "ID":
                Conditions cond = new UserIDConditions(this.TARGET_ID);
                resp = dao.search(cond);
            case "Time":
                cond = new TimestampConditions(this.START_TIME, this.END_TIME);
                resp = dao.search(cond);
            case "None":
                resp = dao.search();
        }

        report_generator generator = new report_generator();
        try {
            generator.GenerateReport(resp);
        }catch (Exception e)
        {
            System.out.println("Write failed");
        }

        BluetoothPingsMessage result = new BluetoothPingsMessage("Save report file to result folder.", false);
        result.setToDisplayList(resp);
        return result;

    }


}
