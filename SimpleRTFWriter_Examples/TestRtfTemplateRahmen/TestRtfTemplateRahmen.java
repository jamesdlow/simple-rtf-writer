
import java.io.File;
import java.io.IOException;

//import com.itseasy.rtf.RTFDocument;
import com.itseasy.rtf.RTFTemplate;
//import com.itseasy.rtf.data.InfoGroup;
//import com.itseasy.rtf.data.Paragraph;
import com.itseasy.rtf.exception.BookmarkNotFoundException;
import com.itseasy.rtf.text.List;
import com.itseasy.rtf.text.MultiParagraphs;
import com.itseasy.rtf.text.Paragraph;
import com.itseasy.rtf.text.TextPart;

/**
 * TestRtfTemplateRahmen - Füllt ein Formular mit Inhalt. Das RTF Formular ist mit Word 97/2002
 * erstellt worden und mit Formularfeldern, sowie einer Textmarke bestückt worden. 
 *
 * @version 0.1.0 	22.05.2004
 * @author 			IT'S EASY
 */

public class TestRtfTemplateRahmen {

    private static final String FILE_NAME = "out_testrtftemplate_rahmen.rtf";
    private static final String TEMPLATE_NAME = "rtftest_template_rahmen.rtf";

    public static void main(String[] args) {
        try {
            // RTF Template lesen und Textmarken füllen
            RTFTemplate doc = new RTFTemplate(new File(TEMPLATE_NAME));
            // Einfachen Text für Felder einfügen
            MultiParagraphs multi = new MultiParagraphs();
            
            // 1. Absatz hinzufügen - Schriftmöglichkeiten
            Paragraph absatz = new Paragraph(0, 6);
            absatz.addText(new TextPart("Mit dem Simple RTF Writer (SRW) kann man einfache RTF Dokumente erstellen oder RTF Formulare ausfüllen. Der SRW unterstüzt "));
            absatz.addText(new TextPart(TextPart.FORMAT_ITALIC, "kursiv, "));
            absatz.addText(new TextPart(TextPart.FORMAT_UNDERLINE, "unterstrichen, "));
            absatz.addText(new TextPart(TextPart.FORMAT_BOLD,"fett, "));
            absatz.addText(new TextPart(TextPart.FORMAT_OUTLINE, "outline, "));
            absatz.addText(new TextPart(TextPart.FORMAT_SHADOW, "mit Schatten "));
            absatz.addText(new TextPart("oder "));
            absatz.addText(new TextPart(TextPart.FORMAT_ITALIC + TextPart.FORMAT_UNDERLINE + TextPart.FORMAT_SHADOW 
                    + TextPart.FORMAT_BOLD + TextPart.FORMAT_OUTLINE, "alles zusammen."));
            multi.addParagraph(absatz);
            // 2. Absatz hinzufügen - Aufzählungsliste (Eine Aufzählungsliste ist auch ein Absatz!)
            absatz = new Paragraph(0, 0, new TextPart("Daneben können auch Aufzählungslisten erstellt werden, wie diese:"));
            multi.addParagraph(absatz);
            List listabsatz = new List(3, 6);
            listabsatz.setSpace(1);		// Zwischen den Aufzählungszeilen, soll ein zusätzlicher Punkt Abstand sein 
            listabsatz.addText(new TextPart("1. Aufzählung"));
            listabsatz.addText(new TextPart("2. Aufzählung"));
            listabsatz.addText(new TextPart("3. Aufzählung (Funktioniert auch, wenn ein Aufzählungspunkt über mehr als eine Zeile geht...)"));
            listabsatz.addText(new TextPart("4. Aufzählung"));
            multi.addParagraph(listabsatz);
            
            doc.setBookmarkContent("Inhalt", multi, false);
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
        }            
    }
}
