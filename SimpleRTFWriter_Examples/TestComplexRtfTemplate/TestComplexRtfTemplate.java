
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

//import com.itseasy.rtf.RTFDocument;
import com.itseasy.rtf.RTFTemplate;
//import com.itseasy.rtf.data.InfoGroup;
//import com.itseasy.rtf.data.Paragraph;
import com.itseasy.rtf.exception.BookmarkIsNotCheckboxException;
import com.itseasy.rtf.exception.BookmarkNotFoundException;
import com.itseasy.rtf.text.Font;
import com.itseasy.rtf.text.List;
import com.itseasy.rtf.text.MultiTextParts;
import com.itseasy.rtf.text.TextPart;

/**
 * TestComplexRtfTemplate - F�llt die Felder eines bestehenden RTF Formulars. Das RTF Formular ist mit Word 97/2002
 * erstellt worden und mit Formularfeldern, sowie einer Textmarke best�ckt worden. An der Textmarke wird
 * durch dieses Programm eine Aufz�hlungsliste mit verschiedenen Schriften eingef�gt.
 *
 * @version 0.1.0 	22.05.2004
 * @author 			IT'S EASY
 */

public class TestComplexRtfTemplate {

    private static final String FILE_NAME = "out_testcomplexrtftemplate.rtf";
    private static final String TEMPLATE_NAME = "rtftest_template.rtf";

    public static void main(String[] args) {
        try {
            // RTF Template lesen und Textmarken f�llen
            RTFTemplate doc = new RTFTemplate(new File(TEMPLATE_NAME));
            // Text f�r Felder einf�gen
            doc.setBookmarkContent("Nummer", "08/15");
            doc.setBookmarkContent("Wert", "4.060,00");
            doc.setBookmarkContent("MWSt", "560,00");
            doc.setBookmarkContent("Wert_Wort", "-- viertausendsechzig --");
            doc.setBookmarkContent("Von", "Mustermann GmbH, M�nchen");
            doc.setBookmarkContent("Ort_Datum", "M�nchen, " + new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
            // Checkboxen mit Inhalt f�llen
            doc.setBookmarkCheckbox("Bar", true);
            doc.setBookmarkCheckbox("Kreditkarte", false);	// ist eigenltich �berfl�ssig
            // Ausf�hrlicher Inhalt f�r "fuer" definieren
            MultiTextParts mtp = new MultiTextParts();
            mtp.addText(new TextPart("Auftragsarbeit zur Erstellung der Sitzordnung f�r Olympia 2004. Aufragsnummer "));
            mtp.addText(TextPart.NEWLINE);
            mtp.addText(new TextPart("Aufragsnummer: "));
            mtp.addText(new TextPart(TextPart.FORMAT_BOLD, "5533-1092-2004."));
            mtp.addText(TextPart.NEWLINE);
            mtp.addText(new TextPart("Bearbeiter: "));
            mtp.addText(new TextPart(TextPart.FORMAT_UNDERLINE, "Hugo Schmidt"));
            doc.setBookmarkContent("Fuer", mtp);
            // Bemerkung hinzuf�gen; Textmarke
            List listabsatz = new List(3, 6);
            listabsatz.setSpace(1);		// Zwischen den Aufz�hlungszeilen, soll ein zus�tzlicher Punkt Abstand sein 
            listabsatz.addText(new TextPart(TextPart.FORMAT_NORMAL, 12, Font.ARIAL, "Schrift 'Arial' in Gr��e 12"));
            listabsatz.addText(new TextPart(TextPart.FORMAT_NORMAL, 14, Font.TIMES_NEW_ROMAN, "Schrift 'Times New Roman' in Gr��e 14"));
            listabsatz.addText(new TextPart(TextPart.FORMAT_NORMAL, 10, Font.COURIER_NEW, "Schrift 'Courier New' in Gr��e 10"));
            listabsatz.addText(new TextPart(TextPart.FORMAT_NORMAL, 8, Font.SYMBOL, "Schrift 'Symbol' in Gr��e 8"));
            doc.setBookmarkContent("Bemerkung", listabsatz, false);
            // Dokument wieder speichern
            File savefile = new File(FILE_NAME);
            doc.save(savefile);
            System.out.println("Neues RTF Dokument erstellt: " + savefile.getAbsolutePath());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BookmarkNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BookmarkIsNotCheckboxException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }            
    }
}
