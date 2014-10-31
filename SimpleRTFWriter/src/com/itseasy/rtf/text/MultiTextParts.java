package com.itseasy.rtf.text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

/**
 * Fasst einzelne TextParts zu einem Textabschnitt zusammen. Im Gegensatz zum 
 * Paragraph endet ein MultiTextParts nicht mit einem Zeilenumbruch und es k�nnen auch keine
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
     * Definiert einen TextPart-Container und f�gt diesem bereits den ersten TextPart hinzu
     * 
     * @param content - 1. Textpart dem Container hinzuf�gen
     */
    public MultiTextParts(TextPart content) {
        this();
        this.addText(content);
    }

    /**
     * F�gt einen TextPart dem TextPart-Container hinzu
     * 
     * @param part - TextPart, der hinzugef�gt werden soll
     */
    public void addText(TextPart part) {
        this.content.add(part);
    }
    
    /**
     * Gibt alle Schriftenobjekte zur�ck, die an diesem MultiTextParts (inkl. TextParts) h�ngen.
     * Wenn keine Schriften angeh�ngt sind, wird "null" zur�ckgegeben.
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
        // Absatztext zur�ckgeben
        return sb.toString();			// Inhalt hinzuf�gen
    }

}
