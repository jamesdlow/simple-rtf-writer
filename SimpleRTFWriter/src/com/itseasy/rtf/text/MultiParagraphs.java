package com.itseasy.rtf.text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

/**
 * Fasst einzelne Abs�tze (Paragraph) zusammen, um diese in einem RTFTemplate als eine Einheit einzuf�gen. 
 * 
 * @version 0.1.0 22.05.2004
 * @author Stefan Finkenzeller
 */
public class MultiParagraphs {

    // Variablen
    private List content; // Inhalt sind Objekte vom Typ Paragraph

    /**
     * Definiert einen leeren TextPart-Container
     */
    public MultiParagraphs() {
        this.content = new ArrayList();
    }

    /**
     * Definiert einen Paragraph-Container und f�gt diesem bereits den ersten
     * Paragraph hinzu
     * 
     * @param content - 1. Paragraph dem Container hinzuf�gen
     */
    public MultiParagraphs(Paragraph content) {
        this();
        this.addParagraph(content);
    }

    /**
     * F�gt einen Paragraph dem Paragraph-Container hinzu
     * 
     * @param part - Paragraph, der hinzugef�gt werden soll
     */
    public void addParagraph(Paragraph part) {
        this.content.add(part);
    }

    /**
     * Gibt alle Schriftenobjekte zur�ck, die an diesem MultiParagraphs (inkl.
     * TextParts) h�ngen. Wenn keine Schriften angeh�ngt sind, wird "null"
     * zur�ckgegeben.
     * 
     * @return - Set mit allen Fonts
     */
    public Set getAllFonts() {
        Set fs = new HashSet();
        ListIterator it = content.listIterator();
        while (it.hasNext()) {
            Set f = ((Paragraph) it.next()).getAllFonts();
            if (f != null) fs.addAll(f);
        }

        return (fs.size() > 0 ? fs : null);
    }

    /**
     * Gibt den RTF String zur�ck, welcher dieses Element mit RTF Steuerzeichen definiert.
     * 
     * @return RTF String
     */
    public String getRtfContent() {
        return this.getRtfContent(false);
    }

    /**
     * Gibt den RTF String zur�ck, welcher dieses Element mit RTF Steuerzeichen definiert. Abs�tze, die innerhalb
     * einer Tabelle eingef�gt werden, m�ssen das Kennzeichen "insideTable" gesetzt haben.
     * 
     * @return RTF String
     */
    public String getRtfContent(boolean insideTable) {
        StringBuffer sb = new StringBuffer();
        // Texte extrahieren
        ListIterator it = content.listIterator();
        while (it.hasNext()) {
            sb.append(((Paragraph) it.next()).getRtfContent(insideTable));
        }
        // Absatztext zur�ckgeben
        return sb.toString(); // Inhalt hinzuf�gen
    }

}