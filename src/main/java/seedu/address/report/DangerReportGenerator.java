package seedu.address.report;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import seedu.address.model.bluetooth.BluetoothPings;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import seedu.address.model.bluetooth.BluetoothPingsSummary;


import java.io.File;
import java.io.IOException;
import java.util.*;

public class DangerReportGenerator {
    public static final String DEST = "results/report.pdf";

    public void GenerateReport(ArrayList<BluetoothPingsSummary> resp) throws IOException
    {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new DangerReportGenerator().createPdf(DEST, resp);
    }

    public void createPdf(String dest, ArrayList<BluetoothPingsSummary> resp) throws IOException
    {
        PdfWriter writer = new PdfWriter(dest);


        PdfDocument pdf = new PdfDocument(writer);


        Document document = new Document(pdf, PageSize.A4.rotate());

        document.setMargins(20, 20, 20, 20);


        float [] pointColumnWidths = {150F, 150F, 150F};

        Table table = new Table(pointColumnWidths);


        table.addCell("Counts");
        table.addCell("ID1");
        table.addCell("ID2");


        Set IDSet = new HashSet();

        if (resp.size() > 0) {
            for (int i = 0; i < resp.size(); i++) {
                BluetoothPingsSummary instance = resp.get(i);
                table.addCell(instance.getCounts() + "");
                List<Integer> epochTimes = instance.getUserIDs();
                table.addCell(epochTimes.get(0) + "");
                IDSet.add(epochTimes.get(0) + "");
                table.addCell(epochTimes.get(1) + "");
                IDSet.add(epochTimes.get(1) + "");
            }
        }



        document.add(new Paragraph("CONTACT TRACING REPORT"));
        document.add(new Paragraph("----------------------------------------------"));
        Paragraph paragraph = new Paragraph("This is the report for dangerous cases");

        document.add(paragraph);

        String TextString = "The ID included in this report are:";
        Iterator it = IDSet.iterator();
        while (it.hasNext())
        {
            TextString = TextString + "   " + it.next();
        }

        document.add(new Paragraph((TextString)));
        document.add(new Paragraph("----------------------------------------------"));


        document.add(table);
        document.close();

    }

}
