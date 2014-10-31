package com.itseasy.rtf.text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;


/**
 * Stellt den Inhalt eines RTF Dokumentes dar und beschreibt einen Absatz. Ein Absatz besteht
 * wiederrum aus TextParts, die den eigentlichen Text beinhalten. Bei einem Absatz kann man angeben, welcher
 * Abstand vor dem Absatz und danach freigehalten werden soll (Ma�einheit: Punkte). Ebenso definiert ein 
 * Absatz die grunds�tzliche Textausrichtung (Linksb�ndig, Rechtsb�ndig, Zentriert, Blocksatz). Tabulatordefinitionen
 * werden �ber <code>addTabulator(Tabulator)</code> dem Absatz hinzugef�gt.
 *
 * @version 0.1.0 	22.05.2004
 * @author 			Stefan Finkenzeller
 */

public class Paragraph /* implements Text */{
    /**
     * Der Absatztext soll zentriert dargestellt werden
     */
    public static final String ALIGN_CENTER = "qc";
    /**
     * Der Absatztext soll als Blocksatz dargestellt werden
     */
    public static final String ALIGN_JUSTIFIED = "qj";
    /**
     * Der Absatztext soll linksb�ndig dargestellt werden
     */
    public static final String ALIGN_LEFT = "ql";
    /**
     * Der Absatztext soll rechtsb�ndig dargestellt werden
     */
    public static final String ALIGN_RIGHT = "qr";
    
    // Variablen
    List content;				// Inhalt sind Objekte vom Typ TextPart
    List tabulator;				// Tabulatordefinitionen 
    Border border;				// Rahmen f�r diesen Absatz
    private String align;
    private boolean newpage = false;	// Kennzeichen, ob vor dem Absatz eine neue Seite begonnen werden soll
    private int spacebefore = 0;		// Punktabstand vor dem Absatz 
    private int spaceafter = 0;			// Punktabstand nach dem Absatz
    private Font font;					// Schriftart
    private int fontsize = 0;			// Default = 12
    
    
    /**
     * Erzeugt einen neuen Absatz mit Textausrichtung "Linksb�ndig".
     */
    public Paragraph() {
        this.content = new ArrayList();
        this.tabulator = new ArrayList();
        this.align = ALIGN_LEFT;
    }

    /**
     * Erzeugt einen neuen Absatz mit Textausrichtung "Linksb�ndig". Es kann direkt ein Text-Objekt mit Inhalt 
     * dem Absatz hinzugef�gt werden.
     * 
     * @param content - Text-Objekt mit Inhalt
     */
    public Paragraph(Text content) {
        this();
        this.addText(content);
    }
    
    /**
     * Erzeugt einen neuen Absatz mit Textausrichtung "Linksb�ndig". Es kann direkt ein Text-Objekt mit Inhalt 
     * dem Absatz hinzugef�gt werden. Zus�tzlich l�sst sich auch der Abstand vor und nach dem Absatz definieren.
     * 
     * @param spacebefore - Abstand vor dem Absatz in Punkten
     * @param spaceafter  - Abstand nach dem Absatz in Punkten
     * @param content     - Text-Objekt mit Inhalt
     */
    public Paragraph(int spacebefore, int spaceafter, TextPart content) {
        this();
        this.addText(content);
        this.spacebefore = spacebefore;
        this.spaceafter = spaceafter;
    }

    /**
     * Erzeugt einen neuen Absatz mit Textausrichtung "Linksb�ndig". Es kann direkt der Abstand vor 
     * und nach dem Absatz definiert werden.
     * 
     * @param spacebefore - Abstand vor dem Absatz in Punkten
     * @param spaceafter  - Abstand nach dem Absatz in Punkten
     */
    public Paragraph(int spacebefore, int spaceafter) {
        this();
        this.spacebefore = spacebefore;
        this.spaceafter = spaceafter;
    }

