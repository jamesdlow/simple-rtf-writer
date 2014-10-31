package com.itseasy.rtf.text;

import java.util.HashSet;
import java.util.ListIterator;
import java.util.Set;

/**
 * Erzeugt eine Aufz�hlungsliste. Jeder hinzugef�gte <code>Text</code> wird als neuer Aufz�hlungspunkt in
 * einer neuen Zeile dargestellt. Als Symbol wird ein Punkt als Zeilenbeginn verwendet. Die �ber den 
 * Konstruktor zu setzenden Abst�nde, beziehen sich auf die Aufz�hlungsliste als Ganzes und werden nicht
 * zwischen den Aufz�hlungszeilen angewandt. Um den Zeilenabstand zwischen den Aufz�hlungszeilen zu
 * beeinflussen, kann �ber die Methode <code>setSpace(int)</code> der Punktabstand festgelegt werden.<br>
 * <br>
 * Folgendes Codebeispiel legt eine Aufz�hlungsliste mit 4 Aufz�hlungspunkten an. Der Abstand zwischen den
 * Aufz�hlungszeilen betr�gt 1 Punkt. Vor der Aufz�hlung werden 3 Punkte Abstand eingef�gt und danach 
 * nochmal 6 Punkte.<br>
 * <blockquote> 
 *    List absatz = new List(3, 6); <br>
 *    absatz.setSpace(1); 	// Default sind 0 Punkte <br>
 *    absatz.addText(new TextPart("1. Aufz�hlung")); <br>
 *    absatz.addText(new TextPart("2. Aufz�hlung")); <br>
 *    absatz.addText(new TextPart("3. Aufz�hlung (Funktioniert auch, wenn ein Aufz�hlungspunkt �ber mehr als eine Zeile geht...)")); <br>
 *    absatz.addText(new TextPart("4. Aufz�hlung")); <br>
 * </blockquote>
 *
 * @version 0.1.0 	26.06.2004
 * @author 			Stefan Finkenzeller
 */

public class List extends Paragraph {

    private int space = 0;		// Abstand zwischen den Aufz�hlungszeilen
    
    /**
     * Erzeugt eine Liste ohne Abstand vor und nach dem Absatzes mit Textausrichtung "Linksb�ndig".
     */
    public List() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Erzeugt eine Liste ohne Abstand vor und nach dem Absatzes mit Textausrichtung "Linksb�ndig". Zus�tzlich
     * kann der Inhalt des ersten Aufz�hlungspunktes angegeben werden.
     * 
     * @param content - Inhalt des ersten Aufz�hlungspunktes
     */
    public List(Text content) {
        super(content);
        // TODO Auto-generated constructor stub
    }

    /**
     * Erzeugt eine Liste mit Textausrichtung "Linksb�ndig". �ber Parameter kann der Abstand vor und
     * nach des Absatzes definiert werden. Zus�tzlich kann der Inhalt des ersten Aufz�hlungspunktes angegeben werden.
     * 
     * @param spacebefore - Abstand in Punkten vor der Liste
     * @param spaceafter  - Abstand in Punkten nach der Liste
     * @param content     - Inhalt des ersten Aufz�hlungspunktes
     */
    public List(int spacebefore, int spaceafter, TextPart content) {
        super(spacebefore, spaceafter, content);
        // TODO Auto-generated constructor stub
    }

    /**
     * Erzeugt eine Liste mit Textausrichtung "Linksb�ndig". �ber Parameter kann der Abstand vor und
     * nach des Absatzes definiert werden.
     *  
     * @param spacebefore - Abstand in Punkten vor der Liste
     * @param spaceafter  - Abstand in Punkten nach der Liste
     */
    public List(int spacebefore, int spaceafter) {
        super(spacebefore, spaceafter);
        // TODO Auto-generated constructor stub
    }

    /**
     * Erzeugt eine Liste mit Textausrichtung "Linksb�ndig". �ber Parameter kann der Abstand vor und
     * nach des Absatzes definiert werden. Zus�tzlich kann der Inhalt des ersten Aufz�hlungspunktes angegeben werden.
     * Mit dem Parameter "font" und "fontsize" ist die M�glichkeit gegeben, die Standardschriftart und -gr��e
     * des Absatzes zu definieren.
     * 
     * @param spacebefore - Abstand in Punkten vor der Liste
     * @param spaceafter  - Abstand in Punkten nach der Liste
     * @param fontsize
     * @param font
     * @param content     - Inhalt des ersten Aufz�hlungspunktes
     */
    public List(int spacebefore, int spaceafter, int fontsize, Font font,
            TextPart content) {
        super(spacebefore, spaceafter, fontsize, font, content);
        // TODO Auto-generated constructor stub
    }

    /**
     * Legt den Abstand zwischen den Zeilen der Aufz�hlungspunkte fest.
     * (Default: 0 Punkte)
     * 
     * @param space	- Abstand zwischen den Zeilen der Aufz�hlungspunkte in Punkten
     */
    public void setSpace(int space) {
        this.space = space;
    }
    
    /* (non-Javadoc)
     * @see com.itseasy.rtf.text.Paragraph#getAllFonts()
     */
    public Set getAllFonts() {
        // Alle Schriften lesen
        Set fs = super.getAllFonts();
        // Wenn keine Schriften gesetzt waren, dann trotzdem einen HashSet anlegen f�r die Schrift SYMBOL
        if (fs == null) fs = new HashSet();
        // Schrift SYMBOL f�r Aufz�hlungszeichen hinzuf�gen
        fs.add(Font.SYMBOL);
        return (fs.size() > 0 ? fs : null);
    }

    
    /* (non-Javadoc)
     * @see com.itseasy.rtf.text.Paragraph#getContent()
     */
    protected String getContent() {
        StringBuffer sb = new StringBuffer();
        // Kennzeichen f�r ersten Aufz�hlungspunkt
        boolean firstitem = true;
        // Texte extrahieren
        ListIterator it = content.listIterator();
        while (it.hasNext()) {
            if (firstitem) {
                // Beim ersten Listeneintrag auch noch linken Rand setzen
                sb.append("{\\*\\pn\\pnlvlblt\\pnf" + Font.SYMBOL.getFontnumber() + "\\pnindent0{\\pntxtb\\'B7}}\\fi-380\\li380");
                // Zwischenraum zwischen den Aufz�hlungspunkten setzen; Wenn keine weiteren Eintr�ge folgen, dann Absatzabstand einf�gen
                firstitem = false;
            }
            else {
                // Aufz�hlungspunkt setzen f�r alle weiteren Inahlte
                sb.append("{\\pntext\\f" + Font.SYMBOL.getFontnumber() + "\\'B7\\tab}");
                // Zwischenraum zwischen den Aufz�hlungspunkten setzen; Wenn keine weiteren Eintr�ge folgen, dann Absatzabstand einf�gen
                sb.append("\\sb0");
            }
            // Inhalt des Listeneintrages, inkl. abschlie�endem Return (beim letzten Eintrag wird das \par weggelassen)
            String itemcontent = ((Text) it.next()).getRtfContent();
            sb.append("\\sa" + ((it.hasNext() ? this.space : this.getSpaceAfter()) * 10 * 2) );
            sb.append(itemcontent);
            if(it.hasNext()) sb.append("\\par ");
        }
        return sb.toString();
    }

    public String getRtfContent(boolean insideTable) {
        return "{" + super.getRtfContent(insideTable) + "}";
    }
}
