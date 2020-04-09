package seedu.address.report_generator;

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



import java.io.File;
import java.io.IOException;
import java.util.*;

public class report_generator {
    public static final String DEST = "results/report.pdf";

    public void GenerateReport(ArrayList<BluetoothPings> resp) throws IOException
    {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new report_generator().createPdf(DEST, resp);
    }

    public void createPdf(String dest, ArrayList<BluetoothPings> resp) throws IOException
    {
        PdfWriter writer = new PdfWriter(dest);


        PdfDocument pdf = new PdfDocument(writer);


        Document document = new Document(pdf, PageSize.A4.rotate());

        document.setMargins(20, 20, 20, 20);


        float [] pointColumnWidths = {150F, 150F, 150F};

        Table table = new Table(pointColumnWidths);


        table.addCell("Time");
        table.addCell("ID1");
        table.addCell("ID2");



        Set ID_set = new HashSet();
        BluetoothPings instance = resp.get(0);

        if (resp.size() > 0) {
            for (int i = 0; i < resp.size(); i++) {
                instance = resp.get(i);
                table.addCell(instance.getEpochTs() + "");
                List<Integer> epochTimes = instance.getUserIDs();
                table.addCell(epochTimes.get(0) + "");
                ID_set.add(epochTimes.get(0) + "");
                table.addCell(epochTimes.get(1) + "");
                ID_set.add(epochTimes.get(1) + "");
            }
        }


        document.add(new Paragraph("CONTACT TRACING REPORT"));
        document.add(new Paragraph("----------------------------------------------"));
        Paragraph paragraph = new Paragraph("The total number of instances in this report is: " + resp.size());

        document.add(paragraph);

        String text_string = "The ID included in this file are:";
        Iterator it = ID_set.iterator();
        while (it.hasNext())
        {
            text_string = text_string + "   " + it.next();
        }

        document.add(new Paragraph((text_string)));
        document.add(new Paragraph("----------------------------------------------"));


        document.add(table);
        document.close();
        System.out.println("Report Generation finished");

    }

}
