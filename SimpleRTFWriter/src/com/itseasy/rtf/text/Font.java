
package com.itseasy.rtf.text;

/**
 * Repräsentiert eine Schriftart f[r den Simple RTF Writer
 * 
 * @version 0.1.0 	22.05.2004
 * @author Stefan Finkenzeller
 */
public class Font {
    /**
     * Schriftfamilie: "Roman", z.B. Times New Roman, Palatino
     */
    public static final String FAMILY_ROMAN = "froman";
    /**
     * Schriftfamilie: "Swiss", z.B. Arial
     */
    public static final String FAMILY_SWISS = "fswiss";
    /**
     * Schriftfamilie: "Modern", z.B. Courier New, Pica
     */
    public static final String FAMILY_MODERN = "fmodern";
    /**
     * Schriftfamilie: "Script", z.B. Cursive
     */
    public static final String FAMILY_SCRIPT = "fscript";
    /**
     * Schriftfamilie: "Decorative Schrift", z.B. Old English, ITC Zapf Chancery
     */
    public static final String FAMILY_DECOR = "fdecor";
    /**
     * Schriftfamilie: "Technik", z.B. Symbol
     */
    public static final String FAMILY_TECH = "ftech";
    /**
     * Schriftfamilie: "Bidirectionale Schriften", z.B. Miriam
     */
    public static final String FAMILY_BIDI = "fbidi";
    /**
     * Schriftfamilie: "Unbekannt"
     */
    public static final String FAMILY_NIL = "fnil";
    
    
    /**
     * Vordefinierte Schriftart "Times New Roman"
     */
    public static final Font TIMES_NEW_ROMAN = new Font("Times New Roman", FAMILY_ROMAN);
    /**
     * Vordefinierte Schriftart "Arial"
     */
    public static final Font ARIAL = new Font("Arial", FAMILY_SWISS);
    /**
     * Vordefinierte Schriftart "Courier New"
     */
    public static final Font COURIER_NEW = new Font("Courier New", FAMILY_MODERN);
    /**
     * Vordefinierte Schriftart "Symbol" mit Charset 2
     */
    public static final Font SYMBOL = new Font("Symbol", FAMILY_NIL, 2);
    
    
    // Statische Variablen - Fontzähler
    private static int fontcount;
    // Variablen
    private String name;
    private String family;
    private int fontnumber;
    private int charset;
    
    /**
     * Erzeugt eine neue Schrift. Es kann dabei der Schriftname und die Schriftfamilie angegeben werden. 
     * Als Charset wird "0" implizit gesetzt. Die Textverarbeitung versucht eine RTF Schift zuerst durch
     * ihren Namen zu identifizieren. Ist dort keine entsprechnede Schrift verfügbar, wird eine ähnliche
     * aufgrund ihrer Schriftfamilie von der Textverarbeitung ausgesucht.
     * 
     * @param name	  - Name der Schrift
     * @param familie - Schriftfamilie
     */
    public Font(String name, String familie) {
        this(name, familie, 0);
    }
    
    /**
     * Erzeugt eine neue Schrift mit allen Parametern.
     * 
     * @param name	  - Name der Schrift
     * @param familie - Schriftfamilie
     * @param charset - Charset der Schrift
     */
    private Font(String name, String familie, int charset) {
        this.fontnumber = getFontcount();
        this.name = name;
        this.family = familie;
        this.charset = charset;
    }

    /**
     * Gibt den Namen der Schrift zurück
     * 
     * @return Schriftname
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Gibt die Schriftnummer dieser Schriftart zurück
     * 
     * @return Schriftnummer der Schrift
     */
    public int getFontnumber() {
        return this.fontnumber;
    }
    
//    protected void setFontnumber(int newfontnr) {
//        this.fontnumber = newfontnr;
//    }
    
    /**
     * Gibt die RTF Definition dieser Schriftart zurück
     * 
     * @return RTF-konformer String
     */
    public String getRtfContent() {
        return "\\f" + fontnumber		// Schriftnummer 
        	 + "\\" + family			// Schriftfamilie
        	 + "\\charset" + charset	// Charset = ANSI
        	 + " " + name + ";";
    }    
    
    /**
     * Gibt die nächste freie Schriftnummer zurück. Mittels Schriftennummer wird eine Schrift innerhalb
     * eines RTF Dokumentes referenziert. Desshalb ist es wichtig, das keine Überscheidungen entstehen.
     * Alle SRW-Schriften haben Schriften ab der Nummer 200.
     * 
     * @return Nächste freie Schriftennummer
     */
    private static int getFontcount() {
        return 200 + fontcount++;
    }
}
