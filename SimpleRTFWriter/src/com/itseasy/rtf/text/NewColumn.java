package com.itseasy.rtf.text;

/**
 * NewColumn - Repr�sentiert eine neue Seite. Wenn man einem RTF Dokument ein NewPage-Objekt hinzuf�gt, dann 
 * wird daraus ein Seitenvorschub generiert. <br><br>
 * <code> 
 *   RTFDocument doc = new RTFDocument();<br>
 *   doc.addParagraph(new NewColumn());<br>
 * </code> 
 *
 * @version 0.1.0 	31.05.2008
 * @author 			James Low
 */
public class NewColumn extends Paragraph {

    /* (non-Javadoc)
     * @see com.itseasy.rtf.text.Paragraph#getRtfContent()
     */
    public String getRtfContent() {
        // Absatztext zur�ckgeben
        return "\\sbkcol";
    }
}
