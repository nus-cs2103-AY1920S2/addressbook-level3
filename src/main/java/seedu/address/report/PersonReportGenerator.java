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
import seedu.address.model.bluetooth.Person;


import java.io.File;
import java.io.IOException;
import java.util.*;

public class PersonReportGenerator {
    public static final String DEST = "results/report.pdf";

    public void GenerateReport(ArrayList<Person> resp) throws IOException
    {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new PersonReportGenerator().createPdf(DEST, resp);
    }

    public void createPdf(String dest, ArrayList<Person> resp) throws IOException
    {
        PdfWriter writer = new PdfWriter(dest);


        PdfDocument pdf = new PdfDocument(writer);

        Document document = new Document(pdf, PageSize.A4.rotate());

        document.setMargins(20, 20, 20, 20);


        float [] pointColumnWidths = {150F, 150F, 150F, 150F, 150F};

        Table table = new Table(pointColumnWidths);


        table.addCell("Name");
        table.addCell("ID");
        table.addCell("NRIC");
        table.addCell("Age");
        table.addCell("Contact");


        if (resp.size() > 0) {
            for (int i = 0; i < resp.size(); i++) {
                Person instance = resp.get(i);
                table.addCell(instance.getName()+ "");
                table.addCell(instance.getUserId() + "");
                table.addCell(instance.getNric()+ "");
                table.addCell(instance.getAge()+ "");
                table.addCell(instance.getMobile() + "");
            }
        }



        document.add(new Paragraph("CONTACT TRACING REPORT"));
        document.add(new Paragraph("----------------------------------------------"));
        document.add(new Paragraph("This is the report for person information"));
        document.add(new Paragraph("The total number of instances in this report is: " + resp.size()));
        document.add(new Paragraph("----------------------------------------------"));


        document.add(table);
        document.close();

    }

}
