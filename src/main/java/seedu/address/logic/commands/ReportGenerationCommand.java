package seedu.address.logic.commands;

import seedu.address.logic.aggregators.Aggregators;
import seedu.address.logic.aggregators.GroupByIDPairsAggregators;
import seedu.address.logic.conditions.*;
import seedu.address.logic.messages.AppMessage;
import seedu.address.logic.messages.BluetoothPingsMessage;
import seedu.address.logic.messages.BluetoothSummaryMessage;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.bluetooth.BluetoothPings;
import seedu.address.model.bluetooth.BluetoothPingsSummary;
import seedu.address.report.DangerReportGenerator;
import seedu.address.report.ReportGenerator;
import seedu.address.storage.AppStorage;
import seedu.address.storage.BluetoothPingStorageAccess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReportGenerationCommand implements AppCommand, BluetoothPingStorageAccess {
    public static final String COMMAND_WORD = "report";
    public static String ConType = "None";
    public static int TargetID;
    public static long StartTime;
    public static long EndTime;
    public static int ID1;
    public static int ID2;
    public static int Threshold;
    public static final String CON_TYPE_ERROR = "Condition type not well defined";


    @Override
    public ReportGenerationCommand validate(String arguments) throws ParseException {

        if (arguments.trim().equals("all"))
        {
            ConType = "None";
        }
        else
        {
            Pattern ID = Pattern.compile("id is (?<userid>\\d+)");
            Matcher m = ID.matcher(arguments.trim());
            if (m.matches())
            {
                this.TargetID = Integer.parseInt(m.group("userid"));
                this.ConType = "ID";
            }
            else
            {
                Pattern Time = Pattern.compile("time from (?<start>\\d+) to (?<end>\\d+)");
                m = Time.matcher(arguments.trim());
                if (m.matches())
                {
                    this.StartTime = Long.parseLong(m.group("start"));
                    this.EndTime = Long.parseLong(m.group("end"));
                    this.ConType = "Time";
                }

                else
                {
                    Pattern pair = Pattern.compile("pairs (?<userA>\\d+) and (?<userB>\\d+)");
                    m = pair.matcher(arguments.trim());

                    if (m.matches())
                    {
                        this.ID1 = Integer.parseInt(m.group("userA"));
                        this.ID2 = Integer.parseInt(m.group("userB"));
                        this.ConType = "pairs";
                    }
                    else
                    {
                        Pattern danger = Pattern.compile("danger (?<threshold>\\d+)");
                        m = danger.matcher(arguments.trim());

                        if (m.matches())
                        {
                            this.Threshold = Integer.parseInt(m.group("threshold"));
                            this.ConType = "danger";
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
        return this;
    }

    @Override
    public AppMessage execute(AppStorage dao) {
        ArrayList resp = new ArrayList<BluetoothPings>();
        int DangerFlag = 0;
        switch (this.ConType)
        {
            case "ID":
                System.out.println("Search by ID");
                Conditions cond = new UserIDConditions(this.TargetID);
                resp = dao.search(cond);

                break;

            case "Time":
                System.out.println("Search by time");
                cond = new TimestampConditions(this.StartTime, this.EndTime);
                resp = dao.search(cond);

                break;

            case "pairs":

                cond = new UserPairsConditions(this.ID1, this.ID2);
                resp  = dao.search(cond);
                System.out.println(("Search by pair"));
                break;

            case "danger":
                System.out.println("search by danger");
                DangerFlag = 1;
                cond = new DangerConditions(this.Threshold);
                Aggregators<BluetoothPings, BluetoothPingsSummary> agg = new GroupByIDPairsAggregators();
                agg = new GroupByIDPairsAggregators();
                resp  = dao.search(cond, agg);
                break;

            case "None":
                System.out.println("Search all cases");
                resp = dao.search();
                break;
        }



        if (DangerFlag == 0)
        {
            if (resp.size() > 0)
            {
                ReportGenerator generator = new ReportGenerator();
                try {
                    generator.GenerateReport(resp);
                }catch (Exception e)
                {
                    BluetoothPingsMessage result = new BluetoothPingsMessage("Write failed.", false);
                    result.setToDisplayList(resp);
                    return result;
                }
                BluetoothPingsMessage result = new BluetoothPingsMessage("Saved report file to result folder.", false);
                result.setToDisplayList(resp);
                return result;
            }
            else
            {
                BluetoothPingsMessage result = new BluetoothPingsMessage("No instance in database.", false);
                result.setToDisplayList(resp);
                return result;
            }

        }
        else
        {
            if (resp.size() > 0) {
                DangerReportGenerator generator = new DangerReportGenerator();
                try {
                    generator.GenerateReport(resp);
                } catch (Exception e) {
                    System.out.println("Write failed");
                    BluetoothPingsMessage result = new BluetoothPingsMessage("Write failed.", false);
                    result.setToDisplayList(resp);
                    return result;
                }
                BluetoothSummaryMessage result = new BluetoothSummaryMessage("Saved report file to result folder.", false);
                result.setToDisplayList(resp);
                return result;
            }
            else
            {
                BluetoothSummaryMessage result = new BluetoothSummaryMessage("No instance in database.", false);
                result.setToDisplayList(resp);
                return result;
            }
        }
    }
}
