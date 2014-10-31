import java.io.File;
import java.io.IOException;

import com.itseasy.rtf.RTFDocument;
import com.itseasy.rtf.text.Border;
import com.itseasy.rtf.text.Field;
import com.itseasy.rtf.text.Font;
import com.itseasy.rtf.text.InfoGroup;
import com.itseasy.rtf.text.List;
import com.itseasy.rtf.text.NewPage;
import com.itseasy.rtf.text.Paragraph;
import com.itseasy.rtf.text.Tabulator;
import com.itseasy.rtf.text.TextPart;


/**
 * Erstellt ein neues 3-seitiges RTF Dokument mit vielen Formatierungsm�glichkeiten 
 *
 * @version 0.1.0 	08.07.2004
 * @author 			IT'S EASY
 */
public class TestNewRtfDocFull {

    private static final String FILE_NAME = "out_testnewrtfdocfull.rtf";
    
    public static void main(String[] args) {
        // RTF Dokument generieren
        RTFDocument doc = new RTFDocument();
        // Dokumenteninformation hinzuf�gen
        doc.getInfo().setInfoAsString(InfoGroup.INFO_AUTHOR, "IT'S EASY");
        doc.getInfo().setInfoAsString(InfoGroup.INFO_TITLE, "Dies ist ein RTF Generierungstest...");
        // Kopfzeile hinzuf�gen (Zentriert, Schriftgr��e 16, Rahmen am Boden)
        Paragraph header = new Paragraph(0, 18, 16, Font.ARIAL, new TextPart("Simple RTF Writer Testdokument"));
        header.setAlignment(Paragraph.ALIGN_CENTER);
        header.setBorder(new Border(Border.BORDER_BOTTOM, Border.BORDERTHICKNESS_SINGLE));
        doc.addHeader(header);
        // Fu�zeile hinzuf�gen (Dateiname links, Aktuelles Datum und Seitennummer Rechts)
        Paragraph footer = new Paragraph(12, 0, 8, Font.TIMES_NEW_ROMAN);
        footer.addTabulator(new Tabulator(170, Tabulator.TABKIND_RIGHT));	// Tabulator bei 17 cm definieren
        footer.addText(new TextPart("Datei: "));
        footer.addText(Field.FIELD_FILENAME);
        footer.addText(new TextPart(TextPart.SIGN_TAB + "Datum: "));
        footer.addText(Field.FIELD_CURRENT_DATE);
        footer.addText(TextPart.NEWLINE);
        footer.addText(new TextPart(TextPart.SIGN_TAB + "Seite "));
        footer.addText(Field.FIELD_CURRENT_PAGENO);
        footer.addText(new TextPart(" von "));
        footer.addText(Field.FIELD_TOTAL_PAGES);
        footer.setBorder(new Border(Border.BORDER_TOP, Border.BORDERTHICKNESS_SINGLE));
        doc.addFooter(footer);
        // 1. Absatz hinzuf�gen - Schriftm�glichkeiten
        Paragraph absatz = new Paragraph(0, 6);
        absatz.addText(new TextPart("Mit dem Simple RTF Writer (SRW) kann man einfache RTF Dokumente erstellen oder RTF Formulare ausf�llen. Der SRW unterst�zt "));
        absatz.addText(new TextPart(TextPart.FORMAT_ITALIC, "kursiv, "));
        absatz.addText(new TextPart(TextPart.FORMAT_UNDERLINE, "unterstrichen, "));
        absatz.addText(new TextPart(TextPart.FORMAT_BOLD,"fett, "));
        absatz.addText(new TextPart(TextPart.FORMAT_OUTLINE, "outline, "));
        absatz.addText(new TextPart(TextPart.FORMAT_SHADOW, "mit Schatten "));
        absatz.addText(new TextPart("oder "));
        absatz.addText(new TextPart(TextPart.FORMAT_ITALIC + TextPart.FORMAT_UNDERLINE + TextPart.FORMAT_SHADOW 
                + TextPart.FORMAT_BOLD + TextPart.FORMAT_OUTLINE, "alles zusammen."));
        doc.addParagraph(absatz);
        // 2. Absatz hinzuf�gen - Aufz�hlungsliste (Eine Aufz�hlungsliste ist auch ein Absatz!)
        absatz = new Paragraph(0, 0, new TextPart("Daneben k�nnen auch Aufz�hlungslisten erstellt werden, wie diese:"));
        doc.addParagraph(absatz);
        List listabsatz = new List(3, 6);
        listabsatz.setSpace(1);		// Zwischen den Aufz�hlungszeilen, soll ein zus�tzlicher Punkt Abstand sein 
        listabsatz.addText(new TextPart("1. Aufz�hlung"));
        listabsatz.addText(new TextPart("2. Aufz�hlung"));
        listabsatz.addText(new TextPart("3. Aufz�hlung (Funktioniert auch, wenn ein Aufz�hlungspunkt �ber mehr als eine Zeile geht...)"));
        listabsatz.addText(new TextPart("4. Aufz�hlung"));
        doc.addParagraph(listabsatz);
        // 3. Absatz hinzuf�gen - Tabulatoren
        absatz = new Paragraph(0, 6, new TextPart("Nat�rlich unters�tzt der Simple RTF Writer auch Tabulatoren."));
        absatz.addText(TextPart.NEWLINE); 
        // Abs�tze dem 3. Absatz hinzuf�gen - kann auch erst zum Schluss passieren...; 3 Mal hinzuf�gen
        absatz.addTabulator(new Tabulator(10, Tabulator.TABKIND_LEFT));		// 1. Tabulator; 1 cm; Ausrichtung: Links
        absatz.addTabulator(new Tabulator(50, Tabulator.TABKIND_CENTER));	// 2. Tabulator; 5 cm; Ausrichtung: Center
        absatz.addTabulator(new Tabulator(100, Tabulator.TABKIND_DECIMAL));	// 3. Tabulator; 10 cm; Ausrichtung: Dezimal
        absatz.addTabulator(new Tabulator(doc.getPageDefinition().getVisibleLineSize(), Tabulator.TABKIND_RIGHT));	// 4. Tabulator; Rechter Rand; Ausrichtung: Rechts
        // Jetzt kommt der Text f�r den 3. Absatz
        absatz.addText(TextPart.TABULATOR);
        absatz.addText(new TextPart("Links-Tab"));
        absatz.addText(TextPart.TABULATOR);
        absatz.addText(new TextPart("Center-Tab"));
        absatz.addText(TextPart.TABULATOR);
        absatz.addText(new TextPart("Dezimal,Tab"));
        absatz.addText(TextPart.TABULATOR);
        absatz.addText(new TextPart("Rechts-Tab (ganz Rechts)"));
        // Noch zwei Zeilen
        absatz.addText(TextPart.NEWLINE); 
        absatz.addText(new TextPart(TextPart.SIGN_TAB + "xxxx" + TextPart.SIGN_TAB + "xxxx" + TextPart.SIGN_TAB + "xxxx,xx" + TextPart.SIGN_TAB + "xxxx"));
        absatz.addText(TextPart.NEWLINE); 
        absatz.addText(new TextPart(TextPart.SIGN_TAB + "xxxxxx" + TextPart.SIGN_TAB + "xxxxxx" + TextPart.SIGN_TAB + "xxxxxx,xx" + TextPart.SIGN_TAB + "xxxxxx"));
        doc.addParagraph(absatz);
        // 4. Absatz hinzuf�gen - Tabulatoren mit F�llzeichen (Punkte)
        absatz = new Paragraph(0, 0);
        absatz.addTabulator(new Tabulator(10, Tabulator.TABKIND_LEFT));		// 1. Tabulator; 1 cm; Ausrichtung: Links
        absatz.addTabulator(new Tabulator(150, Tabulator.TABKIND_RIGHT, Tabulator.TABLEAD_DOTS));	// 2. Tabulator; 15 cm; Ausrichtung: Rechts
        absatz.addText(new TextPart(TextPart.SIGN_TAB + "Zeilenanfang " + TextPart.SIGN_TAB + " Tab mit Punkten"));
        doc.addParagraph(absatz);
        // 5. Absatz hinzuf�gen - Tabulatoren mit F�llzeichen (Unterstrichen)
        absatz = new Paragraph(0, 6);
        absatz.addTabulator(new Tabulator(10, Tabulator.TABKIND_LEFT));		// 1. Tabulator; 1 cm; Ausrichtung: Links
        absatz.addTabulator(new Tabulator(150, Tabulator.TABKIND_RIGHT, Tabulator.TABLEAD_UNDERLINE));	// 2. Tabulator; 15 cm; Ausrichtung: Rechts
        absatz.addText(new TextPart(TextPart.SIGN_TAB + "Zeilenanfang " + TextPart.SIGN_TAB + " Tab mit Unterstrich"));
        doc.addParagraph(absatz);
        // 6. Absatz - Verschiedene Schrifte aufzeigen
        absatz = new Paragraph(0, 0, new TextPart("Auch verschiedene Schriftarten werden unterst�tzt:"));
        doc.addParagraph(absatz);
        absatz = new List(0, 6);
        absatz.addText(new TextPart(TextPart.FORMAT_NORMAL, 12, Font.ARIAL, "Schrift 'Arial' in Gr��e 12"));
        absatz.addText(new TextPart(TextPart.FORMAT_NORMAL, 14, Font.TIMES_NEW_ROMAN, "Schrift 'Times New Roman' in Gr��e 14"));
        absatz.addText(new TextPart(TextPart.FORMAT_NORMAL, 10, Font.COURIER_NEW, "Schrift 'Courier New' in Gr��e 10"));
        absatz.addText(new TextPart(TextPart.FORMAT_NORMAL, 8, Font.SYMBOL, "Schrift 'Symbol' in Gr��e 8"));
        doc.addParagraph(absatz);
        // 7. Absatz mit Seitenvorschub vor dem Absatz
        absatz = new Paragraph(0, 6);
        absatz.setNewPageBefore(true);		// Neue Zeile vor dem Absatz
        absatz.addText(new TextPart("Dieser Absatz wurde so definiert, dass er auf einer neuen Seite beginnt. "));
        doc.addParagraph(absatz);
        // 8. Normaler Absatz, aber davor nochmal ein Seitenvorschub
        doc.addParagraph(new NewPage());
        absatz = new Paragraph(0, 6);
        absatz.addText(new TextPart("Auch dieser Absatz liegt auf einer neuen Seite. Das Objekt NewPage bewirkt �hnliches wie die Absatzformatierung setNewPageBefore."));
        doc.addParagraph(absatz);
        // Dokument speichern
        try {
            File savefile = new File(FILE_NAME);
            doc.save(savefile);
            System.out.println("Neues RTF Dokument erstellt: " + savefile.getAbsolutePath());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