    /**
     * Erzeugt einen neuen Absatz mit Textausrichtung "Linksb�ndig". Es kann direkt der Abstand vor 
     * und nach dem Absatz definiert werden. Zus�tzlich l�sst sich auch die Defaultschriftart/-gr��e 
     * des Absatzes angeben. Diese zieht immer dann, wenn ein TextPart ohne Schriftart an diesem 
     * Absatz h�ngt. 
     * 
     * @param spacebefore - Abstand vor dem Absatz in Punkten
     * @param spaceafter  - Abstand nach dem Absatz in Punkten
     * @param fontsize    - Schriftgr��e in Punkten
     * @param font		  - Standardschriftart dieses Absatzes (Font Objekt)
     */
    public Paragraph(int spacebefore, int spaceafter, int fontsize, Font font) {
        this();
        this.spacebefore = spacebefore;
        this.spaceafter = spaceafter;
        this.fontsize = fontsize;
        this.font = font;
    }

    /**
     * Erzeugt einen neuen Absatz mit Textausrichtung "Linksb�ndig". Es kann direkt der Abstand vor 
     * und nach dem Absatz definiert werden. Zus�tzlich l�sst sich auch die Defaultschriftart/-gr��e 
     * des Absatzes angeben. Diese zieht immer dann, wenn ein TextPart ohne Schriftart an diesem 
     * Absatz h�ngt. Es kann direkt ein Text-Objekt mit Inhalt dem Absatz hinzugef�gt werden.
     *  
     * @param spacebefore - Abstand vor dem Absatz in Punkten
     * @param spaceafter  - Abstand nach dem Absatz in Punkten
     * @param fontsize    - Schriftgr��e in Punkten
     * @param font		  - Standardschriftart dieses Absatzes (Font Objekt)
     * @param content     - Text-Objekt mit Inhalt
     */
    public Paragraph(int spacebefore, int spaceafter, int fontsize, Font font, TextPart content) {
        this(spacebefore, spaceafter, content);
        this.fontsize = fontsize;
        this.font = font;
    }

    /**
     * Setzt den Abstand vor dem Absatz. Ma�einheit in Punkten
     * 
     * @param points - Abstand in Punkten
     */
    public void setSapceBefore(int points) {
         this.spacebefore = points;
    }

    /**
     * Gibt den Abstand in Punkten zur�ck, der vor dem Absatz gesetzt ist.
     * 
     * @return Abstand in Punkten
     */
    public int getSpaceBefore() {
        return this.spacebefore;
    }

    /**
     * Setzt den Abstand nach dem Absatz. Ma�einheit in Punkten
     * 
     * @param points - Abstand in Punkten
     */
    public void setSapceAfter(int points) {
        this.spaceafter = points;
    }

    /**
     * Gibt den Abstand in Punkten zur�ck, der nach dem Absatz gesetzt ist.
     * 
     * @return Abstand in Punkten
     */
    public int getSpaceAfter() {
       return this.spaceafter;
    }

    /**
     * Neue Seite vor dem Absatz einf�gen
     * 
     * @param value - true = neue Seite einf�gen
     */
    public void setNewPageBefore(boolean value) {
        this.newpage = value;
    }
    
    /** 
     * Setzt eine neue Ausrichtung f�r den Absatz
     * 
     * @param value	ALIGN_LEFT, ALIGN_RIGHT, ALIGN_CENTER oder ALIGN_JUSTIFIED
     */
    public void setAlignment(String value) {
        this.align = value;
    }
    
    /**
     * Gibt die Standardschriftgr��e des Absatzes zur�ck
     * 
     * @return Standardschriftgr��e
     */
    public int getFontsize() {
        return this.fontsize;
    }

    /**
     * Setzt die Standardschriftgr��e dieses Absatzes
     * 
     * @param size - Standardschriftgr��e in Punkten
     */
    public void setFontsize(int size) {
        this.fontsize = size;
    }

    /**
     * Gibt die Standardschriftart des Absatzes zur�ck
     * 
     * @return Standardschriftart
     */
    public Font getFont() {
        return this.font;
    }

    /**
     * Setzt die Standardschriftart des Absatzes
     * 
     * @param font - Standardschriftart
     */
    public void setFont(Font font) {
        this.font = font;
    }
    
    /**
     * Gibt alle Schriftenobjekte zur�ck, die an diesem Absatz (inkl. TextParts) h�ngen.
     * Wenn keine Schriften angeh�ngt sind, wird "null" zur�ckgegeben.
     * 
     * @return	- Set mit allen Fonts
     */
    public Set getAllFonts() {
        Set fs = new HashSet();
        // Zuerst wird der Font des Absatzes angef�gt
        if (getFont() != null) fs.add(getFont());
        ListIterator it = content.listIterator();
        while (it.hasNext()) {
            Set f = ((Text) it.next()).getAllFonts();
            if (f != null) fs.addAll(f);
        }
        
        return (fs.size() > 0 ? fs : null);
    }

