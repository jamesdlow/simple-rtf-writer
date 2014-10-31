package com.itseasy.rtf.text;

import com.itseasy.rtf.RtfUtil;

/**
 * Definiert die Seitenmaße eines RTFDocuments. Neben der Seitengröße werden auch die Seitenränder
 * gespeichert. Über die Methode <code>getVisibleLineSize()</code> erhält man die Größe des 
 * Schreibbereichs einer Zeile.  
 * 
 * @version 0.2.0 	31.05.2008
 * @author 			James Low
 */
public class PageDefinition {
    // Variablen
    private PageSize papersize;
    private int margright;
    private int margleft;
    private int margtop;
    private int margbottom;
    private int columns;
    private int margcolumns;
    
    /**
     * Erzeugt eine Seitendefinition in Default (Größe = DIN_A4, Seitenränder = 2 cm)
     */
    public PageDefinition() {
        this(PageSize.DIN_A4, 20, 20, 20, 20);
    }
    
    /**
     * Erzeugt eine Seitendefinition mit der Papiergröße und den Seitenrändern (in Millimeter).
     * 
     * @param ps 			- Papiergröße
     * @param marginleft	- Seitenrand links
     * @param marginright	- Seitenrand rechts
     * @param margintop		- Seitenrand oben
     * @param marginbottom	- Seitenrand unten
     */
    public PageDefinition(PageSize ps, double marginleft, double marginright, double margintop, double marginbottom) {
    	this(ps, marginleft, marginright, margintop, marginbottom, 1, 20);
    }
    
    /**
     * Erzeugt eine Seitendefinition mit der Papiergröße und den Seitenrändern (in Millimeter).
     * 
     * @param ps 			- Papiergröße
     * @param marginleft	- Seitenrand links
     * @param marginright	- Seitenrand rechts
     * @param margintop		- Seitenrand oben
     * @param marginbottom	- Seitenrand unten
     * @param columns		- Number columns
     * @param margincolumns	- Seitenrand columns
     */
    public PageDefinition(PageSize ps, double marginleft, double marginright, double margintop, double marginbottom, int columns, double margincolumns) {
        this.papersize = ps;
        this.margleft = RtfUtil.getTwipFromMillimeter(marginleft);
        this.margright = RtfUtil.getTwipFromMillimeter(marginright);
        this.margtop = RtfUtil.getTwipFromMillimeter(margintop);
        this.margbottom = RtfUtil.getTwipFromMillimeter(marginbottom);
        this.columns = columns;
        this.margcolumns = RtfUtil.getTwipFromMillimeter(margincolumns);
    }

    /**
     * Gibt den RTF String zurück, welcher dieses Element mit RTF Steuerzeichen definiert.
     * 
     * @return RTF String
     */
    public String getRtfContent() {
        return "\\paperw" + papersize.getWidth() + "\\paperh" + papersize.getHeight() 
		+ "\\margl" + margleft + "\\margr" + margright + "\\margt" + margtop + "\\margb" + margbottom
		+ "\\cols" + columns + "\\colsx"+ margcolumns;
    }
    
    /**
     * Gibt die Größe des möglichen Schreibbereiches einer Zeile in Millimetern zurück.
     * 
     * @return - Schreibbare Zeilenbreite in Millimetern
     */
    public double getVisibleLineSize() {
        return RtfUtil.getMillimeterFromTwip(papersize.getWidth() - margleft - margright);
    }
}
