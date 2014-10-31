import java.io.File;
import java.io.IOException;

import com.itseasy.rtf.RTFTemplate;
import com.itseasy.rtf.exception.BookmarkNotFoundException;
import com.itseasy.rtf.text.MultiParagraphs;
import com.itseasy.rtf.text.Paragraph;
import com.itseasy.rtf.text.TextPart;



/**
 * Erstellt einen kurzen RTF-Brief und speichert das Dokument in drei Dateien ab, mit jeweils einer unterschiedlichen
 * Empfängeradresse. Damit wird gezeigt, dass nachträglich ein RTFDokument/RTFTemplate  geändert werden kann. 
 *
 * @version 0.1.0 	21.09.2004
 * @author 			IT'S EASY
 */
public class TestRtfSerienbrief {
    
    private static final String FILE_NAME = "out_testrtfserienbrief#.rtf";
    private static final String TEMPLATE_NAME = "rtftest_template_rahmen.rtf";

    private static TextPart adr_name = new TextPart("");	// Inhalt wird später ergänzt
    private static TextPart adr_strasse = new TextPart("");	// Inhalt wird später ergänzt
    private static TextPart adr_ort = new TextPart("");		// Inhalt wird später ergänzt
    
    private static String adressen[][] = {{"Michael Eins", "Einserstraße 87", "80809 München"},
    									  {"Sven Zwei", "Zweierstraße 2", "10974 Berlin"},
    									  {"Ulrike Drei", "Dreierstraße 22", "85622 Feldkirchen"}};
    
    public static void main(String[] args) {
        // Dokument erstellen
        try {
            // RTF Template lesen und Textmarken füllen
            RTFTemplate doc = new RTFTemplate(new File(TEMPLATE_NAME));
            MultiParagraphs multi = new MultiParagraphs();		// Container für Absätze
            // Adresse hinzufügen
            Paragraph absatz = new Paragraph(18, 6);
            absatz.addText(adr_name);		// Namensobjekt
            absatz.addText(TextPart.NEWLINE);
            absatz.addText(adr_strasse);
            absatz.addText(TextPart.NEWLINE);
            absatz.addText(TextPart.NEWLINE);
            absatz.addText(adr_ort);
            absatz.addText(TextPart.NEWLINE);			/// 1:17
            multi.addParagraph(absatz);
            // Anrede hinzufügen
            absatz = new Paragraph(18, 12);
            absatz.addText(new TextPart("Hallo "));
            absatz.addText(adr_name);		// Namensobjekt
            absatz.addText(new TextPart(","));
            multi.addParagraph(absatz);
            // Text hinzufügen
            absatz = new Paragraph(0, 6);
            absatz.addText(new TextPart("dies ist ein kleiner Serienbrief, der mit dem SimpleRTFWriter erstellt wurde. Auch so etwas ist sehr einfach möglich."));
            absatz.addText(TextPart.NEWLINE);
            absatz.addText(TextPart.NEWLINE);
            absatz.addText(new TextPart("Viele Grüße,"));
            absatz.addText(TextPart.NEWLINE);
            absatz.addText(new TextPart("Ihr IT'S easy Team"));
            multi.addParagraph(absatz);
            // Dokument drei mal mit unterschiedlichen Adressen abspeichern
            for (int i = 0; i < 3; i++) {
                // Adresse setzen
                adr_name.setContent(adressen[i][0]);
                adr_strasse.setContent(adressen[i][1]);
                adr_ort.setContent(adressen[i][2]);
                // Dokument mit Inhalt füllen und speichern
                File savefile = new File(FILE_NAME.replace('#', (char)('1' + i)));
                doc.setBookmarkContent("Inhalt", multi, false);
                doc.save(savefile);
                System.out.println("Neues RTF Dokument erstellt: " + savefile.getAbsolutePath());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BookmarkNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }            
    }
}
