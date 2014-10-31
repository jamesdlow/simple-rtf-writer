package com.itseasy.rtf.text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

/**
 * Fasst einzelne TextParts zu einem Textabschnitt zusammen. Im Gegensatz zum 
 * Paragraph endet ein MultiTextParts nicht mit einem Zeilenumbruch und es können auch keine
 * Absatzformatierungen vorgenommen werden.
 *
 * @version 0.1.0 	22.05.2004
 * @author 			Stefan Finkenzeller
 */

public class MultiTextParts implements Text {
    // Variablen
    private List content;				// Inhalt sind Objekte vom Typ TextPart

    /**
     * Definiert einen leeren TextPart-Container
     */
    public MultiTextParts() {
        this.content = new ArrayList();
    }

    /**
     * Definiert einen TextPart-Container und fügt diesem bereits den ersten TextPart hinzu
     * 
     * @param content - 1. Textpart dem Container hinzufügen
     */
    public MultiTextParts(TextPart content) {
        this();
        this.addText(content);
    }

    /**
     * Fügt einen TextPart dem TextPart-Container hinzu
     * 
     * @param part - TextPart, der hinzugefügt werden soll
     */
    public void addText(TextPart part) {
        this.content.add(part);
    }
    
    /**
     * Gibt alle Schriftenobjekte zurück, die an diesem MultiTextParts (inkl. TextParts) hängen.
     * Wenn keine Schriften angehängt sind, wird "null" zurückgegeben.
     * 
     * @return	- Set mit allen Fonts
     */
    public Set getAllFonts() {
        Set fs = new HashSet();
        ListIterator it = content.listIterator();
        while (it.hasNext()) {
            Set f = ((TextPart) it.next()).getAllFonts();		
            if (f != null) fs.add(f);
        }
        
        return (fs.size() > 0 ? fs : null);
    }

    
    /* (non-Javadoc)
     * @see com.itseasy.rtf.text.Text#getRtfContent()
     */
    public String getRtfContent() {
        StringBuffer sb = new StringBuffer();
        // Texte extrahieren
        ListIterator it = content.listIterator();
        while (it.hasNext()) {
            sb.append(((TextPart) it.next()).getRtfContent());
        }
        // Absatztext zurückgeben
        return sb.toString();			// Inhalt hinzufügen
    }

}
