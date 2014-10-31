package com.itseasy.rtf.text;

import java.util.Set;

/**
 * Fasst alle Objekte zusammen, die RTF-Content beschreiben und in einen Paragraph eingef�gt werden k�nnen.
 *
 * 0.6.0	22.05.2004	S. Finkenzeller		First Version
 * 
 * @version 0.5.0 	22.05.2004
 * @author 			Stefan Finkenzeller
 */

public interface Text {
    /**
     * Gibt den RTF String zur�ck, welcher dieses Element mit RTF Steuerzeichen definiert.
     * 
     * @return RTF String
     */
    public String getRtfContent();

    /**
     * Gibt ein Set mit allen Fonts zur�ck, die innerhalb dieses Text-Objektes verwendet werden.
     * Wenn keine Schriften angeh�ngt sind, wird "null" zur�ckgegeben.
     *  
     * @return Set mit allen enthaltenen Fonts
     */
    public Set getAllFonts();
}