    /**
     * F�gt einen Text dem Absatz hinzu. Dies kann mehrmals hintereinander aufgerufen werden.
     * 
     * @param part - Text-Objekt mit Inhalt
     */
    public void addText(Text part) {
        this.content.add(part);
    }
    
    /**
     * F�gt eine Tabulatordefinition ein
     * 
     * @param tab	- Neuer Tabulator
     */
    public void addTabulator(Tabulator tab) {
        this.tabulator.add(tab);
    }
    
    /**
     * Setzt einen Rahmen f�r diesen Absatz
     * 
     * @param border	- Neuer Rahmen
     */
    public void setBorder(Border border) {
        this.border = border;
    }
    
    /**
     * Sammelt den Inhalt aller angeh�ngten Text-Objekte zusammen und gibt diesen als RTF-konformen String
     * zur�ck
     * 
     * @return RTF-konformer String mit dem konsolidierten Inhalt aller Text-Objekte
     */
    protected String getContent() {
        StringBuffer sb = new StringBuffer();
        // Texte extrahieren
        ListIterator it = content.listIterator();
        while (it.hasNext()) {
            sb.append(((Text) it.next()).getRtfContent());
        }
        return sb.toString();
    }

    /**
     * Gibt die Tabulatordefinition dieses Absatzes als RTF String zur�ck
     * 
     * @return RTF-konformer String mit der Tabulatordefinition dieses Absatzes
     */
    protected String getTabulatorDefinitions() {
        StringBuffer sb = new StringBuffer();
        // Texte extrahieren
        ListIterator it = tabulator.listIterator();
        while (it.hasNext()) {
            sb.append(((Tabulator) it.next()).getRtfContent());
        }
        return sb.toString();
    }

    /**
     * Gibt den RTF String zur�ck, welcher dieses Element mit RTF Steuerzeichen definiert.
     * 
     * @return RTF String
     */
    public String getRtfContent() {
        return getRtfContent(false);
    }
    
    public String getRtfContent(boolean insideTable) {
        // Absatztext zur�ckgeben
        return "\\pard\\plain"			// Beginn eines neuen Absatz (Reset der Formatierung)
        	 + (insideTable ? "\\intbl" : "")	// Kennzeichen, dass sich der Absatz innerhalb einer Tabelle befindet
        	 + "\\" + align + "\\faauto"
        	 + (newpage ? "\\pagebb" : "")	// Neue Seite vor dem Absatz einf�gen
        	 + (spacebefore > 0 ? "\\sb" + (spacebefore * 10 * 2) : "")
        	 + (spaceafter > 0 ? "\\sa" + (spaceafter * 10 * 2) : "")
        	 + (border != null ? border.getRtfContent() : "")
    		 + getTabulatorDefinitions() // Tabulatordefinitionen
			 + (font != null ? "\\f" + font.getFontnumber() : "")
    		 + (fontsize > 0 ? "\\fs" + (fontsize * 2) : "")
        	 + getContent()				// Inhalt hinzuf�gen
        	 + "\\par";					// Absatzende, neue Zeile
    }

//    public String getRtfContent() {
//        StringBuffer sb = new StringBuffer();
//        // Texte extrahieren
//        ListIterator it = content.listIterator();
//        while (it.hasNext()) {
//            sb.append(((Text) it.next()).getRtfContent());
//        }
//        // Absatztext zur�ckgeben
//        return "\\pard\\plain"			// Beginn eines neuen Absatz (Reset der Formatierung)
//        	 + "\\" + align + "\\faauto"
//        	 + (newpage ? "\\pagebb" : "")	// Neue Seite vor dem Absatz einf�gen
//        	 + (spacebefore > 0 ? "\\sb" + (spacebefore * 10 * 2) : "")
//        	 + (spaceafter > 0 ? "\\sa" + (spaceafter * 10 * 2) : "")
//			 + (font != null ? "\\f" + font.getFontnumber() : "")
//    		 + (fontsize > 0 ? "\\fs" + (fontsize * 2) : "")
//        	 + sb.toString()			// Inhalt hinzuf�gen
//        	 + "\\par";					// Absatzende, neue Zeile
//    }
}
