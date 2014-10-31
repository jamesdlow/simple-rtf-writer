package com.itseasy.rtf.text;

import com.itseasy.rtf.RtfUtil;

/**
 * Definiert einen Tabulator für einen Absatz (Paragraph). Ein Absatz wird zum einen durch die Position vom 
 * linken Rand beschrieben und der Textausrichtung, die der Text an dieser Stelle annehmen soll. Unterstützt
 * werden linksbündig (TABKIND_LEFT), rechtbündig (TABKIND_RIGHT), Dezimal-Ausrichtung (TABKIND_DECIMAL)
 * und zentriert (TABKIND_CENTER). Zusätzlich lässt sich mit angeben, ob der Tabulatorzwischenraum durch 
 * Punkte (TABLEAD_DOTS) oder mit Unterstrichen (TABLEAD_UNDERLINE) aufgefüllt werden soll.<br><br>
 * Folgender Codeauschnitt definiert vier Tabulatoren mit entsprechender Ausrichtung:<br>
 * <code>
 *  Paragraph absatz = new Paragraph(0, 6);
 *  // 1. Tabulator; 1 cm; Ausrichtung: Links
 *  absatz.addTabulator(new Tabulator(10, Tabulator.TABKIND_LEFT));		
 *  // 2. Tabulator; 5 cm; Ausrichtung: Center
 *  absatz.addTabulator(new Tabulator(50, Tabulator.TABKIND_CENTER));	
 *  // 3. Tabulator; 10 cm; Ausrichtung: Dezimal
 *  absatz.addTabulator(new Tabulator(100, Tabulator.TABKIND_DECIMAL));	
 *  // 4. Tabulator; Rechter Rand; Ausrichtung: Rechts
 *  absatz.addTabulator(new Tabulator(doc.getPageDefinition().getVisibleLineSize(), Tabulator.TABKIND_RIGHT));	
 * </code>  
 *
 * @version 0.1.0 	04.08.2004
 * @author 			Stefan Finkenzeller
 */
public class Tabulator {
    /**
     * Tabulatorausrichtung "Linksbündig"
     */
    public static final int TABKIND_LEFT = 0;
    /**
     * Tabulatorausrichtung "Rechtsbündig"
     */
    public static final int TABKIND_RIGHT = 1;
    /**
     * Tabulatorausrichtung "Zentriert"
     */
    public static final int TABKIND_CENTER = 2;
    /**
     * Tabulatorausrichtung "Dezimal" (Ausrichtung am Dezimalkomma)
     */
    public static final int TABKIND_DECIMAL = 3;
    /**
     * Tabulatorzwischenraum nicht mit einem Zeichen auffüllen (Standard)
     */
    public static final int TABLEAD_NOTHING = 0;
    /**
     * Tabulatorzwischenraum mit Punkten auffüllen
     */
    public static final int TABLEAD_DOTS = 1;
    /**
     * Tabulatorzwischenraum mit Unterstrichen auffüllen
     */
    public static final int TABLEAD_UNDERLINE = 2;
    
    // Tabulator Variablen
    private int tabpos = 0;					// Position des Tabulators in Twips vom linken Rand aus
    private int tabkind = TABKIND_LEFT; 	// Art des Tabulators 
    private int tablead = TABLEAD_NOTHING;	// Füllzeichen des Tabulators
    
    /**
     * Definiert einen einfachen Tabulator (Ausrichtung links).
     * 
     * @param position - Position des Tabulators in Millimetern
     */
    public Tabulator(double position) {
        super();
        this.tabpos = RtfUtil.getTwipFromMillimeter(position);
    }

    /**
     * Definiert einen Tabulator bei dem die Ausrichtung angegeben werden kann.
     * 
     * @param position - Position des Tabulators in Millimetern
     * @param tabkind  - Art und Ausrichtung des Tabulators
     */
    public Tabulator(double position, int tabkind) {
        this(position);
        this.tabkind = tabkind;
    }
    
    /**
     * Definiert einen Tabulator bei dem die Ausrichtung angegeben werden kann. Zusätzlich lässt sich noch 
     * definieren, mit welchem Zeichen der Tabulatorzwischenraum ausgefüllt wird (Punkte oder Striche). Dies
     * wird vor allem in Inhaltsverzeichnissen verwendet 
     * 
     * @param position - Position des Tabulators in Millimetern
     * @param tabkind  - Art und Ausrichtung des Tabulators (TABKIND_*)
     * @param tablead  - Art, wie der Tabulator aufgefüllt werden soll (TABLEAD_*)
     */
    public Tabulator(double position, int tabkind, int tablead) {
        this(position, tabkind);
        this.tablead = tablead;
    }
    
    /**
     * Gibt die RTF Definition dieses Tabulators zurück.
     * 
     * @return	RTF-konformer String
     */
    public String getRtfContent() {
        StringBuffer format = new StringBuffer();
        // Art des Tabulators
        switch(tabkind) {
        	case TABKIND_LEFT: break;
        	case TABKIND_RIGHT: format.append("\\tqr"); break;
        	case TABKIND_CENTER: format.append("\\tqc"); break;
        	case TABKIND_DECIMAL: format.append("\\tqdec"); break;
        }
        // Füllzeichen des Tabulators
        switch(tablead) {
    		case TABLEAD_NOTHING: break;
    		case TABLEAD_DOTS: format.append("\\tldot"); break;
    		case TABLEAD_UNDERLINE: format.append("\\tlul"); break;
        }
        // Abstand vom linken Rand
        format.append("\\tx" + tabpos);
        return format.toString();		
    }

}
