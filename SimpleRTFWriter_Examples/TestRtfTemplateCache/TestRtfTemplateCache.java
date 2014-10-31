import java.io.File;
import java.io.IOException;

import com.itseasy.rtf.RTFTemplate;
import com.itseasy.rtf.exception.BookmarkNotFoundException;

/**
 * Dieser Test läd 3000 Mal ein RTF-Template. Dies wird einmal mit eingeschalteter Cache-Option
 * durchgeführt (Dauer ca. 7 Sekunden) und einmal ohne (Dauer ca. 29 Sekunden). 
 *
 * @version 0.1.0 	21.09.2004
 * @author 			IT'S EASY
 */
public class TestRtfTemplateCache {

    private static final String TEMPLATE_NAME = "rtftest_template_rahmen.rtf";

    public static void main(String[] args) {
        try {
            // Variable anlegen
            RTFTemplate doc = null;
            
            // Startzeit merken
            long start1 = System.currentTimeMillis();
            // 100 Mal das RTF-Template laden und mit Ihnalt füllen (Cache Deaktiviert)
            for(int i = 0; i < 3000; i++) {
                // RTF Template lesen und eine Textmarken füllen
                doc = new RTFTemplate(new File(TEMPLATE_NAME));
                // Text für Felder einfügen
                doc.setBookmarkContent("Inhalt", "Irgend ein Inhalt");
            }
            // Endezeit merken
            long end1 = System.currentTimeMillis();
            
            // Startzeit merken
            long start2 = System.currentTimeMillis();
            // RTF Template lesen und eine Textmarken füllen
            doc = new RTFTemplate(new File(TEMPLATE_NAME), true);
            // 100 Mal das RTF-Template laden und mit Ihnalt füllen (Cache Aktiviert)
            for(int i = 0; i < 2999; i++) {
                // RTF Template lesen und eine Textmarken füllen
                doc.reset();
                // Text für Felder einfügen
                doc.setBookmarkContent("Inhalt", "Irgend ein Inhalt");
            }
            // Endezeit merken
            long end2 = System.currentTimeMillis();
            
            System.out.println("100 x RTFTemplate laden: vom Dateisystem = " + ((end1 - start1) / 1000) + " Sekunden, mit Cache = " + ((end2 - start2) / 1000) + " Sekunden");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BookmarkNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }            
    }

}
