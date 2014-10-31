package com.itseasy.rtf.text;

import java.util.HashSet;
import java.util.ListIterator;
import java.util.Set;

/**
 * Erzeugt eine Aufzählungsliste. Jeder hinzugefügte <code>Text</code> wird als neuer Aufzählungspunkt in
 * einer neuen Zeile dargestellt. Als Symbol wird ein Punkt als Zeilenbeginn verwendet. Die über den 
 * Konstruktor zu setzenden Abstände, beziehen sich auf die Aufzählungsliste als Ganzes und werden nicht
 * zwischen den Aufzählungszeilen angewandt. Um den Zeilenabstand zwischen den Aufzählungszeilen zu
 * beeinflussen, kann über die Methode <code>setSpace(int)</code> der Punktabstand festgelegt werden.<br>
 * <br>
 * Folgendes Codebeispiel legt eine Aufzählungsliste mit 4 Aufzählungspunkten an. Der Abstand zwischen den
 * Aufzählungszeilen beträgt 1 Punkt. Vor der Aufzählung werden 3 Punkte Abstand eingefügt und danach 
 * nochmal 6 Punkte.<br>
 * <blockquote> 
 *    List absatz = new List(3, 6); <br>
 *    absatz.setSpace(1); 	// Default sind 0 Punkte <br>
 *    absatz.addText(new TextPart("1. Aufzählung")); <br>
 *    absatz.addText(new TextPart("2. Aufzählung")); <br>
 *    absatz.addText(new TextPart("3. Aufzählung (Funktioniert auch, wenn ein Aufzählungspunkt über mehr als eine Zeile geht...)")); <br>
 *    absatz.addText(new TextPart("4. Aufzählung")); <br>
 * </blockquote>
 *
 * @version 0.1.0 	26.06.2004
 * @author 			Stefan Finkenzeller
 */

public class List extends Paragraph {

    private int space = 0;		// Abstand zwischen den Aufzählungszeilen
    
    /**
     * Erzeugt eine Liste ohne Abstand vor und nach dem Absatzes mit Textausrichtung "Linksbündig".
     */
    public List() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Erzeugt eine Liste ohne Abstand vor und nach dem Absatzes mit Textausrichtung "Linksbündig". Zusätzlich
     * kann der Inhalt des ersten Aufzählungspunktes angegeben werden.
     * 
     * @param content - Inhalt des ersten Aufzählungspunktes
     */
    public List(Text content) {
        super(content);
        // TODO Auto-generated constructor stub
    }

    /**
     * Erzeugt eine Liste mit Textausrichtung "Linksbündig". Über Parameter kann der Abstand vor und
     * nach des Absatzes definiert werden. Zusätzlich kann der Inhalt des ersten Aufzählungspunktes angegeben werden.
     * 
     * @param spacebefore - Abstand in Punkten vor der Liste
     * @param spaceafter  - Abstand in Punkten nach der Liste
     * @param content     - Inhalt des ersten Aufzählungspunktes
     */
    public List(int spacebefore, int spaceafter, TextPart content) {
        super(spacebefore, spaceafter, content);
        // TODO Auto-generated constructor stub
    }

    /**
     * Erzeugt eine Liste mit Textausrichtung "Linksbündig". Über Parameter kann der Abstand vor und
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
     * Erzeugt eine Liste mit Textausrichtung "Linksbündig". Über Parameter kann der Abstand vor und
     * nach des Absatzes definiert werden. Zusätzlich kann der Inhalt des ersten Aufzählungspunktes angegeben werden.
     * Mit dem Parameter "font" und "fontsize" ist die Möglichkeit gegeben, die Standardschriftart und -größe
     * des Absatzes zu definieren.
     * 
     * @param spacebefore - Abstand in Punkten vor der Liste
     * @param spaceafter  - Abstand in Punkten nach der Liste
     * @param fontsize
     * @param font
     * @param content     - Inhalt des ersten Aufzählungspunktes
     */
    public List(int spacebefore, int spaceafter, int fontsize, Font font,
            TextPart content) {
        super(spacebefore, spaceafter, fontsize, font, content);
        // TODO Auto-generated constructor stub
    }

    /**
     * Legt den Abstand zwischen den Zeilen der Aufzählungspunkte fest.
     * (Default: 0 Punkte)
     * 
     * @param space	- Abstand zwischen den Zeilen der Aufzählungspunkte in Punkten
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
        // Wenn keine Schriften gesetzt waren, dann trotzdem einen HashSet anlegen für die Schrift SYMBOL
        if (fs == null) fs = new HashSet();
        // Schrift SYMBOL für Aufzählungszeichen hinzufügen
        fs.add(Font.SYMBOL);
        return (fs.size() > 0 ? fs : null);
    }

    
    /* (non-Javadoc)
     * @see com.itseasy.rtf.text.Paragraph#getContent()
     */
    protected String getContent() {
        StringBuffer sb = new StringBuffer();
        // Kennzeichen für ersten Aufzählungspunkt
        boolean firstitem = true;
        // Texte extrahieren
        ListIterator it = content.listIterator();
        while (it.hasNext()) {
            if (firstitem) {
                // Beim ersten Listeneintrag auch noch linken Rand setzen
                sb.append("{\\*\\pn\\pnlvlblt\\pnf" + Font.SYMBOL.getFontnumber() + "\\pnindent0{\\pntxtb\\'B7}}\\fi-380\\li380");
                // Zwischenraum zwischen den Aufzählungspunkten setzen; Wenn keine weiteren Einträge folgen, dann Absatzabstand einfügen
                firstitem = false;
            }
            else {
                // Aufzählungspunkt setzen für alle weiteren Inahlte
                sb.append("{\\pntext\\f" + Font.SYMBOL.getFontnumber() + "\\'B7\\tab}");
                // Zwischenraum zwischen den Aufzählungspunkten setzen; Wenn keine weiteren Einträge folgen, dann Absatzabstand einfügen
                sb.append("\\sb0");
            }
            // Inhalt des Listeneintrages, inkl. abschließendem Return (beim letzten Eintrag wird das \par weggelassen)
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
