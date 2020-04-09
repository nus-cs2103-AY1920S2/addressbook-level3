package seedu.address.logic.commands;

import seedu.address.logic.aggregators.Aggregators;
import seedu.address.logic.aggregators.GroupByIDPairsAggregators;
import seedu.address.logic.conditions.*;
import seedu.address.logic.messages.BluetoothPingsMessage;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.bluetooth.BluetoothPings;
import seedu.address.model.bluetooth.BluetoothPingsSummary;
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
    public static int ID1;
    public static int ID2;
    public static int THRESHOLD;
    public static final String CON_TYPE_ERROR = "Condition type not well defined";


    @Override
    public ReportGenerationCommand validate(String arguments) throws ParseException {
        System.out.println("In validate function, argument is " + arguments.trim());

        if (arguments.trim().equals("all"))
        {
            System.out.println("Report all");
            con_type = "None";
        }
        else
        {
            Pattern ID = Pattern.compile("id is (?<userid>\\d+)");
            Matcher m = ID.matcher(arguments.trim());
            if (m.matches())
            {
                System.out.println("Report ID");
                this.TARGET_ID = Integer.parseInt(m.group("userid"));
                this.con_type = "ID";
            }
            else
            {
                Pattern Time = Pattern.compile("time from (?<start>\\d+) to (?<end>\\d+)");
                m = Time.matcher(arguments.trim());
                if (m.matches())
                {
                    System.out.println("Report Time");
                    this.START_TIME = Long.parseLong(m.group("start"));
                    this.END_TIME = Long.parseLong(m.group("end"));
                    this.con_type = "Time";
                }

                else
                {
                    Pattern pair = Pattern.compile("pairs (?<userA>\\d+) and (?<userB>\\d+)");
                    m = pair.matcher(arguments.trim());

                    if (m.matches())
                    {
                        System.out.println("Report ID pairs");
                        this.ID1 = Integer.parseInt(m.group("userA"));
                        this.ID2 = Integer.parseInt(m.group("userB"));
                        this.con_type = "pairs";
                    }
                    else
                    {
                        Pattern danger = Pattern.compile("danger (?<threshold>\\d+)");
                        m = danger.matcher(arguments.trim());

                        if (m.matches())
                        {
                            System.out.println("Report Danger pairs");
                            this.THRESHOLD = Integer.parseInt(m.group("threshold"));
                            this.con_type = "danger";
                        }
                        else
                            {

                            String error = String.format("Command %s invalid", arguments);
                            throw new ParseException(error);
                        }
                    }
                }
            }
        }

        System.out.println(this.con_type + " " + this.TARGET_ID + " " + this.START_TIME + " " +this.END_TIME + " " + this.ID1 + " " + this.ID2 + " " + this.THRESHOLD);

        return this;
    }

    @Override
    public BluetoothPingsMessage execute(AppStorage dao) {
        System.out.println("Now in execute function");
        ArrayList resp = new ArrayList<BluetoothPings>();
        switch (this.con_type)
        {
            case "ID":
                System.out.println("Search by ID");
                Conditions cond = new UserIDConditions(this.TARGET_ID);
                resp = dao.search(cond);
                System.out.println("The size of search result is");
                System.out.println(resp.size());
                break;

            case "Time":
                System.out.println("Search by time");
                cond = new TimestampConditions(this.START_TIME, this.END_TIME);
                resp = dao.search(cond);
                System.out.println("The size of search result is");
                System.out.println(resp.size());
                break;

            case "pairs":
                System.out.println(("Search by pair"));
                cond = new UserPairsConditions(this.ID1, this.ID2);
                resp  = dao.search(cond);

            case "danger":
                System.out.println("search by danger");
                cond = new DangerConditions(this.THRESHOLD);
                Aggregators<BluetoothPings, BluetoothPingsSummary> agg = new GroupByIDPairsAggregators();
                agg = new GroupByIDPairsAggregators();
                resp  = dao.search(cond, agg);

            case "None":
                System.out.println("Search all");
                resp = dao.search();
                System.out.println("The size of search result is");
                System.out.println(resp.size());
                break;
        }


        report_generator generator = new report_generator();
        System.out.println("Generating reports");
        try {
            generator.GenerateReport(resp);
        }catch (Exception e)
        {
            System.out.println("Write failed");
        }

        BluetoothPingsMessage result = new BluetoothPingsMessage("Saved report file to result folder.", false);
        result.setToDisplayList(resp);
        return result;

    }
}
