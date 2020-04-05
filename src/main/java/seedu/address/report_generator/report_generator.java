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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

        document.add(new Paragraph("The total number of instances in this report is "));

        float [] pointColumnWidths = {150F, 150F, 150F};

        Table table = new Table(pointColumnWidths);

        table.addCell("Time");
        table.addCell("ID1");
        table.addCell("ID2");

        for(int i = 0; i < resp.size(); i++)
        {
            BluetoothPings instance = resp.get(i);
            table.addCell(instance.getEpochTs()+"");
            List<Integer> epochTimes = instance.getUserIDs();
            table.addCell(epochTimes.get(0) + "");
            table.addCell(epochTimes.get(1) + "");
        }
        document.add(table);
        document.close();

    }

}
