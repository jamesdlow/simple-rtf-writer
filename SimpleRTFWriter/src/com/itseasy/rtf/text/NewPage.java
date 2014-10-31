package com.itseasy.rtf.text;

/**
 * NewPage - Repräsentiert eine neue Seite. Wenn man einem RTF Dokument ein NewPage-Objekt hinzufügt, dann 
 * wird daraus ein Seitenvorschub generiert. <br><br>
 * <code> 
 *   RTFDocument doc = new RTFDocument();<br>
 *   doc.addParagraph(new NewPage());<br>
 * </code> 
 *
 * @version 0.1.0 	24.08.2004
 * @author 			Stefan Finkenzeller
 */
public class NewPage extends Paragraph {

    /* (non-Javadoc)
     * @see com.itseasy.rtf.text.Paragraph#getRtfContent()
     */
    public String getRtfContent() {
        // Absatztext zurückgeben
        return "\\page";
    }
}
