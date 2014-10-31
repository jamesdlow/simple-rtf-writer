package com.itseasy.rtf.text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

/**
 * Fasst einzelne Absätze (Paragraph) zusammen, um diese in einem RTFTemplate als eine Einheit einzufügen. 
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
     * Definiert einen Paragraph-Container und fügt diesem bereits den ersten
     * Paragraph hinzu
     * 
     * @param content - 1. Paragraph dem Container hinzufügen
     */
    public MultiParagraphs(Paragraph content) {
        this();
        this.addParagraph(content);
    }

    /**
     * Fügt einen Paragraph dem Paragraph-Container hinzu
     * 
     * @param part - Paragraph, der hinzugefügt werden soll
     */
    public void addParagraph(Paragraph part) {
        this.content.add(part);
    }

    /**
     * Gibt alle Schriftenobjekte zurück, die an diesem MultiParagraphs (inkl.
     * TextParts) hängen. Wenn keine Schriften angehängt sind, wird "null"
     * zurückgegeben.
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
     * Gibt den RTF String zurück, welcher dieses Element mit RTF Steuerzeichen definiert.
     * 
     * @return RTF String
     */
    public String getRtfContent() {
        return this.getRtfContent(false);
    }

    /**
     * Gibt den RTF String zurück, welcher dieses Element mit RTF Steuerzeichen definiert. Absätze, die innerhalb
     * einer Tabelle eingefügt werden, müssen das Kennzeichen "insideTable" gesetzt haben.
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
        // Absatztext zurückgeben
        return sb.toString(); // Inhalt hinzufügen
    }

}