import java.io.File;
import java.io.IOException;

import com.itseasy.rtf.RTFDocument;
import com.itseasy.rtf.text.Font;
import com.itseasy.rtf.text.PageSize;
import com.itseasy.rtf.text.Paragraph;
import com.itseasy.rtf.text.TextPart;


/**
 * Dieses Testprogramm erzeugt ein neues RTF Dokument mit einem Satz in der Schriftgröße 16 der Schriftart Arial.
 * Das Dokument wird in einem RTF-Reader im Anzeigemodus "Seitenlayout" und "Komplette Seite darstellen" angezeigt.
 * 
 * @version 0.1.0 21.09.2004
 * @author 	IT'S EASY
 */
public class TestSimpleRtf {
    
    private static final String FILE_NAME = "out_testsimplertf.rtf";
    
    public static void main(String[] args) {
        try {
            // RTF Dokument generieren (in Querformat)
            RTFDocument doc = new RTFDocument(PageSize.DIN_A4_QUER);
            // Anzeige-Zoom und Ansicht definieren
            doc.setViewscale(RTFDocument.VIEWSCALE_FULLPAGE);	// Anzeige-Zoom auf "komplette Seite" einstellen
            doc.setViewkind(RTFDocument.VIEWKIND_PAGELAYOUT);	// ViewMode auf Seitenlayout stellen
            
            // Text hinzufügen (Zentriert, Schriftgröße 16, Schrift Arial, Abstand vor dem Absatz 18 Punkte)
            Paragraph absatz = new Paragraph(18, 0, 16, Font.ARIAL, new TextPart("Simple RTF Writer Testdokument"));
            absatz.setAlignment(Paragraph.ALIGN_CENTER);
            doc.addParagraph(absatz);
            // Dokument wieder speichern
            File savefile = new File(FILE_NAME);
            doc.save(savefile);
            System.out.println("Neues RTF Dokument erstellt: " + savefile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }            
    }
}
